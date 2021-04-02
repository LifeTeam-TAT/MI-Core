package org.ace.insurance.web.manage.life.approval;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.product.Product;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "EndorsementLifeApprovalActionBean")
public class EndorsementLifeApprovalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private ProposalInsuredPerson proposalInsuredPerson;
	private String remark;
	private User responsiblePerson;
	private boolean approved;
	private boolean allApproved;
	// private boolean isEndorse;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSportMan;
	private boolean isSnakeBite;
	private boolean isShortTermEndowment;
	private boolean isEndownmentLife;
	private boolean isGroupLife;
	private LifeEndorseInfo lifeEndorseInfo;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		isPersonalAccident = (KeyFactorChecker.isPersonalAccident(product));
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSportMan = KeyFactorChecker.isSportMan(product);
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isShortTermEndowment = KeyFactorChecker.isShortTermEndowment(product.getId());
		isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		lifeEndorseInfo = lifeEndorsementService.findLastLifeEndorseInfoByPolicyId(lifeProposal.getLifePolicy().getId());
		checkUserAuthority(product);
		loadAllApproved();
	}

	private void checkUserAuthority(Product product) {
		String productId = product.getId();
		double userAuthorityAmount = getAuthorityAmount(user.getAuthorityPermissionList(), productId, TransactionType.ENDORSEMENT);
		double sumInsured = lifeProposal.getTotalSumInsured();
		approved = userAuthorityAmount >= sumInsured;
	}

	public void selectUser() {
		WorkflowTask workflowTask = !approved ? WorkflowTask.APPROVAL : WorkflowTask.CONFIRMATION;
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT
				: isFarmer ? WorkFlowType.FARMER : isShortTermEndowment ? WorkFlowType.SHORT_ENDOWMENT : isSnakeBite ? WorkFlowType.SNAKE_BITE : WorkFlowType.LIFE;
		selectUser(workflowTask, workFlowType, TransactionType.ENDORSEMENT, approved ? user.getLoginBranch().getId() : null, approved ? null : user.getLoginBranch().getId());
	}

	public void changeBooleanApprove(AjaxBehaviorEvent event) {
		loadAllApproved();
	}

	private void loadAllApproved() {
		this.allApproved = true;
		for (ProposalInsuredPerson p : lifeProposal.getProposalInsuredPersonList()) {
			if (!p.isApproved()) {
				this.allApproved = false;
				break;
			}
		}
	}

	public void handleManyBooleanCheckBox() {
		for (ProposalInsuredPerson insuPerson : lifeProposal.getProposalInsuredPersonList()) {
			insuPerson.setApproved(allApproved);
		}
	}

	public String addNewLifeApproval() {
		String result = null;
		try {
			WorkflowTask workflowTask = WorkflowTask.INFORM;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isSnakeBite ? ReferenceType.SNAKE_BITE
									: isShortTermEndowment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											: isGroupLife ? ReferenceType.GROUP_LIFE : isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : ReferenceType.SPORT_MAN;
			WorkFlowDTO workFlowDTO = null;

			workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.ENDORSEMENT, user,
					responsiblePerson);
			lifeEndorsementService.approveLifeProposal(lifeProposal, workFlowDTO);

			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.APPROVAL_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public String redriectLifeApproval() {
		String result = null;
		try {
			WorkflowTask workflowTask = WorkflowTask.APPROVAL;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isSnakeBite ? ReferenceType.SNAKE_BITE
									: isShortTermEndowment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											: isGroupLife ? ReferenceType.GROUP_LIFE : isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : ReferenceType.SPORT_MAN;
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.ENDORSEMENT,
					user, responsiblePerson);
			workFlowService.updateWorkFlow(workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.REDIRECT_PROCESS_SUCCESS);
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public ProposalInsuredPerson getProposalInsuredperson() {
		return proposalInsuredPerson;
	}

	public void setProposalInsuredperson(ProposalInsuredPerson proposalInsuredPerson) {
		this.proposalInsuredPerson = proposalInsuredPerson;
	}

	public void prepareApproveInsuredperson(ProposalInsuredPerson proposalInsuredPerson) {
		this.proposalInsuredPerson = proposalInsuredPerson;
	}

	public LifeEndorseInfo getLifeEndorseInfo() {
		return lifeEndorseInfo;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean getIsSportMan() {
		return isSportMan;
	}

	public boolean getIsFarmer() {
		return isFarmer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getIsGroupLife() {
		return isGroupLife;
	}

	public boolean getIsSnakeBite() {
		return isSnakeBite;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		user.setBranch(this.user.getLoginBranch());
		this.responsiblePerson = user;
	}

	/* Life Proposal Template */
	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{LifePolicyHistoryService}")
	private ILifePolicyHistoryService lifePolicyHistoryService;

	public void setLifePolicyHistoryService(ILifePolicyHistoryService lifePolicyHistoryService) {
		this.lifePolicyHistoryService = lifePolicyHistoryService;
	}

	@ManagedProperty(value = "#{LifePolicySummaryService}")
	private ILifePolicySummaryService lifePolicySummaryService;

	public void setLifePolicySummaryService(ILifePolicySummaryService lifePolicySummaryService) {
		this.lifePolicySummaryService = lifePolicySummaryService;
	}

	private List<LifePolicy> lifePolicyList;

	public List<LifePolicy> getLifePolicyList() {
		lifePolicyList = new ArrayList<LifePolicy>();
		if (lifeProposal.getLifePolicy() != null) {
			lifePolicyList.add(lifeProposal.getLifePolicy());
		}
		return lifePolicyList;
	}

	public List<LifePolicyHistory> getLifePolicyHistoryList() {
		if (lifeProposal.getLifePolicy() != null) {
			return lifePolicyHistoryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getPolicyNo());
		}
		return new ArrayList<>();

	}

	public LifePolicySummary getLifePolicySummary(String policyId) {
		LifePolicySummary summary = lifePolicySummaryService.findLifePolicyByPolicyNo(policyId);
		return summary;
	}

	public String getPageHeader() {
		return "Life Endorsement Proposal Approval";
	}

	public boolean isAllApproved() {
		return allApproved;
	}

	public void setAllApproved(boolean allApproved) {
		this.allApproved = allApproved;
	}
}

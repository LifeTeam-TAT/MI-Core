package org.ace.insurance.web.manage.life.studentLife.approve;

import java.io.Serializable;
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
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.user.User;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "NewStudentLifeApprovalActionBean")
public class NewStudentLifeApprovalActionBean extends BaseBean implements Serializable {
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

	private boolean approved;
	private User user;
	private LifeProposal lifeProposal;
	private double approvedSumInsured;
	private boolean needMedicalCheckup;
	private String rejectReason;
	private String remark;
	private User responsiblePerson;
	private boolean allApproved;
	private ProposalInsuredPerson proposalInsuredPerson;

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		// TODO FIXME PSH for better Performance use proposalid from param
		String lifeProposalId = (String) getParam("lifeProposalId");
		if (null != lifeProposalId && !lifeProposalId.trim().isEmpty()) {
			lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalId);
		}
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		checkUserAuthority();
		loadAllApproved();
	}

	private void checkUserAuthority() {
		String productId = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId();
		double userAuthorityAmount = getAuthorityAmount(user.getAuthorityPermissionList(), productId, TransactionType.UNDERWRITING);
		double sumInsured = lifeProposal.getTotalSumInsured();
		approved = userAuthorityAmount >= sumInsured;
	}

	public void openTemplateDialog() {
		putParam("lifeProposal", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openStudentLifeInfoTemplate();
	}

	public void selectUser() {
		WorkflowTask workflowTask = !approved ? WorkflowTask.APPROVAL : WorkflowTask.INFORM;
		selectUser(workflowTask, WorkFlowType.STUDENT_LIFE);
	}

	public String redriectLifeApproval() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, WorkflowTask.APPROVAL, ReferenceType.STUDENT_LIFE,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			workFlowService.updateWorkFlow(workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.REDIRECT_PROCESS_SUCCESS);
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void prepareApproveLifeProposal() {
		checkUserAuthority();
		if (responsiblePerson != null) {
			PrimeFaces.current().executeScript("PF('approveConfirmDialog').show()");
		}
	}

	public void handleManyBooleanCheckBox() {
		for (ProposalInsuredPerson insuPerson : lifeProposal.getProposalInsuredPersonList()) {
			insuPerson.setApproved(allApproved);
		}
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

	public String addNewLifeApproval() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, WorkflowTask.INFORM, ReferenceType.STUDENT_LIFE,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			lifeProposalService.approveLifeProposal(lifeProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.APPROVAL_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public double getApprovedSumInsured() {
		return approvedSumInsured;
	}

	public void setApprovedSumInsured(double approvedSumInsured) {
		this.approvedSumInsured = approvedSumInsured;
	}

	public boolean isNeedMedicalCheckup() {
		return needMedicalCheckup;
	}

	public void setNeedMedicalCheckup(boolean needMedicalCheckup) {
		this.needMedicalCheckup = needMedicalCheckup;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public boolean isAllApproved() {
		return allApproved;
	}

	public void setAllApproved(boolean allApproved) {
		this.allApproved = allApproved;
	}

	public ProposalInsuredPerson getProposalInsuredperson() {
		return proposalInsuredPerson;
	}

	public void setProposalInsuredperson(ProposalInsuredPerson proposalInsuredPerson) {
		this.proposalInsuredPerson = proposalInsuredPerson;
	}
}

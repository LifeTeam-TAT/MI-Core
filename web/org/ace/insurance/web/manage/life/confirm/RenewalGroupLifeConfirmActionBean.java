package org.ace.insurance.web.manage.life.confirm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.EndorsementUtil;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.user.User;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "RenewalGroupLifeConfirmActionBean")
public class RenewalGroupLifeConfirmActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RenewalGroupLifeProposalService}")
	private IRenewalGroupLifeProposalService renewalGroupLifeProposalService;

	public void setRenewalGroupLifeProposalService(IRenewalGroupLifeProposalService renewalGroupLifeProposalService) {
		this.renewalGroupLifeProposalService = renewalGroupLifeProposalService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{AcceptedInfoService}")
	private IAcceptedInfoService acceptedInfoService;

	public void setAcceptedInfoService(IAcceptedInfoService acceptedInfoService) {
		this.acceptedInfoService = acceptedInfoService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private WorkFlowDTO workFlowDTO;
	private boolean approvedProposal = true;
	private String remark;
	private User responsiblePerson;
	private List<Payment> paymentList;
	private PaymentDTO paymentDTO;
	private boolean isUserDefinedDate;
	private Date effectiveDate;
	private boolean showPrintPreview;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
		workFlowDTO = (workFlowDTO == null) ? (WorkFlowDTO) getParam("workFlowDTO") : workFlowDTO;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
		removeParam("workFlowDTO");
	}

	@PostConstruct
	public void init() {
		showPrintPreview = false;
		paymentDTO = new PaymentDTO();
		initializeInjection();
		String productId = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId();
		if (lifeProposal.getProposalInsuredPersonList().size() >= 1 && productId.equals(getGroupLifeId())) {
			int approvedCount = 0;
			for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
				if (pv.isApproved()) {
					approvedCount++;
				}
			}
			if (approvedCount < 5) {
				approvedProposal = false;
			}
		} else {
			approvedProposal = lifeProposal.getProposalInsuredPersonList().get(0).isApproved();
		}
		if (!EndorsementUtil.isEndorsementProposal(lifeProposal.getLifePolicy())) {
			renewalGroupLifeProposalService.calculatePremium(lifeProposal);
		}
		AcceptedInfo acceptedInfo = acceptedInfoService.findAcceptedInfoByReferenceNo(lifeProposal.getId());
		paymentDTO.setBasicPremium(lifeProposal.getProposedPremium());
		paymentDTO.setAddOnPremium(lifeProposal.getProposedAddOnPremium());
		paymentDTO.setServicesCharges(acceptedInfo.getServicesCharges());
		paymentDTO.setStampFees(acceptedInfo.getStampFeesAmount());
	}

	public boolean isApprovedProposal() {
		return approvedProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public void confirmLifeProposal() {
		try {
			lifeProposal.setStartDate(effectiveDate);
			if (effectiveDate == null) {
				lifeProposal.setEndDate(null);
			}
			WorkflowTask workflowTask = WorkflowTask.PAYMENT;
			workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, ReferenceType.GROUP_LIFE, TransactionType.RENEWAL, user,
					responsiblePerson);
			paymentList = renewalGroupLifeProposalService.confirmLifeProposal(lifeProposal, workFlowDTO, paymentDTO, user.getBranch(), null);
			paymentDTO = new PaymentDTO(paymentList);
			showPrintPreview = true;
			addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS_PARAM, lifeProposal.getProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String editLifeProposal() {
		putParam("lifeProposal", lifeProposal);
		return "editLifeProposal";
	}

	public String getGroupLifeId() {
		return KeyFactorIDConfig.getGroupLifeId();
	}

	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public boolean isUserDefinedDate() {
		return isUserDefinedDate;
	}

	public void setUserDefinedDate(boolean isUserDefinedDate) {
		this.isUserDefinedDate = isUserDefinedDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String denyLifeProposal() {
		String result = null;
		try {
			if (responsiblePerson == null) {
				responsiblePerson = user;
			}
			WorkflowTask workflowTask = null;
			workflowTask = WorkflowTask.REJECT;
			// FIXME CHECK REFTYPE
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, ReferenceType.ENDOWMENT_LIFE,
					TransactionType.RENEWAL, user, responsiblePerson);
			renewalGroupLifeProposalService.rejectLifeProposal(lifeProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.DENY_PROCESS_OK);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void selectUser() {
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE, TransactionType.RENEWAL, user.getLoginBranch().getId(), null);
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public boolean isShowPrintPreview() {
		return showPrintPreview;
	}

	/* For Template */
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

}

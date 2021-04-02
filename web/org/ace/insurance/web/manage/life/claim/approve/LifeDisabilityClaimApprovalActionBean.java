package org.ace.insurance.web.manage.life.claim.approve;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.LifeDisabilityClaim;
import org.ace.insurance.life.claim.LifeDisabilityClaimType;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
@ViewScoped
@ManagedBean(name = "LifeDisabilityClaimApprovalActionBean")
public class LifeDisabilityClaimApprovalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean pending;
	private boolean userFlag;
	private int periodMonth;
	private String remark;
	private User responsiblePerson;
	private LifeDisabilityClaim disabilityClaim;
	private LifeClaimInsuredPerson claimInsuredPerson;
	private List<WorkFlowHistory> workflowList;
	private User user;
	private LifeClaim lifeClaim;

	@ManagedProperty(value = "#{PaymentTypeService}")
	private IPaymentTypeService paymentTypeService;

	public void setPaymentTypeService(IPaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{LifeClaimService}")
	private ILifeClaimService claimService;

	public void setClaimService(ILifeClaimService claimService) {
		this.claimService = claimService;
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeClaim = (lifeClaim == null) ? (LifeClaim) getParam("lifeDisabilityClaim") : lifeClaim;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		disabilityClaim = (LifeDisabilityClaim) lifeClaim;
	}

	/********************************************
	 * Action Controller
	 ********************************************/

	// Detail PopUp Click Event
	public void loadWorkFlow() {
		workflowList = workFlowService.findWorkFlowHistoryByRefNo(disabilityClaim.getClaimRequestId());
	}

	// Approve CheckBox Click Event Listener
	public void approve(LifeClaimInsuredPerson lifeClaimInsuredPerson) {
		// clear rejectReason and needSomeDocument if approved.
		if (lifeClaimInsuredPerson.isApproved()) {
			lifeClaimInsuredPerson.setNeedSomeDocument(false);
			lifeClaimInsuredPerson.setRejectedReason(null);
		}
	}

	// Approval Dialog Save Event
	public void checkPending() {
		if (claimInsuredPerson.isNeedSomeDocument()) {
			this.pending = true;
		} else {
			this.pending = false;
		}
		if (pending) {
			responsiblePerson = user;
			userFlag = true;
		}
	}

	// Pending CheckBox Click Event
	public void reLoadUser() {
		if (pending) {
			responsiblePerson = user;
			userFlag = true;
		} else {
			responsiblePerson = null;
			userFlag = false;
		}
	}

	// Submit Button Click Event
	public String addNewLifeDisabilityClaimApproval() {
		String result = null;
		try {
			if (checkApproveInfo()) {
				disabilityClaim.setWaitingStartDate(new Date());
				Calendar cal = Calendar.getInstance();
				cal.setTime(disabilityClaim.getWaitingStartDate());
				cal.add(Calendar.MONTH, disabilityClaim.getWaitingPeriod());
				disabilityClaim.setWaitingEndDate(cal.getTime());
				if (Utils.isNotNull(claimInsuredPerson)) {
					disabilityClaim.getClaimInsuredPerson().setNeedSomeDocument(claimInsuredPerson.isNeedSomeDocument());
					disabilityClaim.getClaimInsuredPerson().setRejectedReason(claimInsuredPerson.getRejectedReason());
				}
				WorkflowTask workflowTask = disabilityClaim.getClaimInsuredPerson().isNeedSomeDocument() ? WorkflowTask.PROPOSAL
						: pending ? WorkflowTask.APPROVAL : WorkflowTask.INFORM;
				WorkFlowDTO workFlowDTO = new WorkFlowDTO(disabilityClaim.getClaimRequestId(), getLoginBranchId(), remark, workflowTask, ReferenceType.LIFE_DIS_CLAIM,
						TransactionType.UNDERWRITING, user, responsiblePerson);
				claimService.approveLifeClaim((LifeClaim) disabilityClaim, workFlowDTO);
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.APPROVAL_PROCESS_SUCCESS);
				result = "dashboard";
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public Boolean checkApproveInfo() {
		boolean valid = true;
		String formID = "lifeDisabilityClaimApproval";

		if (disabilityClaim.getClaimInsuredPerson().getClaimAmount() <= 0) {
			addErrorMessage(formID + ":claimAmount", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (responsiblePerson == null) {
			addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (disabilityClaim.getWaitingPeriod() <= 0) {
			addErrorMessage(formID + ":waitingPeriod", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (disabilityClaim.getDisabilityClaimType() == null) {
			addErrorMessage(formID + ":lifeDisabilityClaimType", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		return valid;
	}

	/***********************************************
	 * End Helper
	 ************************************************/

	/********************************************
	 * Getter/Setter
	 *************************************************/

	public LifeDisabilityClaimType[] getClaimTypeList() {
		return LifeDisabilityClaimType.values();
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowList;
	}

	public LifeDisabilityClaim getDisabilityClaim() {
		return disabilityClaim;
	}

	public LifeClaimInsuredPerson getClaimInsuredPerson() {
		return claimInsuredPerson;
	}

	public void setClaimInsuredPerson(LifeClaimInsuredPerson claimInsuredPerson) {
		this.claimInsuredPerson = claimInsuredPerson;
	}

	public void setDisabilityClaim(LifeDisabilityClaim disabilityClaim) {
		this.disabilityClaim = disabilityClaim;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUser() {
		return user;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public LifeClaim getLifeClaim() {
		return lifeClaim;
	}

	public void setLifeClaim(LifeClaim lifeClaim) {
		this.lifeClaim = lifeClaim;
	}

	public boolean isUserFlag() {
		return userFlag;
	}

	public void setUserFlag(boolean userFlag) {
		this.userFlag = userFlag;
	}

	/******************************************
	 * End Getter/Setter
	 ***********************************************/

	public void returnClaimType(SelectEvent event) {
		LifeDisabilityClaimType claimType = (LifeDisabilityClaimType) event.getObject();
		disabilityClaim.setDisabilityClaimType(claimType);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void selectUser() {
		selectUser(WorkflowTask.INFORM, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public LifeDisabilityClaimType[] getClaimTypes() {
		LifeDisabilityClaimType[] claimType = { LifeDisabilityClaimType.valueOf("SEMI_ANNUAL"), LifeDisabilityClaimType.valueOf("LUMPSUM"),
				LifeDisabilityClaimType.valueOf("ANNUAL"), LifeDisabilityClaimType.valueOf("QUATER") };
		return claimType;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}

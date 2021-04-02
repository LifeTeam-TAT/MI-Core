package org.ace.insurance.web.manage.life.claim.approve;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimDeathPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.policy.BeneficiaryStatus;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
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
 * @author T&D Information System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
@ViewScoped
@ManagedBean(name = "LifeDeathClaimApprovalActionBean")
public class LifeDeathClaimApprovalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean pending;
	private boolean approved;
	private String remark;
	private User responsiblePerson;
	private LifeClaimDeathPerson deathPerson;
	private PolicyInsuredPerson policyInsuredPerson;
	// private List<User> userList;
	private List<WorkFlowHistory> workflowList;
	private User user;
	private LifeClaim lifeClaim;
	@ManagedProperty(value = "#{LifeClaimService}")
	private ILifeClaimService lifeClaimService;

	public void setLifeClaimService(ILifeClaimService lifeClaimService) {
		this.lifeClaimService = lifeClaimService;
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

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeClaim = (lifeClaim == null) ? (LifeClaim) getParam("lifeClaim") : lifeClaim;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		this.deathPerson = (LifeClaimDeathPerson) lifeClaim.getClaimInsuredPerson();
	}

	public void selectUser() {
		if (lifeClaim.getClaimInsuredPerson().isNeedSomeDocument()) {
			selectUser(WorkflowTask.REQUEST, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
		} else {
			selectUser(WorkflowTask.INFORM, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
		}
	}

	public void loadWorkflow() {
		workflowList = workFlowService.findWorkFlowHistoryByRefNo(lifeClaim.getClaimRequestId());
	}

	public void prepareApprove() {
		deathPerson.setApproved(isApproved());
		if (isApproved()) {
			deathPerson.setNeedSomeDocument(false);
			deathPerson.setRejectedReason(null);
		}
		if (!deathPerson.isApproved()) {
			deathPerson.setDeathDate(null);
			deathPerson.setDeathReason("");
		}
	}

	public void saveDeathPerson() {
		responsiblePerson = null;
		if (deathPerson.isNeedSomeDocument()) {
			pending = true;
		} else {
			pending = false;
		}
	}

	public void holdDeathClaimApproval() {
		if (pending) {
			responsiblePerson = user;
		} else {
			responsiblePerson = null;
		}
	}

	public String addNewLifeDeathClaimApproval() {
		String result = null;
		try {
			if (!pending) {
				for (LifeClaimInsuredPersonBeneficiary claimBeneficiary : lifeClaim.getClaimInsuredPersonBeneficiaryList()) {
					if (claimBeneficiary.isClaimInsuredPersonBeneficiary() || claimBeneficiary.isClaimSuccessor()) {
						if (!lifeClaim.getClaimInsuredPerson().isApproved()) {
							claimBeneficiary.setBeneficiaryStatus(BeneficiaryStatus.REJECTED);
						} else {
							claimBeneficiary.setBeneficiaryStatus(BeneficiaryStatus.DISCHARGED);
						}
					}
				}
			}
			if (checkLifeDeathClaimApproval()) {
				WorkflowTask workflowTask = WorkflowTask.INFORM;
				if (pending) {
					workflowTask = WorkflowTask.APPROVAL;
				}
				if (deathPerson.isNeedSomeDocument()) {
					workflowTask = WorkflowTask.PROPOSAL;
				}

				WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeClaim.getClaimRequestId(), getLoginBranchId(), remark, workflowTask, ReferenceType.LIFE_DEALTH_CLAIM,
						TransactionType.UNDERWRITING, user, responsiblePerson);
				deathPerson.setApproved(isApproved());
				lifeClaim.addClaimInsuredPerson(deathPerson);
				lifeClaimService.approveLifeClaim(lifeClaim, workFlowDTO);
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.APPROVAL_PROCESS_SUCCESS);
				extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeClaim.getClaimRequestId());
				result = "dashboard";
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}

		return result;
	}

	private boolean checkLifeDeathClaimApproval() {
		boolean flag;
		try {
			flag = true;
			String formID = "lifeDeathClaimApprovalForm";
			if (approved == false) {
				if (deathPerson != null) {
					if (deathPerson.getRejectedReason() == null || deathPerson.getRejectedReason().isEmpty()) {
						addErrorMessage(formID + ":claimInsuredPersonTable", "RejectReason Value is required");
						flag = false;
					}
				}
			}
			if (responsiblePerson == null) {
				addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
				return false;
			}
			if (flag == true) {
				return true;
			} else {
				return false;
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return true;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowList;
	}

	public LifeClaimDeathPerson getDeathPerson() {
		return deathPerson;
	}

	public void setDeathPerson(LifeClaimDeathPerson deathPerson) {
		this.deathPerson = deathPerson;
	}

	public boolean isUserFormDisable() {
		return !this.deathPerson.isNeedSomeDocument() && this.pending;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LifeClaim getLifeClaim() {
		return this.lifeClaim;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}

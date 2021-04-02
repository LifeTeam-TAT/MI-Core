package org.ace.insurance.web.manage.medical.approval;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/***************************************************************************************
 * @author NNH
 * @Date 2014-08-14
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalProposal</code> approve process.
 * 
 ***************************************************************************************/
@ViewScoped
@ManagedBean(name = "NewMedicalApprovalActionBean")
public class NewMedicalApprovalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalProposalService) {
		this.medicalProposalService = medicalProposalService;
	}

	private String loginBranchId;
	private User user;
	private MedicalProposal medicalProposal;
	private MedicalProposalInsuredPerson proposalInsuredPerson;
	private String remark;
	private boolean isApproved;
	private boolean needMedicalCheckUp;
	private String rejectReason;
	private List<WorkFlowHistory> workflowHistoryList;
	private User responsiblePerson;
	private boolean isAllApproved;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		loginBranchId = user.getLoginBranch().getId();
		medicalProposal = (MedicalProposal) ((medicalProposal == null) ? getParam("medicalProposal") : medicalProposal);
		workflowHistoryList = workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId());
	}

	@PreDestroy
	public void destroy() {
		removeParam("medicalProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		double authourity = user.getAuthority();
		if (authourity >= medicalProposal.getTotalSumInsured()) {
			isApproved = true;
			isAllApproved = true;
			for (MedicalProposalInsuredPerson insuPerson : medicalProposal.getMedicalProposalInsuredPersonList()) {
				insuPerson.setApproved(true);
			}
		}
	}

	public void rejectInsuredPerson() {
		PrimeFaces.current().executeScript("PF('medicalRejectDialog').hide()");
	}

	public void openTemplateDialog() {
		putParam("medicalProposal", medicalProposal);
		putParam("workFlowList", getWorkFlowList());
		openMedicalProposalInfoTemplate();
	}

	private ReferenceType getReferenceType() {
		ReferenceType referenceType = null;
		switch (medicalProposal.getHealthType()) {
			case HEALTH:
				referenceType = ReferenceType.HEALTH;
				break;
			case CRITICALILLNESS:
				referenceType = ReferenceType.CRITICAL_ILLNESS;
				break;
			case MICROHEALTH:
				referenceType = ReferenceType.MICRO_HEALTH;
				break;
			default:
				break;
		}
		return referenceType;
	}

	public String addNewMedicalApproval() {
		String result = null;
		try {
			String loginBranchId = user.getLoginBranch().getId();
			ReferenceType referenceType = getReferenceType();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, WorkflowTask.INFORM, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			medicalProposalService.approveMedicalProposal(medicalProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.APPROVAL_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, medicalProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public String redriectMedicalApproval() {
		String result = null;
		try {
			ReferenceType referenceType = getReferenceType();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, WorkflowTask.APPROVAL, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			workFlowService.updateWorkFlow(workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.REDIRECT_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, medicalProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void selectUser() {
		if (isApproved) {
			selectUser(WorkflowTask.INFORM, WorkFlowType.MEDICAL_INSURANCE, TransactionType.UNDERWRITING, loginBranchId, null);
		} else {
			selectUser(WorkflowTask.APPROVAL, WorkFlowType.MEDICAL_INSURANCE, TransactionType.UNDERWRITING, null, loginBranchId);
		}
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void handleSingleBooleanCheckBox() {
		isAllApproved = true;
		for (MedicalProposalInsuredPerson insuPerson : medicalProposal.getMedicalProposalInsuredPersonList()) {
			if (!insuPerson.isApproved()) {
				isAllApproved = false;
			}
		}
	}

	public void handleManyBooleanCheckBox() {
		for (MedicalProposalInsuredPerson insuPerson : medicalProposal.getMedicalProposalInsuredPersonList()) {
			insuPerson.setApproved(isAllApproved);
		}
	}

	public boolean isApproved() {
		return isApproved;
	}

	public boolean isNeedMedicalCheckUp() {
		return needMedicalCheckUp;
	}

	public void setNeedMedicalCheckUp(boolean needMedicalCheckUp) {
		this.needMedicalCheckUp = needMedicalCheckUp;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowHistoryList;
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public void setMedicalProposal(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
	}

	public MedicalProposalInsuredPerson getProposalInsuredPerson() {
		return proposalInsuredPerson;
	}

	public void setProposalInsuredPerson(MedicalProposalInsuredPerson proposalInsuredPerson) {
		this.proposalInsuredPerson = proposalInsuredPerson;
	}

	public boolean isAllApproved() {
		return isAllApproved;
	}

	public void setAllApproved(boolean isAllApproved) {
		this.isAllApproved = isAllApproved;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}
}

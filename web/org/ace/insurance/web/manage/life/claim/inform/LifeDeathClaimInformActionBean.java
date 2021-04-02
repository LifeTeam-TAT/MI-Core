package org.ace.insurance.web.manage.life.claim.inform;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
@ViewScoped
@ManagedBean(name = "LifeDeathClaimInformActionBean")
public class LifeDeathClaimInformActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean printFlag;
	private boolean rejectFlag;
	private String approvalStatus;
	private String remark;
	private User responsiblePerson;
	private List<WorkFlowHistory> workflowList;
	private ClaimAcceptedInfo claimAcceptedInfo;
	private LifeClaim lifeClaim;
	private User user;

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{LifeClaimService}")
	private ILifeClaimService lifeClaimService;

	public void setLifeClaimService(ILifeClaimService lifeClaimService) {
		this.lifeClaimService = lifeClaimService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeClaim = (lifeClaim == null) ? (LifeClaim) getParam("lifeClaim") : lifeClaim;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeClaim");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		printFlag = false;
		claimAcceptedInfo = new ClaimAcceptedInfo();
		claimAcceptedInfo.setReferenceNo(lifeClaim.getId());
		claimAcceptedInfo.setReferenceType(ReferenceType.ENDOWMENT_LIFE);
		claimAcceptedInfo.setClaimAmount(lifeClaim.getTotalClaimAmount());
		if (lifeClaim != null) {
			if (lifeClaim.getClaimInsuredPerson().isApproved()) {
				rejectFlag = false;
			} else {
				rejectFlag = true;
			}
		}
	}

	/********************************************
	 * Action Controller
	 ********************************************/

	// Detail PopUp Click Event
	public void loadWorkflow() {
		workflowList = workFlowService.findWorkFlowHistoryByRefNo(lifeClaim.getClaimRequestId());
	}

	// Submit Button Click Event
	public void informApproveLifeDeathClaim() {
		try {
			if (checkData()) {
				WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeClaim.getClaimRequestId(), getLoginBranchId(), remark, WorkflowTask.CONFIRMATION, ReferenceType.LIFE_DEALTH_CLAIM,
						TransactionType.UNDERWRITING, user, responsiblePerson);
				lifeClaimService.informLifeClaim(lifeClaim, workFlowDTO, claimAcceptedInfo);
				addInfoMessage(null, MessageId.INFORM_PROCESS_SUCCESS_PARAM, lifeClaim.getClaimRequestId());
				printFlag = true;
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	private boolean checkData() {
		boolean result = true;
		String formID = "lifeDeathClaimCustomerInformForm";
		if (claimAcceptedInfo.getServicesCharges() < 0) {
			result = false;
			addErrorMessage(formID + ":additionalCharges", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (responsiblePerson == null) {
			result = false;
			addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
		}

		return result;
	}

	public void selectUser() {
		selectUser(WorkflowTask.CONFIRMATION, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	/******************************************
	 * End Action Controller
	 ******************************************/
	/**********************************************
	 * Helper
	 *****************************************************/

	/********************************************
	 * End Helper
	 ***************************************************/
	/********************************************
	 * Getter/Setter
	 *************************************************/

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowList;
	}

	public boolean isPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public boolean isRejectFlag() {
		return rejectFlag;
	}

	public ClaimAcceptedInfo getClaimAcceptedInfo() {
		return claimAcceptedInfo;
	}

	public void setClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo) {
		this.claimAcceptedInfo = claimAcceptedInfo;
	}

	/******************************************
	 * End Getter/Setter
	 ***********************************************/

	// prepare
	// for reject letter
	private final String reportName = "lifeDeathClaimInformRejectLetter";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";
	// for acceptance letter
	private final String acceptanceReportName = "lifeDeathClaimInformAcceptanceLetter";
	private final String acceptancePdfDirPath = "/pdf-report/" + acceptanceReportName + "/" + System.currentTimeMillis() + "/";
	private final String acceptanceDirPath = getSystemPath() + acceptancePdfDirPath;
	private final String acceptanceDirFileName = acceptanceReportName + ".pdf";

	public String getStream() {
		if (!rejectFlag) {
			return acceptancePdfDirPath + acceptanceDirFileName;
		} else {
			return pdfDirPath + fileName;
		}
	}

	public void generateReport() {
		if (!rejectFlag) {
			DocumentBuilder.generateLifeDeathClaimAcceptanceLetter(lifeClaim, claimAcceptedInfo, acceptanceDirPath, acceptanceDirFileName);
		} else {
			DocumentBuilder.generateLifeDeathClaimRejectLetter(lifeClaim, claimAcceptedInfo, dirPath, fileName);
		}
	}

	public void returnResponsibleUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void handleClose(CloseEvent event) {
		try {
			if (!rejectFlag) {
				org.ace.insurance.web.util.FileHandler.forceDelete(acceptanceDirPath);
			} else {
				org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}

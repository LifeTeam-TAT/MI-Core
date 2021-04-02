package org.ace.insurance.web.manage.life.claim.confirm;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeDeathClaim;
import org.ace.insurance.life.claim.LifeHospitalizedClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.life.claim.service.interfaces.ILifePolicyClaimService;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeClaimConfirmationActionBean")
public class LifeClaimConfirmationActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService lifeClaimProposalService;

	public void setLifeClaimProposalService(ILifeClaimProposalService lifeClaimProposalService) {
		this.lifeClaimProposalService = lifeClaimProposalService;
	}

	@ManagedProperty(value = "#{ClaimAcceptedInfoService}")
	private IClaimAcceptedInfoService claimAcceptedInfoService;

	public void setClaimAcceptedInfoService(IClaimAcceptedInfoService claimAcceptedInfoService) {
		this.claimAcceptedInfoService = claimAcceptedInfoService;
	}

	@ManagedProperty(value = "#{LifePolicyClaimService}")
	private ILifePolicyClaimService lifePolicyClaimService;

	public void setLifePolicyClaimService(ILifePolicyClaimService lifePolicyClaimService) {
		this.lifePolicyClaimService = lifePolicyClaimService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private User user;
	private LifeClaimProposal lifeClaimProposal;
	private ClaimAcceptedInfo claimAcceptedInfo;
	private User responsiblePerson;
	private String remark;
	private PaymentDTO paymentDTO;
	private boolean isDisabilityClaim;
	private boolean isDeathClaim;
	private boolean isHospitalClaim;
	private DisabilityLifeClaim disabiliyClaim;
	private LifeDeathClaim lifeDeathClaim;
	private LifeHospitalizedClaim hospitalClaim;
	private boolean isPrint;
	private boolean reject;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeClaimProposal = (lifeClaimProposal == null) ? (LifeClaimProposal) getParam("lifeClaimProposal") : lifeClaimProposal;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		paymentDTO = new PaymentDTO();
		claimAcceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo(lifeClaimProposal.getId(), ReferenceType.LIFE_CLAIM);
		if (claimAcceptedInfo != null) {
			paymentDTO.setServicesCharges(claimAcceptedInfo.getServicesCharges());
			paymentDTO.setClaimAmount(claimAcceptedInfo.getClaimAmount());
			paymentDTO.setMedicalFees(claimAcceptedInfo.getMedicalfees());
		}
		checkDisabilityClaimType();

		if (!lifeClaimProposal.getLifePolicyClaim().isApproved()) {
			reject = true;
		}

	}

	private void checkDisabilityClaimType() {
		if (lifeClaimProposal.getLifePolicyClaim() instanceof LifeDeathClaim) {
			lifeDeathClaim = (LifeDeathClaim) lifeClaimProposal.getLifePolicyClaim();
			isDeathClaim = true;
		} else if (lifeClaimProposal.getLifePolicyClaim() instanceof LifeHospitalizedClaim) {
			isHospitalClaim = true;
			hospitalClaim = (LifeHospitalizedClaim) lifeClaimProposal.getLifePolicyClaim();
		} else {
			isDisabilityClaim = true;
			disabiliyClaim = (DisabilityLifeClaim) lifeClaimProposal.getLifePolicyClaim();
		}
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeClaimProposal");
	}

	public String deny() {
		String result = null;
		try {
			if (responsiblePerson == null) {
				String formID = "confirmaitonMedicalClaimProposalForm";
				addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
			} else {
				WorkFlowDTO workFlowDTO = new WorkFlowDTO("", user.getLoginBranch().getId(), remark, WorkflowTask.REJECT, ReferenceType.LIFE_CLAIM, TransactionType.CLAIM, user,
						responsiblePerson);
				lifeClaimProposalService.rejectLifeClaimPropsal(lifeClaimProposal, workFlowDTO);
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.DENY_PROCESS_OK);
				extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeClaimProposal.getClaimProposalNo());
				result = "dashboard";
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public String confirmClaimBeneficiary() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeClaimProposal.getId(), user.getLoginBranch().getId(), remark, WorkflowTask.PAYMENT, ReferenceType.LIFE_CLAIM,
					TransactionType.CLAIM, user, responsiblePerson);
			lifeClaimProposalService.confirmLifeClaimPropsal(lifeClaimProposal, paymentDTO, workFlowDTO);
			addInfoMessage(null, "LIFE_ClAIM_CONFIRM_SUCCESS_PARAM", lifeClaimProposal.getClaimProposalNo());
			isPrint = true;
			result = "null";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	/** Generate Report */
	private final String reportName = "LifeReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = "ClaimPaymentSlip.pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generatePaymentLetter() {
		int claimCount = 0;
		try {
			claimCount = lifePolicyClaimService.findCountByPolicyNo(lifeClaimProposal.getLifePolicyClaim().getPolicyNo());
		} catch (SystemException se) {
			handelSysException(se);
		}
		ClaimAcceptedInfo acceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo(lifeClaimProposal.getId(), ReferenceType.LIFE_CLAIM);
		DocumentBuilder.generateLifeClaimPaymentSlipLetters(lifeClaimProposal, dirPath, fileName, claimCount, acceptedInfo);
	}

	public void openTemplateDialog() {
		putParam("lifeClaimProposal", lifeClaimProposal);
		putParam("workFlowList", getWorkflowList());
		openLifeClaimInfoTemplate();
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<WorkFlowHistory> getWorkflowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeClaimProposal.getId());
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		paymentDTO.setBank(bank);
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public void selectUser() {
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE);
	}

	public LifeClaimProposal getLifeClaimProposal() {
		return lifeClaimProposal;
	}

	public ClaimAcceptedInfo getClaimAcceptedInfo() {
		return claimAcceptedInfo;
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

	public void setLifeClaimProposal(LifeClaimProposal lifeClaimProposal) {
		this.lifeClaimProposal = lifeClaimProposal;
	}

	public void setClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo) {
		this.claimAcceptedInfo = claimAcceptedInfo;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getIsDisabilityClaim() {
		return isDisabilityClaim;
	}

	public boolean getIsDeathClaim() {
		return isDeathClaim;
	}

	public boolean getIsHospitalClaim() {
		return isHospitalClaim;
	}

	public DisabilityLifeClaim getDisabiliyClaim() {
		return disabiliyClaim;
	}

	public void setDisabiliyClaim(DisabilityLifeClaim disabiliyClaim) {
		this.disabiliyClaim = disabiliyClaim;
	}

	public boolean isPrint() {
		return isPrint;
	}

	public boolean isReject() {
		return reject;
	}

	public void setReject(boolean reject) {
		this.reject = reject;
	}

}

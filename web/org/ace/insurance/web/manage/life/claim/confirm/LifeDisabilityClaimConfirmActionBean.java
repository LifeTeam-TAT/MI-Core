package org.ace.insurance.web.manage.life.claim.confirm;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.web.manage.life.claim.LifeClaimDischargeFormDTO;
import org.ace.insurance.web.manage.life.claim.LifeClaimRefundFormDTO;
import org.ace.insurance.web.manage.life.claim.request.DisabilityClaimDTO;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.event.SelectEvent;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
@ViewScoped
@ManagedBean(name = "LifeDisabilityClaimConfirmActionBean")
public class LifeDisabilityClaimConfirmActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	private LifeClaim lifeClaim;

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

	@ManagedProperty(value = "#{ClaimAcceptedInfoService}")
	private IClaimAcceptedInfoService claimAcceptedInfoService;

	public void setClaimAcceptedInfoService(IClaimAcceptedInfoService claimAcceptedInfoService) {
		this.claimAcceptedInfoService = claimAcceptedInfoService;
	}

	@ManagedProperty(value = "#{BankService}")
	private IBankService bankService;

	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	private boolean approvedProposal = true;
	private boolean btnOkFlag;
	private boolean showFormFlg;
	private boolean rejectflag;
	private String claimRequestId;
	private String remark;
	private LifeClaimInsuredPerson claimInsuredPerson;
	private PolicyInsuredPerson policyInsuredPerson;
	private User responsiblePerson;
	private DisabilityClaimDTO claimInfoDTO;
	private LifeClaimRefundFormDTO lifeClaimRefundFormDTO;
	private LifeClaimDischargeFormDTO lifeClaimDischargeFormDTO;
	private List<PolicyInsuredPerson> policyInsuredPersonList;
	private List<WorkFlowHistory> workflowList;
	private List<User> userList;
	private PaymentDTO paymentDTO;
	private ClaimAcceptedInfo acceptedInfo;

	private boolean isCheque;
	//add for transfer
	private Payment payment;
	private boolean isAccountBank;
	private boolean isBank;
	private boolean isTransfer;
	private PaymentChannel channelValue;
	private List<Payment> paymentList;

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		lifeClaim = (LifeClaim) getParam("lifeDisabilityClaim");
	}

	@PostConstruct
	public void init() {
		initializeInjection();

		btnOkFlag = false;
		showFormFlg = true;
		paymentDTO = new PaymentDTO();
		acceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo(lifeClaim.getId(), ReferenceType.LIFE_DIS_CLAIM);
		paymentDTO.setServicesCharges(acceptedInfo.getServicesCharges());
		paymentDTO.setClaimAmount(acceptedInfo.getClaimAmount());
		paymentDTO.setSalesPoints(lifeClaim.getLifePolicy().getSalesPoints());
		rejectflag = !lifeClaim.getClaimInsuredPerson().isApproved();
	}

	public String editLifeClaim() {
		putParam("lifeClaim", lifeClaim);
		return "editDisabilityClaimRequestForm";
	}

	/********************************************
	 * Action Controller
	 ********************************************/

	// Detail PopUp Click Event
	public void loadWorkflow() {
		workflowList = workFlowService.findWorkFlowHistoryByRefNo(lifeClaim.getClaimRequestId());
	}

	// Confirm Button Click Event
	public void confirm() {
		String formID = "lifeDisabilityClaimCustomerConfirmaitonForm";
		Boolean valid = true;
		if (responsiblePerson == null) {
			addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (!valid) {
			showFormFlg = true;
		} else {
			showFormFlg = false;
		}
	}

	// Ok Button Click Event
	public void confirmClaimBeneficiary() {
		btnOkFlag = true;
		try {
			if (validPayment()) {

				paymentDTO.setPaymentChannel(isCheque && !isTransfer ? PaymentChannel.CHEQUE :!isCheque && isTransfer?PaymentChannel.TRANSFER: PaymentChannel.CASHED);
				WorkFlowDTO workFlowDTO = new WorkFlowDTO("", getLoginBranchId(), remark, WorkflowTask.PAYMENT, ReferenceType.LIFE_DIS_CLAIM, TransactionType.UNDERWRITING, user,
						responsiblePerson);
				paymentList = claimService.confirmLifeClaim(lifeClaim, workFlowDTO, paymentDTO);
				paymentDTO = new PaymentDTO(paymentList);
				addInfoMessage(null, "CONFIRMATION_PROCESS_SUCCESS_REFUND_PARAM", workFlowDTO.getReferenceNo());
			} else {
				return;
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}
	
	


	// Refund Form Print Preview Button Click Event
	public void prepareClaimRefundForm() {
		LifeClaimRefundFormDTO lifeClaimRefundForm = new LifeClaimRefundFormDTO();
		LifeClaimInsuredPerson claimInsuredPerson = lifeClaim.getClaimInsuredPerson();
		lifeClaimRefundForm.setClaimRequestId(lifeClaim.getClaimRequestId());
		lifeClaimRefundForm.setCustomerName(claimInsuredPerson.getPolicyInsuredPerson().getFullName());
		lifeClaimRefundForm.setAddress(claimInsuredPerson.getPolicyInsuredPerson().getResidentAddress().getFullResidentAddress());
		lifeClaimRefundForm.setClaimAmount(paymentDTO.getClaimAmount());
		lifeClaimRefundForm.setLoanAmount(claimInsuredPerson.getLoanAmount());
		lifeClaimRefundForm.setLoanInterest(claimInsuredPerson.getLoanInterest());
		lifeClaimRefundForm.setNetClaimAmount(claimInsuredPerson.getNetClaimAmount());
		lifeClaimRefundForm.setRefundNo(claimInsuredPerson.getRefundNo());
		lifeClaimRefundForm.setServiceCharges(paymentDTO.getServicesCharges());
		this.lifeClaimRefundFormDTO = lifeClaimRefundForm;
	}

	// Discharge Form Print Preview Button Click Event
	LifeClaimDischargeFormDTO lifeClaimDischargeForm = new LifeClaimDischargeFormDTO();

	public void prepareDischargeForm() {

		LifeClaimInsuredPerson claimInsuredPerson = lifeClaim.getClaimInsuredPerson();
		lifeClaimDischargeForm.setPolicyNo(lifeClaim.getLifePolicy().getPolicyNo());
		lifeClaimDischargeForm.setInsuredPersonName(lifeClaim.getClaimInsuredPerson().getPolicyInsuredPerson().getFullName());
		lifeClaimDischargeForm.setCustomerName(claimInsuredPerson.getPolicyInsuredPerson().getFullName());
		lifeClaimDischargeForm.setAddress(claimInsuredPerson.getPolicyInsuredPerson().getResidentAddress().getFullResidentAddress());
		lifeClaimDischargeForm.setClaimAmount(claimInsuredPerson.getClaimAmount());
		lifeClaimDischargeForm.setLoanAmount(claimInsuredPerson.getLoanAmount());
		lifeClaimDischargeForm.setLoanInterest(claimInsuredPerson.getLoanInterest());
		lifeClaimDischargeForm.setNetClaimAmount(claimInsuredPerson.getNetClaimAmount());
		lifeClaimDischargeForm.setRefundNo(claimInsuredPerson.getRefundNo());
		lifeClaimDischargeForm.setBeneficiaryName(claimInsuredPerson.getPolicyInsuredPerson().getFullName());
		lifeClaimDischargeForm.setCommenmanceDate(lifeClaim.getLifePolicy().getCommenmanceDate());
		lifeClaimDischargeForm.setSumInsured(lifeClaim.getLifePolicy().getSumInsured());
		lifeClaimDischargeForm.setIdNo(claimInsuredPerson.getPolicyInsuredPerson().getIdNo());
		lifeClaimDischargeForm.setFatherName(claimInsuredPerson.getPolicyInsuredPerson().getFatherName());
		lifeClaimDischargeForm.setServiceCharges(lifeClaim.getServiceCharges());

		this.lifeClaimDischargeFormDTO = lifeClaimDischargeForm;
	}

	/*
	 * private boolean validPayment() { boolean valid = true; String formID =
	 * "lifeDisabilityClaimConfirm"; if (paymentDTO.getServicesCharges() < 0) {
	 * addErrorMessage(formID + ":additionalCharges",
	 * UIInput.REQUIRED_MESSAGE_ID); valid = false; }
	 * 
	 * return valid; }
	 */
	
	//for paymenttransfer
	private boolean validPayment() {
		boolean valid = true;
		String formID = "lifePaymentForm";
		if (channelValue.equals(PaymentChannel.CHEQUE)) {
			if (paymentDTO.getChequeNo() == null || paymentDTO.getChequeNo().isEmpty()) {
				addErrorMessage(formID + ":chequeNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (paymentDTO.getBank() == null) {
				addErrorMessage(formID + ":bankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (paymentDTO.getAccountBank() == null) {
				addErrorMessage(formID + ":accountBankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		if (channelValue.equals(PaymentChannel.TRANSFER)) {
			if (paymentDTO.getPoNo() == null || paymentDTO.getPoNo().isEmpty()) {
				addErrorMessage(formID + ":poNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (paymentDTO.getBank() == null) {
				addErrorMessage(formID + ":bankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (paymentDTO.getAccountBank() == null) {
				addErrorMessage(formID + ":accountBankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		return valid;
	}
	
	
	//for payment transfer
	public void setChannelValue(PaymentChannel channelValue) {
		if (channelValue.equals(PaymentChannel.CHEQUE)) {
			isAccountBank = true;
			isCheque = true;
			isBank = true;
			isTransfer = false;
		} else if (channelValue.equals(PaymentChannel.TRANSFER)) {
			isAccountBank = true;
			isCheque = false;
			isBank = true;
			isTransfer = true;
		} else {
			isAccountBank = false;
			isCheque = false;
			isBank = false;
			isTransfer = false;
		}
		this.channelValue = channelValue;
	}
	
	

	public void selectUser() {
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public boolean getIsCheque() {
		return isCheque;
	}

	public void setIsCheque(boolean isCheque) {
		this.isCheque = isCheque;
	}

	public List<User> getUserList() {
		return userList;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowList;
	}

	public LifeClaimInsuredPerson getLifeClaimInsuredPerson() {
		return claimInsuredPerson;
	}

	public void setLifeClaimInsuredPerson(LifeClaimInsuredPerson claimBeneficiary) {
		this.claimInsuredPerson = claimBeneficiary;
	}

	public LifeClaim getLifeClaim() {
		return lifeClaim;
	}

	public void setLifeClaim(LifeClaim claim) {
		this.lifeClaim = claim;
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

	public boolean isApprovedProposal() {
		return approvedProposal;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public DisabilityClaimDTO getClaimInfoDTO() {
		return claimInfoDTO;
	}

	public void setClaimInfoDTO(DisabilityClaimDTO claimInfoDTO) {
		this.claimInfoDTO = claimInfoDTO;
	}

	public List<PolicyInsuredPerson> getPolicyInsuredPersonList() {
		return policyInsuredPersonList;
	}

	public void setPolicyInsuredPersonList(List<PolicyInsuredPerson> policyInsuredPersonList) {
		this.policyInsuredPersonList = policyInsuredPersonList;
	}

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public void setClaimRequestId(String claimRequestId) {
		this.claimRequestId = claimRequestId;
	}

	public boolean isBtnOkFlag() {
		return btnOkFlag;
	}

	public void setBtnOkFlag(boolean btnOkFlag) {
		this.btnOkFlag = btnOkFlag;
	}

	public LifeClaimRefundFormDTO getLifeClaimRefundFormDTO() {
		return lifeClaimRefundFormDTO;
	}

	public void setLifeClaimRefundFormDTO(LifeClaimRefundFormDTO lifeClaimRefundFormDTO) {
		this.lifeClaimRefundFormDTO = lifeClaimRefundFormDTO;
	}

	public LifeClaimDischargeFormDTO getLifeClaimDischargeFormDTO() {
		return lifeClaimDischargeFormDTO;
	}

	public void setLifeClaimDischargeFormDTO(LifeClaimDischargeFormDTO lifeClaimDischargeFormDTO) {
		this.lifeClaimDischargeFormDTO = lifeClaimDischargeFormDTO;
	}

	public boolean isShowFormFlg() {
		return showFormFlg;
	}

	public void setShowFormFlg(boolean showFormFlg) {
		this.showFormFlg = showFormFlg;
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	/******************************************
	 * End Getter/Setter
	 ***********************************************/

	public boolean isRejectflag() {
		return rejectflag;
	}

	// Payment Channel
	/*
	 * public void changePaymentChannel(AjaxBehaviorEvent event) { if
	 * (!isCheque) { paymentDTO.setBank(null); paymentDTO.setChequeNo(null); } }
	 */

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		paymentDTO.setAccountBank(null);
		paymentDTO.setBank(null);
		paymentDTO.setChequeNo(null);
		paymentDTO.setPoNo(null);
	}

	

	
	
	
	// prepare for lifedisability claim cash recepit
	private final String reportName = "LifeDisabilityClaimCashReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		DocumentBuilder.generateLifeDisabilityClaimCashReceipt(lifeClaim, paymentDTO, dirPath, fileName);
	}

	private final String reportDischargeName = "LifeDisabilityClaimDischargeCertificate";
	private final String pdfDischargeDirPath = "/pdf-report/" + reportDischargeName + "/" + System.currentTimeMillis() + "/";
	private final String dirDischargePath = getWebRootPath() + pdfDischargeDirPath;
	private final String fileDischargeName = reportDischargeName + ".pdf";

	public String getDischargeReportStream() {
		return pdfDischargeDirPath + fileDischargeName;
	}

	public void generateDischargeReport() {
		DocumentBuilder.generateLifeDisabilityClaimDischargeCertificate(lifeClaimDischargeFormDTO, dirDischargePath, fileDischargeName);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		paymentDTO.setBank(bank);
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public boolean isAccountBank() {
		return isAccountBank;
	}

	public void setAccountBank(boolean isAccountBank) {
		this.isAccountBank = isAccountBank;
	}

	public boolean isBank() {
		return isBank;
	}

	public void setBank(boolean isBank) {
		this.isBank = isBank;
	}

	public boolean isTransfer() {
		return isTransfer;
	}

	public void setTransfer(boolean isTransfer) {
		this.isTransfer = isTransfer;
	}

	public void setCheque(boolean isCheque) {
		this.isCheque = isCheque;
	}
	

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}
	
	public void returnAccountBank(SelectEvent event) {
		Bank accountBank = (Bank) event.getObject();
		paymentDTO.setAccountBank(accountBank);
	}

	public PaymentChannel getChannelValue() {
		return channelValue;
	}
	
	
	
}

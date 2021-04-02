package org.ace.insurance.web.manage.life.claim.confirm;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.ace.insurance.life.claim.LifeClaimDeathPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.web.manage.life.claim.LifeClaimDischargeFormDTO;
import org.ace.insurance.web.manage.life.claim.LifeClaimRefundFormDTO;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
@ViewScoped
@ManagedBean(name = "LifeDeathClaimConfirmActionBean")
public class LifeDeathClaimConfirmActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean disabledBtn;
	private boolean disabledOKBtn;
	private boolean rejectflag;
	private boolean showFormFlg = true;
	private String remark;
	private User responsiblePerson;
	private LifeClaimRefundFormDTO lifeClaimRefundFormDTO;
	private LifeClaimDischargeFormDTO lifeClaimDischargeFormDTO;
	private List<User> userList;
	private List<Payment> paymentList;
	private List<WorkFlowHistory> workflowList;
	private PaymentDTO paymentDTO;
	private ClaimAcceptedInfo acceptedInfo;
	private LifeClaim lifeClaim;

	private User user;
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
	private ILifeClaimService lifeClaimService;

	public void setLifeClaimService(ILifeClaimService lifeClaimService) {
		this.lifeClaimService = lifeClaimService;
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

	private boolean isCheque;
	private boolean actualPayment;
	private String presentDate;
	
	//for paymentransfer
	private Payment payment;
	private boolean isAccountBank;
	private boolean isBank;
	private boolean isTransfer;
	private PaymentChannel channelValue;



	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		lifeClaim = (LifeClaim) getParam("lifeClaim");
	}

	@PostConstruct
	public void init() {
		initializeInjection();

		showFormFlg = true;
		disabledBtn = true;
		disabledOKBtn = false;
		lifeClaimRefundFormDTO = new LifeClaimRefundFormDTO();
		lifeClaimDischargeFormDTO = new LifeClaimDischargeFormDTO();
		paymentDTO = new PaymentDTO();
		acceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo(lifeClaim.getId(), ReferenceType.ENDOWMENT_LIFE);
		paymentDTO.setServicesCharges(acceptedInfo.getServicesCharges());
		paymentDTO.setClaimAmount(acceptedInfo.getClaimAmount());
		paymentDTO.setSalesPoints(lifeClaim.getLifePolicy().getSalesPoints());
		rejectflag = !lifeClaim.getClaimInsuredPerson().isApproved();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		presentDate = format.format(new Date());

	}

	public String editLifeDeathClaim() {
		putParam("lifeClaim", lifeClaim);
		return "editLifeDeathClaimRequestForm";
	}

	/********************************************
	 * Action Controller
	 ********************************************/

	// Detail PopUp Click Event
	public void loadWorkflow() {
		workflowList = workFlowService.findWorkFlowHistoryByRefNo(lifeClaim.getClaimRequestId());
	}

	// Confirm Button Click Event
	public void confirmDeathClaimCustomerConfirmation() {
		Boolean valid = true;
		try {
			String formID = "lifeDeathClaimCustomerConfirmationForm";
			if (responsiblePerson == null) {
				addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (!valid) {
				showFormFlg = true;
			} else {
				showFormFlg = false;
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	// OK Button Click Event
	public String confirmDeathClaimCustomerOK() {
		String result = null;
		try {
			if (validPayment()) {
				paymentDTO.setPaymentChannel(isCheque ? PaymentChannel.CHEQUE : PaymentChannel.CASHED);
				WorkFlowDTO workFlowDTO = new WorkFlowDTO(null, getLoginBranchId(), remark, WorkflowTask.PAYMENT, ReferenceType.LIFE_DEALTH_CLAIM, TransactionType.UNDERWRITING,
						user, responsiblePerson);
				paymentList = lifeClaimService.confirmLifeClaim(lifeClaim, workFlowDTO, paymentDTO);
				paymentDTO = new PaymentDTO(paymentList);
				actualPayment = true;
				disabledOKBtn = true;
				disabledBtn = false;
				addInfoMessage(null, "CONFIRMATION_PROCESS_SUCCESS_REFUND_PARAM", workFlowDTO.getReferenceNo());
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}

		return result;
	}

	/*
	 * private boolean validPayment() { boolean valid = true; String formID =
	 * "lifeDeathClaimCustomerConfirmationForm"; if (isCheque) { if
	 * (paymentDTO.getBankAccountNo() == null ||
	 * paymentDTO.getBankAccountNo().isEmpty()) { addErrorMessage(formID +
	 * ":chequeNo", UIInput.REQUIRED_MESSAGE_ID); valid = false; } if
	 * (paymentDTO.getBank() == null) { addErrorMessage(formID + ":bankName",
	 * UIInput.REQUIRED_MESSAGE_ID); valid = false; } } return valid; }
	 */

	// Refund Form Button Click Event
	public void prepareLifeClaimRefundFormDTO(LifeClaimInsuredPersonBeneficiary claimBeneficiary) {
		LifeClaimRefundFormDTO lifeClaimRefundForm = new LifeClaimRefundFormDTO();

		lifeClaimRefundForm.setClaimRequestId(claimBeneficiary.getLifeClaim().getClaimRequestId());
		lifeClaimRefundForm.setCustomerName(claimBeneficiary.getFullName());
		lifeClaimRefundForm.setAddress(claimBeneficiary.getFullResidentAddress());
		lifeClaimRefundForm.setClaimAmount(claimBeneficiary.getClaimAmount());
		lifeClaimRefundForm.setLoanAmount(claimBeneficiary.getLoanAmount());
		lifeClaimRefundForm.setLoanInterest(claimBeneficiary.getLoanInterest());
		lifeClaimRefundForm.setNetClaimAmount(claimBeneficiary.getNetClaimAmount());
		lifeClaimRefundForm.setRefundNo(claimBeneficiary.getRefundNo());
		lifeClaimRefundForm.setServiceCharges(claimBeneficiary.getLifeClaim().getServiceCharges());
		this.lifeClaimRefundFormDTO = lifeClaimRefundForm;
	}

	// Discharge Form Click Event
	public void prepareLifeClaimDischargeFormDTO(LifeClaimInsuredPersonBeneficiary claimBeneficiary) {
		LifeClaimDischargeFormDTO lifeClaimDischargeForm = new LifeClaimDischargeFormDTO();
		lifeClaimDischargeForm.setPolicyNo(claimBeneficiary.getLifeClaim().getLifePolicy().getPolicyNo());
		lifeClaimDischargeForm.setInsuredPersonName(claimBeneficiary.getLifeClaim().getClaimInsuredPerson().getPolicyInsuredPerson().getFullName());
		lifeClaimDischargeForm.setCustomerName(claimBeneficiary.getFullName());
		lifeClaimDischargeForm.setAddress(claimBeneficiary.getFullResidentAddress());
		lifeClaimDischargeForm.setClaimAmount(claimBeneficiary.getClaimAmount());
		lifeClaimDischargeForm.setLoanAmount(claimBeneficiary.getLoanAmount());
		lifeClaimDischargeForm.setLoanInterest(claimBeneficiary.getLoanInterest());
		lifeClaimDischargeForm.setNetClaimAmount(claimBeneficiary.getNetClaimAmount());
		lifeClaimDischargeForm.setRefundNo(claimBeneficiary.getRefundNo());
		lifeClaimDischargeForm.setBeneficiaryName(claimBeneficiary.getFullName());
		lifeClaimDischargeForm.setCommenmanceDate(claimBeneficiary.getLifeClaim().getLifePolicy().getCommenmanceDate());
		lifeClaimDischargeForm.setSumInsured(new Double(claimBeneficiary.getLifeClaim().getLifePolicy().getSumInsured()).intValue());
		lifeClaimDischargeForm.setIdNo(claimBeneficiary.getIdNo());
		lifeClaimDischargeForm.setFatherName(claimBeneficiary.getFatherName());
		lifeClaimDischargeForm.setServiceCharges(claimBeneficiary.getLifeClaim().getServiceCharges());
		LifeClaimDeathPerson deathPerson = (LifeClaimDeathPerson) claimBeneficiary.getLifeClaim().getClaimInsuredPerson();
		lifeClaimDischargeForm.setMaturityDate(deathPerson.getDeathDate());
		this.lifeClaimDischargeFormDTO = lifeClaimDischargeForm;
	}

	public void selectUser() {
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	/******************************************
	 * End Action Controller
	 ******************************************/

	/********************************************
	 * Getter/Setter
	 *************************************************/

	public String getPresentDate() {
		return presentDate;
	}

	public boolean isAtualPayment() {
		return actualPayment;
	}

	public void setActualPayment(boolean actualPayment) {
		this.actualPayment = actualPayment;
	}

	public boolean getIsCheque() {
		return isCheque;
	}

	public void setIsCheque(boolean isCheque) {
		this.isCheque = isCheque;
	}

	public LifeClaim getLifeClaim() {
		return lifeClaim;
	}

	public List<User> getUserList() {
		return userList;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowList;
	}

	public boolean isShowFormFlg() {
		return showFormFlg;
	}

	public void setShowFormFlg(boolean showFormFlg) {
		this.showFormFlg = showFormFlg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isRejectflag() {
		return rejectflag;
	}

	public void setRejectflag(boolean rejectflag) {
		this.rejectflag = rejectflag;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public boolean isDisabledBtn() {
		return disabledBtn;
	}

	public void setDisabledBtn(boolean isDisabledBtn) {
		this.disabledBtn = isDisabledBtn;
	}

	public boolean isDisabledOKBtn() {
		return disabledOKBtn;
	}

	public void setDisabledOKBtn(boolean disabledOKBtn) {
		this.disabledOKBtn = disabledOKBtn;
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

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public boolean hasSuccessor() {
		for (LifeClaimInsuredPersonBeneficiary beneficiary : lifeClaim.getClaimInsuredPersonBeneficiaryList()) {
			if (beneficiary.getClaimSuccessor() != null) {
				return true;
			}
		}
		return false;
	}

	/******************************************
	 * End Getter/Setter
	 ***********************************************/
	// Payment Channel
	/*
	 * public void changePaymentChannel(AjaxBehaviorEvent event) { if
	 * (!isCheque) { paymentDTO.setBank(null); paymentDTO.setChequeNo(null); } }
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
		
		
		public void changePaymentChannel(AjaxBehaviorEvent event) {
			paymentDTO.setAccountBank(null);
			paymentDTO.setBank(null);
			paymentDTO.setChequeNo(null);
			paymentDTO.setPoNo(null);
		}

		


	public void prePrint() {
		RequestContext.getCurrentInstance().execute("cashClaimRefundPrintDialog.show()");
		if (isCheque) {
			paymentDTO.setPaymentChannel(PaymentChannel.CHEQUE);
		} else {
			paymentDTO.setPaymentChannel(PaymentChannel.CASHED);
		}
	}

	/******************************************
	 * User
	 *********************************************************/

	// prepare for life Death Claim

	private final String reportName = "LifeDeathClaimCashReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		DocumentBuilder.generateLifeDeathClaimCashReceipt(lifeClaim, paymentDTO, dirPath, fileName);
	}

	private final String reportDischargeName = "LifeDeathClaimDischargeCertificate";
	private final String pdfDischargeDirPath = "/pdf-report/" + reportDischargeName + "/" + System.currentTimeMillis() + "/";
	private final String dirDischargePath = getWebRootPath() + pdfDischargeDirPath;
	private final String fileDischargeName = reportDischargeName + ".pdf";

	public String getDischargeReportStream() {
		return pdfDischargeDirPath + fileDischargeName;
	}

	public void generateDischargeReport() {
		DocumentBuilder.generateLifeClaimDischargeCertificate(lifeClaimDischargeFormDTO, dirDischargePath, fileDischargeName);
	}

	public void returnResponsiblePerson(SelectEvent event) {
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

	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
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

	public PaymentChannel getChannelValue() {
		return channelValue;
	}



	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}
	
	public void returnAccountBank(SelectEvent event) {
		Bank accountBank = (Bank) event.getObject();
		paymentDTO.setAccountBank(accountBank);
	}


	
	
}

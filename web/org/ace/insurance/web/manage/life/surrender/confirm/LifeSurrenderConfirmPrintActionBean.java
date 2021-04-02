package org.ace.insurance.web.manage.life.surrender.confirm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.life.surrender.service.interfaces.ILifeSurrenderProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeSurrenderConfirmPrintActionBean")
public class LifeSurrenderConfirmPrintActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Bank bank;
	private PaymentDTO paymentDTO;
	private boolean actualPayment;
	private LifeSurrenderProposal surrenderProposal;
	private List<Payment> paymentList;
	private User user;
	private WorkFlowDTO workFlowDTO;

	
	//for paymenttransfer
	private boolean isCheque;
	//add for transfer

	private boolean isAccountBank;
	private boolean isBank;
	private boolean isTransfer;
	private PaymentChannel channelValue;
	

	

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{LifeSurrenderProposalService}")
	private ILifeSurrenderProposalService surrenderProposalService;

	public void setSurrenderProposalService(ILifeSurrenderProposalService surrenderProposalService) {
		this.surrenderProposalService = surrenderProposalService;
	}

	@ManagedProperty(value = "#{ClaimAcceptedInfoService}")
	private IClaimAcceptedInfoService claimAcceptedInfoService;

	public void setClaimAcceptedInfoService(IClaimAcceptedInfoService claimAcceptedInfoService) {
		this.claimAcceptedInfoService = claimAcceptedInfoService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostConstruct
	public void init() {
		paymentDTO = new PaymentDTO();
		paymentList = new ArrayList<Payment>();
		initializeInjection();
		ClaimAcceptedInfo claimAcceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo(surrenderProposal.getId(), ReferenceType.LIFESURRENDER);
		if (claimAcceptedInfo != null) {
			paymentDTO.setDiscountPercent(0.0);
			paymentDTO.setServicesCharges(claimAcceptedInfo.getServicesCharges());
			paymentDTO.setStampFees(0.0);
		}
	}

	@PreDestroy
	public void destroy() {
		removeParam("surrenderProposal");
		removeParam("workFlowDTO");
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		surrenderProposal = (LifeSurrenderProposal) getParam("surrenderProposal");
		workFlowDTO = (WorkFlowDTO) getParam("workFlowDTO");
	}

	public void addSurrenderReceiptInfo() {
		try {
			workFlowDTO.setTransactionType(TransactionType.UNDERWRITING);
			paymentDTO.setPaymentChannel(channelValue);
			paymentList = surrenderProposalService.confirmLifeSurrenderProposal(surrenderProposal, workFlowDTO, paymentDTO, user.getBranch(), PolicyStatus.SURRENDER);
			paymentDTO = new PaymentDTO(paymentList);
			actualPayment = true;
			addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS_PARAM, surrenderProposal.getProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public PaymentChannel[] getChannelValues() {
		return new PaymentChannel[] { PaymentChannel.CASHED, PaymentChannel.CHEQUE, PaymentChannel.TRANSFER};
	}

	public void returnAccountBank(SelectEvent event) {
		Bank accountBank = (Bank) event.getObject();
		paymentDTO.setAccountBank(accountBank);
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		paymentDTO.setBank(bank);
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		paymentDTO.setAccountBank(null);
		paymentDTO.setBank(null);
		paymentDTO.setChequeNo(null);
		paymentDTO.setPoNo(null);
	}

	
	
	
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
		
	

	private final String reportName = "lifeSurrenderClaimCashReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		DocumentBuilder.generateLifeSurrenderCashReceipt(surrenderProposal, paymentDTO, dirPath, fileName);
	}

	public Bank getBank() {
		return bank;
	}


	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public LifeSurrenderProposal getSurrenderProposal() {
		return surrenderProposal;
	}

	public boolean isActualPayment() {
		return actualPayment;
	}



	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
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

	public PaymentChannel getChannelValue() {
		return channelValue;
	}

	
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public boolean isAccountBank() {
		return isAccountBank;
	}

	public void setAccountBank(boolean isAccountBank) {
		this.isAccountBank = isAccountBank;
	}

	public boolean isCheque() {
		return isCheque;
	}

	public void setCheque(boolean isCheque) {
		this.isCheque = isCheque;
	}




	
	
}

package org.ace.insurance.web.manage.life.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.insurance.life.paidUp.service.interfaces.ILifePaidUpProposalService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.payment.AgentPaymentCashReceiptDTO;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.user.User;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionCashReceiptDTO;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionModel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

/**
 * 
 * LifePaidUpListingFormActionBean
 * 
 * @author Kyaw Myat Htut
 * @since 1.0.0
 * @date 2016/09/20
 */
@ViewScoped
@ManagedBean(name = "LifePaidUpListingFormActionBean")
public class LifePaidUpListingFormActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{LifePolicySummaryService}")
	private ILifePolicySummaryService lifePolicySummaryService;

	public void setLifePolicySummaryService(ILifePolicySummaryService lifePolicySummaryService) {
		this.lifePolicySummaryService = lifePolicySummaryService;
	}

	@ManagedProperty(value = "#{LifePaidUpProposalService}")
	private ILifePaidUpProposalService lifePaidUpProposalService;

	public ILifePaidUpProposalService getLifePaidUpProposalService() {
		return lifePaidUpProposalService;
	}

	public void setLifePaidUpProposalService(ILifePaidUpProposalService lifePaidUpProposalService) {
		this.lifePaidUpProposalService = lifePaidUpProposalService;
	}

	private BillCollectionDTO billCollection;
	private List<BillCollectionDTO> billCollectionList;
	private List<BillCollectionCashReceiptDTO> cashReceiptDTOList;
	private boolean availablePrint;
	private PolicyCriteria policyCriteria;
	private boolean isCheque;
	private boolean renderButton;
	private List<Payment> payments;
	private User user;
	private boolean isTransfer;
	private boolean isBank;
	private boolean isAccBank;
	private PaymentChannel channelValue;
	private String poNo;
	private Bank bank;
	private Bank accountBank;
	private String chequeNo;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		reset();
	}

	public void reset() {
		policyCriteria = new PolicyCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		policyCriteria.setFromDate(cal.getTime());
		Date endDate = new Date();
		policyCriteria.setToDate(endDate);
		billCollection = new BillCollectionDTO();

		channelValue = PaymentChannel.CASHED;
		isAccBank = false;
		isCheque = false;
		isBank = false;
		isTransfer = false;
		bank = null;
		accountBank = null;
		chequeNo = null;
		poNo = null;
		search();
	}

	public boolean validation() {
		boolean result = true;
		if ((policyCriteria.getPolicyCriteria() != null)) {
			if (policyCriteria.getCriteriaValue() == null || policyCriteria.getCriteriaValue().isEmpty()) {
				addErrorMessage("lifePaidUpListForm:policyCriteria", MessageId.ATLEAST_ONE_REQUIRED);
				result = false;
			}
		}

		if (policyCriteria.getCriteriaValue() != null && !policyCriteria.getCriteriaValue().isEmpty()) {
			if (policyCriteria.getPolicyCriteria() == null) {
				addErrorMessage("lifePaidUpListForm:selectPolicyCriteria", MessageId.ATLEAST_ONE_REQUIRED);
				result = false;
			}
		}

		return result;

	}

	public void search() {
		if (validation()) {
			billCollectionList = lifePolicyService.findLifePaidUpProposalByCriteria(policyCriteria);
			for (BillCollectionDTO billCollection : billCollectionList) {
				LifePolicySummary lifePolicySummary = lifePolicySummaryService.findLifePolicyByPolicyNo(billCollection.getPolicyNo());
				if (lifePolicySummary != null) {
					billCollection.setRefund(lifePolicySummary.getRefund());
				}
			}

			availablePrint = false;
			renderButton = true;
		}
	}

	public void confirm() {
		renderButton = true;
		if (!validCorrelationCheck()) {
			return;
		}
		try {
			payments = new ArrayList<Payment>();
			cashReceiptDTOList = new ArrayList<BillCollectionCashReceiptDTO>();
			Payment payment = new Payment();
			payment.setBank(bank);
			payment.setAccountBank(accountBank);
			payment.setChequeNo(chequeNo);
			payment.setPoNo(poNo);
			payment.setPaymentChannel(channelValue);
			payment.setReferenceNo(billCollection.getPolicyId());
			payment.setReferenceType(PolicyReferenceType.LIFE_PAIDUP_CLAIM);
			payment.setBasicPremium(billCollection.getBasicTermPremium());
			payment.setComplete(false);
			payment.setPaymentType(billCollection.getPaymentType());
			payment.setRefund(billCollection.getRealPaidUpAmount());
			payment.setConfirmDate(new Date());
			payment.setServicesCharges(billCollection.getServiceCharges());
			payment.setRenewalInterest(billCollection.getRenewalInterest());
			payment.setLoanInterest(billCollection.getLoanInterest());
			payment.setFromTerm(billCollection.getLastPaymentTerm());
			payments.add(payment);
			cashReceiptDTOList.add(new BillCollectionCashReceiptDTO(payment, billCollection, ReferenceType.LIFE_PAIDUP_PROPOSAL));
			LifePaidUpProposal proposal = lifePaidUpProposalService.findByPolicyNo(billCollection.getPolicyNo());
			// paymentService.prePaymentAndTlfLifePaidUp(payments, proposal);
			lifePaidUpProposalService.updateCompleteStatus(billCollection.getPolicyNo());
			renderButton = false;
			addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS);
			availablePrint = true;
			billCollectionList = lifePolicyService.findLifePaidUpProposalByCriteria(policyCriteria);

		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		bank = null;
		accountBank = null;
		chequeNo = null;
		poNo = null;
	}

	private boolean validCorrelationCheck() {
		boolean valid = true;
		String formID = "lifePaidUpListForm";
		if (isCheque) {
			if (chequeNo == null || chequeNo.isEmpty()) {
				addErrorMessage(formID + ":chequeNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (bank == null) {
				addErrorMessage(formID + ":bankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (accountBank == null) {
				addErrorMessage(formID + ":accountBankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}

		}
		if (isTransfer) {
			if (poNo == null || poNo.isEmpty()) {
				addErrorMessage(formID + ":poNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (bank == null) {
				addErrorMessage(formID + ":bankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (accountBank == null) {
				addErrorMessage(formID + ":accountBankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}

		}

		if (billCollection == null) {
			addErrorMessage("lifePaidUpListForm:lifePolicyInfoTable", MessageId.ATLEAST_ONE_CHECK_REQUIRED);
			valid = false;
		}
		return valid;
	}

	private final String reportName = "LifeBillCollectionCashReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public void generateReport() {
		for (BillCollectionCashReceiptDTO dto : cashReceiptDTOList) {
			LifePolicy policy = lifePolicyService.findLifePolicyById(dto.getBillCollection().getPolicyId());
			dto.setLifePolicy(policy);

			if (policy.getAgent() != null) {
				AgentPaymentCashReceiptDTO agentDto = null;
				// AgentPaymentCashReceiptDTO agentDto =
				// paymentService.getAgentPaymentCashReceipt(policy,
				// InsuranceType.LIFE, user.getBranch(), 0);
				dto.setAgentComission(agentDto);
			}
		}
		// DocumentBuilder.generateLifePaymentBillCashReceipt(cashReceiptDTOList,
		// dirPath, fileName);
	}

	public boolean isAvailablePrint() {
		return availablePrint;
	}

	public BillCollectionDTO getBillCollection() {
		return billCollection;
	}

	public void setBillCollection(BillCollectionDTO lineBean) {
		this.billCollection = lineBean;
	}

	public BillCollectionModel getBillCollectionModel() {
		return new BillCollectionModel(billCollectionList);
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public PolicyCriteria getPolicyCriteria() {
		return policyCriteria;
	}

	public void setPolicyCriteria(PolicyCriteria policyCriteria) {
		this.policyCriteria = policyCriteria;
	}

	public String getSumTotalAmounts() {
		double sumTotalAmounts = 0.0;
		if (billCollectionList != null) {
			for (BillCollectionDTO line : billCollectionList) {
				sumTotalAmounts += line.getTotalAmount();
			}
		}
		return Utils.getCurrencyFormatString(sumTotalAmounts);
	}

	public boolean getIsCheque() {
		return isCheque;
	}

	public void setCheque(boolean isCheque) {
		this.isCheque = isCheque;
	}

	public boolean isRenderButton() {
		return renderButton;
	}

	public void setRenderButton(boolean renderButton) {
		this.renderButton = renderButton;
	}

	public Bank getBank() {
		return bank;
	}

	public void returnBank(SelectEvent event) {
		bank = (Bank) event.getObject();
	}

	public PaymentChannel getChannelValue() {
		return channelValue;
	}

	public void setChannelValue(PaymentChannel channelValue) {
		if (channelValue.equals(PaymentChannel.CHEQUE)) {
			isAccBank = true;
			isCheque = true;
			isBank = true;
			isTransfer = false;
		} else if (channelValue.equals(PaymentChannel.TRANSFER)) {
			isAccBank = true;
			isCheque = false;
			isBank = true;
			isTransfer = true;
		} else {
			isAccBank = false;
			isCheque = false;
			isBank = false;
			isTransfer = false;
		}
		this.channelValue = channelValue;
	}

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}

	public void returnAccountBank(SelectEvent event) {
		accountBank = (Bank) event.getObject();
	}

	public boolean getIsTransfer() {
		return isTransfer;
	}

	public void setTransfer(boolean isTransfer) {
		this.isTransfer = isTransfer;
	}

	public boolean getIsBank() {
		return isBank;
	}

	public void setBank(boolean isBank) {
		this.isBank = isBank;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public Bank getAccountBank() {
		return accountBank;
	}

	public boolean isAccBank() {
		return isAccBank;
	}

	public void setAccBank(boolean isAccBank) {
		this.isAccBank = isAccBank;
	}

	public void setAccountBank(Bank accountBank) {
		this.accountBank = accountBank;
	}

}

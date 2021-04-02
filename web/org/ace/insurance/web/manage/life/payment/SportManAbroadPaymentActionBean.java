package org.ace.insurance.web.manage.life.payment;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
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
@ManagedBean(name = "SportManAbroadPaymentActionBean")
public class SportManAbroadPaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private Payment payment;
	private LifePolicy lifePolicy;
	private User user;
	private List<SportManTravelAbroad> sportManAbroadTravelList;
	private PaymentChannel channelValue;
	private List<Payment> paymentList;
	private boolean receiptPrint;
	private LifeProposal lifeProposal;
	private boolean isAccountBank;
	private boolean isCheque;
	private boolean isBank;
	private boolean isTransfer;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		payment = (payment == null) ? (Payment) getParam("payment") : payment;
	}

	@PreDestroy
	public void destroy() {
		removeParam("payment");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		lifePolicy = lifePolicyService.findLifePolicyById(payment.getReferenceNo());
		paymentList = new ArrayList<Payment>();
		payment.setSalesPoints(lifePolicy.getSalesPoints());
		paymentList.add(payment);
		lifeProposal = lifePolicy.getLifeProposal();
		sportManAbroadTravelList = lifePolicyService.findSportManAbroadListByInvoiceNo(payment.getInvoiceNo());
	}

	public void paymentLifeProposal() {
		try {
			addToPaymentList();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), null, WorkflowTask.ISSUING, ReferenceType.SPORT_MAN_ABROAD,
					TransactionType.UNDERWRITING, user, null);
			lifeProposalService.paymentLifeProposal(lifeProposal, workFlowDTO, paymentList, user.getBranch(), RequestStatus.FINISHED.name());
			addInfoMessage(null, MessageId.PAYMENT_PROCESS_SUCCESS, lifeProposal.getProposalNo());
			receiptPrint = true;
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	private void addToPaymentList() {
		paymentList.get(0).setSalesPoints(payment.getSalesPoints());
		paymentList.get(0).setPaymentChannel(channelValue);
		paymentList.get(0).setAccountBank(payment.getAccountBank());
		paymentList.get(0).setBank(payment.getBank());
		paymentList.get(0).setBankAccountNo(payment.getBankAccountNo());
		paymentList.get(0).setChequeNo(payment.getChequeNo());
		paymentList.get(0).setPoNo(payment.getPoNo());
	}

	public void returnAccountBank(SelectEvent event) {
		Bank accountBank = (Bank) event.getObject();
		payment.setAccountBank(accountBank);
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		payment.setBank(bank);
	}

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		payment.setSalesPoints(salesPoints);
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		if (channelValue.equals(PaymentChannel.CASHED)) {
			payment.setAccountBank(null);
			payment.setBank(null);
			payment.setChequeNo(null);
			payment.setPoNo(null);
			// cashPayment = true;
		} else if (channelValue.equals(PaymentChannel.TRANSFER)) {
			payment.setChequeNo(null);
			payment.setAccountBank(null);
		} else if (channelValue.equals(PaymentChannel.CHEQUE)) {
			payment.setPoNo(null);
			// cashPayment = false;
		}
	}

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

	public PaymentChannel getChannelValue() {
		return channelValue;
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

	/** Generate Report */
	String fileName = "SportManAbroadReceipt.pdf";
	private final String reportName = "LifeReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateCashReceipt() {
		DocumentBuilder.generateLifeReceiptLetter(lifeProposal, payment, true, dirPath, fileName);
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isReceiptPrint() {
		return receiptPrint;
	}

	public void openTemplateDialog() {
		putParam("workFlowList", getWorkFlowList());
		putParam("sportManAbroadTravelList", sportManAbroadTravelList);
		openSportManAbroadInfoTemplate();
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(payment.getInvoiceNo());
	}
}

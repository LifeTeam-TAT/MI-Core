package org.ace.insurance.web.manage.medical.payment;

import java.io.IOException;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.web.manage.medical.proposal.MedProDTO;
import org.ace.insurance.web.manage.medical.proposal.factory.MedicalProposalFactory;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "MedicalPaymentActionBean")
public class MedicalPaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CustomIDGenerator}")
	private ICustomIDGenerator customIDGenerator;

	public void setCustomIDGenerator(ICustomIDGenerator customIDGenerator) {
		this.customIDGenerator = customIDGenerator;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalProposalService) {
		this.medicalProposalService = medicalProposalService;
	}

	private String loginBranchId;
	private User user;
	private boolean cashPayment = true;
	private String remark;
	private List<Payment> paymentList;
	private User responsiblePerson;
	private List<WorkFlowHistory> workflowHistoryList;
	private MedicalProposal medicalProposal;
	private PaymentChannel channelValue;
	private boolean cheque;
	private boolean transfer;
	private boolean accountBank;
	private boolean bank;
	private Payment payment;
	private boolean receiptPrint;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		loginBranchId = user.getLoginBranch().getId();
		medicalProposal = (medicalProposal == null) ? (MedicalProposal) getParam("medicalProposal") : medicalProposal;
		workflowHistoryList = workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId());
	}

	private boolean isFinished() {
		if (medicalProposal == null) {
			return true;
		} else {
			if (workFlowService.findWorkFlowByRefNo(medicalProposal.getId(), WorkflowTask.PAYMENT) == null) {
				paymentList = paymentService.findByProposal(medicalProposal.getId(), getPolicyReferenceType(), true);
				return true;
			} else
				return false;
		}
	}

	@PreDestroy
	public void destroy() {
		removeParam("medicalProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		paymentList = paymentService.findByProposal(medicalProposal.getId(), getPolicyReferenceType(), false);
		payment = new Payment(paymentList);
		payment.setSalesPoints(medicalProposal.getSalesPoints());
		if (isFinished()) {
			receiptPrint = true;
		}
	}

	private ReferenceType getReferenceType() {
		ReferenceType referenceType = null;
		switch (medicalProposal.getHealthType()) {
			case CRITICALILLNESS:
				referenceType = ReferenceType.CRITICAL_ILLNESS;
				break;
			case HEALTH:
				referenceType = ReferenceType.HEALTH;
				break;
			case MICROHEALTH:
				referenceType = ReferenceType.MICRO_HEALTH;
				break;
			default:
				break;
		}
		return referenceType;
	}

	private PolicyReferenceType getPolicyReferenceType() {
		PolicyReferenceType referenceType = null;
		switch (medicalProposal.getHealthType()) {
			case CRITICALILLNESS:
				referenceType = PolicyReferenceType.CRITICAL_ILLNESS_POLICY;
				break;
			case HEALTH:
				referenceType = PolicyReferenceType.HEALTH_POLICY;
				break;
			case MICROHEALTH:
				referenceType = PolicyReferenceType.MICRO_HEALTH_POLICY;
				break;
			default:
				break;
		}
		return referenceType;
	}

	private void loadPaymentData() {
		for (Payment p : paymentList) {
			p.setPaymentChannel(channelValue);
			p.setAccountBank(payment.getAccountBank());
			p.setBank(payment.getBank());
			p.setBankAccountNo(payment.getBankAccountNo());
			p.setChequeNo(payment.getChequeNo());
			p.setPoNo(payment.getPoNo());
			p.setSalesPoints(payment.getSalesPoints());
		}
	}

	public void paymentMedicalProposal() {
		try {
			loadPaymentData();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, WorkflowTask.ISSUING, getReferenceType(), TransactionType.UNDERWRITING, user,
					responsiblePerson);
			medicalProposalService.paymentMedicalProposal(medicalProposal, workFlowDTO, paymentList, user.getBranch());
			addInfoMessage(null, MessageId.PAYMENT_PROCESS_SUCCESS, medicalProposal.getProposalNo());
			receiptPrint = true;
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void returnAccountBank(SelectEvent event) {
		Bank accountBank = (Bank) event.getObject();
		payment.setAccountBank(accountBank);
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		payment.setBank(bank);
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		payment.setSalesPoints(salesPoints);
	}

	public boolean isCashPayment() {
		return cashPayment;
	}

	public EnumSet<PaymentChannel> getPaymentChannels() {
		return EnumSet.of(PaymentChannel.CASHED, PaymentChannel.CHEQUE);
	}

	// TODO FIXME PSH
	/*
	 * public void changePaymentChannel(AjaxBehaviorEvent e) { PaymentChannel
	 * paymentChannel = (PaymentChannel) ((SelectOneMenu)
	 * e.getSource()).getValue(); if
	 * (paymentChannel.equals(PaymentChannel.CHEQUE)) { cashPayment = false; }
	 * else { cashPayment = true; } }
	 */

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public MedProDTO getMedicalProposalDTO() {
		return MedicalProposalFactory.getMedProDTO(medicalProposal);
	}

	public Payment getPayment() {
		return payment;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowHistoryList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void openTemplateDialog() {
		putParam("medicalProposal", medicalProposal);
		putParam("workFlowList", getWorkFlowList());
		openMedicalProposalInfoTemplate();
	}

	public void selectUser() {
		selectUser(WorkflowTask.ISSUING, WorkFlowType.MEDICAL_INSURANCE, TransactionType.UNDERWRITING, loginBranchId, null);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public PaymentChannel getChannelValue() {
		return channelValue;
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		if (channelValue.equals(PaymentChannel.CASHED)) {
			payment.setAccountBank(null);
			payment.setBank(null);
			payment.setChequeNo(null);
			payment.setPoNo(null);
		} else if (channelValue.equals(PaymentChannel.TRANSFER)) {
			payment.setChequeNo(null);
			payment.setAccountBank(null);
		} else if (channelValue.equals(PaymentChannel.CHEQUE)) {
			payment.setPoNo(null);
		}
	}

	public void setChannelValue(PaymentChannel channelValue) {
		if (channelValue.equals(PaymentChannel.CHEQUE)) {
			accountBank = true;
			cheque = true;
			bank = true;
			transfer = false;
		} else if (channelValue.equals(PaymentChannel.TRANSFER)) {
			accountBank = true;
			cheque = false;
			bank = true;
			transfer = true;
		} else {
			accountBank = false;
			cheque = false;
			bank = false;
			transfer = false;
		}
		this.channelValue = channelValue;
	}

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}

	public boolean isCheque() {
		return cheque;
	}

	public void setCheque(boolean cheque) {
		this.cheque = cheque;
	}

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public boolean isAccountBank() {
		return accountBank;
	}

	public void setAccountBank(boolean accountBank) {
		this.accountBank = accountBank;
	}

	public boolean isBank() {
		return bank;
	}

	public void setBank(boolean bank) {
		this.bank = bank;
	}

	/** Generate Report */
	String fileName = "HealthCashReceipt.pdf";
	private final String reportName = "LifeReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateCashReceipt() {
		DocumentBuilder.generateMedicalReceiptLetter(medicalProposal, paymentList.get(0), dirPath, fileName);
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

}

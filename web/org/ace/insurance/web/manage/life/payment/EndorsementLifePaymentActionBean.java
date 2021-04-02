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
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
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
@ManagedBean(name = "EndorsementLifePaymentActionBean")
public class EndorsementLifePaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{BankService}")
	private IBankService bankService;

	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private List<Payment> paymentList;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private boolean isSportMan;
	private boolean isShortTermEndownment;
	private boolean isSnakeBite;
	private String remark;
	private Payment payment;
	private boolean isAccountBank;
	private boolean isCheque;
	private boolean isBank;
	private boolean isTransfer;
	private boolean showPrintPreview;
	private User responsiblePerson;
	private List<LifePolicy> lifePolicyList;
	private LifeEndorseInfo lifeEndorseInfo;
	private PaymentChannel channelValue;
	private String fileName;
	private boolean receiptPrint;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		lifePolicyList = new ArrayList<LifePolicy>();
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		isSportMan = KeyFactorChecker.isSportMan(product);
		PolicyReferenceType referenceType = isPersonalAccident ? PolicyReferenceType.PA_POLICY
				: isFarmer ? PolicyReferenceType.FARMER_POLICY
						: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
								: isShortTermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
										: isSportMan ? PolicyReferenceType.SPORT_MAN_POLICY
												: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY : PolicyReferenceType.ENDOWNMENT_LIFE_POLICY;
		paymentList = paymentService.findByProposal(lifeProposal.getId(), referenceType, false);
		payment = new Payment(paymentList);
		payment.setSalesPoints(lifeProposal.getSalesPoints());
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId(lifeProposal.getId());
		lifePolicyList.add(lifePolicy);
		lifeEndorseInfo = lifeEndorsementService.findLastLifeEndorseInfoByPolicyId(lifeProposal.getLifePolicy().getId());
		fileName = isPersonalAccident ? "PersonalAccidentCashReceipt.pdf"
				: isFarmer ? "FarmerCashReceipt.pdf" : isSnakeBite ? "SnakeBiteCashReceipt.pdf" : isSportMan ? "SportManCashReceipt.pdf" : "LifeCashReceipt.pdf";
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public void selectUser() {
		WorkflowTask workflowTask = WorkflowTask.ISSUING;
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT : isFarmer ? WorkFlowType.FARMER : WorkFlowType.LIFE;
		selectUser(workflowTask, workFlowType, TransactionType.ENDORSEMENT, user.getLoginBranch().getId(), null);
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PaymentChannel getChannelValue() {
		return channelValue;
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

	public boolean isPersonalAccident() {
		return isPersonalAccident;
	}

	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public boolean isFarmer() {
		return isFarmer;
	}

	public boolean isAccountBank() {
		return isAccountBank;
	}

	public boolean isCheque() {
		return isCheque;
	}

	public boolean isBank() {
		return isBank;
	}

	public boolean isTransfer() {
		return isTransfer;
	}

	public boolean isShowPrintPreview() {
		return showPrintPreview;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LifeEndorseInfo getLifeEndorseInfo() {
		return lifeEndorseInfo;
	}

	public void paymentLifeProposal() {
		try {
			if (validPayment()) {
				addToPaymentList();

				WorkflowTask workflowTask = WorkflowTask.ISSUING;
				ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
						: isFarmer ? ReferenceType.FARMER
								: isSnakeBite ? ReferenceType.SNAKE_BITE
										: isShortTermEndownment ? ReferenceType.SHORT_ENDOWMENT_LIFE
												: isGroupLife ? ReferenceType.GROUP_LIFE : isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : ReferenceType.SPORT_MAN;
				WorkFlowDTO workFlowDTO = null;
				workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.ENDORSEMENT, user,
						responsiblePerson);
				lifeEndorsementService.paymentLifeProposal(lifeProposal, workFlowDTO, paymentList, user.getBranch(), RequestStatus.FINISHED.name());
				addInfoMessage(null, MessageId.PAYMENT_PROCESS_SUCCESS, lifeProposal.getProposalNo());
				receiptPrint = true;
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	private void addToPaymentList() {
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

	private boolean validPayment() {
		boolean valid = true;
		String formID = "lifePaymentForm";
		if (channelValue.equals(PaymentChannel.CHEQUE)) {
			if (payment.getChequeNo() == null || payment.getChequeNo().isEmpty()) {
				addErrorMessage(formID + ":chequeNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (payment.getBank() == null) {
				addErrorMessage(formID + ":bankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (payment.getAccountBank() == null) {
				addErrorMessage(formID + ":accountBankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		if (channelValue.equals(PaymentChannel.TRANSFER)) {
			if (payment.getPoNo() == null || payment.getPoNo().isEmpty()) {
				addErrorMessage(formID + ":poNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (payment.getBank() == null) {
				addErrorMessage(formID + ":bankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (payment.getAccountBank() == null) {
				addErrorMessage(formID + ":accountBankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		return valid;
	}

	public List<LifePolicy> getLifePolicyList() {
		return lifePolicyList;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
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

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		payment.setAccountBank(null);
		payment.setBank(null);
		payment.setChequeNo(null);
		payment.setPoNo(null);
	}

	/* Life Proposal Template */

	@ManagedProperty(value = "#{LifePolicyHistoryService}")
	private ILifePolicyHistoryService lifePolicyHistoryService;

	public void setLifePolicyHistoryService(ILifePolicyHistoryService lifePolicyHistoryService) {
		this.lifePolicyHistoryService = lifePolicyHistoryService;
	}

	@ManagedProperty(value = "#{LifePolicySummaryService}")
	private ILifePolicySummaryService lifePolicySummaryService;

	public void setLifePolicySummaryService(ILifePolicySummaryService lifePolicySummaryService) {
		this.lifePolicySummaryService = lifePolicySummaryService;
	}

	private List<LifePolicyHistory> lifePolicyHistoryList;

	public List<LifePolicyHistory> getLifePolicyHistoryList() {
		if (lifePolicyHistoryList == null) {
			if (lifeProposal.getLifePolicy() != null) {
				lifePolicyHistoryList = lifePolicyHistoryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getPolicyNo());
			}
		}
		return lifePolicyHistoryList;
	}

	public LifePolicySummary getLifePolicySummary() {
		LifePolicySummary summary = lifePolicySummaryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getId());
		return summary;
	}

	/** Generate Report */
	private final String reportName = "LifeReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateCashReceipt() {
		DocumentBuilder.generateLifeReceiptLetter(lifeProposal, paymentList.get(0), false, dirPath, fileName);
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPageHeader() {
		return "Life Endorsement Proposal Payment";
	}

	public boolean isReceiptPrint() {
		return receiptPrint;
	}

}

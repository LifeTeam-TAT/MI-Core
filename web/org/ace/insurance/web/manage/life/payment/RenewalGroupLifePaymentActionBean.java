package org.ace.insurance.web.manage.life.payment;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.EndorsementUtil;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifePolicyService;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "RenewalGroupLifePaymentActionBean")
public class RenewalGroupLifePaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RenewalGroupLifeProposalService}")
	private IRenewalGroupLifeProposalService renewalGroupLifeProposalService;

	public void setRenewalGroupLifeProposalService(IRenewalGroupLifeProposalService renewalGroupLifeProposalService) {
		this.renewalGroupLifeProposalService = renewalGroupLifeProposalService;
	}

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

	@ManagedProperty(value = "#{RenewalGroupLifePolicyService}")
	private IRenewalGroupLifePolicyService renewalGroupLifePolicyService;

	public void setRenewalGroupLifePolicyService(IRenewalGroupLifePolicyService renewalGroupLifePolicyService) {
		this.renewalGroupLifePolicyService = renewalGroupLifePolicyService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private List<Payment> paymentList;
	private String remark;
	private Payment payment;
	private boolean cashPayment = true;
	private boolean isAccountBank;
	private boolean isCheque;
	private boolean isBank;
	private boolean isTransfer;
	private boolean showPrintPreview;
	private PaymentChannel channelValue;
	private LifePolicy lifePolicy;

	private User responsiblePerson;
	private List<WorkFlowHistory> workflowHistoryList;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
		workflowHistoryList = workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		// FIXME CHECK REFTYPE
		paymentList = paymentService.findByProposal(lifeProposal.getId(), PolicyReferenceType.GROUP_LIFE_POLICY, false);
		payment = new Payment(paymentList);
		payment.setSalesPoints(lifeProposal.getSalesPoints());
		lifePolicy = renewalGroupLifePolicyService.findLifePolicyByProposalId(lifeProposal.getId());
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public void selectUser() {
		selectUser(WorkflowTask.ISSUING, WorkFlowType.LIFE, TransactionType.RENEWAL, user.getLoginBranch().getId(), null);
	}

	public boolean isCashPayment() {
		return cashPayment;
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

	public PaymentChannel getChannelValue() {
		return channelValue;
	}

	public EnumSet<PaymentChannel> getPaymentChannels() {
		return EnumSet.of(PaymentChannel.CASHED, PaymentChannel.CHEQUE);
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

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowHistoryList;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private void addToPaymentList() {
		paymentList.get(0).setPaymentChannel(channelValue);
		paymentList.get(0).setAccountBank(payment.getAccountBank());
		paymentList.get(0).setBank(payment.getBank());
		paymentList.get(0).setBankAccountNo(payment.getBankAccountNo());
		paymentList.get(0).setChequeNo(payment.getChequeNo());
		paymentList.get(0).setPoNo(payment.getPoNo());
	}

	public String paymentLifeProposal() {
		addToPaymentList();
		String result = null;
		try {
			WorkflowTask workflowTask = WorkflowTask.ISSUING;
			// FIXME CHECK REFTYPE
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, ReferenceType.GROUP_LIFE,
					TransactionType.RENEWAL, user, responsiblePerson);
			if (!lifePolicy.isEndorsementApplied()) {
				Date validDate = paymentDateStatus(lifePolicy);
				renewalGroupLifePolicyService.updatePaymentDate(lifePolicy.getId(), new Date(), validDate);
			}
			renewalGroupLifeProposalService.paymentLifeProposal(lifeProposal, workFlowDTO, paymentList, user.getBranch(), RequestStatus.FINISHED.name());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	private Date paymentDateStatus(LifePolicy lifePolicy) {
		int addMonth = 0;
		if (lifePolicy.getPaymentType().getName().equals("ANNUAL")) {
			addMonth = 12;
		} else if (lifePolicy.getPaymentType().getName().equals("SEMI-ANNUAL")) {
			addMonth = 6;
		} else if (lifePolicy.getPaymentType().getName().equals("QUARTER")) {
			addMonth = 3;
		} else if (lifePolicy.getPaymentType().getName().equals("LUMPSUM")) {
			addMonth = lifePolicy.getPeriodMonth() * 12;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, addMonth);
		Date paymentValidDate = cal.getTime();
		return paymentValidDate;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	/* For Template */
	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

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

	public boolean isEndorse(LifeProposal lifeProposal) {
		if (lifeProposal == null) {
			return false;
		}
		return EndorsementUtil.isEndorsementProposal(lifeProposal.getLifePolicy());
	}

}

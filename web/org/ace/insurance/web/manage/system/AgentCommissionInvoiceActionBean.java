package org.ace.insurance.web.manage.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionDTO;
import org.ace.insurance.report.agent.service.interfaces.IAgentSanctionService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AgentCommissionInvoiceActionBean")
public class AgentCommissionInvoiceActionBean<T extends IDataModel> extends BaseBean {

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@ManagedProperty(value = "#{AgentSanctionService}")
	private IAgentSanctionService agentSanctionService;

	public void setAgentSanctionService(IAgentSanctionService agentSanctionService) {
		this.agentSanctionService = agentSanctionService;
	}

	private User user;
	private User responsiblePerson;
	private String remark;
	private AgentSanctionCriteria criteria;
	private List<AgentSanctionDTO> sanctionList;
	private List<AgentSanctionDTO> selectedSanctionList;
	private List<Currency> currencyList;
	private boolean disablePrintBtn = true;
	private PaymentChannel paymentChannel;
	private Bank bank;
	private String accountNo;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
	}

	@PostConstruct
	public void init() {
		resetCriteria();
		initializeInjection();
		currencyList = currencyService.findAllCurrency();
	}

	private void resetCriteria() {
		criteria = new AgentSanctionCriteria();
		criteria.setEnquiry(false);
		responsiblePerson = null;
		if (criteria.getStartDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			criteria.setStartDate(cal.getTime());
		}

		if (criteria.getEndDate() == null) {
			criteria.setEndDate(new Date());
		}

		sanctionList = new ArrayList<>();
		selectedSanctionList = new ArrayList<>();
	}

	public void filter() {
		sanctionList = agentSanctionService.findAgentSanctionDTO(criteria);
	}

	public void invoiceAgentCommission() {
		try {
			if (validationSanction()) {
				WorkFlowDTO workFlow = new WorkFlowDTO(null, null, remark, WorkflowTask.PAYMENT, ReferenceType.AGENT_COMMISSION, TransactionType.UNDERWRITING, user,
						responsiblePerson);
				agentSanctionService.invoicedAgentSanction(selectedSanctionList, workFlow, paymentChannel, bank, accountNo);
				addInfoMessage(null, MessageId.AGENT_COMMISSION_INVOICE_SUCCESS);
				disablePrintBtn = false;
			} else {
				addErrorMessage("agentCommissionListForm:varTableMsg", MessageId.SELECT_ONE_MOTORCLAIMITEM);
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}

	}

	private boolean validationSanction() {
		return (sanctionList != null && !sanctionList.isEmpty()) ? true : false;
	}

	public void reset() {
		resetCriteria();
		sanctionList = new ArrayList<>();
		disablePrintBtn = false;
	}

	public void selectUser() {
		String branchId = criteria.getBranchId();
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.AGENT_COMMISSION, TransactionType.UNDERWRITING, null, branchId);
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public List<AgentSanctionDTO> getSanctionList() {
		return sanctionList;
	}

	public EnumSet<PaymentChannel> getPaymentChannels() {
		return EnumSet.of(PaymentChannel.CASHED, PaymentChannel.CHEQUE);
	}

	public boolean isDisablePrintBtn() {
		return disablePrintBtn;
	}

	public List<AgentSanctionDTO> getSelectedSanctionList() {
		return selectedSanctionList;
	}

	public void setSelectedSanctionList(List<AgentSanctionDTO> selectedSanctionList) {
		this.selectedSanctionList = selectedSanctionList;
	}

	public AgentSanctionCriteria getCriteria() {
		return criteria;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		if (PaymentChannel.CASHED.equals(paymentChannel)) {
			this.bank = null;
			this.accountNo = null;
		}

	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Bank getBank() {
		return bank;
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgentId(agent.getId());
		criteria.setAgentName(agent.getFullName());
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnBank(SelectEvent event) {
		bank = (Bank) event.getObject();
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private final String reportName = "AgentInvoice";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public void generateReport() {
	}

	public String getStream() {
		return pdfDirPath + fileName;
	}

	public void handleClose(CloseEvent event) {
		try {
			FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

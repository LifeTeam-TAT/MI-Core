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
import org.ace.insurance.life.claim.service.interfaces.IClaimMedicalFeesService;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.report.agent.ClaimMedicalFeesCriteria;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ClaimMedicalFeeSanctionActionBean")
public class ClaimMedicalFeeSanctionActionBean<T extends IDataModel> extends BaseBean {

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService claimProposalService;

	public void setClaimProposalService(ILifeClaimProposalService claimProposalService) {
		this.claimProposalService = claimProposalService;
	}

	@ManagedProperty(value = "#{ClaimMedicalFeesService}")
	private IClaimMedicalFeesService claimMedicalFeesService;

	public void setClaimMedicalFeesService(IClaimMedicalFeesService claimMedicalFeesService) {
		this.claimMedicalFeesService = claimMedicalFeesService;
	}

	private User user;
	private User responsiblePerson;
	private String remark;
	private ClaimMedicalFeesCriteria criteria;
	private List<LifeClaimMedicalFeeDTO> medicalFeesList;
	private List<LifeClaimMedicalFeeDTO> selectedMedicalFeesList;

	private List<Currency> currencyList;
	private boolean disablePrintBtn = true;
	private PaymentChannel paymentChannel;
	private Bank bank;
	private String accountNo;
	private Hospital hospital;

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
		criteria = new ClaimMedicalFeesCriteria();
		hospital = new Hospital();
		responsiblePerson = null;
		if (criteria.getStartDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			criteria.setStartDate(cal.getTime());
		}

		if (criteria.getEndDate() == null) {
			criteria.setEndDate(new Date());
		}

		medicalFeesList = new ArrayList<>();
		selectedMedicalFeesList = new ArrayList<>();
	}

	public void filter() {
		medicalFeesList = claimProposalService.findLifeClaimMedicalFeeSanction(criteria);
	}

	private boolean validationSanction() {
		return (selectedMedicalFeesList != null && !selectedMedicalFeesList.isEmpty()) ? true : false;
	}

	public void sanctionMedicalFees() {
		try {
			if (validationSanction()) {
				WorkFlowDTO workFlow = new WorkFlowDTO(null, null, remark, WorkflowTask.PAYMENT, ReferenceType.CLAIM_MEDICAL_FEES, TransactionType.CLAIM, user, responsiblePerson);
				claimProposalService.sanctionClaimMedicalFees(selectedMedicalFeesList, workFlow, paymentChannel, bank, accountNo);
				addInfoMessage(null, MessageId.CLAIM_MEDICAL_SANCTION_SUCCESS);
				disablePrintBtn = false;
			} else {
				addErrorMessage("agentCommissionListForm:varTableMsg", MessageId.SELECT_ONE_MOTORCLAIMITEM);
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}

	}

	public void reset() {
		resetCriteria();
		medicalFeesList = new ArrayList<>();
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

	public List<LifeClaimMedicalFeeDTO> getMedicalFeesList() {
		return medicalFeesList;
	}

	public List<LifeClaimMedicalFeeDTO> getSelectedMedicalFeesList() {
		return selectedMedicalFeesList;
	}

	public void setMedicalFeesList(List<LifeClaimMedicalFeeDTO> medicalFeesList) {
		this.medicalFeesList = medicalFeesList;
	}

	public void setSelectedMedicalFeesList(List<LifeClaimMedicalFeeDTO> selectedMedicalFeesList) {
		this.selectedMedicalFeesList = selectedMedicalFeesList;
	}

	public EnumSet<PaymentChannel> getPaymentChannels() {
		return EnumSet.of(PaymentChannel.CASHED, PaymentChannel.CHEQUE);
	}

	public boolean isDisablePrintBtn() {
		return disablePrintBtn;
	}

	public ClaimMedicalFeesCriteria getCriteria() {
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

	private final String reportName = "MedicalFees Sanction";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public void generateReport() {

		DocumentBuilder.generateClaimMedicalFeesSanctionReport(selectedMedicalFeesList, hospital, dirPath, fileName);

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

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public void returnHospital(SelectEvent event) {
		Hospital medicalPlace = (Hospital) event.getObject();
		hospital = medicalPlace;
		criteria.setHospitalId(medicalPlace.getId());
	}

}

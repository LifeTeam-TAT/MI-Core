package org.ace.insurance.web.manage.life.studentLife.confirm;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AddNewStudentLifeConfirmationActionBean")
public class AddNewStudentLifeConfirmationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	@ManagedProperty(value = "#{AcceptedInfoService}")
	private IAcceptedInfoService acceptedInfoService;

	public void setAcceptedInfoService(IAcceptedInfoService acceptedInfoService) {
		this.acceptedInfoService = acceptedInfoService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private WorkFlowDTO workFlowDTO;
	private String fileName;
	// private boolean actualPayment;
	private int periodInsurance;
	private int insuredAge;
	private int maxTerm;
	private ProposalInsuredPerson insuredPerson;
	private boolean approvedProposal = true;
	private String remark;
	private User responsiblePerson;
	private PaymentDTO paymentDTO;
	private PaymentDTO payment;
	private Branch branch;
	private String status;
	private Date effectiveDate;
	private boolean isUserDefinedDate;
	private boolean showPrintPreview;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
		workFlowDTO = (workFlowDTO == null) ? (WorkFlowDTO) getParam("workFlowDTO") : workFlowDTO;
		showPrintPreview = false;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		isUserDefinedDate = true;
		if (isUserDefinedDate) {
			effectiveDate = lifeProposal.getStartDate();
		}
		approvedProposal = true;
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			if (!pv.isApproved()) {
				approvedProposal = false;
				break;
			}
		}
		payment = new PaymentDTO();
		paymentDTO = new PaymentDTO();
		AcceptedInfo acceptedInfo = acceptedInfoService.findAcceptedInfoByReferenceNo(lifeProposal.getId());
		if (acceptedInfo != null) {
			payment.setDiscountPercent(acceptedInfo.getDiscountPercent());
			payment.setServicesCharges(acceptedInfo.getServicesCharges());
			payment.setStampFees(acceptedInfo.getStampFeesAmount());
		}
		payment.setConfirmDate(new Date());
		fileName = "StudentLifeCashReceipt.pdf";
		insuredPerson = lifeProposal.getProposalInsuredPersonList().get(0);
		maxTerm = insuredPerson.getProduct().getMaxTerm();
		onDateSelect();
		if (isFinished()) {
			showPrintPreview = true;
		}
	}

	private boolean isFinished() {
		if (lifeProposal == null) {
			return true;
		} else {
			if (workFlowService.findWorkFlowByRefNo(lifeProposal.getId(), WorkflowTask.CONFIRMATION) == null)
				return true;
			else
				return false;
		}
	}

	public void onDateSelect() {
		insuredAge = getAgeForNextYear(insuredPerson.getDateOfBirth());
		periodInsurance = maxTerm - insuredAge + 1;
		insuredPerson.setAge(insuredAge);
		lifeProposal.setPeriodMonth(periodInsurance * 12);
		if (insuredAge < 1 || insuredAge > 12) {
			addErrorMessage("proposalReceiiptEntryForm:insuredPersonAge", MessageId.CHILD_AGE_MUST_BE_BETWEEN_30DAYS_AND_12YEARS);
			return;
		}
		setKeyFactorValue(insuredPerson);
		lifeProposalService.calculatePremium(lifeProposal);
		lifeProposalService.calculateTermPremium(lifeProposal);
		paymentDTO.setBasicPremium(lifeProposal.getTotalTermPremium());
		// paymentDTO.setAddOnPremium(lifeProposal.getTotalAddOnTermPremium());
	}

	public int getAgeForNextYear(Date dateOfBirth) {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(effectiveDate);
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dateOfBirth);
		cal_2.set(Calendar.YEAR, currentYear);

		if (DateUtils.resetEndDate(effectiveDate).after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	private void setKeyFactorValue(ProposalInsuredPerson person) {
		for (InsuredPersonKeyFactorValue vehKF : person.getKeyFactorValueList()) {
			KeyFactor kf = vehKF.getKeyFactor();
			if (KeyFactorChecker.isAge(kf)) {
				vehKF.setValue(person.getAge() + "");
			}
			if (KeyFactorChecker.isTerm(kf)) {
				vehKF.setValue(lifeProposal.getPeriodOfYears() + "");
			}
		}
	}

	public boolean isApprovedProposal() {
		return approvedProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void addNewLifeProposalReceiptInfo() {
		try {
			WorkflowTask workflowTask = lifeProposal.isSkipPaymentTLF() ? WorkflowTask.ISSUING : WorkflowTask.PAYMENT;
			ReferenceType referenceType = ReferenceType.STUDENT_LIFE;
			workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			lifeProposal.setStartDate(effectiveDate);
			if (effectiveDate == null) {
				lifeProposal.setEndDate(null);
			}
			if (insuredAge < 1 || insuredAge > 12) {
				addErrorMessage("proposalReceiiptEntryForm:insuredPersonAge", MessageId.CHILD_AGE_MUST_BE_BETWEEN_30DAYS_AND_12YEARS);
			} else {
				List<Payment> paymentList = null;
				if (lifeProposal.isSkipPaymentTLF()) {
					paymentList = lifeProposalService.confirmSkipPaymentTLFLifeProposal(lifeProposal, workFlowDTO, paymentDTO, branch, status);
				} else {
					paymentList = lifeProposalService.confirmLifeProposal(lifeProposal, workFlowDTO, paymentDTO, branch, status);
				}
				paymentDTO = new PaymentDTO(paymentList);
				// actualPayment = true;
				showPrintPreview = true;
				addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS_PARAM, lifeProposal.getProposalNo());
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String editLifeProposal() {
		putParam("insuranceType", InsuranceType.STUDENT_LIFE);
		putParam("lifeProposal", lifeProposal);
		putParam("editLifeProposal", true);
		return "addNewStudentLifeProposal";
	}

	public String denyLifeProposal() {
		String result = null;
		try {
			WorkflowTask workflowTask = WorkflowTask.REJECT;
			ReferenceType referenceType = ReferenceType.STUDENT_LIFE;
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING,
					user, responsiblePerson);
			lifeProposalService.rejectLifeProposal(lifeProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.DENY_PROCESS_OK);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void isAlreadyPaid() {
		responsiblePerson = null;
		isUserDefinedDate = true;
	}

	public void selectUser() {
		WorkflowTask workflowTask = WorkflowTask.PAYMENT;
		WorkFlowType workFlowType = WorkFlowType.STUDENT_LIFE;
		selectUser(workflowTask, workFlowType, TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);

	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public LifePolicySummary getLifePolicySummary() {
		return lifePolicySummaryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getId());
	}

	public String getPageHeader() {
		return "Student Life Proposal Confirmation";
	}

	public void openTemplateDialog() {
		putParam("lifeProposal", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openStudentLifeInfoTemplate();
	}

	private final String reportName = "lifePaymentInvoice";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		String customerName = lifeProposal.getCustomerName();
		if (customerName.contains("\\")) {
			customerName = customerName.replace("\\", "");
		}
		if (customerName.contains("/")) {
			customerName = customerName.replace("/", "");
		}
		fileName = "StudentLife_" + customerName + "_Receipt" + ".pdf";
		DocumentBuilder.generateLifeInvoice(lifeProposal, paymentDTO, dirPath, fileName);
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public boolean isUserDefinedDate() {
		return isUserDefinedDate;
	}

	public void setUserDefinedDate(boolean isUserDefinedDate) {
		this.isUserDefinedDate = isUserDefinedDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean getIsShowPrintPreview() {
		return showPrintPreview;
	}

	public int getPeriodInsurance() {
		return periodInsurance;
	}

	public int getInsuredAge() {
		return insuredAge;
	}

}

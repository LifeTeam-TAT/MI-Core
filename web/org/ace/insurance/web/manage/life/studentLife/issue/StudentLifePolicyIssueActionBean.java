package org.ace.insurance.web.manage.life.studentLife.issue;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.ListSplitor;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonBeneficiariesHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;

@ViewScoped
@ManagedBean(name = "StudentLifePolicyIssueActionBean")
public class StudentLifePolicyIssueActionBean extends BaseBean implements Serializable {
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

	@ManagedProperty(value = "#{LifePolicyHistoryService}")
	private ILifePolicyHistoryService lifePolicyHistoryService;

	public void setLifePolicyHistoryService(ILifePolicyHistoryService lifePolicyHistoryService) {
		this.lifePolicyHistoryService = lifePolicyHistoryService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private LifeEndorseInfo lifeEndorseInfo;

	private List<LifePolicyHistory> lifePolicyHistoryList;
	private Date todaydate = new Date();
	private LifePolicyHistory lifePolicyHistory;
	private List<LifePolicy> lifePolicyList;
	private List<Payment> paymentList;
	private LifePolicy lifePolicy;
	private PaymentDTO paymentDTO;
	private boolean nilExcess;
	private boolean showPreview;
	private boolean disableIssueBtn;
	private boolean disableSetUPBtn;
	private String AddDispline;
	private String Reasons;
	private String Changes;

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
		disableSetUPBtn = true;
		lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId(lifeProposal.getId());
		lifePolicyList.add(lifePolicy);
		PolicyReferenceType policyReferenceType = PolicyReferenceType.STUDENT_LIFE_POLICY;
		paymentList = paymentService.findByProposal(lifeProposal.getId(), policyReferenceType, true);
		paymentDTO = new PaymentDTO(paymentList);
		if (lifeProposal.getComplete()) {
			this.disableIssueBtn = true;
		}

		fileName = "StudentLifeIssue.pdf";
	}

	public void preparePolicyIssue(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public int getYear() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		return year;
	}

	public int getMonth() {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH);
		return month + 1;
	}

	public int getDay() {
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	public void issuePolicy() {
		try {
			lifeProposalService.issueLifeProposal(lifeProposal);
			lifeProposal = lifeProposalService.findLifeProposalById(lifeProposal.getId());
			this.showPreview = true;
			this.disableIssueBtn = true;
			addInfoMessage(null, MessageId.ISSUING_PROCESS_SUCCESS_PARAM, lifeProposal.getProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}

	}

	private final String reportName = "StudentLifePolicyIssue";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private String fileName;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport(LifePolicy lifePolicy) {
		String customerName = lifeProposal.getCustomerName();
		if (customerName.contains("\\")) {
			customerName = customerName.replace("\\", "");
		}
		if (customerName.contains("/")) {
			customerName = customerName.replace("/", "");
		}
		fileName = "StudentLife_" + customerName + "_Issue" + ".pdf";
		DocumentBuilder.generateStudentLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
	}

	public void increasePrintCount() {
		lifePolicyService.increaseLifePolicyPrintCount(lifePolicy.getId());
		lifePolicy.setPrintCount(lifePolicy.getPrintCount() + 1);
	}

	public Date getPresentDate() {
		return new Date();
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public List<LifePolicy> getLifePolicyList() {
		return lifePolicyList;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public boolean getNilExcess() {
		return nilExcess;
	}

	public void setNilExcess(boolean nilExcess) {
		this.nilExcess = nilExcess;
	}

	public boolean getShowPreview() {
		return showPreview;
	}

	public boolean getDisableIssueBtn() {
		return disableIssueBtn;
	}

	public LifePolicyHistory getLifePolicyHistory() {
		return lifePolicyHistory;
	}

	public Date getTodaydate() {
		return todaydate;
	}

	public void setTodaydate(Date todaydate) {
		this.todaydate = todaydate;
	}

	public String getAddDispline() {
		return AddDispline;
	}

	public void setAddDispline(String addDispline) {
		AddDispline = addDispline;
	}

	public String getReasons() {
		return Reasons;
	}

	public void setReasons(String reasons) {
		Reasons = reasons;
	}

	public String getChanges() {
		return Changes;
	}

	public void setChanges(String changes) {
		Changes = changes;
	}

	public PaymentDTO getPayment() {
		return paymentDTO;
	}

	public LifeEndorseInfo getLifeEndorseInfo() {
		return lifeEndorseInfo;
	}

	public boolean isChangeOthers() {
		if (lifeProposal.getLifePolicy() != null) {
			PolicyInsuredPersonHistory insuPersonHistory;
			if (lifeProposal.getLifePolicy().getInsuredPersonInfo().size() != lifePolicyHistory.getInsuredPersonInfo().size()) {
				return true;
			}
			if (lifeProposal.getLifePolicy().getPolicyStatus() == PolicyStatus.TERMINATE) {
				return true;
			}
			if (!lifeProposal.getLifePolicy().getPaymentType().getId().equalsIgnoreCase(lifePolicyHistory.getPaymentType().getId())) {
				return true;
			}
			for (PolicyInsuredPerson insuPerson : lifeProposal.getLifePolicy().getInsuredPersonInfo()) {
				if (insuPerson.getEndorsementStatus() == EndorsementStatus.NEW) {
					return true;
				}
				if (insuPerson.getEndorsementStatus() == EndorsementStatus.REPLACE) {
					return true;
				}
				if (insuPerson.getEndorsementStatus() == EndorsementStatus.EDIT) {
					insuPersonHistory = lifePolicyHistoryService.findInsuredPersonByCodeNo(insuPerson.getInsPersonCodeNo(), lifePolicyHistory.getId());
					if (!insuPerson.getFullName().equalsIgnoreCase(insuPersonHistory.getFullName())) {
						return true;
					} else if (!insuPerson.getResidentAddress().getResidentAddress().equalsIgnoreCase(insuPersonHistory.getResidentAddress().getResidentAddress())) {
						return true;
					} else if (insuPerson.getOccupation() != null && !insuPerson.getOccupation().getId().equalsIgnoreCase(insuPersonHistory.getOccupation().getId())) {
						return true;
						// } else if (insuPerson.getPeriodMonth() !=
						// insuPersonHistory.getPeriodMonth()) {
						// return true;
					} else if (insuPerson.getSumInsured() != insuPersonHistory.getSumInsured()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isChangeBeneficiary() {
		PolicyInsuredPersonHistory insuPersonHistory;
		PolicyInsuredPersonBeneficiariesHistory beneficiaryHistory;
		if (lifeProposal.getLifePolicy() != null && lifeProposal.getLifePolicy().getPolicyStatus() != PolicyStatus.TERMINATE) {
			for (PolicyInsuredPerson insuPerson : lifeProposal.getLifePolicy().getInsuredPersonInfo()) {
				if (insuPerson.getEndorsementStatus() == EndorsementStatus.EDIT) {
					insuPersonHistory = lifePolicyHistoryService.findInsuredPersonByCodeNo(insuPerson.getInsPersonCodeNo(), lifePolicyHistory.getId());
					if (insuPerson.getPolicyInsuredPersonBeneficiariesList().size() != insuPersonHistory.getPolicyInsuredPersonBeneficiariesList().size()) {
						return true;
					}
					for (PolicyInsuredPersonBeneficiaries beneficiary : insuPerson.getPolicyInsuredPersonBeneficiariesList()) {
						beneficiaryHistory = lifePolicyHistoryService.findBeneficiaryByCodeNo(beneficiary.getBeneficiaryNo(), insuPersonHistory.getId());
						if (beneficiaryHistory == null) {
							return true;
						}
						if (!beneficiary.getName().equals(beneficiaryHistory.getName())) {
							return true;
						} else if (beneficiary.getAge() != beneficiaryHistory.getAge()) {
							return true;
						} else if (beneficiary.getPercentage() != beneficiaryHistory.getPercentage()) {
							return true;
						} else if (beneficiary.getRelationship() == null) {
							if (beneficiaryHistory.getRelationship() != null) {
								return true;
							}
						} else if (beneficiary.getRelationship() != null) {
							if (beneficiaryHistory.getRelationship() == null) {
								return true;
							} else if (!beneficiary.getRelationship().equals(beneficiaryHistory.getRelationship())) {
								return true;
							}
						} else if (!beneficiary.getResidentAddress().equals(beneficiaryHistory.getResidentAddress())) {
							return true;
						} else if (!beneficiary.getIdType().equals(beneficiaryHistory.getIdType())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isLicenseNoNull() {
		if (lifeProposal.getAgent() != null) {
			return true;
		} else
			return false;
	}

	public List<List<PolicyInsuredPerson>> getPolicyInsuredList() {
		if (lifePolicy != null) {
			return ListSplitor.split(lifePolicy.getInsuredPersonInfo(), 8);
		}
		return null;
	}

	public boolean isDisableSetUPBtn() {
		return disableSetUPBtn;
	}

	public void setDisableSetUPBtn(boolean disableSetUPBtn) {
		this.disableSetUPBtn = disableSetUPBtn;
	}

	private boolean isEmpty(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* For Template */
	@ManagedProperty(value = "#{LifePolicySummaryService}")
	private ILifePolicySummaryService lifePolicySummaryService;

	public void setLifePolicySummaryService(ILifePolicySummaryService lifePolicySummaryService) {
		this.lifePolicySummaryService = lifePolicySummaryService;
	}

	public List<LifePolicyHistory> getLifePolicyHistoryList() {
		if (lifePolicyHistoryList == null) {
			if (lifeProposal.getLifePolicy() != null) {
				lifePolicyHistoryList = lifePolicyHistoryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getPolicyNo());
			}
		}
		return lifePolicyHistoryList;
	}

	public LifePolicySummary getLifePolicySummary(String policyId) {
		LifePolicySummary summary = lifePolicySummaryService.findLifePolicyByPolicyNo(policyId);
		return summary;
	}

	public void openTemplateDialog() {
		putParam("lifeProposal", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openStudentLifeInfoTemplate();
	}

	public String getPageHeader() {
		return "Student Life Policy Issue";
	}
}

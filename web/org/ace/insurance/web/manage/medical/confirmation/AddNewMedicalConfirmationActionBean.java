package org.ace.insurance.web.manage.medical.confirmation;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.proposal.MedicalKeyFactorValue;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
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
@ManagedBean(name = "AddNewMedicalConfirmationActionBean")
public class AddNewMedicalConfirmationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalProposalService) {
		this.medicalProposalService = medicalProposalService;
	}

	@ManagedProperty(value = "#{AcceptedInfoService}")
	private IAcceptedInfoService acceptedInfoService;

	public void setAcceptedInfoService(IAcceptedInfoService acceptedInfoService) {
		this.acceptedInfoService = acceptedInfoService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private String loginBranchId;
	private User user;
	private MedicalProposal medicalProposal;
	private String remark;
	private User responsiblePerson;
	private boolean approvedProposal = true;
	private PaymentDTO payment;
	private boolean printInvoice;
	private boolean isUserDefinedDate;
	private Date effectiveDate;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		loginBranchId = user.getLoginBranch().getId();
		medicalProposal = (medicalProposal == null) ? (MedicalProposal) getParam("medicalProposal") : medicalProposal;
	}

	@PreDestroy
	public void destroy() {
		removeParam("medicalProposal");
	}

	private boolean isFinished() {
		if (medicalProposal == null) {
			return true;
		} else {
			if (workFlowService.findWorkFlowByRefNo(medicalProposal.getId(), WorkflowTask.CONFIRMATION) == null)
				return true;
			else
				return false;
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		isUserDefinedDate = true;
		effectiveDate = medicalProposal.getStartDate();
		AcceptedInfo acceptedInfo = acceptedInfoService.findAcceptedInfoByReferenceNo(medicalProposal.getId());
		payment = new PaymentDTO();
		if (acceptedInfo != null) {
			int paymentTerm = medicalProposal.getPaymentTerm();
			payment.setBasicPremium(Utils.divide(acceptedInfo.getBasicPremium(), paymentTerm));
			payment.setAddOnPremium(Utils.divide(acceptedInfo.getAddOnPremium(), paymentTerm));
			payment.setDiscountPercent(acceptedInfo.getDiscountPercent());
			payment.setServicesCharges(acceptedInfo.getServicesCharges());
			payment.setStampFees(acceptedInfo.getStampFeesAmount());
			payment.setPaymentType(acceptedInfo.getPaymentType());
			payment.setNcbPremium(acceptedInfo.getNcbPremium());
		}
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			if (!person.isApproved()) {
				approvedProposal = false;
				break;
			}
		}
		if (isFinished()) {
			printInvoice = true;
		}
	}

	public void onDateSelect(SelectEvent event) {
		int age;
		Date startDate = medicalProposal.getStartDate();
		if (!effectiveDate.equals(startDate)) {
			medicalProposal.setStartDate(startDate);
			for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
				Date dateOfBirth = person.getCustomer().getDateOfBirth();
				age = Utils.getAgeForNextYear(dateOfBirth, startDate);
				person.setAge(age);
				for (MedicalKeyFactorValue kfv : person.getKeyFactorValueList()) {
					if (KeyFactorChecker.isMedicalAge(kfv.getKeyFactor())) {
						kfv.setValue(age + "");
						break;
					}
				}
			}
			medicalProposalService.calculatePremium(medicalProposal);
			int paymentTerm = medicalProposal.getPaymentTerm();
			payment.setBasicPremium(Utils.divide(medicalProposal.getBasicPremium(), paymentTerm));
			payment.setAddOnPremium(Utils.divide(medicalProposal.getAddOnPremium(), paymentTerm));
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

	public void confirmMedicalProposal() {
		try {
			if (!isUserDefinedDate) {
				medicalProposal.setStartDate(null);
			}
			payment.setReferenceType(getPolicyReferenceType());
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, WorkflowTask.PAYMENT, getReferenceType(), TransactionType.UNDERWRITING, user,
					responsiblePerson);
			List<Payment> paymentList = medicalProposalService.confirmMedicalProposal(medicalProposal, workFlowDTO, payment);
			payment = new PaymentDTO(paymentList);
			printInvoice = true;
			addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS_PARAM, medicalProposal.getProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String editMedicalProposal() {
		outjectMedicalProposal(medicalProposal);
		return "editMedicalProposal";
	}

	public String denyMedicalProposal() {
		String result = null;
		try {
			if (responsiblePerson == null) {
				responsiblePerson = user;
			}
			WorkflowTask workflowTask = null;
			workflowTask = WorkflowTask.REJECT;
			if (responsiblePerson == null) {
				responsiblePerson = user;
			}
			ReferenceType referenceType = getReferenceType();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			medicalProposalService.rejectMedicalProposal(medicalProposal, workFlowDTO);
			outjectMedicalProposal(medicalProposal);
			outjectWorkFlowDTO(workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.DENY_PROCESS_OK);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, medicalProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	private final String reportName = "HealthPaymentInvoice";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateReport() {
		DocumentBuilder.generateMedicalPaymentInvoice(medicalProposal, payment, dirPath, fileName);
	}

	/*************************************************
	 * Responsible Person Criteria
	 **********************************************/

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	private void outjectMedicalProposal(MedicalProposal medicalProposal) {
		putParam("editMedicalProposal", medicalProposal);
	}

	private void outjectWorkFlowDTO(WorkFlowDTO workFlowDTO) {
		putParam("workFlowDTO", workFlowDTO);
	}

	public void openTemplateDialog() {
		putParam("medicalProposal", medicalProposal);
		putParam("workFlowList", getWorkFlowList());
		openMedicalProposalInfoTemplate();
	}

	public void selectUser() {
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.MEDICAL_INSURANCE, TransactionType.UNDERWRITING, loginBranchId, null);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public boolean isPrintInvoice() {
		return printInvoice;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public boolean isApprovedProposal() {
		return approvedProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId());
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isUserDefinedDate() {
		return isUserDefinedDate;
	}

	public void setUserDefinedDate(boolean isUserDefinedDate) {
		this.isUserDefinedDate = isUserDefinedDate;
	}
}

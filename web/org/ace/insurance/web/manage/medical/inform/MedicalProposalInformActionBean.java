package org.ace.insurance.web.manage.medical.inform;

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
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.product.PremiumCalData;
import org.ace.insurance.product.service.interfaces.IPremiumCalculatorService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
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
@ManagedBean(name = "MedicalProposalInformActionBean")
public class MedicalProposalInformActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{PremiumCalculatorService}")
	private IPremiumCalculatorService premiumCalculatorService;

	public void setPremiumCalculatorService(IPremiumCalculatorService premiumCalculatorService) {
		this.premiumCalculatorService = premiumCalculatorService;
	}

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalproposalService;

	public void setMedicalproposalService(IMedicalProposalService medicalproposalService) {
		this.medicalproposalService = medicalproposalService;
	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private String loginBranchId;
	private User user;
	private MedicalProposal medicalProposal;
	private boolean isHealth;
	private boolean isCriticalillness;
	private boolean isMicroHealth;
	private boolean isGroupHealth;
	private boolean isGroupCritical;
	private boolean approvedProposal = true;
	private boolean disablePrintBtn = true;
	private String remark;
	private AcceptedInfo acceptedInfo;
	private User responsiblePerson;

	/**
	 * for inform letter
	 */
	private final String reportNameAcceptedLetter = "MedicalProposalAcceptedLetter";
	private final String pdfDirPathAcceptedLetter = "/pdf-report/" + reportNameAcceptedLetter + "/" + System.currentTimeMillis() + "/";
	private final String dirPathAcceptedLetter = getSystemPath() + pdfDirPathAcceptedLetter;
	private final String fileNameAcceptedLetter = reportNameAcceptedLetter + ".pdf";

	private final String reportNameRejectLetter = "MedicalProposalRejectLetter";
	private final String pdfDirPathRejectLetter = "/pdf-report/" + reportNameRejectLetter + "/" + System.currentTimeMillis() + "/";
	private final String dirPathRejectLetter = getSystemPath() + pdfDirPathRejectLetter;
	private final String fileNameRejectLetter = reportNameRejectLetter + ".pdf";

	public Date getCurrentDate() {
		return new Date();
	}

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
		removeParam("medicalproposal");
	}

	private boolean isFinished() {
		if (medicalProposal == null) {
			return true;
		} else {
			if (workFlowService.findWorkFlowByRefNo(medicalProposal.getId(), WorkflowTask.INFORM) == null)
				return true;
			else
				return false;
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			if (!person.isApproved()) {
				approvedProposal = false;
				break;
			}
		}
		acceptedInfo = new AcceptedInfo();
		acceptedInfo.setReferenceNo(medicalProposal.getId());
		acceptedInfo.setBasicPremium(medicalProposal.getBasicPremium());
		acceptedInfo.setAddOnPremium(medicalProposal.getAddOnPremium());
		acceptedInfo.setBasicTermPremium(medicalProposal.getTotalBasicTermPremium());
		acceptedInfo.setAddOnTermPremium(medicalProposal.getTotalAddOnTermPremium());
		acceptedInfo.setPaymentType(medicalProposal.getPaymentType());
		acceptedInfo.setReferenceType(ReferenceType.HEALTH);
		calcStampFee();
		if (isFinished())
			disablePrintBtn = false;
	}

	private void calcStampFee() {
		double stamFees = premiumCalculatorService.calculateStampFee(medicalProposal.getMedicalProposalInsuredPersonList().get(0).getProduct(),
				new PremiumCalData(null, medicalProposal.getTotalSumInsured(), null, Double.valueOf(medicalProposal.getTotalBasicUnit())));
		acceptedInfo.setStampFeesAmount(stamFees);
	}

	public void informApprovedMedicalProposal() {
		try {
			ReferenceType referenceType = getReferenceType();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, WorkflowTask.CONFIRMATION, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			medicalproposalService.informProposal(medicalProposal.getId(), workFlowDTO, acceptedInfo);
			outjectMedicalProposal(medicalProposal);
			outjectWorkFlowDTO(workFlowDTO);
			addInfoMessage(null, MessageId.INFORM_PROCESS_SUCCESS_PARAM, medicalProposal.getProposalNo());
			disablePrintBtn = false;

		} catch (SystemException ex) {
			handelSysException(ex);
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

	public void informRejectMedicalProposal() {
		try {
			ReferenceType referenceType = getReferenceType();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, WorkflowTask.CONFIRMATION, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			medicalproposalService.informProposal(medicalProposal.getId(), workFlowDTO, null);
			outjectMedicalProposal(medicalProposal);
			outjectWorkFlowDTO(workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.INFORM_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, medicalProposal.getProposalNo());
			disablePrintBtn = false;
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String getReportStream() {
		if (approvedProposal) {
			return pdfDirPathAcceptedLetter + fileNameAcceptedLetter;
		} else {
			return pdfDirPathRejectLetter + fileNameRejectLetter;
		}
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId());
	}

	public void generateReport() {
		if (checkApproved()) {
			DocumentBuilder.generateMedicalAcceptanceLetter(medicalProposal, acceptedInfo, dirPathAcceptedLetter, fileNameAcceptedLetter);
		} else {
			DocumentBuilder.generateMedicalRejectLetter(medicalProposal, dirPathRejectLetter, fileNameRejectLetter);
		}
	}

	public boolean checkApproved() {
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			if (person.isApproved()) {
				approvedProposal = true;
			}
		}
		return approvedProposal;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public void setMedicalProposal(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
	}

	public boolean isApprovedProposal() {
		return approvedProposal;
	}

	public void setApprovedProposal(boolean approvedProposal) {
		this.approvedProposal = approvedProposal;
	}

	public boolean isDisablePrintBtn() {
		return disablePrintBtn;
	}

	public void setDisablePrintBtn(boolean disablePrintBtn) {
		this.disablePrintBtn = disablePrintBtn;
	}

	public AcceptedInfo getAcceptedInfo() {
		return acceptedInfo;
	}

	public void setAcceptedInfo(AcceptedInfo acceptedInfo) {
		this.acceptedInfo = acceptedInfo;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	private void outjectMedicalProposal(MedicalProposal medicalProposal) {
		putParam("medicalProposal", medicalProposal);
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
		selectUser(WorkflowTask.CONFIRMATION, WorkFlowType.MEDICAL_INSURANCE, TransactionType.UNDERWRITING, loginBranchId, null);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(getSystemPath() + "/pdf-report/MedicalProposalAcceptedLetter");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

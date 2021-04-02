package org.ace.insurance.web.manage.medical.issue;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.ExportExcel;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.web.export.PolicyIssueExportExcel;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;

@ViewScoped
@ManagedBean(name = "MedicalPolicyIssueActionBean")
public class MedicalPolicyIssueActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
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

	private User user;
	private List<MedicalPolicy> medicalPolicyList;
	private MedicalProposal medicalProposal;
	private MedicalPolicy medicalPolicy;
	private boolean showPreview;
	private PaymentDTO paymentDTO;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
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
			if (workFlowService.findWorkFlowByRefNo(medicalProposal.getId(), WorkflowTask.ISSUING) == null)
				return true;
			else
				return false;
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		medicalPolicyList = new ArrayList<MedicalPolicy>();
		medicalPolicy = medicalPolicyService.findMedicalPolicyByProposalId(medicalProposal.getId());
		medicalPolicyList.add(medicalPolicy);
		if (isFinished()) {
			showPreview = true;
		}
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public void openTemplateDialog() {
		putParam("medicalProposal", medicalProposal);
		putParam("workFlowList", getWorkFlowList());
		openMedicalProposalInfoTemplate();
	}

	public void issuePolicy() {
		try {
			medicalProposalService.issueMedicalProposal(medicalProposal.getId());
			this.showPreview = true;
			addInfoMessage(null, MessageId.ISSUING_PROCESS_SUCCESS_PARAM, medicalProposal.getProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void increasePrintCount() {
		// medicalPolicyService.increaseMedicalPolicyPrintCount(medicalPolicy.getId());
	}

	public void exportPolicyIssueExcel() {
		List<Payment> paymentList = paymentService.findByPolicy(medicalPolicy.getId());
		paymentDTO = new PaymentDTO(paymentList);
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String reportNamePolicyIssue = "MedicalPolicyIssue";
		String fileName = medicalPolicy.getPolicyNo() + "(" + reportNamePolicyIssue + ")" + ".xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel policyIssueExportExcel = new PolicyIssueExportExcel(medicalPolicy, paymentDTO);
			policyIssueExportExcel.generate(op);
			getFacesContext().responseComplete();

		} catch (IOException e) {
			throw new SystemException(null, "Failed to export " + medicalPolicy.getPolicyNo() + " MedicalPolicyIssue.xlsx", e);
		}
	}

	public List<MedicalPolicy> getMedicalPolicyList() {
		return medicalPolicyList;
	}

	public void setMedicalPolicyList(List<MedicalPolicy> medicalPolicyList) {
		this.medicalPolicyList = medicalPolicyList;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId());
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public void setMedicalProposal(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
	}

	public MedicalPolicy getMedicalPolicy() {
		return medicalPolicy;
	}

	public void setMedicalPolicy(MedicalPolicy medicalPolicy) {
		this.medicalPolicy = medicalPolicy;
	}

	public boolean getShowPreview() {
		return showPreview;
	}

	public KeyFactorIDConfig getKFC() {
		KeyFactorIDConfig kfConfig = new KeyFactorIDConfig();
		return kfConfig;
	}

	private final String reportName = "HealthPolicyIssue";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport(MedicalPolicy medicalPolicy) {
		Payment payment = paymentService.findPaymentByReferenceNo(medicalPolicy.getId());
		// TODOFIXME
		// MedicalUnderwritingDocFactory.generateMedicalPolicyIssue(medicalPolicy,
		// payment, dirPath, fileName, healthType);
		DocumentBuilder.generateMedicalPolicyIssueLetter(medicalPolicy, payment, dirPath, fileName);
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package org.ace.insurance.web.manage.medical.renewal.issue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PaymentDTO;
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
import org.ace.insurance.web.manage.medical.proposal.MedProDTO;
import org.ace.insurance.web.manage.medical.proposal.factory.MedicalProposalFactory;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;

@ViewScoped
@ManagedBean(name = "MedicalRenewalIssueActionBean")
public class MedicalRenewalIssueActionBean extends BaseBean {
	// TODO FIXME PSH Renewal Case

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
	private MedProDTO medProDTO;
	private MedicalPolicy medicalPolicy;
	private boolean showPreview;
	private PaymentDTO paymentDTO;
	private HealthType healthType;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		medicalProposal = (medicalProposal == null) ? (MedicalProposal) getParam("medicalProposal") : medicalProposal;
		healthType = (HealthType) getParam("WORKFLOWHEALTHTYPE");
	}

	@PreDestroy
	public void destroy() {
		removeParam("medicalProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		medicalPolicyList = new ArrayList<MedicalPolicy>();
		medProDTO = MedicalProposalFactory.getMedProDTO(medicalProposal);
		medicalPolicy = medicalPolicyService.findMedicalPolicyByProposalId(medicalProposal.getId());
		medicalPolicyList.add(medicalPolicy);
		medicalProposal = medicalProposalService.findMedicalProposalById(medicalProposal.getId());

	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public MedProDTO getMedProDTO() {
		return medProDTO;
	}

	public void setMedProDTO(MedProDTO medProDTO) {
		this.medProDTO = medProDTO;
	}

	public void preparePolicyIssue(MedicalPolicy medicalPolicy) {
		this.medicalPolicy = medicalPolicy;
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

	private final String reportName = "MedicalPolicyIssue";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport(MedicalPolicy medicalPolicy) {
		Payment payment = paymentService.findPaymentByReferenceNo(medicalPolicy.getId());
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

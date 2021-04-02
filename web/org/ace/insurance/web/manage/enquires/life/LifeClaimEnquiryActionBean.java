package org.ace.insurance.web.manage.enquires.life;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LCL001;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.life.claim.service.interfaces.ILifePolicyClaimService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonDTO;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeClaimEnquiryActionBean")
public class LifeClaimEnquiryActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService lifeClaimProposalService;

	public void setLifeClaimProposalService(ILifeClaimProposalService lifeClaimProposalService) {
		this.lifeClaimProposalService = lifeClaimProposalService;
	}

	@ManagedProperty(value = "#{ClaimAcceptedInfoService}")
	private IClaimAcceptedInfoService claimAcceptedInfoService;

	public void setClaimAcceptedInfoService(IClaimAcceptedInfoService claimAcceptedInfoService) {
		this.claimAcceptedInfoService = claimAcceptedInfoService;
	}

	@ManagedProperty(value = "#{LifePolicyClaimService}")
	private ILifePolicyClaimService lifePolicyClaimService;

	public void setLifePolicyClaimService(ILifePolicyClaimService lifePolicyClaimService) {
		this.lifePolicyClaimService = lifePolicyClaimService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private List<LCL001> lifeClaimList;
	private LCL001 criteria;
	String reportName = "LifePolicyClaim";
	String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	String dirPath = getSystemPath() + pdfDirPath;
	String fileName = reportName + ".pdf";

	@PostConstruct
	public void init() {
		resetCriteria();
	}

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void resetCriteria() {
		criteria = new LCL001();
		lifeClaimList = new ArrayList<LCL001>();
	}

	public void search() {
		lifeClaimList = lifeClaimProposalService.findLifeClaimProposalByCriteria(criteria);
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public List<LCL001> getLifeClaimList() {
		return lifeClaimList;
	}

	public void setLifeClaimList(List<LCL001> lifeClaimList) {
		this.lifeClaimList = lifeClaimList;
	}

	public LCL001 getCriteria() {
		return criteria;
	}

	public void setCriteria(LCL001 criteria) {
		this.criteria = criteria;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean allowToPrint(LifeClaimProposal lifeClaimProposal, WorkflowTask... workflowTasks) {
		List<WorkFlowHistory> wfhList = workFlowService.findWorkFlowHistoryByRefNo(lifeClaimProposal.getId(), workflowTasks);
		if (wfhList == null || wfhList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void generatePrintLifeClaimInform(LCL001 dto) {
		LifeClaimProposal lifeClaimProposal = lifeClaimProposalService.findLifeClaimProposalById(dto.getId());
		DocumentBuilder.generateDisabilityLifeClaimInformLetter(lifeClaimProposal, dirPath, fileName);
		showPdfDialog();
	}

	public void generateLifeClaimIssue(LCL001 dto) {
		LifeClaimProposal lifeClaimProposal = lifeClaimProposalService.findLifeClaimProposalById(dto.getId());
		if (allowToPrint(lifeClaimProposal, WorkflowTask.ISSUING)) {
			List<Payment> paymentList = paymentService.findByClaimProposal(dto.getId(), PolicyReferenceType.LIFE_CLAIM, true);
			PaymentDTO paymentDTO = new PaymentDTO(paymentList);
			DocumentBuilder.generateDisabilityLifeClaimLetter(lifeClaimProposal, paymentDTO, dirPath, fileName);
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.ISSUING.getLabel().toLowerCase());
			showInformationDialog();
		}
	}

	public void generateLifeClaimPaymentSlip(LCL001 dto) {
		int claimCount = 0;
		LifeClaimProposal lifeClaimProposal = lifeClaimProposalService.findLifeClaimProposalById(dto.getId());
		if (allowToPrint(lifeClaimProposal, WorkflowTask.PAYMENT)) {
			claimCount = lifePolicyClaimService.findCountByPolicyNo(lifeClaimProposal.getLifePolicyClaim().getPolicyNo());
			ClaimAcceptedInfo acceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo(lifeClaimProposal.getId(), ReferenceType.LIFE_CLAIM);
			DocumentBuilder.generateLifeClaimPaymentSlipLetters(lifeClaimProposal, dirPath, fileName, claimCount, acceptedInfo);
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.CONFIRMATION.getLabel().toLowerCase());
			showInformationDialog();
		}
	}

	public void generateLifeClaimPaymentLetter(LCL001 dto) {
		LifeClaimProposal lifeClaimProposal = lifeClaimProposalService.findLifeClaimProposalById(dto.getId());
		if (allowToPrint(lifeClaimProposal, WorkflowTask.ISSUING)) {
			List<Payment> paymentList = paymentService.findByClaimProposal(dto.getId(), PolicyReferenceType.LIFE_CLAIM, true);
			DocumentBuilder.generateLifeClaimPaymentLetters(lifeClaimProposal, dirPath, fileName, paymentList.get(0));
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.PAYMENT.getLabel().toLowerCase());
			showInformationDialog();
		}
	}

	private void showPdfDialog() {
		PrimeFaces.current().executeScript("PF('pdfPreviewDialog').show();");
	}

	private void showInformationDialog() {
		PrimeFaces.current().executeScript("PF('informationDialog').show();");
	}

	public void returnLifePolicyNo(SelectEvent event) {
		LifePolicySearch lifePolicysearch = (LifePolicySearch) event.getObject();
		criteria.setPolicyNo(lifePolicysearch.getPolicyNo());
		criteria.setInsuredPersonDTOList(new ArrayList<PolicyInsuredPersonDTO>());
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(lifePolicysearch.getPolicyNo());
		for (PolicyInsuredPerson person : lifePolicy.getPolicyInsuredPersonList()) {
			criteria.getInsuredPersonDTOList().add(new PolicyInsuredPersonDTO(person.getId(), person.getFullName()));
		}
	}

	public void openTemplateDialog(LCL001 lifeClaimProposal) {
		putParam("lifeClaimProposal", lifeClaimProposalService.findLifeClaimProposalById(lifeClaimProposal.getId()));
		putParam("workFlowList", getWorkflowList(lifeClaimProposal.getId()));
		openLifeClaimInfoTemplate();
	}

	public List<WorkFlowHistory> getWorkflowList(String lifeClaimProposalId) {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeClaimProposalId);
	}
}

package org.ace.insurance.web.manage.life.claim.issue;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeDeathClaim;
import org.ace.insurance.life.claim.LifeHospitalizedClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
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
@ManagedBean(name = "IssueLifeClaimActionBean")
public class IssueLifeClaimActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService lifeClaimProposalService;

	public void setLifeClaimProposalService(ILifeClaimProposalService lifeClaimProposalService) {
		this.lifeClaimProposalService = lifeClaimProposalService;
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

	private User user;
	private boolean showPreview;
	private boolean disabledBtn;
	private LifeClaimProposal lifeClaimProposal;
	private PaymentDTO paymentDTO;
	private List<Payment> paymentList;
	private String fileName;
	private String reportName = "";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;

	private LifeDeathClaim lifeDeathClaim;
	private LifeHospitalizedClaim lifeHospitalizedClaim;
	private DisabilityLifeClaim disabilityLifeClaim;
	private boolean death;
	private boolean disbility;
	private boolean hospital;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeClaimProposal = (lifeClaimProposal == null) ? (LifeClaimProposal) getParam("lifeClaimProposal") : lifeClaimProposal;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		disabledBtn = false;
		paymentList = paymentService.findByClaimProposal(lifeClaimProposal.getId(), PolicyReferenceType.LIFE_CLAIM, true);
		paymentDTO = new PaymentDTO(paymentList);
		if (lifeClaimProposal.getLifePolicyClaim() instanceof LifeDeathClaim) {
			lifeDeathClaim = (LifeDeathClaim) lifeClaimProposal.getLifePolicyClaim();
			death = true;
			this.showPreview = true;
		} else if (lifeClaimProposal.getLifePolicyClaim() instanceof LifeHospitalizedClaim) {
			hospital = true;
			lifeHospitalizedClaim = (LifeHospitalizedClaim) lifeClaimProposal.getLifePolicyClaim();
		} else {
			disbility = true;
			disabilityLifeClaim = (DisabilityLifeClaim) lifeClaimProposal.getLifePolicyClaim();
		}
		if (lifeClaimProposal.isComplete()) {
			this.disabledBtn = true;
		}
		fileName = "disabilityLifeClaimIssue";
	}

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport(LifeClaimProposal lifeClaimProposal) {

		fileName = "DisabilityLifeClaimLetter" + ".pdf";
		DocumentBuilder.generateDisabilityLifeClaimLetter(lifeClaimProposal, paymentDTO, dirPath, fileName);
	}

	public void issuePolicy() {
		try {
			lifeClaimProposalService.issueLifeClaimPolicy(lifeClaimProposal);
			lifeClaimProposal = lifeClaimProposalService.findLifeClaimProposalById(lifeClaimProposal.getId());
			putParam("lifeClaimProposal", lifeClaimProposal);
			if (!isDeath()) {
				this.showPreview = false;
			}
			this.disabledBtn = true;
			addInfoMessage(null, MessageId.ISSUING_PROCESS_SUCCESS_PARAM, lifeClaimProposal.getClaimProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}

	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isShowPreview() {
		return showPreview;
	}

	public boolean isDisabledBtn() {
		return disabledBtn;
	}

	public LifeClaimProposal getLifeClaimProposal() {
		return lifeClaimProposal;
	}

	public List<WorkFlowHistory> getWorkflowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeClaimProposal.getId());
	}

	public void openTemplateDialog() {
		putParam("lifeClaimProposal", lifeClaimProposal);
		putParam("workFlowList", getWorkflowList());
		openLifeClaimInfoTemplate();
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public boolean isDeath() {
		return death;
	}

	public boolean isDisbility() {
		return disbility;
	}

	public boolean isHospital() {
		return hospital;
	}

	public LifeDeathClaim getLifeDeathClaim() {
		return lifeDeathClaim;
	}

	public LifeHospitalizedClaim getLifeHospitalizedClaim() {
		return lifeHospitalizedClaim;
	}

	public DisabilityLifeClaim getDisabilityLifeClaim() {
		return disabilityLifeClaim;
	}
}

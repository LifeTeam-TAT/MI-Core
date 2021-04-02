package org.ace.insurance.web.manage.life.surrender.issue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletContext;

import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.life.surrender.service.interfaces.ILifeSurrenderProposalService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "LifeSurrenderIssueActionBean")
public class LifeSurrenderIssueActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeSurrenderProposalService}")
	private ILifeSurrenderProposalService lifeSurrenderProposalService;

	public void setLifeSurrenderProposalService(ILifeSurrenderProposalService lifeSurrenderProposalService) {
		this.lifeSurrenderProposalService = lifeSurrenderProposalService;
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
	private LifeSurrenderProposal proposal;
	private List<LifeSurrenderProposal> proposalList;
	private boolean isApplyIssue;
	private List<WorkFlowHistory> workFlowList;
	private List<PaymentTrackDTO> paymentList;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		proposal = (proposal == null) ? (LifeSurrenderProposal) getParam("surrenderProposal") : proposal;
	}

	@PreDestroy
	public void destroy() {
		removeParam("surrenderProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		proposalList = new ArrayList<LifeSurrenderProposal>();
		proposalList.add(proposal);
		workFlowList = workFlowService.findWorkFlowHistoryByRefNo(proposal.getId());
		paymentList = paymentService.findPaymentTrack(proposal.getPolicyNo());
	}

	public void issuePolicy() {
		try {
			lifeSurrenderProposalService.issueLifeSurrenderProposal(proposal);
			addInfoMessage(null, MessageId.ISSUING_PROCESS_SUCCESS_PARAM, proposal.getProposalNo());
			isApplyIssue = true;
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String getSystemPath() {
		Object context = getFacesContext().getExternalContext().getContext();
		String systemPath = ((ServletContext) context).getRealPath("/");
		return systemPath;
	}

	private final String reportName = "LifeSurrenderIssue";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport(LifeSurrenderProposal proposal) {
		DocumentBuilder.generateLifeSurrenderIssue(proposal, dirPath, fileName);
	}

	public boolean isApplyIssue() {
		return isApplyIssue;
	}

	public LifeSurrenderProposal getProposal() {
		return proposal;
	}

	public List<LifeSurrenderProposal> getProposalList() {
		return proposalList;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowList;
	}

	public List<PaymentTrackDTO> getPaymentList() {
		return paymentList;
	}

}

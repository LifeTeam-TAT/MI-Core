package org.ace.insurance.web.manage.life.paidUp.inform;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletContext;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.insurance.life.paidUp.service.interfaces.ILifePaidUpProposalService;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifePaidUpInformActionBean")
public class LifePaidUpInformActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{LifePaidUpProposalService}")
	private ILifePaidUpProposalService lifePaidUpProposalService;

	public void setLifePaidUpProposalService(ILifePaidUpProposalService lifePaidUpProposalService) {
		this.lifePaidUpProposalService = lifePaidUpProposalService;
	}

	private boolean approvedProposal;
	private boolean printFlag;
	private LifePaidUpProposal paidUpProposal;
	private String remark;
	private User responsiblePerson;
	private User user;
	private ClaimAcceptedInfo claimAcceptedInfo;
	private List<WorkFlowHistory> workFlowList;
	private List<PaymentTrackDTO> paymentList;

	private final String reportName = "lifePaidUpInform";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		paidUpProposal = (LifePaidUpProposal) getParam("paidUpProposal");
		workFlowList = workFlowService.findWorkFlowHistoryByRefNo(paidUpProposal.getId());
		paymentList = paymentService.findPaymentTrack(paidUpProposal.getPolicyNo());
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		claimAcceptedInfo = new ClaimAcceptedInfo();
		claimAcceptedInfo.setReferenceNo(paidUpProposal.getId());
		claimAcceptedInfo.setReferenceType(ReferenceType.LIFE_PAIDUP_PROPOSAL);
		claimAcceptedInfo.setClaimAmount(paidUpProposal.getPaidUpAmount());
		approvedProposal = paidUpProposal.isApproved();
	}

	@PreDestroy
	public void destory() {
		removeParam("paidUpProposal");
	}

	public void informLifePaidUp() {
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(paidUpProposal.getId(), getLoginBranchId(), remark, WorkflowTask.CONFIRMATION, ReferenceType.LIFE_PAIDUP_PROPOSAL,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			lifePaidUpProposalService.informLifePaidUpProposal(paidUpProposal, workFlowDTO, claimAcceptedInfo);
			addInfoMessage(null, MessageId.INFORM_PROCESS_SUCCESS_PARAM, paidUpProposal.getProposalNo());
			printFlag = true;
		} catch (SystemException ex) {
			handelSysException(ex);
		}

	}

	public void selectUser() {
		selectUser(WorkflowTask.CONFIRMATION, WorkFlowType.LIFE_PAIDUP, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public String getStream() {
		return pdfDirPath + fileName;

	}

	public void generateReport() {
		if (approvedProposal) {
			DocumentBuilder.generateLifePaidUpInformForm(paidUpProposal, claimAcceptedInfo, dirPath, fileName);
		} else {
			DocumentBuilder.generateLifePaidUpRejectLetter(paidUpProposal, claimAcceptedInfo, dirPath, fileName);
		}

	}

	public String getSystemPath() {
		Object context = getFacesContext().getExternalContext().getContext();
		String systemPath = ((ServletContext) context).getRealPath("/");
		return systemPath;
	}

	public boolean isApprovedProposal() {
		return approvedProposal;
	}

	public boolean isPrintFlag() {
		return printFlag;
	}

	public LifePaidUpProposal getPaidUpProposal() {
		return paidUpProposal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public User getUser() {
		return user;
	}

	public ClaimAcceptedInfo getClaimAcceptedInfo() {
		return claimAcceptedInfo;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowList;
	}

	public List<PaymentTrackDTO> getPaymentList() {
		return paymentList;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}

}

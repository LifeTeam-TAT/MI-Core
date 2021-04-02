package org.ace.insurance.web.manage.life.paidUp.approval;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

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
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifePaidUpApprovalActionBean")
public class LifePaidUpApprovalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{LifePaidUpProposalService}")
	private ILifePaidUpProposalService lifePaidUpProposalService;

	public void setLifePaidUpProposalService(ILifePaidUpProposalService lifePaidUpProposalService) {
		this.lifePaidUpProposalService = lifePaidUpProposalService;
	}

	private String remark;
	private User responsiblePerson;
	private LifePaidUpProposal paidUpProposal;
	private List<LifePaidUpProposal> paidUpProposalList;
	private List<WorkFlowHistory> workFlowList;
	private List<PaymentTrackDTO> paymentList;
	private User user;

	@PostConstruct
	public void init() {
		initializeInjection();
		paidUpProposalList = new ArrayList<LifePaidUpProposal>();
		paidUpProposalList.add(paidUpProposal);
		workFlowList = workFlowService.findWorkFlowHistoryByRefNo(paidUpProposal.getId());
		paymentList = paymentService.findPaymentTrack(paidUpProposal.getPolicyNo());
	}

	@PreDestroy
	public void destory() {
		removeParam("paidUpProposal");
	}

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		paidUpProposal = (LifePaidUpProposal) getParam("paidUpProposal");
	}

	public String addPaidUpApproval() {
		String result = null;
		WorkFlowDTO workFlowDTO = null;
		try {
			workFlowDTO = new WorkFlowDTO(paidUpProposal.getId(), getLoginBranchId(), remark, WorkflowTask.INFORM, ReferenceType.LIFE_PAIDUP_PROPOSAL, TransactionType.UNDERWRITING,
					user, responsiblePerson);
			lifePaidUpProposalService.approveLifePaidUpProposal(paidUpProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.APPROVAL_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, paidUpProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;

	}

	public void selectUser() {
		selectUser(WorkflowTask.INFORM, WorkFlowType.LIFE_PAIDUP, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public void changeApproval(AjaxBehaviorEvent e) {
		if (paidUpProposal.isApproved()) {
			paidUpProposal.setRejectedReason(null);
		}

	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
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

	public LifePaidUpProposal getPaidUpProposal() {
		return paidUpProposal;
	}

	public void setPaidUpProposal(LifePaidUpProposal paidUpProposal) {
		this.paidUpProposal = paidUpProposal;
	}

	public List<LifePaidUpProposal> getPaidUpProposalList() {
		return paidUpProposalList;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowList;
	}

	public List<PaymentTrackDTO> getPaymentList() {
		return paymentList;
	}

	public User getUser() {
		return user;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}

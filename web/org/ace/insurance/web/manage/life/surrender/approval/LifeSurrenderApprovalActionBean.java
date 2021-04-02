package org.ace.insurance.web.manage.life.surrender.approval;

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
import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.life.surrender.service.interfaces.ILifeSurrenderProposalService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeSurrenderApprovalActionBean")
public class LifeSurrenderApprovalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String remark;
	private User responsiblePerson;
	private LifeSurrenderProposal surrenderProposal;
	private List<LifeSurrenderProposal> surrenderProposalList;
	private List<WorkFlowHistory> workFlowList;
	private List<PaymentTrackDTO> paymentList;
	private User user;

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
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

	@ManagedProperty(value = "#{LifeSurrenderProposalService}")
	private ILifeSurrenderProposalService surrderProposalService;

	public void setSurrderProposalService(ILifeSurrenderProposalService surrderProposalService) {
		this.surrderProposalService = surrderProposalService;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		surrenderProposalList = new ArrayList<LifeSurrenderProposal>();
		surrenderProposalList.add(surrenderProposal);
		workFlowList = workFlowService.findWorkFlowHistoryByRefNo(surrenderProposal.getId());
		paymentList = paymentService.findPaymentTrack(surrenderProposal.getPolicyNo());
	}

	@PreDestroy
	public void destory() {
		removeParam("surrenderProposal");
	}

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		surrenderProposal = (LifeSurrenderProposal) getParam("surrenderProposal");
	}

	public String addSurrenderApproval() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(surrenderProposal.getId(), getLoginBranchId(), remark, WorkflowTask.INFORM, ReferenceType.LIFESURRENDER,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			surrderProposalService.approveLifeSurrenderProposal(surrenderProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.APPROVAL_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, surrenderProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;

	}

	public void selectUser() {
		selectUser(WorkflowTask.INFORM, WorkFlowType.LIFESURRENDER, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public void changeApproval(AjaxBehaviorEvent e) {
		if (surrenderProposal.isApproved()) {
			surrenderProposal.setRejectedReason(null);
		}

	}

	public List<LifeSurrenderProposal> getSurrenderProposalList() {
		return surrenderProposalList;
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

	public LifeSurrenderProposal getSurrenderProposal() {
		return surrenderProposal;
	}

	public void setSurrenderProposal(LifeSurrenderProposal surrenderProposal) {
		this.surrenderProposal = surrenderProposal;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
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

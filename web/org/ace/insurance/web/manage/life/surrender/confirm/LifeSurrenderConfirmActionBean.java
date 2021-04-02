package org.ace.insurance.web.manage.life.surrender.confirm;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

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
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeSurrenderConfirmActionBean")
public class LifeSurrenderConfirmActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String remark;
	private User responsiblePerson;
	private LifeSurrenderProposal surrenderProposal;
	private User user;
	private List<WorkFlowHistory> workFlowList;
	private List<PaymentTrackDTO> paymentList;

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
		workFlowList = workFlowService.findWorkFlowHistoryByRefNo(surrenderProposal.getId());
		paymentList = paymentService.findPaymentTrack(surrenderProposal.getPolicyNo());
	}

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		surrenderProposal = (LifeSurrenderProposal) getParam("surrenderProposal");
	}

	public String confirmSurrenderApproval() {
		WorkFlowDTO workFlowDTO = new WorkFlowDTO(surrenderProposal.getId(), getLoginBranchId(), remark, WorkflowTask.PAYMENT, ReferenceType.LIFESURRENDER, null, user,
				responsiblePerson);
		outjectLifeSurrenderProposal(surrenderProposal);
		outjectWorkFlowDTO(workFlowDTO);
		return "printLifeSurrenderReceipt";
	}

	public String editSurrenderApproval() {
		outjectLifeSurrenderProposal(surrenderProposal);
		return "editLifeSurrenderProposal";
	}

	public String denySurrenderApproval() {
		String result = null;
		try {
			if (responsiblePerson == null) {
				responsiblePerson = user;
			}
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(surrenderProposal.getId(), getLoginBranchId(), remark, WorkflowTask.REJECT, ReferenceType.LIFESURRENDER,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			surrderProposalService.rejectLifeSurrenderProposal(surrenderProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.DENY_PROCESS_OK);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, surrenderProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;

	}

	public void selectUser() {
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFESURRENDER, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public LifeSurrenderProposal getSurrenderProposal() {
		return surrenderProposal;
	}

	private void outjectLifeSurrenderProposal(LifeSurrenderProposal surrenderProposal) {
		putParam("surrenderProposal", surrenderProposal);
	}

	private void outjectWorkFlowDTO(WorkFlowDTO workFlowDTO) {
		putParam("workFlowDTO", workFlowDTO);
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}

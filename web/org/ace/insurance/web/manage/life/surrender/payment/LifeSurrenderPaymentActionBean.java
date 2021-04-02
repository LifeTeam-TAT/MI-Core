package org.ace.insurance.web.manage.life.surrender.payment;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.life.surrender.service.interfaces.ILifeSurrenderProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.user.User;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeSurrenderPaymentActionBean")
public class LifeSurrenderPaymentActionBean extends BaseBean implements Serializable {
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
	private LifeSurrenderProposal lifeSurrenderProposal;
	private PaymentDTO paymentDTO;
	private String remark;
	private User responsiblePerson;
	private boolean cashPayment = true;
	List<Payment> paymentList;
	private List<WorkFlowHistory> workFlowList;
	private List<PaymentTrackDTO> paymentTrackList;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeSurrenderProposal = (LifeSurrenderProposal) getParam("surrenderProposal");
		workFlowList = workFlowService.findWorkFlowHistoryByRefNo(lifeSurrenderProposal.getId());
		paymentTrackList = paymentService.findPaymentTrack(lifeSurrenderProposal.getPolicyNo());
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeSurrenderProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		paymentList = paymentService.findByClaimProposal(lifeSurrenderProposal.getId(), PolicyReferenceType.LIFE_SURRENDER_CLAIM, false);
		Payment payment = paymentList.get(0);
		cashPayment = payment.getPaymentChannel().equals(PaymentChannel.CASHED) ? true : false;
		paymentDTO = new PaymentDTO(paymentList);

	}

	public String paymentLifeSurrenderProposal() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeSurrenderProposal.getId(), getLoginBranchId(), remark, WorkflowTask.ISSUING, ReferenceType.LIFESURRENDER,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			lifeSurrenderProposalService.payLifeSurrender(paymentList, workFlowDTO, user.getBranch(), RequestStatus.FINISHED.name());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeSurrenderProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public LifeSurrenderProposal getLifeSurrenderProposal() {
		return lifeSurrenderProposal;
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

	public boolean isCashPayment() {
		return cashPayment;
	}

	public void selectUser() {
		selectUser(WorkflowTask.ISSUING, WorkFlowType.LIFESURRENDER, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public double getNetSurrenderAmount() {
		return lifeSurrenderProposal.getSurrenderAmount() - paymentDTO.getServicesCharges() - lifeSurrenderProposal.getLifePremium();
	}

	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowList;
	}

	public List<PaymentTrackDTO> getPaymentTrackList() {
		return paymentTrackList;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}

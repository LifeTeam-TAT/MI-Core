package org.ace.insurance.web.manage.life.claim.payment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.web.manage.life.claim.request.DisabilityClaimDTO;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
@ViewScoped
@ManagedBean(name = "LifeClaimPaymentActionBean")
public class LifeClaimPaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean cashPayment = true;
	private boolean cash = true;
	private String remark;
	// private String paymentChannelString;
	private String receiptNo;
	private String chequeNo;
	private LifeClaim claim;
	private DisabilityClaimDTO claimInfoDTO;
	private PaymentDTO paymentDTO;
	private PolicyInsuredPerson policyInsuredPerson;
	private List<PolicyInsuredPerson> policyInsuredPersonList;
	private List<WorkFlowHistory> workflowList;
	private List<Payment> paymentList;
	private Payment payment;

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{LifeClaimService}")
	private ILifeClaimService claimService;

	public void setClaimService(ILifeClaimService claimService) {
		this.claimService = claimService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private LifeClaimBeneficiary claimBeneficiary;

	private void initializeInjection() {
		claimBeneficiary = (claimBeneficiary == null) ? (LifeClaimBeneficiary) getParam("lifeClaimBeneficiary") : claimBeneficiary;
	}

	@PostConstruct
	public void init() {
		initializeInjection();

		if (claimBeneficiary.isClaimInsuredPersonBeneficiary() || claimBeneficiary.isSuccessor()) {
			payment = paymentService.findClaimProposal(claimBeneficiary.getId(), PolicyReferenceType.LIFE_CLAIM, false);
			paymentList = paymentService.findByClaimProposal(claimBeneficiary.getId(), PolicyReferenceType.LIFE_CLAIM, false);
			if (claimBeneficiary.isSuccessor()) {
				claim = claimBeneficiary.castLifeClaimSuccessor().getLifeClaim();
			} else {
				claim = claimBeneficiary.castClaimInsuredPersonBeneficiary().getLifeClaim();
			}
		} else {
			payment = paymentService.findClaimProposal(claimBeneficiary.getId(), PolicyReferenceType.LIFE_DIS_CLAIM, false);
			paymentList = paymentService.findByClaimProposal(claimBeneficiary.getId(), PolicyReferenceType.LIFE_DIS_CLAIM, false);
			claim = claimBeneficiary.castLifeDisabilityPerson().getClaimInsuredPersonLinkClaim();
		}
		paymentDTO = new PaymentDTO(paymentList);
	}

	/********************************************
	 * Action Controller
	 ********************************************/

	// Detail PopUp Click Event
	public void loadWorkflow() {
		workflowList = workFlowService.findWorkFlowHistoryByRefNo(claim.getClaimRequestId());
	}

	// Payment Channel Change Event Listener
	public void selectPayementChannel() {
		if (isCash()) {
			this.payment.setBank(null);
			chequeNo = null;
		}
	}

	// Submit Button Click Event
	public String lifeDisabilityClaimPaymentConfirm() {
		String result = null;
		try {
			payment.setPaymentChannel(paymentDTO.getPaymentChannel());
			payment.setBank(paymentDTO.getBank());
			payment.setChequeNo(paymentDTO.getChequeNo());

			payment.setComplete(true);
			payment.setServicesCharges(payment.getServicesCharges());
			payment.setClaimAmount(payment.getClaimAmount());
			payment.setConfirmDate(new Date());
			payment.setPaymentDate(new Date());

			claimService.payLifeClaim(claimBeneficiary, payment);

			// paymentDTO = new PaymentDTO(payList);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, claim.getClaimRequestId());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	/******************************************
	 * End Action Controller
	 ******************************************/

	/**********************************************
	 * Helper
	 *****************************************************/

	public boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj) ? true : false;
	}

	/********************************************
	 * End Helper
	 ***************************************************/

	/********************************************
	 * Getter/Setter
	 *************************************************/

	public boolean isCash() {
		return cash;
	}

	public void setCash(boolean cash) {
		this.cash = cash;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public DisabilityClaimDTO getClaimInfoDTO() {
		return claimInfoDTO;
	}

	public void setClaimInfoDTO(DisabilityClaimDTO claimInfoDTO) {
		this.claimInfoDTO = claimInfoDTO;
	}

	public boolean isCashPayment() {
		return cashPayment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LifeClaim getClaim() {
		return claim;
	}

	public void setClaim(LifeClaim claim) {
		this.claim = claim;
	}

	public LifeClaimBeneficiary getClaimBeneficiary() {
		return claimBeneficiary;
	}

	public void setClaimBeneficiary(LifeClaimBeneficiary claimBeneficiary) {
		this.claimBeneficiary = claimBeneficiary;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowList;
	}

	public List<PolicyInsuredPerson> getPolicyInsuredPersonList() {
		return policyInsuredPersonList;
	}

	public void setPolicyInsuredPersonList(List<PolicyInsuredPerson> policyInsuredPersonList) {
		this.policyInsuredPersonList = policyInsuredPersonList;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public Payment getPayment() {
		if (payment == null) {
			payment = new Payment();
		}
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	/******************************************
	 * End Getter/Setter
	 ***********************************************/
}

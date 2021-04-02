package org.ace.insurance.payment.service.interfaces;

import java.util.List;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PaymentReferenceType;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.payment.AccountPayment;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TLF;
import org.ace.insurance.report.ClaimVoucher.ClaimVoucherDTO;
import org.ace.insurance.report.TLF.TLFVoucherDTO;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.common.PaymentTableDTO;
import org.ace.insurance.web.manage.life.billcollection.BC0001;
import org.ace.java.component.SystemException;

public interface IPaymentService {

	public double findActivedRate();

	public List<Payment> prePaymentForChalen(List<Payment> paymentList);

	public List<Payment> prePayment(List<Payment> paymentList);

	public List<Payment> preClaimPayment(List<Payment> paymentList);

	/* Bill Collection Generation */
	public void extendPaymentTimes(List<Payment> paymentList, WorkFlowDTO workflowDTO);

	public void activatePayment(List<AccountPayment> accountPaymentList, String customerId, Branch branch, List<AgentCommission> agentCommissionList);

	public void activatePaymentAndTLF(List<Payment> paymentList, List<AgentCommission> agentCommissionList, Branch branch);

	/**
	 * If complete is true, return payments which is already paid. If complete
	 * is false, return payments which is not paid. If complete is null, return
	 * payment which is already paid or not.
	 */
	public List<PaymentDTO> findPaymentByRecipNo(List<String> receiptList, PolicyReferenceType referenceType, Boolean complete);

	public List<Payment> findByPolicy(String policyId);

	public AgentCommission findAgentCommissionByReferenceNo(String referenceNo);

	/**
	 * If complete is true, return payments which is already paid. If complete
	 * is false, return payments which is not paid. If complete is null, return
	 * payment which is already paid or not.
	 */
	public List<Payment> findByProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete);

	public List<Payment> findByClaimProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete);

	public void activateClaimPayment(List<AccountPayment> accountPaymentList, Branch branch);

	public List<Payment> prePaymentForClaim(List<Payment> paymentList);

	public Payment findClaimProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete);

	public List<Payment> findByClaimProposalComplete(String proposalId, PolicyReferenceType referenceType);

	public String findCheckOfAccountCode(String acccountName, Branch branch, String currencyCode);

	public String findCOAAccountNameByCode(String acccountCode);

	public List<Payment> findPaymentByReferenceNoAndMaxDate(String referenceNo);

	public List<Payment> findPaymentByReceiptNo(String receiptNo);

	public void activateTLFClearing(List<Payment> paymentList);

	/**** TLF ***/

	public TLF updateTLF(TLF tlf);

	public List<TLF> findTLFbyTLFNo(String tlfNo, Boolean isClearing);

	public List<TLF> findTLFbyENONo(String enoNo);

	public void deletePayments(List<Payment> paymentList);

	public List<TLF> findTLFbyReferenceNoAndReferenceType(String referenceNo, PolicyReferenceType policyReferenceType);

	public Payment findPaymentByReferenceNo(String referenceNo);

	public List<TLFVoucherDTO> findTLFVoucher(String receiptNo);

	public List<ClaimVoucherDTO> findClaimVoucher(String receiptNo, String damage);

	public List<Payment> findPaymentByReferenceNoAndMaxDateForAgentInvoice(String referenceNo);

	public List<BC0001> findBCPaymentByPolicyNo(PolicyCriteria policyCriteria);

	public Payment findPaymentByReferenceNoAndIsComplete(String referenceNo, Boolean complete);

	public void updatePayment(Payment payment, String damagedVehicleId, WorkFlowDTO workFlowDTO);

	public Payment findByChalanNo(String chalanNo);

	public List<PaymentTableDTO> findByChalanNoForClaim(List<String> receiptList, PaymentReferenceType referenceType, Boolean complete);

	public void activateMedicalClaimPayment(Payment payment);

	public List<PaymentTrackDTO> findPaymentTrack(String policyNo);

	public void activatePayment(List<Payment> payments, String policyNo, double rate) throws SystemException;

	public List<Payment> findByPolicy(String policyId, PolicyReferenceType referenceType, Boolean complete);

	public Payment findByInvoiceNo(String invoiceNo);

	public List<Payment> findPaymentListByInvoiceNo(String invoiceNo);

	public boolean findPaymentByReferenceNoAndIsNotComplete(String id);

	public void activateClaimPayment(List<Payment> paymentList, String policyNo, double rate) throws SystemException;

}
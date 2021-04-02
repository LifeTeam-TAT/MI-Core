package org.ace.insurance.payment.persistence.interfacs;

import java.util.List;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PaymentReferenceType;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.CashDeno;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TLF;
import org.ace.insurance.report.ClaimVoucher.ClaimVoucherDTO;
import org.ace.insurance.report.TLF.TLFVoucherDTO;
import org.ace.insurance.web.common.PaymentTableDTO;
import org.ace.insurance.web.manage.life.billcollection.BC0001;
import org.ace.java.component.persistence.exception.DAOException;

public interface IPaymentDAO {
	public double findUSDActiveRate() throws DAOException;

	public double findSGDActiveRate() throws DAOException;

	public Payment insert(Payment payment) throws DAOException;

	public void insertAgentCommission(AgentCommission agentCommission) throws DAOException;

	public Payment update(Payment payment) throws DAOException;

	public List<PaymentDTO> findByReceiptNo(List<String> receiptList, PolicyReferenceType referenceType, Boolean complete) throws DAOException;

	public List<Payment> findByReceiptNo(String receiptNo, PolicyReferenceType referenceType, Boolean complete) throws DAOException;

	public List<Payment> findByPolicy(String policyId) throws DAOException;

	public AgentCommission findAgentCommissionByReferenceNo(String referenceNo) throws DAOException;

	public TLF insertTLF(TLF tlf) throws DAOException;

	public void deleteTLFs(List<TLF> tlfList) throws DAOException;

	public String findCheckOfAccountNameByCode(String accountName, String branchId, String currencyId) throws DAOException;

	public String findCCOAByCode(String acCode, String branchId, String currencyId) throws DAOException;

	public CashDeno insertCashDeno(CashDeno cashDeno) throws DAOException;

	public List<Payment> findByProposal(String proposalId, PolicyReferenceType PolicyReferenceType, Boolean complete) throws DAOException;

	public List<Payment> findByClaimProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete) throws DAOException;

	public Payment findClaimProposal(String claimId, PolicyReferenceType referenceType, Boolean complete) throws DAOException;

	public List<Payment> findByClaimProposalComplete(String proposalId, PolicyReferenceType referenceType) throws DAOException;

	public String findCOAAccountNameByCode(String groupId) throws DAOException;

	public void updateBillCollection(String receiptNo, boolean complete) throws DAOException;

	public List<Payment> findPaymentByReferenceNoAndMaxDate(String referenceNo) throws DAOException;

	// new
	public List<Payment> findPaymentByReceiptNo(String receiptNo) throws DAOException;

	public Payment findPaymentByReferenceNo(String referenceNo) throws DAOException;

	public List<Payment> findByReferenceNo(String referenceNo) throws DAOException;

	public List<TLF> findTLFbyTLFNo(String tlfNo, Boolean isClearing) throws DAOException;

	public List<TLF> findTLFbyENONo(String enoNo) throws DAOException;

	public void insertTLFList(List<TLF> tlfList) throws DAOException;

	public TLF updateTLF(TLF tlf) throws DAOException;

	public void deletePayments(List<Payment> paymentList) throws DAOException;

	public List<TLF> findTLFbyReferenceNoAndReferenceType(String referenceNo, PolicyReferenceType policyReferenceType) throws DAOException;

	public List<TLFVoucherDTO> findTLFVoucher(String receiptNo) throws DAOException;

	public List<Payment> findPaymentByReferenceNoAndMaxDateForAgentInvoice(String referenceNo) throws DAOException;

	public Payment findPaymentByReferenceNoAndIsComplete(String referenceNo, Boolean complete) throws DAOException;

	public List<BC0001> findBCPaymentByPolicyNo(PolicyCriteria policyCriteria) throws DAOException;

	public List<ClaimVoucherDTO> findClaimVoucher(String receiptNo, String damage) throws DAOException;

	public double findActiveRate() throws DAOException;

	public Payment findByChalanNo(String chalanNo) throws DAOException;

	public List<PaymentTableDTO> findByChalanNoForClaim(List<String> receiptList, PaymentReferenceType referenceType, Boolean complete) throws DAOException;

	/* for Life Surrender */
	public List<PaymentTrackDTO> findPaymentTrack(String policyNo) throws DAOException;

	public Double findSummaryByReferenceNo(String policyId, PolicyReferenceType referenceType, Boolean complete) throws DAOException;

	public Payment findByInvoiceNo(String invoiceNo);

	public List<Payment> findListByInvoiceNo(String invoiceNo);

	public List<Payment> findByPolicy(String policyId, PolicyReferenceType referenceType, Boolean complete) throws DAOException;

	public Boolean findPaymentByReferenceNoAndIsNotComplete(String referenceNo);

	public Payment findPaymentByReferenceNoAndReferenceType(String referenceNo, PolicyReferenceType referenceType) throws DAOException;

}

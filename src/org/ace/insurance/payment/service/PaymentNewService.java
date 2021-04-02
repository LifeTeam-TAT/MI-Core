package org.ace.insurance.payment.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.interfaces.IProposal;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentNewService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PaymentNewService")
public class PaymentNewService extends BaseService implements IPaymentNewService {
	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void activatePayment(IProposal proposal, List<Payment> paymentList, Branch branch, boolean isRenewal) {

		// // prepayment
		// String receiptNo = null;
		// if
		// (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.TRANSFER))
		// {
		// receiptNo =
		// customIDGenerator.getNextId(SystemConstants.TRANSFER_RECEIPT_NO,
		// null);
		// } else if
		// (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.CASHED))
		// {
		// receiptNo =
		// customIDGenerator.getNextId(SystemConstants.CASH_RECEIPT_NO, null);
		// } else if
		// (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.CHEQUE))
		// {
		// receiptNo =
		// customIDGenerator.getNextId(SystemConstants.CHEQUE_RECEIPT_NO, null);
		// }
		// for (Payment payment : paymentList) {
		// payment.setReceiptNo(receiptNo);
		// payment = paymentDAO.insert(payment);
		// }
		//
		// // prepayment fireproposal
		// String currencyCode = proposal.getCurrency().getCurrencyCode();
		// List<FirePolicy> policyList =
		// firePolicyDAO.findByProposalId(proposal.getId());
		// List<AgentCommission> agentCommissionList = null;
		// double commission = 0.0;
		// if (fireProposal.getAgent() != null) {
		// agentCommissionList = new ArrayList<AgentCommission>();
		// for (FirePolicy firePolicy : policyList) {
		// commission = firePolicy.getAgentCommission();
		// agentCommissionList
		// .add(new AgentCommission(firePolicy.getId(),
		// PolicyReferenceType.FIRE_POLICY, firePolicy.getAgent(), commission,
		// firePolicy.getActivedPolicyStartDate()));
		// }
		// }
		// List<AccountPayment> accountPaymentList = new
		// ArrayList<AccountPayment>();
		//
		// for (FirePolicy firePolicy : policyList) {
		// String accountCode =
		// firePolicy.getPolicyBuildingInfoList().get(0).getFirePolicyProductInfoList().get(0).getProduct().getProductGroup().getAccountCode();
		// for (Payment payment : paymentList) {
		// if (firePolicy.getId().equals(payment.getReferenceNo())) {
		// accountPaymentList.add(new AccountPayment(accountCode, payment));
		// break;
		// }
		// }
		// }
		//
		// // activate payment
		// String tlfNo = accountPaymentList.get(0).getPayment().getReceiptNo();
		//
		// // 1. Premium Debit
		// addNewTLF_For_CashDebitForPremium(accountPaymentList, customerId,
		// branch, tlfNo, isRenewal, currencyCode);
		//
		// for (AccountPayment accountPayment : accountPaymentList) {
		// Payment payment = accountPayment.getPayment();
		// if (isCoInsurance(payment.getReferenceNo(),
		// payment.getReferenceType())) {
		// // Net Premium
		// // 2. Income Narration : Being amount of ... or Accrued
		// addNewTLF_For_CoInsuCashPaymentCredit(payment, customerId, branch,
		// tlfNo, isRenewal, currencyCode);
		// // Co-In Premium
		// // 3. Income Contra: Our Share premium for
		// addNewTLF_For_CoInsuPreIncomeDr(payment, customerId, branch, tlfNo,
		// isRenewal, currencyCode);
		// // // 4. Cash - Being amount of ... or Accrued
		// addNewTLF_For_CoInsuPreIncomeCr(payment, customerId, branch,
		// accountPayment.getAcccountName(), tlfNo, isRenewal, currencyCode);
		//
		// // For Co-Access Unauthorized
		// if (!branch.isCoInsuAccess()) {
		// // 5. Narration : Transfer from 'branch' ...
		// addNewTLF_For_SundryDr(payment, customerId, branch,
		// agentCommissionList, tlfNo, isRenewal, currencyCode);
		// // 6. Narration: Transfer from 'branch' ...
		// addNewTLF_For_InterBranchCr(payment, customerId, branch,
		// agentCommissionList, tlfNo, isRenewal, currencyCode);
		// // 7. Narration: Transfer from 'branch' ...
		// addNewTLF_For_SundryCr(payment, customerId, branch,
		// agentCommissionList, tlfNo, isRenewal, currencyCode);
		// // 8. Narration: Transfer from 'branch' ...
		// addNewTLF_For_InterBranchDr(payment, customerId, branch,
		// agentCommissionList, tlfNo, isRenewal, currencyCode);
		// }
		// } else {
		// // 9. Premium Credit
		// addNewTLF_For_PremiumCredit(payment, customerId, branch,
		// accountPayment.getAcccountName(), tlfNo, isRenewal, currencyCode);
		// }
		//
		// }
		//
		// String eNo = accountPaymentList.get(0).getPayment().getId();
		// Payment paymentByIndex = accountPaymentList.get(0).getPayment();
		//
		// if (agentCommissionList != null && !agentCommissionList.isEmpty()) {
		// // 10. Narration: Agent Commission Payable for . . . .
		// addAgentCommissionTLF(agentCommissionList, branch, paymentByIndex,
		// eNo, isRenewal, currencyCode);
		// }
		//
		// Payment payment = accountPaymentList.get(0).getPayment();
		// // 11. Service Charges & Stamp Fees Debit
		// addNewTLF_For_CashDebitForSCSTFees(payment, customerId, branch,
		// tlfNo, isRenewal, currencyCode);
		// // 12. Service Charges & Stamp Fees Credit
		// addNewTLF_For_SCSTFees(payment, customerId, branch, tlfNo, isRenewal,
		// currencyCode);
		//
	}

}

package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.payment.Payment;

public class MedicalPaymentFactory {
	public static Payment createPayment(PaymentDTO paymentDTO) {

		Payment payment = new Payment();

		payment.setReceiptNo(paymentDTO.getReceiptNo());
		payment.setBankAccountNo(paymentDTO.getBankAccountNo());
		payment.setReferenceNo(paymentDTO.getReferenceNo());

		payment.setReferenceType(paymentDTO.getReferenceType());
		// TODO FIXME PSH
		// payment.setDiscountPercent(paymentDTO.getDiscountPercent());
		payment.setServicesCharges(paymentDTO.getServicesCharges());

		payment.setStampFees(paymentDTO.getStampFees());
		payment.setReceivedDeno(paymentDTO.getReceivedDeno());
		payment.setRefundDeno(paymentDTO.getRefundDeno());

		payment.setClaimAmount(paymentDTO.getClaimAmount());
		payment.setPaymentChannel(paymentDTO.getPaymentChannel());

		payment.setChequeNo(paymentDTO.getChequeNo());
		payment.setBank(paymentDTO.getBank());
		payment.setReinstatementPremium(paymentDTO.getReinstatementPremium());
		payment.setAdministrationFees(paymentDTO.getAdministrationFees());
		payment.setNcbPremium(paymentDTO.getNcbPremium());
		payment.setPenaltyPremium(paymentDTO.getPenaltyPremium());
		payment.setConfirmDate(paymentDTO.getConfirmDate());
		payment.setPaymentDate(paymentDTO.getPaymentDate());
		// payment.setChalanNo(paymentDTO.getChalanNo());
		payment.setFromTerm(paymentDTO.getFromTerm());
		payment.setToTerm(paymentDTO.getToTerm());
		payment.setPaymentType(paymentDTO.getPaymentType());
		payment.setLoanInterest(paymentDTO.getLoanInterest());
		payment.setRenewalInterest(paymentDTO.getRenewalInterest());
		payment.setRefund(paymentDTO.getRefund());
		payment.setPoNo(paymentDTO.getPoNo());
		payment.setAccountBank(paymentDTO.getAccountBank());

		return payment;
	}

	public static PaymentDTO createPaymentDTO(Payment payment) {

		PaymentDTO paymentDTO = new PaymentDTO();

		paymentDTO.setReceiptNo(payment.getReceiptNo());
		paymentDTO.setBankAccountNo(payment.getBankAccountNo());
		paymentDTO.setReferenceNo(payment.getReferenceNo());
		paymentDTO.setReferenceType(payment.getReferenceType());
		paymentDTO.setBasicPremium(payment.getBasicPremium());
		paymentDTO.setAddOnPremium(payment.getAddOnPremium());
		// TODO FIXME PSH
		// paymentDTO.setDiscountPercent(payment.getDiscountPercent());
		paymentDTO.setServicesCharges(payment.getServicesCharges());
		paymentDTO.setStampFees(payment.getStampFees());
		paymentDTO.setReceivedDeno(payment.getReceivedDeno());
		paymentDTO.setRefundDeno(payment.getRefundDeno());
		paymentDTO.setPaymentChannel(payment.getPaymentChannel());
		paymentDTO.setBank(payment.getBank());
		// paymentDTO.setChalanNo(payment.getChalanNo());

		return paymentDTO;
	}
}

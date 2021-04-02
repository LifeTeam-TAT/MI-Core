package org.ace.insurance.cashreceipt;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.payment.CoinsuranceCashReceiptDTO;

public class LifeCashReceiptListReportDTO {
	private PaymentDTO payment;
	private CoinsuranceCashReceiptDTO coinsuranceCashReceipt;
	private LifeProposal proposal;
	
	public LifeCashReceiptListReportDTO(LifeProposal proposal, PaymentDTO payment, CoinsuranceCashReceiptDTO coinsuranceCashReceipt) {
		super();
		this.payment = payment;
		this.coinsuranceCashReceipt = coinsuranceCashReceipt;
		this.proposal = proposal;
	}
	public PaymentDTO getPayment() {
		return payment;
	}
	public CoinsuranceCashReceiptDTO getCoinsuranceCashReceipt() {
		return coinsuranceCashReceipt;
	}
	public LifeProposal getProposal() {
		return proposal;
	}		
}

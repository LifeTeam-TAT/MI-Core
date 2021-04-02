package org.ace.insurance.web.manage.life.billcollection;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.payment.AgentPaymentCashReceiptDTO;

public class RGBillCollectionDTO {
	private LifePolicy lifePolicy;
	private BillCollectionDTO dto;
	private AgentPaymentCashReceiptDTO agentDto;
	private PaymentDTO payment;

	public RGBillCollectionDTO(LifePolicy lifePolicy, BillCollectionDTO dto, AgentPaymentCashReceiptDTO agentDto, PaymentDTO payment) {
		this.lifePolicy = lifePolicy;
		this.dto = dto;
		this.agentDto = agentDto;
		this.payment = payment;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public BillCollectionDTO getDto() {
		return dto;
	}

	public void setDto(BillCollectionDTO dto) {
		this.dto = dto;
	}

	public AgentPaymentCashReceiptDTO getAgentDto() {
		return agentDto;
	}

	public void setAgentDto(AgentPaymentCashReceiptDTO agentDto) {
		this.agentDto = agentDto;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPaymentDto(PaymentDTO paymentDto) {
		this.payment = paymentDto;
	}

}

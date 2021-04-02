package org.ace.insurance.web.manage.life.billcollection;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.payment.AgentPaymentCashReceiptDTO;
import org.ace.insurance.payment.Payment;

public class BillCollectionCashReceiptDTO {
	private LifePolicy lifePolicy;
	private MedicalPolicy medicalPolicy;
	private Payment payment;
	private BillCollectionDTO billCollection;
	private AgentPaymentCashReceiptDTO agentComission;
	private ReferenceType referenceType;

	public BillCollectionCashReceiptDTO() {
	}

	public BillCollectionCashReceiptDTO(Payment payment, BillCollectionDTO billCollection, ReferenceType type) {
		this.payment = payment;
		this.billCollection = billCollection;
		this.referenceType = type;
	}

	public MedicalPolicy getMedicalPolicy() {
		return medicalPolicy;
	}

	public void setMedicalPolicy(MedicalPolicy medicalPolicy) {
		this.medicalPolicy = medicalPolicy;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BillCollectionDTO getBillCollection() {
		return billCollection;
	}

	public void setBillCollection(BillCollectionDTO billCollection) {
		this.billCollection = billCollection;
	}

	public AgentPaymentCashReceiptDTO getAgentComission() {
		return agentComission;
	}

	public void setAgentComission(AgentPaymentCashReceiptDTO agentComission) {
		this.agentComission = agentComission;
	}

}

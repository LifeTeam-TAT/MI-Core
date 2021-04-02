package org.ace.insurance.report.TLF;

import java.util.Date;

public class CeoShortTermLifeDTO {

	private String policyNo;
	private int policyTerm;
	private String paymentType;
	private Date activedPolicyStartDate;
	private Date activedPolicyEndDate;
	private double suminsured;
	private Date paymentDate;
	private double premium;

	public CeoShortTermLifeDTO(String policyNo, int policyTerm, String paymentType, Date activedPolicyStartDate, Date activedPolicyEndDate, double suminsured, Date paymentDate) {

		this.policyNo = policyNo;
		this.policyTerm = policyTerm;
		this.paymentType = paymentType;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.activedPolicyEndDate = activedPolicyEndDate;
		this.suminsured = suminsured;
		this.paymentDate = paymentDate;

	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public int getPolicyTerm() {
		return policyTerm;
	}

	public void setPolicyTerm(int policyTerm) {
		this.policyTerm = policyTerm;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public double getSuminsured() {
		return suminsured;
	}

	public void setSuminsured(double suminsured) {
		this.suminsured = suminsured;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

}

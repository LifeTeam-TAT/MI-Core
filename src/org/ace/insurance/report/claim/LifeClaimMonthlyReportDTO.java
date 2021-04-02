package org.ace.insurance.report.claim;

import java.util.Date;

public class LifeClaimMonthlyReportDTO {

	private String policyNo;

	private Date claimDate;

	private double suminsured;

	private double premium;

	private String paymentType;

	private double claimAmount;

	private float percentage;

	private Date policyStartDate;

	private Date policyEndDate;

	private String branchCode;

	private String claimType;

	private String causeOfReason;

	private String insuredPersonName;

	private Date paidDate;

	private String claimNo;

	public String getPolicyNo() {
		return policyNo;
	}

	public LifeClaimMonthlyReportDTO() {
		super();
	}

	public LifeClaimMonthlyReportDTO(LifeClaimMonthlyReportView view) {
		this.policyNo = view.getPolicyNo();
		this.claimDate = view.getClaimDate();
		this.suminsured = view.getSuminsured();
		this.premium = view.getPremium();
		this.paymentType = view.getPaymentType();
		this.claimAmount = view.getClaimAmount();
		this.percentage = view.getPercentage();
		this.policyStartDate = view.getPolicyStartDate();
		this.policyEndDate = view.getPolicyEndDate();
		this.branchCode = view.getBranchCode();
		this.claimType = view.getClaimType();
		this.causeOfReason = view.getCauseOfReason();
		this.insuredPersonName = view.getInsuredPersonName();
		this.claimNo = view.getClaimNo();
		this.paidDate = view.getPaidDate();
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public double getSuminsured() {
		return suminsured;
	}

	public void setSuminsured(double suminsured) {
		this.suminsured = suminsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getCauseOfReason() {
		return causeOfReason;
	}

	public void setCauseOfReason(String causeOfReason) {
		this.causeOfReason = causeOfReason;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

}

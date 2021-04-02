package org.ace.insurance.report.life;

import java.util.Date;

public class LifeClaimRegisterReport {
	private Date submittedDate;
	private String claimRequestId;
	// many
	private String policyInsuredPersonName;
	private String lifePolicyNo;
	private String claimRole;
	// many
	private String policyInsuredPersonAddress;
	private double totalSumInsured;
	private double paymentClaimAmount;
	private String paymentDate;
	// many
	private String remark;
	private String deductionAmount;
	private String branch;
	private double subTotal;

	public LifeClaimRegisterReport(Date submittedDate, String claimRequestId, String policyInsuredPersonName, String lifePolicyNo, String claimRole, String insuredPersonAddress,
			double totalSumInsured, double paymentClaimAmount, String paymentDate, String deductionAmount, String remark, String branch) {
		super();
		this.submittedDate = submittedDate;
		this.claimRequestId = claimRequestId;
		this.policyInsuredPersonName = policyInsuredPersonName;
		this.lifePolicyNo = lifePolicyNo;
		this.claimRole = claimRole;
		this.policyInsuredPersonAddress = insuredPersonAddress;
		this.totalSumInsured = totalSumInsured;
		this.paymentClaimAmount = paymentClaimAmount;
		this.paymentDate = paymentDate;
		this.deductionAmount = deductionAmount;
		this.branch = branch;
		this.remark = remark;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public String getPolicyInsuredPersonName() {
		return policyInsuredPersonName;
	}

	public String getLifePolicyNo() {
		return lifePolicyNo;
	}

	public String getClaimRole() {
		return claimRole;
	}

	public String getPolicyInsuredPersonAddress() {
		return policyInsuredPersonAddress;
	}

	public double getTotalSumInsured() {
		return totalSumInsured;
	}

	public double getPaymentClaimAmount() {
		return paymentClaimAmount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public String getRemark() {
		return remark;
	}

	public String getDeductionAmount() {
		return deductionAmount;
	}

	public String getBranch() {
		return branch;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
}

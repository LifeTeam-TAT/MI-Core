package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class CCL001 implements ISorter {
	private String id;
	private String claimReferenceNo;
	private String policyNo;
	private String customerName;
	private Date pendingSince;
	private double totalClaimAmount;
	private double surveyRemainingValue;
	private double approvedValue;

	public CCL001() {
	}

	public CCL001(String id, String claimReferenceNo, String policyNo, String customerName, Date pendingSince, double totalClaimAmount, double surveyRemainingValue,
			double approvedValue) {
		this.id = id;
		this.claimReferenceNo = claimReferenceNo;
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.pendingSince = pendingSince;
		this.totalClaimAmount = totalClaimAmount;
		this.surveyRemainingValue = surveyRemainingValue;
		this.approvedValue = approvedValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClaimReferenceNo() {
		return claimReferenceNo;
	}

	public void setClaimReferenceNo(String claimReferenceNo) {
		this.claimReferenceNo = claimReferenceNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getPendingSince() {
		return pendingSince;
	}

	public void setPendingSince(Date pendingSince) {
		this.pendingSince = pendingSince;
	}

	public double getTotalClaimAmount() {
		return totalClaimAmount;
	}

	public void setTotalClaimAmount(double totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
	}

	public double getSurveyRemainingValue() {
		return surveyRemainingValue;
	}

	public void setSurveyRemainingValue(double surveyRemainingValue) {
		this.surveyRemainingValue = surveyRemainingValue;
	}

	public double getApprovedValue() {
		return approvedValue;
	}

	public void setApprovedValue(double approvedValue) {
		this.approvedValue = approvedValue;
	}

	@Override
	public String getRegistrationNo() {
		return claimReferenceNo;
	}

}

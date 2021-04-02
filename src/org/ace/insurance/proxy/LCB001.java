package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class LCB001 implements ISorter{
	private String id;
	private String refundNo;
	private String beneficiaryName;
	private Date pendingDate;
	private double claimAmount;
	private double totalSumInsured;
	
	public LCB001() {
		
	}
	
	public LCB001(String id, String refundNo, String beneficiaryName,
			Date pendingDate, double claimAmount, double totalSumInsured) {
		this.id = id;
		this.refundNo = refundNo;
		this.beneficiaryName = beneficiaryName;
		this.pendingDate = pendingDate;
		this.claimAmount = claimAmount;
		this.totalSumInsured = totalSumInsured;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRefundNo() {
		return refundNo;
	}
	
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public Date getPendingDate() {
		return pendingDate;
	}
	
	public void setPendingDate(Date pendingDate) {
		this.pendingDate = pendingDate;
	}
	
	public double getClaimAmount() {
		return claimAmount;
	}
	
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}
	
	public double getTotalSumInsured() {
		return totalSumInsured;
	}
	
	public void setTotalSumInsured(double totalSumInsured) {
		this.totalSumInsured = totalSumInsured;
	}

	@Override
	public String getRegistrationNo() {
		return refundNo;
	}
	
	
}

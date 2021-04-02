package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class LCLD001 implements ISorter{
	private String id;
	private String claimRequestId;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private double totalSumInsured;
	
	public LCLD001() {
	}
	
	public LCLD001(String id, String claimRequestId, String customerName,
			Date submittedDate, Date pendingSince, double totalSumInsured) {
		this.id = id;
		this.claimRequestId = claimRequestId;
		this.customerName = customerName;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.totalSumInsured = totalSumInsured;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public void setClaimRequestId(String claimRequestId) {
		this.claimRequestId = claimRequestId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getPendingSince() {
		return pendingSince;
	}

	public void setPendingSince(Date pendingSince) {
		this.pendingSince = pendingSince;
	}

	public double getTotalSumInsured() {
		return totalSumInsured;
	}

	public void setTotalSumInsured(double totalSumInsured) {
		this.totalSumInsured = totalSumInsured;
	}

	@Override
	public String getRegistrationNo() {
		return claimRequestId;
	}
	
	
}

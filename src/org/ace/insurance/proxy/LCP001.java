package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.Name;

public class LCP001 {

	private String id;
	private String claimProposalNo;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private double totalAmount;

	public LCP001(String id, String claimProposalNo, String salutation, Name customerName, Date submittedDate, Date pendingSince, double totalAmount) {
		this.id = id;
		this.claimProposalNo = claimProposalNo;
		this.customerName = salutation != null ? salutation + " " + customerName.getFullName() : customerName.getFullName();
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.totalAmount = totalAmount;
	}

	public String getId() {
		return id;
	}

	public String getClaimProposalNo() {
		return claimProposalNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public Date getPendingSince() {
		return pendingSince;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setClaimProposalNo(String claimProposalNo) {
		this.claimProposalNo = claimProposalNo;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setPendingSince(Date pendingSince) {
		this.pendingSince = pendingSince;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

}

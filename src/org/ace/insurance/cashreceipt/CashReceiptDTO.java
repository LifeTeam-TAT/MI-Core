package org.ace.insurance.cashreceipt;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class CashReceiptDTO implements ISorter {
	private String id;
	private String proposalNo;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private double sumInsured;
	
	public CashReceiptDTO(String id, String proposalNo,
			String customerName, Date submittedDate, Date pendingSince,
			double sumInsured) {
		super();
		this.id = id;
		this.proposalNo = proposalNo;
		this.customerName = customerName;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.sumInsured = sumInsured;
	}

	public String getId() {
		return id;
	}

	public String getProposalNo() {
		return proposalNo;
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
	
	public double getSumInsured() {
		return sumInsured;
	}
	
	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}
}

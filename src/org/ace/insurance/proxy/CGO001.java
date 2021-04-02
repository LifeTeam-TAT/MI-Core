package org.ace.insurance.proxy;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.interfaces.IDataModel;

public class CGO001 implements Serializable, ISorter, IDataModel {
	private static final long serialVersionUID = 1L;

	private String id;
	private String proposalNo;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private double sumInsured;
	private String cur;

	public CGO001(String id, String proposalNo, String customerId, Name customer, String orgnization, Date submittedDate, Date createdDate, String currency, Double proposedSI,
			Double approvedSI, boolean isApproved) {
		this.id = id;
		this.proposalNo = proposalNo;
		this.customerName = customerId != null ? customerId + " " + customer.getFullName() : orgnization;
		this.submittedDate = submittedDate;
		this.pendingSince = createdDate;
		this.sumInsured = isApproved ? approvedSI : proposedSI;
		this.cur = currency;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
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

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}

}

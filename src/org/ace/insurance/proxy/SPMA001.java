package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.Name;

public class SPMA001 {
	private String invoiceNo;
	private String policyNo;
	private String customerName;
	private Date submittedDate;
	private double premium;

	public SPMA001() {
	}

	public SPMA001(String invoiceNo, String policyNo, String cusId, String initialId, Name cusName, String org, Date submittedDate, double premium) {
		this.invoiceNo = invoiceNo;
		this.policyNo = policyNo;
		this.customerName = cusId != null ? initialId + " " + cusName.getFullName() : org;
		this.submittedDate = submittedDate;
		this.premium = premium;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

}

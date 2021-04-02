package org.ace.insurance.proxy;

import java.util.Date;

public class COCL001 {

	private String invoiceNo;
	private String companyName;
	private Date invoiceDate;
	private double claimAmount;

	public COCL001() {

	}

	public COCL001(String invoiceNo, String companyName, Date invoiceDate, double claimAmount) {
		super();
		this.invoiceNo = invoiceNo;
		this.companyName = companyName;
		this.invoiceDate = invoiceDate;
		this.claimAmount = claimAmount;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

}

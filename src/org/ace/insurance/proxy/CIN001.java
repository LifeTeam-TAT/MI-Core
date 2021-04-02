package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class CIN001 implements ISorter {
	private static final long serialVersionUID = 1L;

	private String invoiceNo;
	private String companyName;
	private Date invoiceDate;
	private double sumInsuranced;
	private double premium;
	private double receivedSumInsured;

	public CIN001() {

	}

	public CIN001(String invoiceNo, String companyName, Date invoiceDate, double sumInsuranced, double premium, double receivedSumInsured) {
		this.invoiceNo = invoiceNo;
		this.companyName = companyName;
		this.invoiceDate = invoiceDate;
		this.sumInsuranced = sumInsuranced;
		this.premium = premium;
		this.receivedSumInsured = receivedSumInsured;
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

	public double getSumInsuranced() {
		return sumInsuranced;
	}

	public void setSumInsuranced(double sumInsuranced) {
		this.sumInsuranced = sumInsuranced;
	}

	// public double getNetPremium() {
	// return netPremium;
	// }

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getReceivedSumInsured() {
		return receivedSumInsured;
	}

	public void setReceivedSumInsured(double receivedSumInsured) {
		this.receivedSumInsured = receivedSumInsured;
	}

	@Override
	public String getRegistrationNo() {
		return null;
	}
}

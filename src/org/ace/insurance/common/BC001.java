package org.ace.insurance.common;

import java.util.Date;

public class BC001 implements ISorter {
	private static final long serialVersionUID = 1L;

	private String invoiceNo;
	private Date invoiceDate;
	private String policyNo;
	private String customerName;
	private double sumInsured;
	private int unit;
	private double premium;

	public BC001() {

	}

	/* used for fire, life */
	public BC001(String invoiceNo, Date invoiceDate, String policyNo, String cusId, String initialId, Name cusName, Name cusHName, String org, double premium, double sumInsured) {
		super();
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.policyNo = policyNo;
		this.customerName = cusId != null ? (initialId + " " + (cusName != null ? cusName.getFullName() : cusHName.getFullName())) : org;
		this.sumInsured = sumInsured;
		this.premium = premium;
	}

	/* used for medical */
	public BC001(String invoiceNo, Date invoiceDate, String policyNo, String cusId, String initialId, Name cusName, String org, double premium, double sumInsured) {
		super();
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.policyNo = policyNo;
		this.customerName = cusId != null ? (initialId + " " + cusName.getFullName()) : org;
		this.sumInsured = sumInsured;
		this.premium = premium;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	@Override
	public String getRegistrationNo() {
		return invoiceNo;
	}
}

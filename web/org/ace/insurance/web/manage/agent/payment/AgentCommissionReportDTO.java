package org.ace.insurance.web.manage.agent.payment;

import java.util.Date;

public class AgentCommissionReportDTO {
	private String invoiceNo;
	private Date invoiceDate;
	private String referenceNo;
	private double commissionAmount;
	private double commissionPercentage;
	private double premium;
	private double renewalPremium;
	private double sumInsured;
	private String customerName;
	private double withHoldingTax;

	public AgentCommissionReportDTO() {
		super();

	}

	public AgentCommissionReportDTO(String invoiceNo, Date invoiceDate, String referenceNo, double commissionAmount, double withHoldingTax, double commissionPercentage,
			double premium, double renewalPremium, double sumInsured, String customerName) {
		super();
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.referenceNo = referenceNo;
		this.commissionAmount = commissionAmount;
		this.commissionPercentage = commissionPercentage;
		this.premium = premium;
		this.renewalPremium = renewalPremium;
		this.sumInsured = sumInsured;
		this.customerName = customerName;
		this.withHoldingTax = withHoldingTax;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public double getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getRenewalPremium() {
		return renewalPremium;
	}

	public void setRenewalPremium(double renewalPremium) {
		this.renewalPremium = renewalPremium;
	}

	public double getWithHoldingTax() {
		return withHoldingTax;
	}

	public void setWithHoldingTax(double withHoldingTax) {
		this.withHoldingTax = withHoldingTax;
	}

}

package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class CIN002 implements ISorter {
	private static final long serialVersionUID = 1L;

	private String id;
	private String invoiceNo;
	private String policyNo;
	private String companyName;
	private Date invoiceDate;
	private double sumInsuranced;
	private double netPremium;
	private double premium;
	private String coinsuranceNo;
	private double receivedSumInsured;

	public CIN002() {

	}

	public CIN002(String id, String invoiceNo, String policyNo, String companyName, Date invoiceDate, double sumInsuranced, double premium, double netPremium,
			String coinsuranceNo, double receivedSumInsured) {
		this.id = id;
		this.invoiceNo = invoiceNo;
		this.policyNo = policyNo;
		this.companyName = companyName;
		this.invoiceDate = invoiceDate;
		this.sumInsuranced = sumInsuranced;
		this.netPremium = netPremium;
		this.premium = premium;
		this.coinsuranceNo = coinsuranceNo;
		this.receivedSumInsured = receivedSumInsured;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public double getNetPremium() {
		return netPremium;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public void setNetPremium(double netPremium) {
		this.netPremium = netPremium;
	}

	public String getCoinsuranceNo() {
		return coinsuranceNo;
	}

	public void setCoinsuranceNo(String coinsuranceNo) {
		this.coinsuranceNo = coinsuranceNo;
	}

	public double getReceivedSumInsured() {
		return receivedSumInsured;
	}

	public void setReceivedSumInsured(double receivedSumInsured) {
		this.receivedSumInsured = receivedSumInsured;
	}

	@Override
	public String getRegistrationNo() {
		return invoiceNo;
	}
}

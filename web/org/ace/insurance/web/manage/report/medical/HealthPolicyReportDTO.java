package org.ace.insurance.web.manage.report.medical;

import java.util.Date;

public class HealthPolicyReportDTO {
	private String policyNo;
	private String proposalNo;
	private String cashReceiptNo;
	private Date cashReceiptDate;
	private String customerName;
	private String nrcNo;
	private String address;
	private String unit;
	private double premium;
	private String branch;
	private String remarks;
	private double addOnTermPremium;

	public HealthPolicyReportDTO() {
		super();
	}

	// public HealthPolicyReportDTO(String policyNo, String proposalNo,
	// String cashReceiptNo, Date cashReceiptDate, String customerName,
	// String nrcNo, String address, String unit, double premium,
	// Branch branch, String remarks, double addOnTermPremium) {
	// super();
	// this.policyNo = policyNo;
	// this.proposalNo = proposalNo;
	// this.cashReceiptNo = cashReceiptNo;
	// this.cashReceiptDate = cashReceiptDate;
	// this.customerName = customerName;
	// this.nrcNo = nrcNo;
	// this.address = address;
	// this.unit = unit;
	// this.premium = premium;
	// this.branch = branch;
	// this.remarks = remarks;
	// this.addOnTermPremium = addOnTermPremium;
	// }

	public HealthPolicyReportDTO(String policyNo, String proposalNo, String cashReceiptNo, Date cashReceiptDate, String customerName, String nrcNo, String address, String unit,
			double premium, String branch, double addOnTermPremium) {
		super();
		this.policyNo = policyNo;
		this.proposalNo = proposalNo;
		this.cashReceiptNo = cashReceiptNo;
		this.cashReceiptDate = cashReceiptDate;
		this.customerName = customerName;
		this.nrcNo = nrcNo;
		this.address = address;
		this.unit = unit;
		this.premium = premium + addOnTermPremium;
		this.branch = branch;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}

	public Date getCashReceiptDate() {
		return cashReceiptDate;
	}

	public void setCashReceiptDate(Date cashReceiptDate) {
		this.cashReceiptDate = cashReceiptDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNrcNo() {
		return nrcNo;
	}

	public void setNrcNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getAddOnTermPremium() {
		return addOnTermPremium;
	}

	public void setAddOnTermPremium(double addOnTermPremium) {
		this.addOnTermPremium = addOnTermPremium;
	}

}

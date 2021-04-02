package org.ace.insurance.web.manage.report.medical;

import java.util.Date;

public class HealthDailyIncomeReportDTO {
	private String policyNo;
	private String cashReceiptNo;
	private String customerName;
	private Date commenmanceDate;
	private Date paymentDate;
	private String unit;
	private double amount;
	private double stampFee;
	private double totalAmount;
	private String branch;
	private int paymentTerm;

	public HealthDailyIncomeReportDTO() {
		super();
	}

	public HealthDailyIncomeReportDTO(String policyNo, String cashReceiptNo, String customerName, Date commenmanceDate, Date paymentDate, String unit, double amount,
			double stampFee, double totalAmount, String branch, int paymentTerm) {
		super();
		this.policyNo = policyNo;
		this.cashReceiptNo = cashReceiptNo;
		this.customerName = customerName;
		this.commenmanceDate = commenmanceDate;
		this.paymentDate = paymentDate;
		this.unit = unit;
		this.amount = amount;
		this.stampFee = stampFee;
		this.totalAmount = totalAmount;
		this.branch = branch;
		this.paymentTerm = paymentTerm;
	}

	public HealthDailyIncomeReportDTO(String policyNo, String cashReceiptNo, String customerName, Date commenmanceDate, Date paymentDate, String unit, double basicTermPremium,
			double addonTermPremium, double stampFee, String branch, int paymentTerm, double totalAmount) {
		this.policyNo = policyNo;
		this.cashReceiptNo = cashReceiptNo;
		this.customerName = customerName;
		this.commenmanceDate = commenmanceDate;
		this.paymentDate = paymentDate;
		this.unit = unit;
		this.amount = basicTermPremium + addonTermPremium;
		this.stampFee = stampFee;
		this.totalAmount = amount + stampFee;
		this.branch = branch;
		this.paymentTerm = paymentTerm;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getStampFee() {
		return stampFee;
	}

	public void setStampFee(double stampFee) {
		this.stampFee = stampFee;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

}

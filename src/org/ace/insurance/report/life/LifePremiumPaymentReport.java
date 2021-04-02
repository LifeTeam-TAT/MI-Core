package org.ace.insurance.report.life;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.report.life.view.LifePolicyView;

public class LifePremiumPaymentReport implements ISorter {
	private static final long serialVersionUID = 1L;
	private String policyNo;
	private String cashReceiptNo;
	private String customerName;
	private double sumInsured;
	private double oneYearPremium;
	private double income;
	private String branch;

	public LifePremiumPaymentReport() {
	}

	public LifePremiumPaymentReport(LifePolicyView view) {
		this.policyNo = view.getPolicyNo();
		this.cashReceiptNo = view.getReceiptNo();
		this.customerName = view.getCustomerName() != null ? view.getCustomerName() : view.getOrganizationName();
		this.sumInsured = view.getSumInsured();
		this.oneYearPremium = view.getOneYearPremium();
		this.income = view.getPremium();
		this.branch = view.getBranchName();
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

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getOneYearPremium() {
		return oneYearPremium;
	}

	public void setOneYearPremium(double oneYearPremium) {
		this.oneYearPremium = oneYearPremium;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
}

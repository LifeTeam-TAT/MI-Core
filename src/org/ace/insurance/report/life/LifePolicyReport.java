package org.ace.insurance.report.life;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.report.life.view.LifePolicyView;

public class LifePolicyReport implements ISorter {

	private static final long serialVersionUID = 2871871150226333134L;

	public String policyNo;
	public String proposalNo;
	public String receiptNo;
	public Date cashReceiptDate;
	public String customerName;
	public String address;
	public double sumInsured;
	public double premium;
	public String branchName;
	public double subTotalSI;
	public double subTotalPremium;

	public LifePolicyReport() {

	}

	public LifePolicyReport(LifePolicyView lifePolicyView) {
		this.policyNo = lifePolicyView.getPolicyNo();
		this.proposalNo = lifePolicyView.getProposalNo();
		this.customerName = lifePolicyView.getName();
		this.address = lifePolicyView.getAddress();
		this.sumInsured = lifePolicyView.getSumInsured();
		this.premium = lifePolicyView.getPremium();
		this.cashReceiptDate = lifePolicyView.getCashReceiptDate();
		this.receiptNo = lifePolicyView.getReceiptNo();
		this.branchName = lifePolicyView.getBranchName();
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

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getSubTotalSI() {
		return subTotalSI;
	}

	public void setSubTotalSI(double subTotalSI) {
		this.subTotalSI = subTotalSI;
	}

	public double getSubTotalPremium() {
		return subTotalPremium;
	}

	public void setSubTotalPremium(double subTotalPremium) {
		this.subTotalPremium = subTotalPremium;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
}

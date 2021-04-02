package org.ace.insurance.report.life;

import java.util.Date;

import org.ace.insurance.common.PolicyStatus;

public class LifeClaimStatusReport {
	private String policyNo;
	private String customerName;
	private int age;
	private String address;
	private String agentName;
	private String branchName;
	private String paymentType;
	private int policyPeriod;
	private double sumInsured;
	private double amount;
	private Date submittedDate;
	private PolicyStatus policyStatus;

	public LifeClaimStatusReport() {

	}

	public LifeClaimStatusReport(String policyNo, String customerName, int age, String address, String agentName, String branchName, String paymentType, int policyPeriod,
			double sumInsured, double amount, Date submittedDate, PolicyStatus policyStatus) {
		super();
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.age = age;
		this.address = address;
		this.agentName = agentName;
		this.branchName = branchName;
		this.paymentType = paymentType;
		this.policyPeriod = policyPeriod;
		this.sumInsured = sumInsured;
		this.amount = amount;
		this.submittedDate = submittedDate;
		this.policyStatus = policyStatus;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getPolicyPeriod() {
		return policyPeriod;
	}

	public void setPolicyPeriod(int policyPeriod) {
		this.policyPeriod = policyPeriod;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

}

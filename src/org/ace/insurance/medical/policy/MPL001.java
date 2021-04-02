package org.ace.insurance.medical.policy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class MPL001 implements ISorter {
	private static final long serialVersionUID = 1L;
	private String id;
	private String policyNo;
	private String agent;
	private String customer;
	private String branch;
	private double unit;
	private double totClaimAmount;
	private int hospitalizedDay;
	private Date commenmanceDate;
	private String expired;
	private String paymentType;
	private Date policyEndDate;

	public MPL001() {
	}

	public MPL001(String id, String policyNo, String agent, String customer, String branch, int unit, double totClaimAmount, int hospitalizedDay, Date commenmanceDate,
			String expired) {
		this.id = id;
		this.policyNo = policyNo;
		this.agent = agent;
		this.customer = customer;
		this.branch = branch;
		this.unit = unit;
		this.totClaimAmount = totClaimAmount;
		this.hospitalizedDay = hospitalizedDay;
		this.commenmanceDate = commenmanceDate;
		this.expired = expired;
	}

	/*
	 * Use by MedicalPolicy DAO's JPQL Query
	 */
	public MPL001(String id, String policyNo, Long unit, String agent,  String customer, String organization, String branch, String paymentType,
			Date policyEndDate) {
		this.id = id;
		this.policyNo = policyNo;
		this.agent = agent;
		this.customer = customer != null ? customer : organization;
		this.branch = branch;
		this.unit = unit != null ? unit.doubleValue() : 0;
		this.paymentType = paymentType;
		this.policyEndDate = policyEndDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public double getUnit() {
		return unit;
	}

	public void setUnit(double unit) {
		this.unit = unit;
	}

	public double getTotClaimAmount() {
		return totClaimAmount;
	}

	public void setTotClaimAmount(double totClaimAmount) {
		this.totClaimAmount = totClaimAmount;
	}

	public int getHospitalizedDay() {
		return hospitalizedDay;
	}

	public void setHospitalizedDay(int hospitalizedDay) {
		this.hospitalizedDay = hospitalizedDay;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

}

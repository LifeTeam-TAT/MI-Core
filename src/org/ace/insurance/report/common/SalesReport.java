package org.ace.insurance.report.common;

import java.util.Date;

import org.ace.insurance.report.agent.AgentSaleReportView;

public class SalesReport {

	private String agentName;
	private String productType;
	private String policyNo;
	private String policyHolder;
	private Date dateOfInsured;
	private String period;
	private double insuredAmount;
	private double premium;
	private String proposalNo;
	private double commission;
	private String remark;
	private String unit;
	private String insuranceType;

	public SalesReport() {
	}

	public SalesReport(String agentName, String productType, String policyNo, String policyHolder, Date dateOfInsured, String period, double insuredAmount, double premium,
			String proposalNo, double commission, String insuranceType, String remark) {
		super();
		this.agentName = agentName;
		this.productType = productType;
		this.policyNo = policyNo;
		this.policyHolder = policyHolder;
		this.dateOfInsured = dateOfInsured;
		this.period = period;
		this.insuredAmount = insuredAmount;
		this.premium = premium;
		this.proposalNo = proposalNo;
		this.commission = commission;
		this.insuranceType = insuranceType;
		this.remark = remark;
	}

	public SalesReport(AgentSaleReportView view) {
		super();
		this.agentName = view.getAgentName();
		this.productType = view.getProductType();
		this.policyNo = view.getPolicyNo();
		this.policyHolder = view.getPolicyHolder();
		this.dateOfInsured = view.getDateOfInsured();
		this.period = view.getPeriod();
		this.insuredAmount = view.getInsuredAmount();
		this.premium = view.getPremium();
		this.proposalNo = view.getProposalNo();
		this.commission = view.getCommission();
		this.insuranceType = view.getInsuranceType();
		this.unit = view.getUnit();
		this.remark = view.getRemark();
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(String policyHolder) {
		this.policyHolder = policyHolder;
	}

	public Date getDateOfInsured() {
		return dateOfInsured;
	}

	public void setDateOfInsured(Date dateOfInsured) {
		this.dateOfInsured = dateOfInsured;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
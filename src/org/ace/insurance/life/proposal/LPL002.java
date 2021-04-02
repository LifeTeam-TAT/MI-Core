package org.ace.insurance.life.proposal;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.web.common.SaleChannelType;

public class LPL002 implements ISorter {
	private String id;
	private String policyNo;
	private String proposalNo;
	private String agent;
	private String customer;
	private String organization;
	private String branch;
	private double premium;
	private double sumInsured;
	private int unit;
	private String paymentType;
	private Date commenmanceDate;
	private SaleChannelType saleChannelType;

	public LPL002(String id, String policyNo, String proposalNo, String agent, String customer, String organization, String branch, double premium, double sumInsured, int unit,
			String paymenttype, Date commenmanceDate, SaleChannelType saleChannelType) {
		this.id = id;
		this.policyNo = policyNo;
		this.proposalNo = proposalNo;
		this.agent = agent;
		this.customer = customer;
		this.organization = organization;
		this.branch = branch;
		this.premium = premium;
		this.sumInsured = sumInsured;
		this.unit = unit;
		this.paymentType = paymenttype;
		this.commenmanceDate = commenmanceDate;
		this.saleChannelType = saleChannelType;
	}

	public LPL002() {

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

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

}

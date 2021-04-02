package org.ace.insurance.medical.proposal;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.web.common.SaleChannelType;

public class MP001 implements ISorter {
	private static final long serialVersionUID = 1L;
	private String id;
	private String proposalNo;
	private Agent agent;
	private String customer;
	private String organization;
	private String branch;
	private int unit;
	private double totalPremium;
	private Date submittedDate;
	private String policyNo;
	private String receivedNo;
	private String salePerson;
	private String productId;
	private SaleChannelType saleChannelType;
	private String salePoint;

	public MP001() {
	}

	public MP001(String id, String proposalNo, SaleChannelType saleChannelType, String salePerson, String customer, String organization, String branch, Long unit,
			Double totalPremium, String productId, Date submittedDate, String policyNo, String receivedNo, String salePoint) {
		this.id = id;
		this.proposalNo = proposalNo;
		this.saleChannelType = saleChannelType;
		this.customer = customer;
		this.organization = organization;
		this.branch = branch;
		this.unit = unit != null ? unit.intValue() : 0;
		this.totalPremium = totalPremium != null ? totalPremium.doubleValue() : 0;
		this.productId = productId;
		this.submittedDate = submittedDate;
		this.policyNo = policyNo;
		this.receivedNo = receivedNo;
		this.salePerson = salePerson;
		this.salePoint = salePoint;
	}

	public String getReceivedNo() {
		return receivedNo;
	}

	public void setReceivedNo(String receivedNo) {
		this.receivedNo = receivedNo;
	}

	public String getSalePerson() {
		return salePerson;
	}

	public void setSalePerson(String salePerson) {
		this.salePerson = salePerson;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getId() {
		return id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public Agent getAgent() {
		return agent;
	}

	public String getCustomer() {
		return customer;
	}

	public String getBranch() {
		return branch;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(String salePoint) {
		this.salePoint = salePoint;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}

	public String getCustomerName() {
		if (customer != null) {
			return customer;
		} else if (organization != null) {
			return organization;
		} else
			return "";
	}

}

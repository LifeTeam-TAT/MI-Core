package org.ace.insurance.medical.policy;

import java.util.Date;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.Utils;
import org.ace.insurance.web.common.SaleChannelType;

public class MED002 {

	private String id;
	private String policyNo;
	private String proposalNo;
	private SaleChannelType saleChannelType;
	private String agent;
	private String customer;
	private String organization;
	private String branch;
	private int unit;
	private double premium;
	private Date policyStartDate;
	private Date policyEndDate;

	public MED002(String id, String policyNo, String proposalNo, String agentInitialId, Name agentName, String saleManInitialId, Name saleManName, String customerInitialId,
			Name customerName, String organizationName, String branchName, Date policyStartDate, Date policyEndDate) {
		super();
		this.id = id;
		this.policyNo = policyNo;
		this.proposalNo = proposalNo;
		this.agent = Utils.getCompleteName(agentInitialId, agentName);
		this.customer = Utils.getCompleteName(customerInitialId, customerName);
		this.organization = organizationName;
		this.branch = branchName;
		this.policyStartDate = policyStartDate;
		this.policyEndDate = policyEndDate;
	}

	public MED002(String id, String policyNo, String proposalNo, SaleChannelType saleChannelType, String agentName, String customerName, String organizationName, String branchName,
			int unit, double premium, Date policyStartDate, Date policyEndDate) {
		super();
		this.id = id;
		this.policyNo = policyNo;
		this.proposalNo = proposalNo;
		this.saleChannelType = saleChannelType;
		this.agent = agentName;
		this.customer = customerName;
		this.organization = organizationName;
		this.branch = branchName;
		this.unit = unit;
		this.premium = premium;
		this.policyStartDate = policyStartDate;
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

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public String getAgent() {
		if (agent == null || agent == "")
			return "-";
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
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

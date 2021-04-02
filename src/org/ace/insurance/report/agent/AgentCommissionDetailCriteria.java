package org.ace.insurance.report.agent;

import java.util.Date;

import org.ace.insurance.common.AgentStatus;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.currency.Currency;

public class AgentCommissionDetailCriteria {
	public Agent agent;
	public InsuranceType insuranceType;
	private Date startDate;
	private Date endDate;
	private AgentStatus agentStatus;
	private Currency currency;
	private String branchId;

	public AgentCommissionDetailCriteria() {
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public AgentStatus getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(AgentStatus agentStatus) {
		this.agentStatus = agentStatus;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}
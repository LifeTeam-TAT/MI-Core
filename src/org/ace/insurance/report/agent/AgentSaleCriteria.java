package org.ace.insurance.report.agent;

import java.util.Date;

import org.ace.insurance.common.AgentSaleInsuranceType;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;

public class AgentSaleCriteria {
	private AgentSaleInsuranceType insuranceType;
	private Branch branch;
	private Agent agent;
	private Date startDate;
	private Date endDate;

	public AgentSaleCriteria() {

	}

	public AgentSaleCriteria(AgentSaleInsuranceType insuranceType, Branch branch, Agent agent, Date startDate, Date endDate) {
		this.insuranceType = insuranceType;
		this.branch = branch;
		this.agent = agent;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void setInsuranceType(AgentSaleInsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public AgentSaleInsuranceType getInsuranceType() {
		return insuranceType;
	}

}

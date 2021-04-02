package org.ace.insurance.web.manage.report.medical;

import java.util.Date;

import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;

public class HealthProposalReportCriteria {
	private Date startDate;
	private Date endDate;
	private Branch branch;
	private Agent agent;
	private Customer customer;

	public HealthProposalReportCriteria(Date startDate, Date endDate, Branch branch, Agent agent, Customer customer) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.branch = branch;
		this.agent = agent;
		this.customer = customer;
	}

	public HealthProposalReportCriteria() {
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

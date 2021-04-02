package org.ace.insurance.web.manage.report.medical;

import java.util.Date;

import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;

public class HealthPolicyReportCriteria {
	private Date paymentStartDate;
	private Date paymentEndDate;
	private Date commenceStartDate;
	private Date commenceEndDate;
	private Branch branch;
	private Agent agent;
	private Customer customer;

	public HealthPolicyReportCriteria() {
		super();
	}

	// public HealthPolicyReportCriteria(Date startDate, Date endDate, Branch
	// branch, Agent agent, Customer customer) {
	// super();
	// this.startDate = startDate;
	// this.endDate = endDate;
	// this.branch = branch;
	// this.agent = agent;
	// this.customer = customer;
	// }

	public Branch getBranch() {
		return branch;
	}

	public Date getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentStartDate(Date paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public Date getPaymentEndDate() {
		return paymentEndDate;
	}

	public void setPaymentEndDate(Date paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}

	public Date getCommenceStartDate() {
		return commenceStartDate;
	}

	public void setCommenceStartDate(Date commenceStartDate) {
		this.commenceStartDate = commenceStartDate;
	}

	public Date getCommenceEndDate() {
		return commenceEndDate;
	}

	public void setCommenceEndDate(Date commenceEndDate) {
		this.commenceEndDate = commenceEndDate;
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

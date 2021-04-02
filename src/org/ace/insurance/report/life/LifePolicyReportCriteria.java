package org.ace.insurance.report.life;

import java.util.Date;

import org.ace.insurance.common.ProposalType;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;

public class LifePolicyReportCriteria {
	private Date paymentStartDate;
	private Date paymentEndDate;
	private Date commenceStartDate;
	private Date commenceEndDate;
	private Agent agent;
	private Customer customer;
	private Branch branch;
	private Organization organization;
	private ProposalType proposaltype;
	private Product product;

	public LifePolicyReportCriteria() {

	}

	/*
	 * public LifePolicyReportCriteria(Date startDate, Date endDate, Agent
	 * agent, Customer customer, Branch branch, Organization organization,
	 * ProposalType proposaltype, Product product) { super(); this.startDate =
	 * startDate; this.endDate = endDate; this.agent = agent; this.customer =
	 * customer; this.branch = branch; this.organization = organization;
	 * this.proposaltype = proposaltype; this.product = product; }
	 */

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public ProposalType getProposaltype() {
		return proposaltype;
	}

	public void setProposaltype(ProposalType proposaltype) {
		this.proposaltype = proposaltype;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}

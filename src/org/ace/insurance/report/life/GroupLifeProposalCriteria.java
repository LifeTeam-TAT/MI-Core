package org.ace.insurance.report.life;

/**
 * @author NNH
 * 
 */
import java.util.Date;

import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;

public class GroupLifeProposalCriteria {
	private Date startDate;
	private Date endDate;
	private Agent agent;
	private Customer customer;
	private Organization organization;
	private Branch branch;
	private String product;
	
	public GroupLifeProposalCriteria(){
		
	}

	public GroupLifeProposalCriteria(Date startDate, Date endDate,Agent agent, Customer customer, Organization organization, Branch branch, String product) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.agent = agent;
		this.customer = customer;
		this.organization = organization;
		this.branch = branch;
		this.product = product;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
}

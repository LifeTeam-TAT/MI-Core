package org.ace.insurance.report.agent;

import java.util.Date;

import org.ace.insurance.common.AgentSaleInsuranceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.currency.Currency;

public class AgentSalesReportCriteria {
	private Date startDate;
	private Date endDate;
	private AgentSaleInsuranceType insuranceType;
	private Branch branch;
	private Agent agent;
	private Currency currency;
	private String proposalType;
	private int month;
	private int year;

	public AgentSalesReportCriteria() {
	}

	public AgentSalesReportCriteria(Date startDate, Date endDate, AgentSaleInsuranceType insuranceType, Branch branch, Agent agent, Currency currency, String proposalType,
			int month, int year) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.insuranceType = insuranceType;
		this.branch = branch;
		this.agent = agent;
		this.currency = currency;
		this.proposalType = proposalType;
		this.month = month;
		this.year = year;
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

	public AgentSaleInsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(AgentSaleInsuranceType insuranceType) {
		this.insuranceType = insuranceType;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}

	public int getMonth() {
		return month;
	}

	public String getMonthString() {
		return Utils.getMonthString(month);
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
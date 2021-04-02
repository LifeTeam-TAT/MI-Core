package org.ace.insurance.report.common;

import org.ace.insurance.common.Utils;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.currency.Currency;

public class AgentSaleComparisonCriteria {

	private int month;
	private int year;
	private Branch branch;
	private Currency currencyType;
	private String proposalType;

	public AgentSaleComparisonCriteria() {
	}

	public AgentSaleComparisonCriteria(int month, int year, Branch branch, Currency currencyType, String proposalType) {
		this.month = month;
		this.year = year;
		this.branch = branch;
		this.currencyType = currencyType;
		this.proposalType = proposalType;
	}

	public String getMonthString() {
		return Utils.getMonthString(month);
	}

	public int getMonth() {
		return month;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Currency getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Currency currency) {
		this.currencyType = currency;
	}

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}

}

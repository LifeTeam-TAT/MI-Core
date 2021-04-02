package org.ace.insurance.common;

import java.io.Serializable;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.currency.Currency;

public class AgentMonthlySaleCriteria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int month;
	private int year;
	private Branch branch;
	private Currency currency;

	public AgentMonthlySaleCriteria() {
		super();
	}

	public AgentMonthlySaleCriteria(int month, int year, Branch branch, Currency currency) {
		super();
		this.month = month;
		this.year = year;
		this.branch = branch;
		this.currency = currency;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentMonthlySaleCriteria other = (AgentMonthlySaleCriteria) obj;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}

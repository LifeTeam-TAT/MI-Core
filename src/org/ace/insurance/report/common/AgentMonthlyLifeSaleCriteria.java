package org.ace.insurance.report.common;

import org.ace.insurance.common.Utils;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;

public class AgentMonthlyLifeSaleCriteria {

	private int year;
	private int month;
	private Branch branch;
	private Agent agent;

	public String getMonthString() {
		return Utils.getMonthString(month);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
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

}

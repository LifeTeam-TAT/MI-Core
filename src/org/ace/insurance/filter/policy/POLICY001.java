package org.ace.insurance.filter.policy;

import java.util.Date;

public class POLICY001 {

	private String policyNo;
	private Date startDate;
	private Date endDate;
	private String AgentName;
	private String branchName;

	public POLICY001() {
	}

	public POLICY001(String policyNo, Date startDate, Date endDate, String agentName, String branchName) {
		super();
		this.policyNo = policyNo;
		this.startDate = startDate;
		this.endDate = endDate;
		AgentName = agentName;
		this.branchName = branchName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

	public String getAgentName() {
		return AgentName;
	}

	public void setAgentName(String agentName) {
		AgentName = agentName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}

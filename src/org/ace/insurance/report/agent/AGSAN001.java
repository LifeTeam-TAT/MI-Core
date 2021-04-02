package org.ace.insurance.report.agent;

public class AGSAN001 {

	private String sanctionDescription;
	private double totalCommission;
	private String agentName;

	public AGSAN001() {

	}

	public AGSAN001(String sanctionDescription, double totalCommission, String agentName) {
		this.sanctionDescription = sanctionDescription;
		this.totalCommission = totalCommission;
		this.agentName = agentName;
	}

	public String getSanctionDescription() {
		return sanctionDescription;
	}

	public void setSanctionDescription(String sanctionDescription) {
		this.sanctionDescription = sanctionDescription;
	}

	public double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

}

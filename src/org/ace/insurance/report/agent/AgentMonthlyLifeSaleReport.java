package org.ace.insurance.report.agent;

public class AgentMonthlyLifeSaleReport {

	private String id;
	private String agentName;
	private String agentCodeNo;
	private int endowmentPolicy;
	private double endowmentPremium;
	private int groupPolicy;
	private double groupPremium;
	private int healthPolicy;
	private double healthPremium;
	private int totalPolicy;
	private double totalPremium;

	public AgentMonthlyLifeSaleReport(String id, String agentName, String agentCodeNo, int endowmentPolicy, double endowmentPremium, int groupPolicy, double groupPremium,
			int healthPolicy, double healthPremium, int totalPolicy, double totalPremium) {
		this.id = id;
		this.agentName = agentName;
		this.agentCodeNo = agentCodeNo;
		this.endowmentPolicy = endowmentPolicy;
		this.endowmentPremium = endowmentPremium;
		this.groupPolicy = groupPolicy;
		this.groupPremium = groupPremium;
		this.healthPolicy = healthPolicy;
		this.healthPremium = healthPremium;
		this.totalPolicy = totalPolicy;
		this.totalPremium = totalPremium;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentCodeNo() {
		return agentCodeNo;
	}

	public void setAgentCodeNo(String agentCodeNo) {
		this.agentCodeNo = agentCodeNo;
	}

	public int getEndowmentPolicy() {
		return endowmentPolicy;
	}

	public void setEndowmentPolicy(int endowmentPolicy) {
		this.endowmentPolicy = endowmentPolicy;
	}

	public double getEndowmentPremium() {
		return endowmentPremium;
	}

	public void setEndowmentPremium(double endowmentPremium) {
		this.endowmentPremium = endowmentPremium;
	}

	public int getGroupPolicy() {
		return groupPolicy;
	}

	public void setGroupPolicy(int groupPolicy) {
		this.groupPolicy = groupPolicy;
	}

	public double getGroupPremium() {
		return groupPremium;
	}

	public void setGroupPremium(double groupPremium) {
		this.groupPremium = groupPremium;
	}

	public int getHealthPolicy() {
		return healthPolicy;
	}

	public void setHealthPolicy(int healthPolicy) {
		this.healthPolicy = healthPolicy;
	}

	public double getHealthPremium() {
		return healthPremium;
	}

	public void setHealthPremium(double healthPremium) {
		this.healthPremium = healthPremium;
	}

	public int getTotalPolicy() {
		return totalPolicy;
	}

	public void setTotalPolicy(int totalPolicy) {
		this.totalPolicy = totalPolicy;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

}

package org.ace.insurance.report.common;

public class AgentComparisonSalesReport {
	private String proposalType;
	private long firePolicy;
	private long motorPolicy;
	private long cargoPolicy;
	private long noOfTotalpolicy;
	private double firePremium;
	private double motorPremium;
	private double cargoPremium;
	private double totalPremium;

	public AgentComparisonSalesReport(String proposalType, long firePolicy, long motorPolicy, long cargoPolicy, long noOfTotalpolicy, double firePremium, double motorPremium,
			double cargoPremium, double totalPremium) {
		super();
		this.proposalType = proposalType;
		this.firePolicy = firePolicy;
		this.motorPolicy = motorPolicy;
		this.cargoPolicy = cargoPolicy;
		this.noOfTotalpolicy = noOfTotalpolicy;
		this.firePremium = firePremium;
		this.motorPremium = motorPremium;
		this.cargoPremium = cargoPremium;
		this.totalPremium = totalPremium;
	}

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}

	public long getFirePolicy() {
		return firePolicy;
	}

	public void setFirePolicy(long firePolicy) {
		this.firePolicy = firePolicy;
	}

	public long getMotorPolicy() {
		return motorPolicy;
	}

	public void setMotorPolicy(long motorPolicy) {
		this.motorPolicy = motorPolicy;
	}

	public long getCargoPolicy() {
		return cargoPolicy;
	}

	public void setCargoPolicy(long cargoPolicy) {
		this.cargoPolicy = cargoPolicy;
	}

	public long getNoOfTotalpolicy() {
		return noOfTotalpolicy;
	}

	public void setNoOfTotalpolicy(long noOfTotalpolicy) {
		this.noOfTotalpolicy = noOfTotalpolicy;
	}

	public double getFirePremium() {
		return firePremium;
	}

	public void setFirePremium(double firePremium) {
		this.firePremium = firePremium;
	}

	public double getMotorPremium() {
		return motorPremium;
	}

	public void setMotorPremium(double motorPremium) {
		this.motorPremium = motorPremium;
	}

	public double getCargoPremium() {
		return cargoPremium;
	}

	public void setCargoPremium(double cargoPremium) {
		this.cargoPremium = cargoPremium;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

}

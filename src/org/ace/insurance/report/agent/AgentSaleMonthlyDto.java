package org.ace.insurance.report.agent;

import java.util.Comparator;

public class AgentSaleMonthlyDto {

	private String codeNo;
	private String agentName;

	// For New Business
	private int newFirePolicy;
	private double newFirePremium;
	private int newMotorPolicy;
	private double newMotorPremium;
	private int newCargoPolicy;
	private double newCargoPremium;

	// For Renewal Business
	private int renewalFirePolicy;
	private double renewalFirePremium;
	private int renewalMotorPolicy;
	private double renewalMotorPremium;
	private int renewalCargoPolicy;
	private double renewalCargoPremium;

	private int newPolicy;
	private int renewalPolicy;
	private double totalPremium;

	private int totalPolicy;

	public AgentSaleMonthlyDto() {
		super();
	}

	/**
	 * This constructor is used to Map the sql query of
	 * AgentDailySalesReportDAO's findForLife
	 * 
	 * @param codeNo
	 * @param agentName
	 * @param endowmentLife
	 * @param groupLife
	 * @param totalPremium
	 * @param referenceType
	 * @param proposalType
	 */
	public AgentSaleMonthlyDto(String codeNo, String agentName, long newPolicy, long renewalPolicy, double totalPremium, String referenceType, String proposalType) {
		super();
		this.codeNo = codeNo;
		this.agentName = agentName;
		this.newPolicy = (int) newPolicy;
		this.renewalPolicy = (int) renewalPolicy;
		if (referenceType.equals("MOTOR_POLICY")) {
			if (proposalType.equals("NEW")) {
				this.newMotorPolicy = this.newPolicy;
				this.newMotorPremium = totalPremium;
			} else {
				this.renewalMotorPolicy = this.renewalPolicy;
				this.renewalMotorPremium = totalPremium;

			}

		} else if (referenceType.equals("FIRE_POLICY")) {
			if (proposalType.equals("NEW")) {
				this.newFirePolicy = this.newPolicy;
				this.newFirePremium = totalPremium;
			} else {
				this.renewalFirePolicy = this.renewalPolicy;
				this.renewalFirePremium = totalPremium;
			}
		} else {// For Cargo
			if (proposalType.equals("NEW")) {
				this.newCargoPolicy = this.newPolicy;
				this.newCargoPremium = totalPremium;
			} else {
				this.renewalCargoPolicy = this.renewalPolicy;
				this.renewalCargoPremium = totalPremium;
			}
		}
	}

	public static Comparator<AgentSaleMonthlyDto> codeNoComparator = new Comparator<AgentSaleMonthlyDto>() {
		public int compare(AgentSaleMonthlyDto s1, AgentSaleMonthlyDto s2) {
			String codeNo1 = s1.getCodeNo();
			String codeNo2 = s2.getCodeNo();
			// ascending order
			return codeNo1.compareTo(codeNo2);
		}
	};

	public AgentSaleMonthlyDto(String codeNo, String agentName, int newFirePolicy, double newFirePremium, int newMotorPolicy, double newMotorPremium, int newCargoPolicy,
			double newCargoPremium, int renewalFirePolicy, double renewalFirePremium, int renewalMotorPolicy, double renewalMotorPremium, int renewalCargoPolicy,
			double renewalCargoPremium) {
		super();
		this.codeNo = codeNo;
		this.agentName = agentName;
		this.newFirePolicy = newFirePolicy;
		this.newFirePremium = newFirePremium;
		this.newMotorPolicy = newMotorPolicy;
		this.newMotorPremium = newMotorPremium;
		this.newCargoPolicy = newCargoPolicy;
		this.newCargoPremium = newCargoPremium;
		this.renewalFirePolicy = renewalFirePolicy;
		this.renewalFirePremium = renewalFirePremium;
		this.renewalMotorPolicy = renewalMotorPolicy;
		this.renewalMotorPremium = renewalMotorPremium;
		this.renewalCargoPolicy = renewalCargoPolicy;
		this.renewalCargoPremium = renewalCargoPremium;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public int getNewFirePolicy() {
		return newFirePolicy;
	}

	public void setNewFirePolicy(int newFirePolicy) {
		this.newFirePolicy = newFirePolicy;
	}

	public double getNewFirePremium() {
		return newFirePremium;
	}

	public void setNewFirePremium(double newFirePremium) {
		this.newFirePremium = newFirePremium;
	}

	public int getNewMotorPolicy() {
		return newMotorPolicy;
	}

	public void setNewMotorPolicy(int newMotorPolicy) {
		this.newMotorPolicy = newMotorPolicy;
	}

	public double getNewMotorPremium() {
		return newMotorPremium;
	}

	public void setNewMotorPremium(double newMotorPremium) {
		this.newMotorPremium = newMotorPremium;
	}

	public int getNewCargoPolicy() {
		return newCargoPolicy;
	}

	public void setNewCargoPolicy(int newCargoPolicy) {
		this.newCargoPolicy = newCargoPolicy;
	}

	public double getNewCargoPremium() {
		return newCargoPremium;
	}

	public void setNewCargoPremium(double newCargoPremium) {
		this.newCargoPremium = newCargoPremium;
	}

	public int getRenewalFirePolicy() {
		return renewalFirePolicy;
	}

	public void setRenewalFirePolicy(int renewalFirePolicy) {
		this.renewalFirePolicy = renewalFirePolicy;
	}

	public double getRenewalFirePremium() {
		return renewalFirePremium;
	}

	public void setRenewalFirePremium(double renewalFirePremium) {
		this.renewalFirePremium = renewalFirePremium;
	}

	public int getRenewalMotorPolicy() {
		return renewalMotorPolicy;
	}

	public void setRenewalMotorPolicy(int renewalMotorPolicy) {
		this.renewalMotorPolicy = renewalMotorPolicy;
	}

	public double getRenewalMotorPremium() {
		return renewalMotorPremium;
	}

	public void setRenewalMotorPremium(double renewalMotorPremium) {
		this.renewalMotorPremium = renewalMotorPremium;
	}

	public int getRenewalCargoPolicy() {
		return renewalCargoPolicy;
	}

	public void setRenewalCargoPolicy(int renewalCargoPolicy) {
		this.renewalCargoPolicy = renewalCargoPolicy;
	}

	public double getRenewalCargoPremium() {
		return renewalCargoPremium;
	}

	public void setRenewalCargoPremium(double renewalCargoPremium) {
		this.renewalCargoPremium = renewalCargoPremium;
	}

	public int getNewPolicy() {
		return newPolicy;
	}

	public void setNewPolicy(int newPolicy) {
		this.newPolicy = newPolicy;
	}

	public int getRenewalPolicy() {
		return renewalPolicy;
	}

	public void setRenewalPolicy(int renewalPolicy) {
		this.renewalPolicy = renewalPolicy;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public int getTotalPolicy() {
		return totalPolicy;
	}

	public void setTotalPolicy(int totalPolicy) {
		this.totalPolicy = totalPolicy;
	}

}

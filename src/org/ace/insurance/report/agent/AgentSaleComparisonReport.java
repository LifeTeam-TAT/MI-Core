package org.ace.insurance.report.agent;

import java.util.Comparator;
import java.util.Date;

public class AgentSaleComparisonReport {

	private String agentId;
	private String currencyId;
	private String codeNo;
	private String agentName;
	private Date activedPolicyStartDate;
	private String proposalType;
	private int newPolicy;
	private int renewalPolicy;
	private int endowmentLife;
	private int groupLife;
	private double endowmentPremium;
	private double groupLifePremium;
	private double totalPremium;
	private double homeTotalPremium;
	private String referenceType;
	private String currency;
	private String branchId;
	private String branchName;
	private double totalmotorPremium;
	private double totalfirePremium;
	private double totalCargoPremium;
	private double totalAmount;
	private int policyCount;
	private int motorPolicy;
	private int firePolicy;
	private int cargoPolicy;
	private int lifePolicy;
	private double totallifePremium;

	public AgentSaleComparisonReport() {

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
	public AgentSaleComparisonReport(String codeNo, String agentName, long newPolicy, long renewalPolicy, double totalPremium, String referenceType, String proposalType) {
		super();
		this.codeNo = codeNo;
		this.agentName = agentName;
		this.newPolicy = (int) newPolicy;
		this.renewalPolicy = (int) renewalPolicy;
		if (referenceType.equals("LIFE_POLICY")) {
			this.endowmentLife = (int) newPolicy;
			this.groupLife = (int) renewalPolicy;
			this.endowmentPremium = this.endowmentLife == 0 ? 0 : totalPremium;
			this.groupLifePremium = this.groupLife == 0 ? 0 : totalPremium;
			this.lifePolicy = this.endowmentLife + this.groupLife;
			this.totallifePremium = this.endowmentPremium + this.groupLifePremium;
		} else if (referenceType.equals("MOTOR_POLICY")) {
			if (proposalType.equals("NEW")) {
				this.motorPolicy = this.newPolicy;
			} else {
				this.motorPolicy = this.renewalPolicy;

			}
			this.totalmotorPremium = totalPremium;
		} else if (referenceType.equals("FIRE_POLICY")) {
			if (proposalType.equals("NEW")) {
				this.firePolicy = this.newPolicy;
			} else {
				this.firePolicy = this.renewalPolicy;

			}
			this.totalfirePremium = totalPremium;
		} else {// For Cargo
			this.cargoPolicy = this.newPolicy;
			this.totalCargoPremium = totalPremium;
		}
	}

	public static Comparator<AgentSaleComparisonReport> codeNoComparator = new Comparator<AgentSaleComparisonReport>() {
		public int compare(AgentSaleComparisonReport s1, AgentSaleComparisonReport s2) {
			String codeNo1 = s1.getCodeNo();
			String codeNo2 = s2.getCodeNo();
			// ascending order
			return codeNo1.compareTo(codeNo2);
		}
	};

	public AgentSaleComparisonReport(String agentId, String currencyId, String codeNo, String agentName, Date activedPolicyStartDate, String proposalType, int newPolicy,
			int renewalPolicy, int endowmentLife, int groupLife, double totalPremium, double homeTotalPremium, String referenceType, String currency, String branchId,
			String branchName, double totalmotorPremium, double totalfirePremium, double totalAmount, int policyCount, int motorPolicy, int firePolicy, int lifePolicy,
			double totallifePremium) {
		this.agentId = agentId;
		this.currencyId = currencyId;
		this.codeNo = codeNo;
		this.agentName = agentName;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.proposalType = proposalType;
		this.newPolicy = newPolicy;
		this.renewalPolicy = renewalPolicy;
		if (firePolicy == 0) {
			this.totalfirePremium = 0;
		} else {

			this.totalfirePremium = totalfirePremium;
		}
		if (motorPolicy == 0) {
			this.totalmotorPremium = 0;
		} else {
			this.totalmotorPremium = totalmotorPremium;
		}

		this.endowmentLife = endowmentLife;
		this.groupLife = groupLife;
		this.totalPremium = totalPremium;
		this.homeTotalPremium = homeTotalPremium;
		this.referenceType = referenceType;
		this.currency = currency;
		this.branchId = branchId;
		this.branchName = branchName;
		this.totalAmount = totalAmount;
		this.policyCount = policyCount;
		this.motorPolicy = motorPolicy;
		this.firePolicy = firePolicy;
		this.lifePolicy = lifePolicy;
		this.totallifePremium = totallifePremium;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
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

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
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

	public int getEndowmentLife() {
		return endowmentLife;
	}

	public void setEndowmentLife(int endowmentLife) {
		if (endowmentLife == 0) {
			this.totalPremium = 0;
		} else

			this.endowmentLife = endowmentLife;
	}

	public int getGroupLife() {
		return groupLife;
	}

	public void setGroupLife(int groupLife) {
		if (groupLife == 0) {
			this.totalPremium = 0;
		}
		this.groupLife = groupLife;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public double getHomeTotalPremium() {
		return homeTotalPremium;
	}

	public void setHomeTotalPremium(double homeTotalPremium) {
		this.homeTotalPremium = homeTotalPremium;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public double getTotalmotorPremium() {
		return totalmotorPremium;
	}

	public void setTotalmotorPremium(double totalmotorPremium) {
		this.totalmotorPremium = totalmotorPremium;
	}

	public double getTotalfirePremium() {
		return totalfirePremium;
	}

	public void setTotalfirePremium(double totalfirePremium) {
		this.totalfirePremium = totalfirePremium;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getPolicyCount() {
		return policyCount;
	}

	public void setPolicyCount(int policyCount) {
		this.policyCount = policyCount;
	}

	public int getMotorPolicy() {
		return motorPolicy;
	}

	public void setMotorPolicy(int motorPolicy) {
		this.motorPolicy = motorPolicy;
	}

	public int getFirePolicy() {
		return firePolicy;
	}

	public void setFirePolicy(int firePolicy) {
		this.firePolicy = firePolicy;
	}

	public int getCargoPolicy() {
		return cargoPolicy;
	}

	public void setCargoPolicy(int cargoPolicy) {
		this.cargoPolicy = cargoPolicy;
	}

	public int getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(int lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public double getTotallifePremium() {
		return totallifePremium;
	}

	public void setTotallifePremium(double totallifePremium) {
		this.totallifePremium = totallifePremium;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public double getTotalCargoPremium() {
		return totalCargoPremium;
	}

	public void setTotalCargoPremium(double totalCargoPremium) {
		this.totalCargoPremium = totalCargoPremium;
	}

	public double getEndowmentPremium() {
		return endowmentPremium;
	}

	public void setEndowmentPremium(double endowmentPremium) {
		this.endowmentPremium = endowmentPremium;
	}

	public double getGroupLifePremium() {
		return groupLifePremium;
	}

	public void setGroupLifePremium(double groupLifePremium) {
		this.groupLifePremium = groupLifePremium;
	}

}

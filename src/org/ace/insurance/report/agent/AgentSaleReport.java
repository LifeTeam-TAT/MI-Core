package org.ace.insurance.report.agent;

import java.util.Date;

import org.ace.insurance.system.common.agent.Agent;

public class AgentSaleReport {
	/* common */
	private String id;
	private String agentName;
	private String agentCodeNo;
	private Date commissionStartDate;

	
	//To FIXME by THK
	/* for general */
	private int firePolicyCount;
	private double firePolicyTotalPremium;
	private double firePolicyTotalCommission;

	private int motorPolicyCount;
	private double motorPolicyTotalPremium;
	private double motorPolicyTotalCommission;

	private int lifePolicyCount;
	private double lifePolicyTotalPremium;
	private double lifePolicyTotalCommission;

	private int CargoPolicyCount;
	private double CargoPolicyTotalPremium;
	private double CargoPolicyTotalCommission;

	private int cisCount;
	private double cisPremium;
	private double cisCommission;

	private int citCount;
	private double citPremium;
	private double citCommission;

	/* for life */
	private int publicLifeCount;
	private double publicLifePremium;
	private double publicLifeCommission;

	private int groupLifeCount;
	private double groupLifePremium;
	private double groupLifeCommission;

	private int medicalPolicyCount;
	private double medicalPremium;
	private double medicalCommission;

	private int sportManPolicyCount;
	private double sportMenPremium;
	private double sportMenCommission;

	private int travelPolicyCount;
	private double travelPremium;
	private double travelCommission;

	private int sankeBiteCount;
	private double snakeBitePremium;
	private double sankeBiteCommission;

	/* total */
	private int policyCount;
	private double totalPremium;
	private double totalCommission;
	private String productName;

	public AgentSaleReport() {

	}

	public AgentSaleReport(GeneralAgentSaleReportView view) {
		super();
		this.id = view.getId();
		this.agentName = view.getAgentName();
		this.agentCodeNo = view.getAgentCodeNo();
		this.commissionStartDate = view.getCommissionStartDate();

		this.motorPolicyCount = view.getMotorPolicyCount();
		this.motorPolicyTotalPremium = view.getMotorPremium();
		this.motorPolicyTotalCommission = view.getMotorCommission();

		this.firePolicyCount = view.getFirePolicyCount();
		this.firePolicyTotalPremium = view.getFirePremium();
		this.firePolicyTotalCommission = view.getFireCommission();

		CargoPolicyCount = view.getCargoPolicyCount();
		CargoPolicyTotalPremium = view.getCargoPremium();
		CargoPolicyTotalCommission = view.getCargoCommission();

		this.cisCount = view.getCisPolicyCount();
		this.cisPremium = view.getCisPremium();
		this.cisCommission = view.getCisCommission();

		this.citCount = view.getCitPolicyCount();
		this.citPremium = view.getCitPremium();
		this.citCommission = view.getCitCommission();

		this.policyCount = view.getTotalPolicyCount();
		this.totalPremium = view.getTotalPremium();
		this.totalCommission = view.getTotalCommission();
	}

	public AgentSaleReport(LifeAgentSaleReportView view) {
		super();
		this.id = view.getAgentId();
		this.agentName = view.getAgentName();
		this.agentCodeNo = view.getAgentCodeNo();
		this.commissionStartDate = view.getCommissionStartDate();

		this.publicLifeCount = view.getPublicLifeCount();
		this.publicLifePremium = view.getPublicLifePremium();
		this.publicLifeCommission = view.getPublicLifeCommission();

		this.groupLifeCount = view.getGroupLifeCount();
		this.groupLifePremium = view.getGroupLifePremium();
		this.groupLifeCommission = view.getGroupLifeCommission();

		this.medicalPolicyCount = view.getMedicalPolicyCount();
		this.medicalPremium = view.getMedicalPremium();
		this.medicalCommission = view.getMedicalCommission();

		this.sportManPolicyCount = view.getSportMenPolicyCount();
		this.sportMenPremium = view.getSportMenPremium();
		this.sportMenCommission = view.getSportMenCommission();

		this.travelPolicyCount = view.getTravelPolicyCount();
		this.travelPremium = view.getTravelPremium();
		this.travelCommission = view.getTravelCommission();

		this.sankeBiteCount = view.getSnakeBitePolicyCount();
		this.snakeBitePremium = view.getSnakeBitePremium();
		this.sankeBiteCommission = view.getSnakeBiteCommission();

		this.policyCount = view.getTotalPolicyCount();
		this.totalPremium = view.getTotalPremium();
		this.totalCommission = view.getTotalCommission();
	}

	// for common
	public AgentSaleReport(Agent agent, int noOfPolicy) {
		this.agentName = agent.getFullName();
		this.agentCodeNo = agent.getCodeNo();
		this.policyCount = noOfPolicy;
	}

	public Date getCommissionStartDate() {
		return commissionStartDate;
	}

	public void setCommissionStartDate(Date commissionStartDate) {
		this.commissionStartDate = commissionStartDate;
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

	public int getFirePolicyCount() {
		return firePolicyCount;
	}

	public void setFirePolicyCount(int firePolicyCount) {
		this.firePolicyCount = firePolicyCount;
	}

	public double getFirePolicyTotalPremium() {
		return firePolicyTotalPremium;
	}

	public void setFirePolicyTotalPremium(double firePolicyTotalPremium) {
		this.firePolicyTotalPremium = firePolicyTotalPremium;
	}

	public double getFirePolicyTotalCommission() {
		return firePolicyTotalCommission;
	}

	public void setFirePolicyTotalCommission(double firePolicyTotalCommission) {
		this.firePolicyTotalCommission = firePolicyTotalCommission;
	}

	public int getMotorPolicyCount() {
		return motorPolicyCount;
	}

	public void setMotorPolicyCount(int motorPolicyCount) {
		this.motorPolicyCount = motorPolicyCount;
	}

	public double getMotorPolicyTotalPremium() {
		return motorPolicyTotalPremium;
	}

	public void setMotorPolicyTotalPremium(double motorPolicyTotalPremium) {
		this.motorPolicyTotalPremium = motorPolicyTotalPremium;
	}

	public double getMotorPolicyTotalCommission() {
		return motorPolicyTotalCommission;
	}

	public void setMotorPolicyTotalCommission(double motorPolicyTotalCommission) {
		this.motorPolicyTotalCommission = motorPolicyTotalCommission;
	}

	public int getLifePolicyCount() {
		return lifePolicyCount;
	}

	public void setLifePolicyCount(int lifePolicyCount) {
		this.lifePolicyCount = lifePolicyCount;
	}

	public double getLifePolicyTotalPremium() {
		return lifePolicyTotalPremium;
	}

	public void setLifePolicyTotalPremium(double lifePolicyTotalPremium) {
		this.lifePolicyTotalPremium = lifePolicyTotalPremium;
	}

	public double getLifePolicyTotalCommission() {
		return lifePolicyTotalCommission;
	}

	public void setLifePolicyTotalCommission(double lifePolicyTotalCommission) {
		this.lifePolicyTotalCommission = lifePolicyTotalCommission;
	}

	public int getPolicyCount() {
		return policyCount;
	}

	public void setPolicyCount(int policyCount) {
		this.policyCount = policyCount;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public int getPublicLifeCount() {
		return publicLifeCount;
	}

	public void setPublicLifeCount(int publicLifeCount) {
		this.publicLifeCount = publicLifeCount;
	}

	public double getPublicLifePremium() {
		return publicLifePremium;
	}

	public void setPublicLifePremium(double publicLifePremium) {
		this.publicLifePremium = publicLifePremium;
	}

	public double getPublicLifeCommission() {
		return publicLifeCommission;
	}

	public void setPublicLifeCommission(double publicLifeCommission) {
		this.publicLifeCommission = publicLifeCommission;
	}

	public int getGroupLifeCount() {
		return groupLifeCount;
	}

	public void setGroupLifeCount(int groupLifeCount) {
		this.groupLifeCount = groupLifeCount;
	}

	public double getGroupLifePremium() {
		return groupLifePremium;
	}

	public void setGroupLifePremium(double groupLifePremium) {
		this.groupLifePremium = groupLifePremium;
	}

	public double getGroupLifeCommission() {
		return groupLifeCommission;
	}

	public void setGroupLifeCommission(double groupLifeCommission) {
		this.groupLifeCommission = groupLifeCommission;
	}

	public int getSankeBiteCount() {
		return sankeBiteCount;
	}

	public void setSankeBiteCount(int sankeBiteCount) {
		this.sankeBiteCount = sankeBiteCount;
	}

	public double getSnakeBitePremium() {
		return snakeBitePremium;
	}

	public void setSnakeBitePremium(double snakeBitePremium) {
		this.snakeBitePremium = snakeBitePremium;
	}

	public double getSankeBiteCommission() {
		return sankeBiteCommission;
	}

	public void setSankeBiteCommission(double sankeBiteCommission) {
		this.sankeBiteCommission = sankeBiteCommission;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getCargoPolicyCount() {
		return CargoPolicyCount;
	}

	public void setCargoPolicyCount(int cargoPolicyCount) {
		CargoPolicyCount = cargoPolicyCount;
	}

	public double getCargoPolicyTotalPremium() {
		return CargoPolicyTotalPremium;
	}

	public void setCargoPolicyTotalPremium(double cargoPolicyTotalPremium) {
		CargoPolicyTotalPremium = cargoPolicyTotalPremium;
	}

	public double getCargoPolicyTotalCommission() {
		return CargoPolicyTotalCommission;
	}

	public void setCargoPolicyTotalCommission(double cargoPolicyTotalCommission) {
		CargoPolicyTotalCommission = cargoPolicyTotalCommission;
	}

	public int getCisCount() {
		return cisCount;
	}

	public void setCisCount(int cisCount) {
		this.cisCount = cisCount;
	}

	public double getCisPremium() {
		return cisPremium;
	}

	public void setCisPremium(double cisPremium) {
		this.cisPremium = cisPremium;
	}

	public double getCisCommission() {
		return cisCommission;
	}

	public void setCisCommission(double cisCommission) {
		this.cisCommission = cisCommission;
	}

	public int getCitCount() {
		return citCount;
	}

	public void setCitCount(int citCount) {
		this.citCount = citCount;
	}

	public double getCitPremium() {
		return citPremium;
	}

	public void setCitPremium(double citPremium) {
		this.citPremium = citPremium;
	}

	public double getCitCommission() {
		return citCommission;
	}

	public void setCitCommission(double citCommission) {
		this.citCommission = citCommission;
	}

	public int getMedicalPolicyCount() {
		return medicalPolicyCount;
	}

	public void setMedicalPolicyCount(int medicalPolicyCount) {
		this.medicalPolicyCount = medicalPolicyCount;
	}

	public double getMedicalPremium() {
		return medicalPremium;
	}

	public void setMedicalPremium(double medicalPremium) {
		this.medicalPremium = medicalPremium;
	}

	public double getMedicalCommission() {
		return medicalCommission;
	}

	public void setMedicalCommission(double medicalCommission) {
		this.medicalCommission = medicalCommission;
	}

	public int getSportManPolicyCount() {
		return sportManPolicyCount;
	}

	public void setSportManPolicyCount(int sportManPolicyCount) {
		this.sportManPolicyCount = sportManPolicyCount;
	}

	public double getSportMenPremium() {
		return sportMenPremium;
	}

	public void setSportMenPremium(double sportMenPremium) {
		this.sportMenPremium = sportMenPremium;
	}

	public double getSportMenCommission() {
		return sportMenCommission;
	}

	public void setSportMenCommission(double sportMenCommission) {
		this.sportMenCommission = sportMenCommission;
	}

	public int getTravelPolicyCount() {
		return travelPolicyCount;
	}

	public void setTravelPolicyCount(int travelPolicyCount) {
		this.travelPolicyCount = travelPolicyCount;
	}

	public double getTravelPremium() {
		return travelPremium;
	}

	public void setTravelPremium(double travelPremium) {
		this.travelPremium = travelPremium;
	}

	public double getTravelCommission() {
		return travelCommission;
	}

	public void setTravelCommission(double travelCommission) {
		this.travelCommission = travelCommission;
	}

}

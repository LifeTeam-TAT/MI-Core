package org.ace.insurance.report.agent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_LIFE_AGENT_SALE_REPORT)
@ReadOnly
public class LifeAgentSaleReportView {
	@Id
	@Column(name = "AGENTID")
	private String agentId;
	@Column(name = "AGENTNAME")
	private String agentName;
	@Column(name = "CODENO")
	private String agentCodeNo;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMISSIONSTARTDATE")
	private Date commissionStartDate;

	@Column(name = "PL_POLICYCOUNT")
	private int publicLifeCount;
	@Column(name = "PL_PREMIUM")
	private double publicLifePremium;
	@Column(name = "PL_COMMISSION")
	private double publicLifeCommission;

	@Column(name = "GL_POLICYCOUNT")
	private int groupLifeCount;
	@Column(name = "GL_PREMIUM")
	private double groupLifePremium;
	@Column(name = "GL_COMMISSION")
	private double groupLifeCommission;

	@Column(name = "MI_POLICYCOUNT")
	private int medicalPolicyCount;
	@Column(name = "MI_PREMIUM")
	private double medicalPremium;
	@Column(name = "MI_COMMISSION")
	private double medicalCommission;

	@Column(name = "SB_POLICYCOUNT")
	private int snakeBitePolicyCount;
	@Column(name = "SB_PREMIUM")
	private double snakeBitePremium;
	@Column(name = "SB_COMMISSION")
	private double snakeBiteCommission;

	@Column(name = "SM_POLICYCOUNT")
	private int sportMenPolicyCount;
	@Column(name = "SM_PREMIUM")
	private double sportMenPremium;
	@Column(name = "SM_COMMISSION")
	private double sportMenCommission;

	@Column(name = "TL_POLICYCOUNT")
	private int travelPolicyCount;
	@Column(name = "TL_PREMIUM")
	private double travelPremium;
	@Column(name = "TL_COMMISSION")
	private double travelCommission;

	@Column(name = "T_POLICYCOUNT")
	private int totalPolicyCount;
	@Column(name = "T_PREMIUM")
	private double totalPremium;
	@Column(name = "T_COMMISSION")
	private double totalCommission;

	@Column(name = "BRANCHID")
	private String branchId;

	public LifeAgentSaleReportView() {
	}

	public LifeAgentSaleReportView(String agentId, String agentName, String agentCodeNo, int publicLifeCount, double publicLifePremium, double publicLifeCommission,
			int groupLifeCount, double groupLifePremium, double groupLifeCommission, int medicalPolicyCount, double medicalPremium, double medicalCommission,
			int snakeBitePolicyCount, double snakeBitePremium, double snakeBiteCommission, int sportMenPolicyCount, double sportMenPremium, double sportMenCommission,
			int travelPolicyCount, double travelPremium, double travelCommission, int totalPolicyCount, double totalPremium, double totalCommission, String branchId) {
		super();
		this.agentId = agentId;
		this.agentName = agentName;
		this.agentCodeNo = agentCodeNo;
		this.publicLifeCount = publicLifeCount;
		this.publicLifePremium = publicLifePremium;
		this.publicLifeCommission = publicLifeCommission;
		this.groupLifeCount = groupLifeCount;
		this.groupLifePremium = groupLifePremium;
		this.groupLifeCommission = groupLifeCommission;
		this.medicalPolicyCount = medicalPolicyCount;
		this.medicalPremium = medicalPremium;
		this.medicalCommission = medicalCommission;
		this.snakeBitePolicyCount = snakeBitePolicyCount;
		this.snakeBitePremium = snakeBitePremium;
		this.snakeBiteCommission = snakeBiteCommission;
		this.sportMenPolicyCount = sportMenPolicyCount;
		this.sportMenPremium = sportMenPremium;
		this.sportMenCommission = sportMenCommission;
		this.travelPolicyCount = travelPolicyCount;
		this.travelPremium = travelPremium;
		this.travelCommission = travelCommission;
		this.totalPolicyCount = totalPolicyCount;
		this.totalPremium = totalPremium;
		this.totalCommission = totalCommission;
		this.branchId = branchId;
	}

	public Date getCommissionStartDate() {
		return commissionStartDate;
	}

	public void setCommissionStartDate(Date commissionStartDate) {
		this.commissionStartDate = commissionStartDate;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
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

	public int getSnakeBitePolicyCount() {
		return snakeBitePolicyCount;
	}

	public void setSnakeBitePolicyCount(int snakeBitePolicyCount) {
		this.snakeBitePolicyCount = snakeBitePolicyCount;
	}

	public double getSnakeBitePremium() {
		return snakeBitePremium;
	}

	public void setSnakeBitePremium(double snakeBitePremium) {
		this.snakeBitePremium = snakeBitePremium;
	}

	public double getSnakeBiteCommission() {
		return snakeBiteCommission;
	}

	public void setSnakeBiteCommission(double snakeBiteCommission) {
		this.snakeBiteCommission = snakeBiteCommission;
	}

	public int getSportMenPolicyCount() {
		return sportMenPolicyCount;
	}

	public void setSportMenPolicyCount(int sportMenPolicyCount) {
		this.sportMenPolicyCount = sportMenPolicyCount;
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

	public int getTotalPolicyCount() {
		return totalPolicyCount;
	}

	public void setTotalPolicyCount(int totalPolicyCount) {
		this.totalPolicyCount = totalPolicyCount;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}

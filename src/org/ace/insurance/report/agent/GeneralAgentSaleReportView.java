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
@Table(name = TableName.VWT_GENERAL_AGENT_SALE_REPORT)
@ReadOnly
public class GeneralAgentSaleReportView {
	@Id
	private String id;
	@Column(name = "AGENTID")
	private String agentId;
	@Column(name = "AGENTNAME")
	private String agentName;
	@Column(name = "CODENO")
	private String agentCodeNo;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMISSIONSTARTDATE")
	private Date commissionStartDate;

	@Column(name = "M_POLICYCOUNT")
	private int motorPolicyCount;
	@Column(name = "M_PREMIUM")
	private double motorPremium;
	@Column(name = "M_COMMISSION")
	private double motorCommission;

	@Column(name = "F_POLICYCOUNT")
	private int firePolicyCount;
	@Column(name = "F_PREMIUM")
	private double firePremium;
	@Column(name = "F_COMMISSION")
	private double fireCommission;

	@Column(name = "C_POLICYCOUNT")
	private int cargoPolicyCount;
	@Column(name = "C_PREMIUM")
	private double cargoPremium;
	@Column(name = "C_COMMISSION")
	private double cargoCommission;

	@Column(name = "CIS_POLICYCOUNT")
	private int cisPolicyCount;
	@Column(name = "CIS_PREMIUM")
	private double cisPremium;
	@Column(name = "CIS_COMMISSION")
	private double cisCommission;

	@Column(name = "CIT_POLICYCOUNT")
	private int citPolicyCount;
	@Column(name = "CIT_PREMIUM")
	private double citPremium;
	@Column(name = "CIT_COMMISSION")
	private double citCommission;

	@Column(name = "T_POLICYCOUNT")
	private int totalPolicyCount;
	@Column(name = "T_PREMIUM")
	private double totalPremium;
	@Column(name = "T_COMMISSION")
	private double totalCommission;

	@Column(name = "BRANCHID")
	private String branchId;

	public GeneralAgentSaleReportView() {
	}

	public GeneralAgentSaleReportView(String agentId, String agentName, String agentCodeNo, int motorPolicyCount, double motorPremium, double motorCommission, int firePolicyCount,
			double firePremium, double fireCommission, int cargoPolicyCount, double cargoPremium, double cargoCommission, int cisPolicyCount, double cisPremium,
			double cisCommission, int citPolicyCount, double citPremium, double citCommission, int totalPolicyCount, double totalPremium, double totalCommission, String branchId) {
		super();
		this.agentId = agentId;
		this.agentName = agentName;
		this.agentCodeNo = agentCodeNo;
		this.motorPolicyCount = motorPolicyCount;
		this.motorPremium = motorPremium;
		this.motorCommission = motorCommission;
		this.firePolicyCount = firePolicyCount;
		this.firePremium = firePremium;
		this.fireCommission = fireCommission;
		this.cargoPolicyCount = cargoPolicyCount;
		this.cargoPremium = cargoPremium;
		this.cargoCommission = cargoCommission;
		this.cisPolicyCount = cisPolicyCount;
		this.cisPremium = cisPremium;
		this.cisCommission = cisCommission;
		this.citPolicyCount = citPolicyCount;
		this.citPremium = citPremium;
		this.citCommission = citCommission;
		this.totalPolicyCount = totalPolicyCount;
		this.totalPremium = totalPremium;
		this.totalCommission = totalCommission;
		this.branchId = branchId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getMotorPolicyCount() {
		return motorPolicyCount;
	}

	public void setMotorPolicyCount(int motorPolicyCount) {
		this.motorPolicyCount = motorPolicyCount;
	}

	public double getMotorPremium() {
		return motorPremium;
	}

	public void setMotorPremium(double motorPremium) {
		this.motorPremium = motorPremium;
	}

	public double getMotorCommission() {
		return motorCommission;
	}

	public void setMotorCommission(double motorCommission) {
		this.motorCommission = motorCommission;
	}

	public int getFirePolicyCount() {
		return firePolicyCount;
	}

	public void setFirePolicyCount(int firePolicyCount) {
		this.firePolicyCount = firePolicyCount;
	}

	public double getFirePremium() {
		return firePremium;
	}

	public void setFirePremium(double firePremium) {
		this.firePremium = firePremium;
	}

	public double getFireCommission() {
		return fireCommission;
	}

	public void setFireCommission(double fireCommission) {
		this.fireCommission = fireCommission;
	}

	public int getCargoPolicyCount() {
		return cargoPolicyCount;
	}

	public void setCargoPolicyCount(int cargoPolicyCount) {
		this.cargoPolicyCount = cargoPolicyCount;
	}

	public double getCargoPremium() {
		return cargoPremium;
	}

	public void setCargoPremium(double cargoPremium) {
		this.cargoPremium = cargoPremium;
	}

	public double getCargoCommission() {
		return cargoCommission;
	}

	public void setCargoCommission(double cargoCommission) {
		this.cargoCommission = cargoCommission;
	}

	public int getCisPolicyCount() {
		return cisPolicyCount;
	}

	public void setCisPolicyCount(int cisPolicyCount) {
		this.cisPolicyCount = cisPolicyCount;
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

	public int getCitPolicyCount() {
		return citPolicyCount;
	}

	public void setCitPolicyCount(int citPolicyCount) {
		this.citPolicyCount = citPolicyCount;
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

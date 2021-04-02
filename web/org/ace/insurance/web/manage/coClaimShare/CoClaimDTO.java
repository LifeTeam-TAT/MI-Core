package org.ace.insurance.web.manage.coClaimShare;

import java.util.Date;

import org.ace.insurance.common.ClaimStatus;
import org.ace.insurance.common.Name;

public class CoClaimDTO {

	private String claimNo;
	private String policyNo;
	private Date lossDate;
	private Name customerName;
	private String riskNo;
	private String vehicleCodeNo;
	private double sumInsured;
	private double claimAmount;
	private double reinstatementPremium;
	private ClaimStatus claimStatus;

	public CoClaimDTO() {

	}

	public CoClaimDTO(String claimNo, String policyNo, Date lossDate, Name customerName, String riskNo, String vehicleCodeNo, double sumInsured, double claimAmount,
			double reinstatementPremium, ClaimStatus claimStatus) {
		super();
		this.claimNo = claimNo;
		this.policyNo = policyNo;
		this.lossDate = lossDate;
		this.customerName = customerName;
		this.riskNo = riskNo;
		this.vehicleCodeNo = vehicleCodeNo;
		this.sumInsured = sumInsured;
		this.claimAmount = claimAmount;
		this.reinstatementPremium = reinstatementPremium;
		this.claimStatus = claimStatus;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getLossDate() {
		return lossDate;
	}

	public void setLossDate(Date lossDate) {
		this.lossDate = lossDate;
	}

	public Name getCustomerName() {
		return customerName;
	}

	public void setCustomerName(Name customerName) {
		this.customerName = customerName;
	}

	public String getRiskNo() {
		return riskNo;
	}

	public void setRiskNo(String riskNo) {
		this.riskNo = riskNo;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getReinstatementPremium() {
		return reinstatementPremium;
	}

	public void setReinstatementPremium(double reinstatementPremium) {
		this.reinstatementPremium = reinstatementPremium;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getVehicleCodeNo() {
		return vehicleCodeNo;
	}

	public void setVehicleCodeNo(String vehicleCodeNo) {
		this.vehicleCodeNo = vehicleCodeNo;
	}

}

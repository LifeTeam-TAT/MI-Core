package org.ace.insurance.medical.claim;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class MC001 implements ISorter {
	private String id;
	private String policyNo;
	private String agent;
	private String branch;
	private String unit;
	private double totalClaimAmount;
	private Date submittedDate;
	private String claimRequestNo;
	private String receivedNo;

	public MC001() {
	}

	public MC001(String id, String policyNo,String agent, String branch, String unit, double totalClaimAmount, Date submittedDate, String claimRequestNo,
			String receivedNo) {
		this.id = id;
		this.agent = agent;
		this.branch = branch;
		this.unit = unit;
		this.submittedDate = submittedDate;
		this.policyNo = policyNo;
		this.receivedNo = receivedNo;
		this.claimRequestNo = claimRequestNo;
		this.totalClaimAmount = totalClaimAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}


	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getTotalClaimAmount() {
		return totalClaimAmount;
	}

	public void setTotalClaimAmount(double totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getClaimRequestNo() {
		return claimRequestNo;
	}

	public void setClaimRequestNo(String claimRequestNo) {
		this.claimRequestNo = claimRequestNo;
	}

	public String getReceivedNo() {
		return receivedNo;
	}

	public void setReceivedNo(String receivedNo) {
		this.receivedNo = receivedNo;
	}

	@Override
	public String getRegistrationNo() {
		return claimRequestNo;
	}

}

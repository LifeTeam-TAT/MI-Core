package org.ace.insurance.report.shortEndowLife;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class ShortEndowLifePolicyReport implements ISorter {
	private static final long serialVersionUID = 1L;
	public Date commencementDate;
	public String policyNo;
	public String insuredPersonName;
	public double sumInsured;
	public Date startDate;
	public Date endDate;
	public String assignee;
	public double oneYearPremium;
	public double termPremium;
	public double installmentPremium;
	public String agentName;
	public String remark;
	public String branchName;

	/**
	 * @param shortEndowLifePolicyView
	 */
	public ShortEndowLifePolicyReport(ShortEndowLifePolicyView shortEndowLifePolicyView) {
		super();
		this.commencementDate = shortEndowLifePolicyView.getCommenmanceDate();
		this.policyNo = shortEndowLifePolicyView.getPolicyNo();
		this.insuredPersonName = shortEndowLifePolicyView.getInsuredPersonName();
		this.sumInsured = shortEndowLifePolicyView.getSumInsured();
		this.startDate = shortEndowLifePolicyView.getPolicyStartDate();
		this.endDate = shortEndowLifePolicyView.getPolicyEndDate();
		this.assignee = shortEndowLifePolicyView.getName();
		this.oneYearPremium = shortEndowLifePolicyView.getPremium();
		this.termPremium = shortEndowLifePolicyView.getTermPremium();
		this.installmentPremium = shortEndowLifePolicyView.getPremium() * (shortEndowLifePolicyView.getPeriodOfMonth() / 12);
		this.agentName = shortEndowLifePolicyView.getAgentName();
		this.branchName = shortEndowLifePolicyView.getBranchName();
		this.remark = "";
	}

	/**
	 * @return the commencementDate
	 */
	public Date getCommencementDate() {
		return commencementDate;
	}

	/**
	 * @param commencementDate
	 *            the commencementDate to set
	 */
	public void setCommencementDate(Date commencementDate) {
		this.commencementDate = commencementDate;
	}

	/**
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * @param policyNo
	 *            the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * @return the insuredPersonName
	 */
	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	/**
	 * @param insuredPersonName
	 *            the insuredPersonName to set
	 */
	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	/**
	 * @return the sumInsured
	 */
	public double getSumInsured() {
		return sumInsured;
	}

	/**
	 * @param sumInsured
	 *            the sumInsured to set
	 */
	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the oneYearPremium
	 */
	public double getOneYearPremium() {
		return oneYearPremium;
	}

	/**
	 * @param oneYearPremium
	 *            the oneYearPremium to set
	 */
	public void setOneYearPremium(double oneYearPremium) {
		this.oneYearPremium = oneYearPremium;
	}

	/**
	 * @return the termPremium
	 */
	public double getTermPremium() {
		return termPremium;
	}

	/**
	 * @param termPremium
	 *            the termPremium to set
	 */
	public void setTermPremium(double termPremium) {
		this.termPremium = termPremium;
	}

	/**
	 * @return the installmentPremium
	 */
	public double getInstallmentPremium() {
		return installmentPremium;
	}

	/**
	 * @param installmentPremium
	 *            the installmentPremium to set
	 */
	public void setInstallmentPremium(double installmentPremium) {
		this.installmentPremium = installmentPremium;
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName
	 *            the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
}

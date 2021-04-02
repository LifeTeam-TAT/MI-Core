package org.ace.insurance.report.personalAccident;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class PersonalAccidentPolicyReport implements ISorter {
	private static final long serialVersionUID = 1L;
	
	public String policyNo;
	public String insuredPersonName;
	public String agentName;
	public String address;
	public String dateOfBirth;
	public Date policyStartDate;
	public Date policyEndDate;
	public double sumInsured;
	public double premium;
	public String receiptNo;
	public String branchId;
	public String branchName;
	public String remark;
	
	public PersonalAccidentPolicyReport() {
		super();
	}
	
	public PersonalAccidentPolicyReport(PersonalAccidentPolicyView view) {
		this.policyNo = view.getPolicyNo();
		this.insuredPersonName = view.getInsuredPersonName();
		this.agentName = view.getAgentName();
		this.address = view.getAddress();
		this.dateOfBirth = view.getDateOfBirth();
		this.policyStartDate = view.getPolicyStartDate();
		this.policyEndDate = view.getPolicyEndDate();
		this.sumInsured = view.getSumInsured();
		this.premium = view.getPremium();
		this.receiptNo = view.getReceiptNo();
		this.branchId = view.getBranchId();
		this.branchName = view.getBranchName();
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
}

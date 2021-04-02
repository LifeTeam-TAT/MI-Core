package org.ace.insurance.web.manage.report.medical;

import java.util.Date;

public class HealthProposalReportDTO {
	private String proposalNo;
	private Date DateOfProposed;
	private String insuredName;
	private String nrcNo;
	private String fatherName;
	private String AgentNameAndCodeNo;
	private String addressAndPhoneNo;
	private String unit;
	private double premium;
	private double addOnTermpremium;
	private String branch;
	private String workFlowTask;
	private String rpName;
	private String remark;

	public HealthProposalReportDTO() {
		super();
	}

	public HealthProposalReportDTO(String proposalNo, Date dateOfProposed, String insuredName, String nrcNo, String fatherName, double addOnTermpremium, String agentNameAndCodeNo,
			String addressAndPhoneNo, String unit, double premium, String branch, String workFlowTask, String rpName, String remark) {
		super();
		this.proposalNo = proposalNo;
		DateOfProposed = dateOfProposed;
		this.insuredName = insuredName;
		this.nrcNo = nrcNo;
		this.fatherName = fatherName;
		AgentNameAndCodeNo = agentNameAndCodeNo;
		this.addressAndPhoneNo = addressAndPhoneNo;
		this.unit = unit;
		this.premium = premium;
		this.branch = branch;
		this.workFlowTask = workFlowTask;
		this.rpName = rpName;
		this.remark = remark;
		this.addOnTermpremium = addOnTermpremium;
	}

	public HealthProposalReportDTO(String proposalNo, Date dateOfProposed, String insuredName, String nrcNo, String fatherName, double addOnTermpremium, String agentNameAndCodeNo,
			String addressAndPhoneNo, String unit, double premium, String branch) {
		super();
		this.proposalNo = proposalNo;
		DateOfProposed = dateOfProposed;
		this.insuredName = insuredName;
		this.nrcNo = nrcNo;
		this.fatherName = fatherName;
		AgentNameAndCodeNo = agentNameAndCodeNo;
		this.addressAndPhoneNo = addressAndPhoneNo;
		this.unit = unit;
		this.premium = premium + addOnTermpremium;
		this.branch = branch;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public Date getDateOfProposed() {
		return DateOfProposed;
	}

	public void setDateOfProposed(Date dateOfProposed) {
		DateOfProposed = dateOfProposed;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getNrcNo() {
		return nrcNo;
	}

	public void setNrcNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getAgentNameAndCodeNo() {
		return AgentNameAndCodeNo;
	}

	public void setAgentNameAndCodeNo(String agentNameAndCodeNo) {
		AgentNameAndCodeNo = agentNameAndCodeNo;
	}

	public String getAddressAndPhoneNo() {
		return addressAndPhoneNo;
	}

	public void setAddressAndPhoneNo(String addressAndPhoneNo) {
		this.addressAndPhoneNo = addressAndPhoneNo;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getWorkFlowTask() {
		return workFlowTask;
	}

	public void setWorkFlowTask(String workFlowTask) {
		this.workFlowTask = workFlowTask;
	}

	public String getRpName() {
		return rpName;
	}

	public void setRpName(String rpName) {
		this.rpName = rpName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getAddOnTermpremium() {
		return addOnTermpremium;
	}

	public void setAddOnTermpremium(double addOnTermpremium) {
		this.addOnTermpremium = addOnTermpremium;
	}
}

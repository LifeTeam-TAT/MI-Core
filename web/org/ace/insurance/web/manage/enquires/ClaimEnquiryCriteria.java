package org.ace.insurance.web.manage.enquires;

import java.util.Date;

import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;

public class ClaimEnquiryCriteria {
	private Date startDate;
	private Date endDate;
	private Agent agent;
	private Branch branch;
	private MedicalPolicy policyNumber;
	private String claimRequestNo;

	public ClaimEnquiryCriteria() {
	}

	public ClaimEnquiryCriteria(Date startDate, Date endDate, Agent agent, Branch branch, MedicalPolicy policyNumber, String claimRequestNo) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.agent = agent;
		this.branch = branch;
		this.policyNumber = policyNumber;
		this.claimRequestNo = claimRequestNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public MedicalPolicy getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(MedicalPolicy policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getClaimRequestNo() {
		return claimRequestNo;
	}

	public void setClaimRequestNo(String claimRequestNo) {
		this.claimRequestNo = claimRequestNo;
	}

}

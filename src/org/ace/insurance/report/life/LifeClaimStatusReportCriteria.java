package org.ace.insurance.report.life;

import java.util.Date;

import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.system.common.branch.Branch;

public class LifeClaimStatusReportCriteria {
	private Date startDate;
	private Date endDate;
	private Branch branch;
	private PolicyStatus policyStatus;

	public LifeClaimStatusReportCriteria() {
	}

	public LifeClaimStatusReportCriteria(Date startDate, Date endDate, Branch branch, PolicyStatus policyStatus) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.branch = branch;
		this.policyStatus = policyStatus;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

}

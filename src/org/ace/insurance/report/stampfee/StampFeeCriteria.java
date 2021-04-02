package org.ace.insurance.report.stampfee;

import java.util.Date;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.system.common.branch.Branch;

public class StampFeeCriteria {
	private Date startDate;
	private Date endDate;
	private Branch branch;
	private PolicyReferenceType policyReferenceType;

	public StampFeeCriteria() {

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

	public PolicyReferenceType getPolicyReferenceType() {
		return policyReferenceType;
	}

	public void setPolicyReferenceType(PolicyReferenceType policyReferenceType) {
		this.policyReferenceType = policyReferenceType;
	}

}

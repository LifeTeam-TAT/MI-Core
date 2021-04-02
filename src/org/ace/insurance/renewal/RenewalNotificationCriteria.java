package org.ace.insurance.renewal;

import java.util.Date;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.system.common.salesPoints.SalesPoints;

public class RenewalNotificationCriteria {
	private Date startDate;
	private Date endDate;
	private PolicyReferenceType policyReferenceType;
	private String branchId;
	private String policyNo;
	private SalesPoints salesPoints;

	public RenewalNotificationCriteria() {
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

	public PolicyReferenceType getPolicyReferenceType() {
		return policyReferenceType;
	}

	public void setPolicyReferenceType(PolicyReferenceType policyReferenceType) {
		this.policyReferenceType = policyReferenceType;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public SalesPoints getSalesPoints() {
		return salesPoints;
	}

	public void setSalesPoints(SalesPoints salesPoints) {
		this.salesPoints = salesPoints;
	}

}

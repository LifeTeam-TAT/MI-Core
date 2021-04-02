package org.ace.insurance.proxy;

import org.ace.insurance.common.ISorter;

public class CCL002 implements ISorter {
	private static final long serialVersionUID = 1L;
	private String id;
	private String claimReferenceNo;
	private String policyNo;
	private String customerName;
	private String salePersonName;
	private String branch;
	private double totalClaimAmount;

	public CCL002(String id, String claimReferenceNo, String policyNo, String customerName, String salePersonName, String branch, double totalClaimAmount) {
		this.id = id;
		this.claimReferenceNo = claimReferenceNo;
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.salePersonName = salePersonName;
		this.branch = branch;
		this.totalClaimAmount = totalClaimAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClaimReferenceNo() {
		return claimReferenceNo;
	}

	public void setClaimReferenceNo(String claimReferenceNo) {
		this.claimReferenceNo = claimReferenceNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSalePersonName() {
		return salePersonName;
	}

	public void setSalePersonName(String salePersonName) {
		this.salePersonName = salePersonName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public double getTotalClaimAmount() {
		return totalClaimAmount;
	}

	public void setTotalClaimAmount(double totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
	}

	@Override
	public String getRegistrationNo() {
		return claimReferenceNo;
	}

}

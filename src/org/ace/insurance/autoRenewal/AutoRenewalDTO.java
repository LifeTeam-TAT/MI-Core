package org.ace.insurance.autoRenewal;

import java.util.Date;

import org.ace.insurance.common.InsuranceType;

public class AutoRenewalDTO {
	private String policyId;
	private String policyNo;
	private String branchId;
	private String customerName;
	private String customerNrc;
	private Date activedPolicyStartDate;
	private Date activedPolicyEndDate;
	private InsuranceType insuranceType;

	public AutoRenewalDTO() {
	}

	public AutoRenewalDTO(String policyId, String policyNo, String branchId, String customerName, String customerNrc, Date activedPolicyStartDate, Date activedPolicyEndDate,
			InsuranceType insuranceType) {
		super();
		this.policyId = policyId;
		this.policyNo = policyNo;
		this.branchId = branchId;
		this.customerName = customerName;
		this.customerNrc = customerNrc;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.activedPolicyEndDate = activedPolicyEndDate;
		this.insuranceType = insuranceType;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNrc() {
		return customerNrc;
	}

	public void setCustomerNrc(String customerNrc) {
		this.customerNrc = customerNrc;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

}

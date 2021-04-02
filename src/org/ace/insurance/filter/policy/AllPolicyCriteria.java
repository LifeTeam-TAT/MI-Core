package org.ace.insurance.filter.policy;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PolicyCriteriaItems;

public class AllPolicyCriteria {
	private String customerName;
	private String organizationName;
	private String policyNo;
	public String criteriaValue;
	public PolicyCriteriaItems policyCriteriaItems;
	private InsuranceType insuranceType;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public PolicyCriteriaItems getPolicyCriteriaItems() {
		return policyCriteriaItems;
	}

	public void setPolicyCriteriaItems(PolicyCriteriaItems policyCriteriaItems) {
		this.policyCriteriaItems = policyCriteriaItems;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

}

package org.ace.insurance.life.claim;

import org.ace.insurance.common.LifePolicyCriteriaItems;

public class LifePolicyCriteria {
	private String customerName;
	private String organizationName;
	private String policyNo;
	public String criteriaValue;
	public LifePolicyCriteriaItems lifePolicyCriteriaItems;
	
	public LifePolicyCriteria() {
		super();
	}

	public LifePolicyCriteria(String customerName, String organizationName, String policyNo) {
		super();
		this.customerName = customerName;
		this.organizationName = organizationName;
		this.policyNo = policyNo;
	}

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

	public LifePolicyCriteriaItems getLifePolicyCriteriaItems() {
		return lifePolicyCriteriaItems;
	}

	public void setLifePolicyCriteriaItems(LifePolicyCriteriaItems lifePolicyCriteriaItems) {
		this.lifePolicyCriteriaItems = lifePolicyCriteriaItems;
	}
}

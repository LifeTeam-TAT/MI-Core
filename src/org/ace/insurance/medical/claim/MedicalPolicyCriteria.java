package org.ace.insurance.medical.claim;

import org.ace.insurance.common.MedicalPolicyCriteriaItems;

public class MedicalPolicyCriteria {
	private String customerName;
	private String organizationName;
	private String policyNo;
	public String criteriaValue;
	public MedicalPolicyCriteriaItems medicalPolicyCriteriaItems;
	
	public MedicalPolicyCriteria() {
		super();
	}

	public MedicalPolicyCriteria(String customerName, String organizationName, String policyNo) {
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

	public MedicalPolicyCriteriaItems getMedicalPolicyCriteriaItems() {
		return medicalPolicyCriteriaItems;
	}

	public void setMedicalPolicyCriteriaItems(
			MedicalPolicyCriteriaItems medicalPolicyCriteriaItems) {
		this.medicalPolicyCriteriaItems = medicalPolicyCriteriaItems;
	}

}

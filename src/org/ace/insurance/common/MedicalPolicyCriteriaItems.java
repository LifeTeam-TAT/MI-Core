package org.ace.insurance.common;

public enum MedicalPolicyCriteriaItems {

	POLICY_NO("Policy No"), CUSTOMER_NAME("Customer Name"), ORGANIZATION_NAME("Organization Name");
	private String label;

	private MedicalPolicyCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
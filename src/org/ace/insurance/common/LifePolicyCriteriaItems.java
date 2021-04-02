package org.ace.insurance.common;

public enum LifePolicyCriteriaItems {
	POLICY_NO("Policy No"), CUSTOMER_NAME("Customer Name"), ORGANIZATION_NAME("Organization Name"), NRC("NRC"), INSURED_PERSON_NAME("Insured Person Name");
	private String label;

	private LifePolicyCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

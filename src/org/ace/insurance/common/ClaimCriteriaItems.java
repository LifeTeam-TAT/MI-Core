package org.ace.insurance.common;

public enum ClaimCriteriaItems {
	POLICYNO("Policy No"), REGNO("Registration No"), CUSTOMERNAME("Customer Name"), ORGANIZATIONNAME("Organization Name");

	private String label;

	private ClaimCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

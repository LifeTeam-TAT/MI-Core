package org.ace.insurance.common;

public enum OrganizationCriteriaItems {
	NAME("NAME"), ORGANIZATION_CODE("ORGANIZATION_CODE"), REGISTRATION_NO("REGISTRATION_NO");
	private String label;

	private OrganizationCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

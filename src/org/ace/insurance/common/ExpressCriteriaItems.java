package org.ace.insurance.common;

public enum ExpressCriteriaItems {
	NAME("NAME"), EXPRESS_CODE("EXPRESS_CODE"), REGISTRATION_NO("REGISTRATION_NO");
	private String label;

	private ExpressCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

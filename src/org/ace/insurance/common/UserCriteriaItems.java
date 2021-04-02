package org.ace.insurance.common;

public enum UserCriteriaItems {
	USER_CODE("USER_CODE"), NAME("NAME");
	private String label;

	private UserCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

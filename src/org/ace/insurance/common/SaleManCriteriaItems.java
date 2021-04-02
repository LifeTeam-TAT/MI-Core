package org.ace.insurance.common;

public enum SaleManCriteriaItems {
	FULLNAME("FULLNAME"), FIRSTNAME("FIRSTNAME"), MIDDLENAME("MIDDLENAME"), LASTNAME("LASTNAME"), CODENO("CODENO"), BRANCH("BRANCH");
	private String label;

	private SaleManCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

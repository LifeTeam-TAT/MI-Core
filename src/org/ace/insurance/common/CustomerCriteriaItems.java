package org.ace.insurance.common;

public enum CustomerCriteriaItems {
	FULLNAME("FULLNAME"), FIRSTNAME("FIRSTNAME"), MIDDLENAME("MIDDLENAME"), LASTNAME("LASTNAME"), NRCNO("NRCNO"), FRCNO("FRCNO"), PASSPORTNO("PASSPORTNO");
	private String label;

	private CustomerCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

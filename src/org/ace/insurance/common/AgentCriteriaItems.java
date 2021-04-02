package org.ace.insurance.common;

public enum AgentCriteriaItems {
	FULLNAME("FULLNAME"),

	FIRSTNAME("FIRSTNAME"),

	MIDDLENAME("MIDDLENAME"),

	LASTNAME("LASTNAME"),

	NRCNO("NRCNO"),

	FRCNO("FRCNO"),

	PASSPORTNO("PASSPORTNO"),

	LISCENSENO("LISCENSE NO");

	private String label;

	private AgentCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

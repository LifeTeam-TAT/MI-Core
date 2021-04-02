package org.ace.insurance.filter.cirteria;

public enum CRIA001 {
	FULLNAME("Full Name"),

	FIRSTNAME("First Name"),

	MIDDLENAME("Middle Name"),

	LASTNAME("Last Name"),

	NRCNO("NRC No"),

	FRCNO("FRC No"),

	PASSPORTNO("Passport No"),

	LISCENSENO("Liscense No");

	private String label;

	private CRIA001(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

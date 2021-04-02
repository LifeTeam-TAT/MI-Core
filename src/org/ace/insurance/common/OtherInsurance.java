package org.ace.insurance.common;

public enum OtherInsurance {

	FIRE("Fire"), MOTOR("Motro"), OTHERS("Others");

	private String label;

	private OtherInsurance(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

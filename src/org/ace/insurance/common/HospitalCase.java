package org.ace.insurance.common;

public enum HospitalCase {

	SP("SPECIALPOWER"), GP("GENERALPOWER");

	private String label;

	private HospitalCase(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

package org.ace.insurance.common;

public enum LossExperience {

	SHORTAGES("Shortages"), SHORTLANDING("Short Landing"), DAMAGES("Damages"), OTHERS("Others");

	private String label;

	private LossExperience(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

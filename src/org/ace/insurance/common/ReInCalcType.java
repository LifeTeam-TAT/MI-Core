package org.ace.insurance.common;

public enum ReInCalcType {
	NO_NEED("No-Need"), UN_COMPLETE("Un-Complete"), COMPLETED("Completed");
	private String label;

	private ReInCalcType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

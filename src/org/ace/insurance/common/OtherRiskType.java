package org.ace.insurance.common;

public enum OtherRiskType {
	ACCESSORIES("Accessories"),

	TRAILER("Trailer");
	private String label;

	private OtherRiskType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

package org.ace.insurance.common;

public enum CoinsuranceType {
	IN("IN"), OUT("OUT");

	private String label;

	private CoinsuranceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

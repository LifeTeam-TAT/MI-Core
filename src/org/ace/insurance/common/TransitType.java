package org.ace.insurance.common;

public enum TransitType {

	ROAD("ROAD"), RAIL("RAIL");
	private String label;

	private TransitType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

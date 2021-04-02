package org.ace.insurance.common;

public enum CompensatedType {
	REPLACEMENT("REPLACEMENT"), REPAIRING("REPAIRING"), CASH("CASH");

	private String label;

	private CompensatedType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

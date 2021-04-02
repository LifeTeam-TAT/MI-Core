package org.ace.insurance.common;

public enum CustomerType {
	INDIVIDUALCUSTOMER("Individual"), CORPORATECUSTOMER("Corporate");

	private String label;

	private CustomerType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

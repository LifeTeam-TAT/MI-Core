package org.ace.insurance.common;

public enum AgentSaleInsuranceType {
	GENERAL("GENERAL"), LIFE("LIFE");

	private String label;

	private AgentSaleInsuranceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

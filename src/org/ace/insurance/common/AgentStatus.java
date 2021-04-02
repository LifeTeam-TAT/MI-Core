package org.ace.insurance.common;

public enum AgentStatus {
	OUTSTANDING("OUTSTANDING"), SANCTION("SANCTION"), PAID("PAID");

	private String label;

	private AgentStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

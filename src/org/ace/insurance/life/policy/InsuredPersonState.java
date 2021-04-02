package org.ace.insurance.life.policy;

public enum InsuredPersonState {
	DEAD("DEAD"),
	DISABLE("DISABLE"),
	ALIVE("ALIVE");

	private String label;

	private InsuredPersonState(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

package org.ace.insurance.life.claim;

public enum ClaimStatus {
	DEATH_CLAIM("DEATH_CLAIM"),
	DISABILITY_CLAIM("DISABILITY_CLAIM"),
	PAID("PAID"),
	WAITING("WAITING");

	private String label;

	private ClaimStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

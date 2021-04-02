package org.ace.insurance.medical.claim;

public enum DeathClaimType {
	ACCIDENT_DEATH("ACCIDENT_DEATH"), OTHER("OTHER");
	private String label;

	private DeathClaimType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

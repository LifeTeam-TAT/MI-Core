package org.ace.insurance.common;

public enum PaidUpClaimStatus {
	CLAIM_APPLIED("CLAIM APPLIED"), CLAIM_REJECTED("CLAIM REJECTED");
	private String label;

	public String getLabel() {
		return label;
	}

	private PaidUpClaimStatus(String label) {
		this.label = label;
	}
}

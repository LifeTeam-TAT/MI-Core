package org.ace.insurance.claimproduct;

public enum ClaimRateType {
	USER_DEFINED("User Definied"), BASED_ON_SUMINSURED("Based on Sum Insured"), PERCENT_OF_SUMINSURED("Precentage of Sum Insured");

	private String label;

	private ClaimRateType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

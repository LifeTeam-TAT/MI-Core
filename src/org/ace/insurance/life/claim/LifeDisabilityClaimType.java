package org.ace.insurance.life.claim;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

public enum LifeDisabilityClaimType {
	SEMI_ANNUAL("SEMI_ANNUAL"),
	LUMPSUM("LUMPSUM"),
	ANNUAL("ANNUAL"),
	QUATER("QUATER");

	private String label;

	private LifeDisabilityClaimType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

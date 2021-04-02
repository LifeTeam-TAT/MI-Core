package org.ace.insurance.common;

public enum HealthType {
	MICROHEALTH("MICRO HEALTH"), CRITICALILLNESS("CRITICAL ILLNESS"), HEALTH("HEALTH");

	private String label;

	private HealthType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

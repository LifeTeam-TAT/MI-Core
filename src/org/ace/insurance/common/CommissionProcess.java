package org.ace.insurance.common;

public enum CommissionProcess {

	NEW("NEW"), RENEWAL("RENEWAL"), NEW_ENDORSE_INCREASE("New Endorse Increase"), NEW_ENDORSE_DECREASE("New Endorse Decrease"), RENEWAL_ENDORSE_INCREASE(
			"Renewal Endorse Increase"), RENEWAL_ENDORSE_DECREASE("Renewal Endorse Decrease");

	private String label;

	private CommissionProcess(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

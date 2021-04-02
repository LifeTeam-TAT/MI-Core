package org.ace.insurance.autoRenewal;

public enum AutoRenewalStatus {
	ACTIVE("Active"), DEACTIVATE("Deactivate"), RENEWAL("Renewal"), CANCEL("Cancel");

	private String label;

	private AutoRenewalStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

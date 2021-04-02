package org.ace.insurance.common;

public enum PolicyHistoryEntryType {

	UNDERWRITING("UNDERWRITING"), 
	ENDORSEMENT("ENDORSEMENT"), 
	RENEWAL("RENEWAL");

	private String label;

	private PolicyHistoryEntryType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

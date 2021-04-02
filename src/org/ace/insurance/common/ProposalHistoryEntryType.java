package org.ace.insurance.common;

public enum ProposalHistoryEntryType {

	UNDERWRITING("Enquiry"), 
	WORKFLOWCHANGER("WorkFlowChanger");

	private String label;

	private ProposalHistoryEntryType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

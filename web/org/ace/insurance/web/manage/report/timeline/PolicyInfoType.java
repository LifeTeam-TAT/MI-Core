package org.ace.insurance.web.manage.report.timeline;

public enum PolicyInfoType {
	
	UNDERWRITING("Underwriting"),
	ENDORSEMENT("Endorsement"),
	CLAIM("Claim"),
	RENEWAL("Renewal");

	private String label;

	private PolicyInfoType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

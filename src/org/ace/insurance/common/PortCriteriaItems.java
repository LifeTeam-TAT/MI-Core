package org.ace.insurance.common;

public enum PortCriteriaItems {
	NAME("NAME"), PORT_CODE("PORT_CODE"), REGISTRATION_NO("REGISTRATION_NO");
	private String label;

	private PortCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

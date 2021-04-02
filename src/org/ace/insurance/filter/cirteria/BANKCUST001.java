package org.ace.insurance.filter.cirteria;

public enum BANKCUST001 {
	NAME("NAME"), DESCRIPTION("DESCRIPTION"), CODE("CODE");
	private String label;

	private BANKCUST001(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

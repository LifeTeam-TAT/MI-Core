package org.ace.insurance.filter.cirteria;

public enum CRIAICD10 {

	CODE("CODE"), DISEASE("DISEASE");
	private String label;

	private CRIAICD10(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

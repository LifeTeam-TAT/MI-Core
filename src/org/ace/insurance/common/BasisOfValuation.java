package org.ace.insurance.common;

public enum BasisOfValuation {

	EX_WORKS("Ex-works"), EX_FACOTRY("Ex-facotry"), FOB("FOB"), C_F("C&F"), CIF("CIF");

	private String label;

	public String getLabel() {
		return label;
	}

	private BasisOfValuation(String label) {
		this.label = label;
	}

}

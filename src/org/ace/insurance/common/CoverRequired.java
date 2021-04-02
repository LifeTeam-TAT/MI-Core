package org.ace.insurance.common;

public enum CoverRequired {

	ICC_A_CL("I.C.C (A) CL"), ICC_C_CL("I.C.C (C) CL"), WAR_STRIKES("War & Strikes"), OTHERS("Others");

	private String label;

	private CoverRequired(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

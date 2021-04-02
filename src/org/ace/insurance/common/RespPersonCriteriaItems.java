package org.ace.insurance.common;

public enum RespPersonCriteriaItems {
	BRANCH("BRANCH"), NAME("NAME"), USERCODE("USERCODE");
	private String label;

	private RespPersonCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

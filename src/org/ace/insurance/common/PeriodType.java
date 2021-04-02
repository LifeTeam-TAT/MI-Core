package org.ace.insurance.common;

public enum PeriodType {
	DAY("Day"),
	MONTH("Month"),
	YEAR("Year"),;
	
	private String label;

	private PeriodType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}
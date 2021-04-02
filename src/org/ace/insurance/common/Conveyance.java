package org.ace.insurance.common;

public enum Conveyance {

	STEAMER("Steamer"), LAND("Land"), AIR("Air"), OTHER("Other");

	private String label;

	private Conveyance(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

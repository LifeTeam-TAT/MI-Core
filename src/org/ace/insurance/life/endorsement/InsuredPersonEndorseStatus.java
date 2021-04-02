package org.ace.insurance.life.endorsement;

public enum InsuredPersonEndorseStatus {

	REPLACE("Replace"), NEW("New"), EDIT("Edit"), NRCNO_CHANGE("NRCNO Change"), TERM_CHANGE("TERM_CHANGE");

	private String label;

	private InsuredPersonEndorseStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

package org.ace.insurance.life.endorsement;

public enum BeneficiaryEndorseStatus {

	REPLACE("Replace"),
	NEW("New"),
	DELETE("Delete");
	
	private String label;
	
	private BeneficiaryEndorseStatus(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

}

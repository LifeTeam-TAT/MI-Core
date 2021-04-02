package org.ace.insurance.medical.productprocess;

public enum PriorityControlType {
CUSTOMIZE("Customize"),ACENDING("Acending"),DECENDING("Decending");
	private String label;

	private PriorityControlType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}

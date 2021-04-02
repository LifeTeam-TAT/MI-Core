package org.ace.insurance.common;

public enum UsageOfVehicle {

	FLIGHT("Flight"), VEHICLE("Vehicle"), VOYAGE("Voyage"), TRAIN("Train");

	private String label;

	public String getLabel() {
		return label;
	}

	private UsageOfVehicle(String label) {
		this.label = label;
	}

}

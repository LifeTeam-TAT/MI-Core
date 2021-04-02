package org.ace.insurance.system.common.buildingOccupation;

public enum BuildingOccupationType {

	RESIDENTIAL("RESIDENTIAL"), WAREHOUSE("WAREHOUSE"), SHOPS("SHOPS"), FACTORIES("FACTORIES");

	private String label;

	private BuildingOccupationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}

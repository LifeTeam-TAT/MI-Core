package org.ace.insurance.common;

public class TownShipCriteria {
	private String name;
	private String province;
	
	public TownShipCriteria(){}
	
	public TownShipCriteria(String name , String province) {
		this.name = name;
		this.province = province;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
}

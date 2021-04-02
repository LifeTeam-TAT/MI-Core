package org.ace.insurance.system.common.township;

import java.io.Serializable;

public class TSP001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String district;
	private String province;

	public TSP001(String id, String name, String district, String province) {
		this.id = id;
		this.name = name;
		this.district = district;
		this.province = province;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDistrict() {
		return district;
	}

	public String getProvince() {
		return province;
	}

}

package org.ace.insurance.system.common.township;

import java.io.Serializable;

public class TSP002 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String code;
	private String shortName;
	private String district;
	private String description;

	public TSP002(String id, String name, String code, String shortName, String district, String description) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.shortName = shortName;
		this.district = district;
		this.description = description;
	}

	public TSP002(Township township) {
		this.id = township.getId();
		this.name = township.getName();
		this.code = township.getCode();
		this.shortName = township.getShortName();
		this.district = township.getDistrict().getName();
		this.description = township.getDescription();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDistrict() {
		return district;
	}

	public String getDescription() {
		return description;
	}

}

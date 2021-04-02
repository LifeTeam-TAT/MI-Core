package org.ace.insurance.system.common.district;

import java.io.Serializable;

public class DIS001 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String code;
	private String province;
	private String description;

	public DIS001(String id, String name, String code, String province, String description) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.province = province;
		this.description = description;
	}

	public DIS001(District district) {
		this.id = district.getId();
		this.name = district.getName();
		this.code = district.getCode();
		this.province = district.getProvince().getName();
		this.description = district.getDescription();
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

	public String getProvince() {
		return province;
	}

	public String getDescription() {
		return description;
	}

}

package org.ace.insurance.system.common.province;

import java.io.Serializable;

public class PRV001 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String provinceNo;
	private String name;
	private String code;
	private String country;
	private String description;

	public PRV001(String id, String name, String provinceNo, String code, String country, String description) {
		this.id = id;
		this.name = name;
		this.provinceNo = provinceNo;
		this.code = code;
		this.country = country;
		this.description = description;
	}

	public PRV001(Province province) {
		this.id = province.getId();
		this.provinceNo = province.getProvinceNo();
		this.name = province.getName();
		this.code = province.getCode();
		this.country = province.getCountry().getName();
		this.description = province.getDescription();
	}

	public String getId() {
		return id;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getCountry() {
		return country;
	}

	public String getDescription() {
		return description;
	}

}

package org.ace.insurance.system.common.country;

import java.io.Serializable;

public class CTY001 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String description;
	private String code;

	public CTY001(String id, String name, String description, String code) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.code = code;
	}

	public CTY001(Country country) {
		this.id = country.getId();
		this.name = country.getName();
		this.description = country.getDescription();
		this.code = country.getCode();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

}

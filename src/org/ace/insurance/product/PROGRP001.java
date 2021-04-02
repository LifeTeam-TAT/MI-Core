package org.ace.insurance.product;

import java.io.Serializable;

public class PROGRP001 implements Serializable {
	private static final long serialVersionUID = 403570860946827514L;
	private String id;
	private String name;

	public PROGRP001() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

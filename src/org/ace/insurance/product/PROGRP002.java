package org.ace.insurance.product;

import java.io.Serializable;

import org.ace.insurance.common.ProductGroupType;

public class PROGRP002 implements Serializable {
	private static final long serialVersionUID = 403570860946827514L;
	private String id;
	private String name;
	private ProductGroupType groupType;

	public PROGRP002(String id, String name, ProductGroupType groupType) {
		this.id = id;
		this.name = name;
		this.groupType = groupType;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProductGroupType getGroupType() {
		return groupType;
	}

}

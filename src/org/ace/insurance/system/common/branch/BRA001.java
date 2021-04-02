package org.ace.insurance.system.common.branch;

import java.io.Serializable;

public class BRA001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String branchCode;
	private String description;

	public BRA001(String id, String name, String branchCode, String description) {
		this.id = id;
		this.name = name;
		this.branchCode = branchCode;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getDescription() {
		return description;
	}

}

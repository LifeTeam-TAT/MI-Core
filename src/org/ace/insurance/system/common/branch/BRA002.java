package org.ace.insurance.system.common.branch;

import java.io.Serializable;

public class BRA002 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String branchCode;
	private String address;
	private boolean isCoInsuAccess;

	public BRA002(String id, String name, String branchCode, String address, String township, boolean isCoInsuAccess) {
		this.id = id;
		this.name = name;
		this.branchCode = branchCode;
		this.address = address + ", " + township;
		this.isCoInsuAccess = isCoInsuAccess;
	}

	public BRA002(Branch branch) {
		this.id = branch.getId();
		this.name = branch.getName();
		this.branchCode = branch.getBranchCode();
		this.address = branch.getAddress() + ", " + branch.getTownship().getName();
		this.isCoInsuAccess = branch.isCoInsuAccess();
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

	public String getAddress() {
		return address;
	}

	public boolean isCoInsuAccess() {
		return isCoInsuAccess;
	}

}

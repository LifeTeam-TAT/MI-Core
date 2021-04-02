package org.ace.insurance.user;

import java.io.Serializable;

public class USR001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String usercode;
	private String name;
	private boolean disabled;
	private double authority;
	private String branch;

	public USR001(String id, String usercode, String name, boolean disabled, double authority, String branch) {
		this.id = id;
		this.usercode = usercode;
		this.name = name;
		this.disabled = disabled;
		this.authority = authority;
		this.branch = branch;
	}

	public USR001(User user) {
		this.id = user.getId();
		this.usercode = user.getUsercode();
		this.name = user.getName();
		this.disabled = user.isDisabled();
		this.authority = user.getAuthority();
		this.branch = user.getBranch().getName();
	}

	public String getId() {
		return id;
	}

	public String getUsercode() {
		return usercode;
	}

	public String getName() {
		return name;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public double getAuthority() {
		return authority;
	}

	public String getBranch() {
		return branch;
	}

	public void setId(String id) {
		this.id = id;
	}

}

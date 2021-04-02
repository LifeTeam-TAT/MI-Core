package org.ace.insurance.system.common.department;

import java.io.Serializable;

public class DEP001 implements Serializable  {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String ownerName;
	private String regNo;
	private String address;
	private String phone;
	private String mobile;
	private String email;

	public DEP001(String id, String name, String ownerName, String regNo, String address, String township, String phone, String mobile, String email) {
		this.id = id;
		this.name = name;
		this.ownerName = ownerName;
		this.regNo = regNo;
		this.address = address + ", " + township;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
	}

	public DEP001(Department department) {
		this.id = department.getId();
		this.name = department.getName();
		this.ownerName = department.getOwnerName();
		this.regNo = department.getRegNo();
		this.address = department.getFullAddress();
		this.phone = department.getContentInfo().getPhone();
		this.mobile = department.getContentInfo().getMobile();
		this.email = department.getContentInfo().getEmail();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getRegNo() {
		return regNo;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getMobile() {
		return mobile;
	}

	public String getEmail() {
		return email;
	}
}

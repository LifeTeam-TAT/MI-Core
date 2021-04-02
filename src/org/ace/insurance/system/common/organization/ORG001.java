package org.ace.insurance.system.common.organization;

import java.io.Serializable;

public class ORG001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String ownerName;
	private String regNo;
	private String address;
	private String phone;
	private String mobile;
	private String email;

	public ORG001(String id, String name, String ownerName, String regNo, String address, String township, String phone, String mobile, String email) {
		this.id = id;
		this.name = name;
		this.ownerName = ownerName;
		this.regNo = regNo;
		this.address = address + ", " + township;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
	}

	public ORG001(Organization organization) {
		this.id = organization.getId();
		this.name = organization.getName();
		this.ownerName = organization.getOwnerName();
		this.regNo = organization.getRegNo();
		this.address = organization.getFullAddress();
		this.phone = organization.getContentInfo().getPhone();
		this.mobile = organization.getContentInfo().getMobile();
		this.email = organization.getContentInfo().getEmail();
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

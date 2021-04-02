package org.ace.insurance.system.common.company;

import java.io.Serializable;

public class CMY001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String address;
	private String phone;
	private String mobile;
	private String email;

	public CMY001(String id, String name, String address, String township, String phone, String mobile, String email) {
		this.id = id;
		this.name = name;
		this.address = address + ", " + township;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
	}

	public CMY001(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.address = company.getAddress().getFullAddress();
		this.phone = company.getContentInfo().getPhone();
		this.mobile = company.getContentInfo().getMobile();
		this.email = company.getContentInfo().getEmail();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
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

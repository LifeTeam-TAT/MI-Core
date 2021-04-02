package org.ace.insurance.filter.customer;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;

public class CUST001 {
	private String id;
	private String name;
	private String fullIdNo;
	private String address;

	public CUST001(String id, String name, String fullIdNo, String address, String initialId) {
		this.id = id;
		this.name = (initialId == null ? "-" : initialId) + " " + name;
		this.address = address;
		this.fullIdNo = fullIdNo;

	}

	public CUST001(String id, Name name, String fullIdNo, ResidentAddress resAddress, String initialId) {
		this.id = id;
		if (initialId != null && !initialId.isEmpty()) {
			this.name = initialId + " " + name.getFullName();
		} else {
			this.name = name.getFullName();
		}
		this.address = resAddress.getFullResidentAddress();
		this.fullIdNo = fullIdNo;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

package org.ace.insurance.filter.school;

import java.io.Serializable;

import org.ace.insurance.common.SchoolLevelType;
import org.ace.insurance.common.SchoolType;

public class SCH001 implements Serializable {

	private String id;
	private String name;
	private String address;
	private String phoneNo;
	private String schoolCodeNo;
	private SchoolType schoolType;
	private SchoolLevelType schoolLevelType;
	private String township;
	private String provience;

	public String getName() {
		return name;
	}

	public SCH001(String id, String name, String address, String phoneNo, String schoolCodeNo, SchoolType schoolType, SchoolLevelType schoolLevelType, String township,
			String provience) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNo = phoneNo;
		this.schoolCodeNo = schoolCodeNo;
		this.schoolType = schoolType;
		this.schoolLevelType = schoolLevelType;
		this.township = township;
		this.provience = provience;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSchoolCodeNo() {
		return schoolCodeNo;
	}

	public void setSchoolCodeNo(String schoolCodeNo) {
		this.schoolCodeNo = schoolCodeNo;
	}

	public SchoolType getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

	public SchoolLevelType getSchoolLevelType() {
		return schoolLevelType;
	}

	public void setSchoolLevelType(SchoolLevelType schoolLevelType) {
		this.schoolLevelType = schoolLevelType;
	}

	public String getTownship() {
		return township;
	}

	public void setTownship(String township) {
		this.township = township;
	}

	public String getProvience() {
		return provience;
	}

	public void setProvience(String provience) {
		this.provience = provience;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

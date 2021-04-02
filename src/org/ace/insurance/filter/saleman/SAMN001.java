package org.ace.insurance.filter.saleman;

public class SAMN001 {
	private String id;
	private String codeNo;
	private String licenseNo;
	private String name;
	private String idNo;
	private String address;
	private String branch;

	public SAMN001() {

	}

	public SAMN001(String id, String codeNo, String licenseNo, String name, String idNo, String address, String branch) {
		this.id = id;
		this.codeNo = codeNo;
		this.licenseNo = licenseNo;
		this.name = name;
		this.idNo = idNo;
		this.address = address;
		this.branch = branch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
}

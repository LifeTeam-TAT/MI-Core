package org.ace.insurance.system.common.customer;

import java.util.Date;

import org.ace.insurance.common.Gender;
import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.Name;

public class CUST001 implements ISorter {
	private String id;
	private String idNo;
	private String fullIdNo;
	private IdConditionType idConditionType;
	private Gender gender;
	private String initialId;
	private String fullName;
	private String phone;
	private String mobilePh;
	private String email;
	private String accountNo;
	private Date dateOfBirth;
	private String fatherName;

	public CUST001() {
		// TODO Auto-generated constructor stub
	}

	public CUST001(String id, String initialId, Name name, String mobile, String email, String phone, String accountNo, Date dateOfBirth, Gender gender, String fatherName,
			String fullIdNo) {
		this.id = id;
		this.fullIdNo = fullIdNo;
		this.gender = gender;
		this.initialId = initialId;
		this.fullName = initialId + " " + name.getFullName();
		this.phone = phone;
		this.mobilePh = mobile;
		this.email = email;
		this.accountNo = accountNo;
		this.dateOfBirth = dateOfBirth;
		this.fatherName = fatherName;
	}

	public CUST001(Customer customer) {
		this.id = customer.getId();
		this.idNo = customer.getIdNo();
		this.fullIdNo = customer.getFullIdNo();
		this.gender = customer.getGender();
		this.initialId = customer.getInitialId();
		this.fullName = customer.getFullName();
		this.phone = customer.getContentInfo().getPhone();
		this.mobilePh = customer.getContentInfo().getMobile();
		this.email = customer.getContentInfo().getEmail();
		this.accountNo = customer.getBankAccountNo();
		this.dateOfBirth = customer.getDateOfBirth();
		this.fatherName = customer.getFatherName();
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public IdConditionType getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(IdConditionType idConditionType) {
		this.idConditionType = idConditionType;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePh() {
		return mobilePh;
	}

	public void setMobilePh(String mobilePh) {
		this.mobilePh = mobilePh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Override
	public String getRegistrationNo() {
		return id;
	}

}

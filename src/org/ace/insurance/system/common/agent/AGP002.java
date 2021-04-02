package org.ace.insurance.system.common.agent;

import java.util.Date;

import org.ace.insurance.common.Gender;
import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.Name;

public class AGP002 implements ISorter {
	private String id;
	private String codeNo;
	private String liscenseNo;

	private String fullIdNo;
	private IdConditionType idConditionType;
	private Gender gender;
	private String initialId;
	private String fullName;
	private String phone;
	private String mobilePh;
	private String email;
	private Date dateOfBirth;
	private String fatherName;
	private String fullAddress;

	public AGP002() {
		// TODO Auto-generated constructor stub
	}

	public AGP002(String id, String codeNo, String fullAddress, String liscenseNo, String initialId, Name name, String phone, Date dateOfBirth, Gender gender, String fatherName,
			String fullIdNo) {
		this.id = id;
		this.codeNo = codeNo;
		this.liscenseNo = liscenseNo;
		this.fullAddress = fullAddress;
		this.fullIdNo = fullIdNo;
		this.gender = gender;
		this.initialId = initialId;
		this.fullName = initialId + " " + name.getFullName();
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.fatherName = fatherName;
	}

	public AGP002(Agent agent) {
		this.id = agent.getId();

		this.fullIdNo = agent.getFullIdNo();
		this.gender = agent.getGender();
		this.initialId = agent.getInitialId();
		this.fullName = agent.getFullName();
		this.phone = agent.getContentInfo().getPhone();
		this.mobilePh = agent.getContentInfo().getMobile();
		this.email = agent.getContentInfo().getEmail();

		this.dateOfBirth = agent.getDateOfBirth();
		this.fatherName = agent.getFatherName();
		this.codeNo = agent.getCodeNo();
		this.fullAddress = agent.getResidentAddress().getFullResidentAddress();
		this.liscenseNo = agent.getLiscenseNo();
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

	@Override
	public String getRegistrationNo() {
		return id;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

}

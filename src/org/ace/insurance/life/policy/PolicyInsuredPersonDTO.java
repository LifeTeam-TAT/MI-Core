package org.ace.insurance.life.policy;

import java.util.Date;

public class PolicyInsuredPersonDTO {

	private String id;
	private String fullName;
	private String fatherName;
	private String occupation;
	private String typeOfSport;
	private String fullIdNo;
	private Date dateOfBirth;
	private String beneficiaryNames;
	private String beneRelationships;
	private String beneFullIdNos;
	private double sumInsured;
	private double premium;
	private double termPremium;
	private String insPersonCodeNo;

	public PolicyInsuredPersonDTO() {
	}

	public PolicyInsuredPersonDTO(String id, String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBeneficiaryNames() {
		return beneficiaryNames;
	}

	public void setBeneficiaryNames(String beneficiaryNames) {
		this.beneficiaryNames = beneficiaryNames;
	}

	public String getBeneRelationships() {
		return beneRelationships;
	}

	public void setBeneRelationships(String beneRelationships) {
		this.beneRelationships = beneRelationships;
	}

	public String getBeneFullIdNos() {
		return beneFullIdNos;
	}

	public void setBeneFullIdNos(String beneFullIdNos) {
		this.beneFullIdNos = beneFullIdNos;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getTypeOfSport() {
		return typeOfSport;
	}

	public void setTypeOfSport(String typeOfSport) {
		this.typeOfSport = typeOfSport;
	}

	public double getTermPremium() {
		return termPremium;
	}

	public void setTermPremium(double termPremium) {
		this.termPremium = termPremium;
	}

	public String getInsPersonCodeNo() {
		return insPersonCodeNo;
	}

	public void setInsPersonCodeNo(String insPersonCodeNo) {
		this.insPersonCodeNo = insPersonCodeNo;
	}

}

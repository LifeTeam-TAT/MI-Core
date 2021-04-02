package org.ace.insurance.report.customer;

import java.util.Date;

public class FamilyInfoDTO {
	private String name;
	private String nrc;
	private String relationship;
	private Date dateOfBirth;

	public FamilyInfoDTO() {
	}

	public FamilyInfoDTO(String name, String nrc, String relationship, Date dateOfBirth) {
		this.name = name;
		this.nrc = nrc;
		this.relationship = relationship;
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}

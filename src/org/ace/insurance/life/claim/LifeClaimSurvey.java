package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFECALIMSURVEY)
@TableGenerator(name = "LIFECALIMSURVEY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECALIMSURVEY_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class LifeClaimSurvey implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECALIMSURVEY_GEN")
	private String id;

	@Temporal(TemporalType.DATE)
	private Date surveyDate;
	private String medicalOfficerName;
	private String rankAndQualification;
	private String address;
	private String remark;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOWNSHIPID", referencedColumnName = "ID")
	private Township township;

	@OneToOne
	@JoinColumn(name = "MEDICALPLACEID", referencedColumnName = "ID")
	private Hospital hospitalPlace;

	@OneToOne
	@JoinColumn(name = "LIFECLAIMPROPOSALID", referencedColumnName = "ID")
	private LifeClaimProposal lifeClaimProposal;

	@Embedded
	private UserRecorder recorder;

	public LifeClaimSurvey() {

	}

	public String getId() {
		return id;
	}

	public Date getSurveyDate() {
		return surveyDate;
	}

	public String getMedicalOfficerName() {
		return medicalOfficerName;
	}

	public String getRankAndQualification() {
		return rankAndQualification;
	}

	public String getAddress() {
		return address;
	}

	public String getRemark() {
		return remark;
	}

	public Township getTownship() {
		return township;
	}

	public Hospital getHospitalPlace() {
		return hospitalPlace;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public void setMedicalOfficerName(String medicalOfficerName) {
		this.medicalOfficerName = medicalOfficerName;
	}

	public void setRankAndQualification(String rankAndQualification) {
		this.rankAndQualification = rankAndQualification;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTownship(Township township) {
		this.township = township;
	}

	public void setHospitalPlace(Hospital hospitalPlace) {
		this.hospitalPlace = hospitalPlace;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public LifeClaimProposal getLifeClaimProposal() {
		return lifeClaimProposal;
	}

	public void setLifeClaimProposal(LifeClaimProposal lifeClaimProposal) {
		this.lifeClaimProposal = lifeClaimProposal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((hospitalPlace == null) ? 0 : hospitalPlace.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lifeClaimProposal == null) ? 0 : lifeClaimProposal.hashCode());
		result = prime * result + ((medicalOfficerName == null) ? 0 : medicalOfficerName.hashCode());
		result = prime * result + ((rankAndQualification == null) ? 0 : rankAndQualification.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((surveyDate == null) ? 0 : surveyDate.hashCode());
		result = prime * result + ((township == null) ? 0 : township.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LifeClaimSurvey other = (LifeClaimSurvey) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (hospitalPlace == null) {
			if (other.hospitalPlace != null)
				return false;
		} else if (!hospitalPlace.equals(other.hospitalPlace))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lifeClaimProposal == null) {
			if (other.lifeClaimProposal != null)
				return false;
		} else if (!lifeClaimProposal.equals(other.lifeClaimProposal))
			return false;
		if (medicalOfficerName == null) {
			if (other.medicalOfficerName != null)
				return false;
		} else if (!medicalOfficerName.equals(other.medicalOfficerName))
			return false;
		if (rankAndQualification == null) {
			if (other.rankAndQualification != null)
				return false;
		} else if (!rankAndQualification.equals(other.rankAndQualification))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (surveyDate == null) {
			if (other.surveyDate != null)
				return false;
		} else if (!surveyDate.equals(other.surveyDate))
			return false;
		if (township == null) {
			if (other.township != null)
				return false;
		} else if (!township.equals(other.township))
			return false;
		return true;
	}

}

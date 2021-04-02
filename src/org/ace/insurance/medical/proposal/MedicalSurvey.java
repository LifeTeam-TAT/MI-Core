package org.ace.insurance.medical.proposal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.MEDICALSURVEY)
@TableGenerator(name = "MEDICALSUVEY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALSUVEY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "MedicalSurvey.findAll", query = "SELECT l FROM MedicalSurvey l "),
		@NamedQuery(name = "MedicalSurvey.findById", query = "SELECT l FROM MedicalSurvey l WHERE l.id = :id") })
@EntityListeners(IDInterceptor.class)
public class MedicalSurvey {
	@Version
	private int version;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALSUVEY_GEN")
	private String id;
	private String rankAndQualification;
	private String remark;
	private String conditionOfHealth;
	@Temporal(TemporalType.DATE)
	private Date surveyDate;

	@OneToOne
	@JoinColumn(name = "MEDICALPROPOSALID", referencedColumnName = "ID")
	private MedicalProposal medicalProposal;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SURVEYID", referencedColumnName = "ID")
	private List<MedicalHistory> medicalHistoryList;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MEDICALSURVEY_MEMBER_LINK", joinColumns = { @JoinColumn(name = "MEDICALSURVEYID", referencedColumnName = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "SURVEYMEMBERID", referencedColumnName = "ID") })
	private List<SurveyMember> surveyMemberList;

	@Embedded
	private UserRecorder recorder;

	public MedicalSurvey() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getRankAndQualification() {
		return rankAndQualification;
	}

	public void setRankAndQualification(String rankAndQualification) {
		this.rankAndQualification = rankAndQualification;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConditionOfHealth() {
		return conditionOfHealth;
	}

	public void setConditionOfHealth(String conditionOfHealth) {
		this.conditionOfHealth = conditionOfHealth;
	}

	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public void setMedicalProposal(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
	}

	public List<MedicalHistory> getMedicalHistoryList() {
		if (medicalHistoryList == null) {
			medicalHistoryList = new ArrayList<MedicalHistory>();
		}
		return medicalHistoryList;
	}

	public void setMedicalHistoryList(List<MedicalHistory> medicalHistoryList) {
		this.medicalHistoryList = medicalHistoryList;
	}

	public void addMedicalHistory(MedicalHistory medicalHistory) {
		getMedicalHistoryList().add(medicalHistory);
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public List<SurveyMember> getSurveyMemberList() {
		if (surveyMemberList == null) {
			return new ArrayList<>();
		}
		return surveyMemberList;
	}

	public void setSurveyMemberList(List<SurveyMember> surveyMemberList) {
		this.surveyMemberList = surveyMemberList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditionOfHealth == null) ? 0 : conditionOfHealth.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicalProposal == null) ? 0 : medicalProposal.hashCode());
		result = prime * result + ((rankAndQualification == null) ? 0 : rankAndQualification.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((surveyDate == null) ? 0 : surveyDate.hashCode());
		result = prime * result + version;
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
		MedicalSurvey other = (MedicalSurvey) obj;
		if (conditionOfHealth == null) {
			if (other.conditionOfHealth != null)
				return false;
		} else if (!conditionOfHealth.equals(other.conditionOfHealth))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalProposal == null) {
			if (other.medicalProposal != null)
				return false;
		} else if (!medicalProposal.equals(other.medicalProposal))
			return false;
		if (rankAndQualification == null) {
			if (other.rankAndQualification != null)
				return false;
		} else if (!rankAndQualification.equals(other.rankAndQualification))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
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
		if (version != other.version)
			return false;
		return true;
	}

}

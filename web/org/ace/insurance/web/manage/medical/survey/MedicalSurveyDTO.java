package org.ace.insurance.web.manage.medical.survey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.insurance.web.common.CommonDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProDTO;

public class MedicalSurveyDTO extends CommonDTO {
	private String rankAndQualification;
	private String remark;
	private String conditionOfHealth;
	private Date surveyDate;
	private MedProDTO medicalProposalDTO;
	private List<MedicalHistoryDTO> medicalHistoryList;
	private List<SurveyMember> surveyMemberList;

	public MedicalSurveyDTO() {
		super();
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

	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public MedProDTO getMedicalProposalDTO() {
		return medicalProposalDTO;
	}

	public void setMedicalProposalDTO(MedProDTO medicalProposalDTO) {
		this.medicalProposalDTO = medicalProposalDTO;
	}

	public List<MedicalHistoryDTO> getMedicalHistoryList() {
		if (medicalHistoryList == null) {
			medicalHistoryList = new ArrayList<MedicalHistoryDTO>();
		}
		return medicalHistoryList;
	}

	public void setMedicalHistoryList(List<MedicalHistoryDTO> medicalHistoryList) {
		this.medicalHistoryList = medicalHistoryList;
	}

	public void addMedicalHistoryDTO(MedicalHistoryDTO medicalHistoryDTO) {
		getMedicalHistoryList().add(medicalHistoryDTO);
	}

	public String getConditionOfHealth() {
		return conditionOfHealth;
	}

	public void setConditionOfHealth(String conditionOfHealth) {
		this.conditionOfHealth = conditionOfHealth;
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
		int result = super.hashCode();
		result = prime * result + ((conditionOfHealth == null) ? 0 : conditionOfHealth.hashCode());
		result = prime * result + ((medicalProposalDTO == null) ? 0 : medicalProposalDTO.hashCode());
		result = prime * result + ((rankAndQualification == null) ? 0 : rankAndQualification.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((surveyDate == null) ? 0 : surveyDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalSurveyDTO other = (MedicalSurveyDTO) obj;
		if (conditionOfHealth == null) {
			if (other.conditionOfHealth != null)
				return false;
		} else if (!conditionOfHealth.equals(other.conditionOfHealth))
			return false;
		if (medicalProposalDTO == null) {
			if (other.medicalProposalDTO != null)
				return false;
		} else if (!medicalProposalDTO.equals(other.medicalProposalDTO))
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
		return true;
	}

}

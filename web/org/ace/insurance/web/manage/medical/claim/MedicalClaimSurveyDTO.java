package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.medical.claim.ClaimType;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.web.common.CommonDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;

public class MedicalClaimSurveyDTO extends CommonDTO {
	private boolean existsEntity;
	private boolean boardOrNot;
	private int version;
	private String id;
	private String medicalOfficerName;
	private String rankAndQualification;
	private String address;
	private String remark;
	private Date surveyDate;
	private Township township;
	private List<SurveyQuestionAnswerDTO> surveyQuestionAnswerDTOList;
	private List<SurveyQuestionAnswerDTO> hospitalizedClaimSurveyQuestionList;
	private List<SurveyQuestionAnswerDTO> medicationClaimSurveyQuestionList;
	private List<SurveyQuestionAnswerDTO> deathClaimSurveyQuestionList;
	private List<SurveyQuestionAnswerDTO> operationClaimSurveyQuestionList;
	private Hospital hospitalPlace;

	public MedicalClaimSurveyDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isBoardOrNot() {
		return boardOrNot;
	}

	public void setBoardOrNot(boolean boardOrNot) {
		this.boardOrNot = boardOrNot;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getMedicalOfficerName() {
		return medicalOfficerName;
	}

	public void setMedicalOfficerName(String medicalOfficerName) {
		this.medicalOfficerName = medicalOfficerName;
	}

	public String getRankAndQualification() {
		return rankAndQualification;
	}

	public void setRankAndQualification(String rankAndQualification) {
		this.rankAndQualification = rankAndQualification;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Township getTownship() {
		return township;
	}

	public void setTownship(Township township) {
		this.township = township;
	}

	public List<SurveyQuestionAnswerDTO> getSurveyQuestionAnswerDTOList() {
		if (surveyQuestionAnswerDTOList == null) {
			surveyQuestionAnswerDTOList = new ArrayList<SurveyQuestionAnswerDTO>();
		}
		return surveyQuestionAnswerDTOList;
	}

	public void setSurveyQuestionAnswerDTOList(List<SurveyQuestionAnswerDTO> surveyQuestionAnswerDTOList) {
		this.surveyQuestionAnswerDTOList = surveyQuestionAnswerDTOList;
	}

	public Hospital getMedicalPlace() {
		return hospitalPlace;
	}

	public void setMedicalPlace(Hospital hospitalPlace) {
		this.hospitalPlace = hospitalPlace;
	}

	public boolean isExistsEntity() {
		return existsEntity;
	}

	public void setExistsEntity(boolean existsEntity) {
		this.existsEntity = existsEntity;
	}

	public void setHospitalizedClaimSurveyQuestionList(List<SurveyQuestionAnswerDTO> hospitalizedClaimSurveyQuestionList) {
		this.hospitalizedClaimSurveyQuestionList = hospitalizedClaimSurveyQuestionList;
	}

	public void setMedicationClaimSurveyQuestionList(List<SurveyQuestionAnswerDTO> medicationClaimSurveyQuestionList) {
		this.medicationClaimSurveyQuestionList = medicationClaimSurveyQuestionList;
	}

	public void setDeathClaimSurveyQuestionList(List<SurveyQuestionAnswerDTO> deathClaimSurveyQuestionList) {
		this.deathClaimSurveyQuestionList = deathClaimSurveyQuestionList;
	}

	public void setOperationClaimSurveyQuestionList(List<SurveyQuestionAnswerDTO> operationClaimSurveyQuestionList) {
		this.operationClaimSurveyQuestionList = operationClaimSurveyQuestionList;
	}

	public List<SurveyQuestionAnswerDTO> getHospitalizedClaimSurveyQuestionList() {
		if (hospitalizedClaimSurveyQuestionList == null) {
			List<SurveyQuestionAnswerDTO> hospitalizedClaimSurveyQuestionList = new ArrayList<SurveyQuestionAnswerDTO>();
			for (SurveyQuestionAnswerDTO sQuestionAnswer : surveyQuestionAnswerDTOList) {
				if (sQuestionAnswer.getClaimType().equals(ClaimType.HOSPITALIZED_CLAIM)) {
					hospitalizedClaimSurveyQuestionList.add(sQuestionAnswer);
				}
			}
		}
		return hospitalizedClaimSurveyQuestionList;
	}

	public List<SurveyQuestionAnswerDTO> getDeathClaimSurveyQuestionList() {
		if (deathClaimSurveyQuestionList == null) {
			List<SurveyQuestionAnswerDTO> deathClaimSurveyQuestionList = new ArrayList<SurveyQuestionAnswerDTO>();
			for (SurveyQuestionAnswerDTO sQuestionAnswer : surveyQuestionAnswerDTOList) {
				if (sQuestionAnswer.getClaimType().equals(ClaimType.DEATH_CLAIM)) {
					deathClaimSurveyQuestionList.add(sQuestionAnswer);
				}
			}
		}
		return deathClaimSurveyQuestionList;
	}

	public List<SurveyQuestionAnswerDTO> getMedicationClaimSurveyQuestionList() {
		if (medicationClaimSurveyQuestionList == null) {
			List<SurveyQuestionAnswerDTO> medicationClaimSurveyQuestionList = new ArrayList<SurveyQuestionAnswerDTO>();
			for (SurveyQuestionAnswerDTO sQuestionAnswer : surveyQuestionAnswerDTOList) {
				if (sQuestionAnswer.getClaimType().equals(ClaimType.MEDICATION_CLAIM)) {
					medicationClaimSurveyQuestionList.add(sQuestionAnswer);
				}
			}
		}
		return medicationClaimSurveyQuestionList;
	}

	public List<SurveyQuestionAnswerDTO> getOperationClaimSurveyQuestionList() {
		if (operationClaimSurveyQuestionList == null) {
			List<SurveyQuestionAnswerDTO> operationClaimSurveyQuestionList = new ArrayList<SurveyQuestionAnswerDTO>();
			for (SurveyQuestionAnswerDTO sQuestionAnswer : surveyQuestionAnswerDTOList) {
				if (sQuestionAnswer.getClaimType().equals(ClaimType.OPERATION_CLAIM)) {
					operationClaimSurveyQuestionList.add(sQuestionAnswer);
				}
			}
		}
		return operationClaimSurveyQuestionList;
	}

	public void addsurveyQuestionAnswer(SurveyQuestionAnswerDTO surveyQuestionAnswerDTO) {
		if (this.surveyQuestionAnswerDTOList == null) {
			this.surveyQuestionAnswerDTOList = new ArrayList<SurveyQuestionAnswerDTO>();
		}
		surveyQuestionAnswerDTO.setMedicalClaimSurveyDTO(this);
		surveyQuestionAnswerDTOList.add(surveyQuestionAnswerDTO);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (boardOrNot ? 1231 : 1237);
		result = prime * result + (existsEntity ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicalOfficerName == null) ? 0 : medicalOfficerName.hashCode());
		result = prime * result + ((hospitalPlace == null) ? 0 : hospitalPlace.hashCode());
		result = prime * result + ((rankAndQualification == null) ? 0 : rankAndQualification.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((surveyDate == null) ? 0 : surveyDate.hashCode());
		result = prime * result + ((township == null) ? 0 : township.hashCode());
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
		MedicalClaimSurveyDTO other = (MedicalClaimSurveyDTO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (boardOrNot != other.boardOrNot)
			return false;
		if (existsEntity != other.existsEntity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalOfficerName == null) {
			if (other.medicalOfficerName != null)
				return false;
		} else if (!medicalOfficerName.equals(other.medicalOfficerName))
			return false;
		if (hospitalPlace == null) {
			if (other.hospitalPlace != null)
				return false;
		} else if (!hospitalPlace.equals(other.hospitalPlace))
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
		if (version != other.version)
			return false;
		return true;
	}
}

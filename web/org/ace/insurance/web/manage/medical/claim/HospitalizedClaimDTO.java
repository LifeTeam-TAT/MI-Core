package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.Utils;
import org.ace.insurance.medical.claim.HospitalizedClaimICD10;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;

public class HospitalizedClaimDTO extends MedicalClaimDTO {
	private Date hospitalizedStartDate;
	private Date hospitalizedEndDate;
	private Hospital medicalPlace;
	private List<AttachmentDTO> attachmentList;
	private List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList;
	private boolean existEntity;
	private double hospitalizedAmount;
	private double actualHospitalizedAmount;
	private List<HospitalizedClaimICD10> hospitalizedClaimICD10List;

	public HospitalizedClaimDTO() {
	}

	public HospitalizedClaimDTO(HospitalizedClaimDTO hospitalizedClaimDTO) {
		super();
		this.hospitalizedStartDate = hospitalizedClaimDTO.getHospitalizedStartDate();
		this.hospitalizedEndDate = hospitalizedClaimDTO.getHospitalizedEndDate();
		this.medicalPlace = hospitalizedClaimDTO.getMedicalPlace();
		this.attachmentList = hospitalizedClaimDTO.getattachmentList();
		this.surveyQuestionAnswersList = hospitalizedClaimDTO.getSurveyQuestionAnswersList();
		this.existEntity = hospitalizedClaimDTO.isExistEntity();
		this.hospitalizedAmount = hospitalizedClaimDTO.getHospitalizedAmount();
		if (hospitalizedClaimDTO.getHospitalizedClaimICD10List() != null) {
			this.hospitalizedClaimICD10List = hospitalizedClaimDTO.getHospitalizedClaimICD10List();
		}
	}

	public int getTotalDays() {
		return Utils.daysBetween(hospitalizedStartDate, hospitalizedEndDate, false, true);
	}

	public List<SurveyQuestionAnswerDTO> getSurveyQuestionAnswersList() {
		return surveyQuestionAnswersList;
	}

	public void setSurveyQuestionAnswersList(List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList) {
		this.surveyQuestionAnswersList = surveyQuestionAnswersList;
	}

	public Date getHospitalizedStartDate() {
		return hospitalizedStartDate;
	}

	public Date getHospitalizedEndDate() {
		return hospitalizedEndDate;
	}

	public Hospital getMedicalPlace() {
		return medicalPlace;
	}

	public List<AttachmentDTO> getattachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<AttachmentDTO>();
		}
		return attachmentList;
	}

	public void setHospitalizedStartDate(Date hospitalizedStartDate) {
		this.hospitalizedStartDate = hospitalizedStartDate;
	}

	public void setHospitalizedEndDate(Date hospitalizedEndDate) {
		this.hospitalizedEndDate = hospitalizedEndDate;
	}

	public void setMedicalPlace(Hospital medicalPlace) {
		this.medicalPlace = medicalPlace;
	}

	public void setattachmentList(List<AttachmentDTO> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public boolean isExistEntity() {
		return existEntity;
	}

	public void setExistEntity(boolean existEntity) {
		this.existEntity = existEntity;
	}

	public double getHospitalizedAmount() {
		return hospitalizedAmount;
	}

	public void setHospitalizedAmount(double hospitalizedAmount) {
		this.hospitalizedAmount = hospitalizedAmount;
	}

	public double getActualHospitalizedAmount() {
		return actualHospitalizedAmount;
	}

	public void setActualHospitalizedAmount(double actualHospitalizedAmount) {
		this.actualHospitalizedAmount = actualHospitalizedAmount;
	}

	public void addAttachment(AttachmentDTO attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<AttachmentDTO>();
		}
		attachmentList.add(attachment);
	}

	public void addSurveyQuestionList(SurveyQuestionAnswerDTO surveyQuestionAnswer) {
		if (surveyQuestionAnswersList == null) {
			surveyQuestionAnswersList = new ArrayList<SurveyQuestionAnswerDTO>();
		}
		surveyQuestionAnswersList.add(surveyQuestionAnswer);
	}

	public List<HospitalizedClaimICD10> getHospitalizedClaimICD10List() {
		if (hospitalizedClaimICD10List == null) {
			hospitalizedClaimICD10List = new ArrayList<HospitalizedClaimICD10>();
		}
		return hospitalizedClaimICD10List;
	}

	public void addHospitalizedClaimICD10(HospitalizedClaimICD10 hospitalizedClaimICD10) {
		if (hospitalizedClaimICD10List == null) {
			hospitalizedClaimICD10List = new ArrayList<HospitalizedClaimICD10>();
		}
		hospitalizedClaimICD10List.add(hospitalizedClaimICD10);
	}

	public void setHospitalizedClaimICD10List(List<HospitalizedClaimICD10> hospitalizedClaimICD10List) {
		this.hospitalizedClaimICD10List = hospitalizedClaimICD10List;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(actualHospitalizedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (existEntity ? 1231 : 1237);
		temp = Double.doubleToLongBits(hospitalizedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((hospitalizedEndDate == null) ? 0 : hospitalizedEndDate.hashCode());
		result = prime * result + ((hospitalizedStartDate == null) ? 0 : hospitalizedStartDate.hashCode());
		result = prime * result + ((medicalPlace == null) ? 0 : medicalPlace.hashCode());
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
		HospitalizedClaimDTO other = (HospitalizedClaimDTO) obj;
		if (Double.doubleToLongBits(actualHospitalizedAmount) != Double.doubleToLongBits(other.actualHospitalizedAmount))
			return false;
		if (existEntity != other.existEntity)
			return false;
		if (Double.doubleToLongBits(hospitalizedAmount) != Double.doubleToLongBits(other.hospitalizedAmount))
			return false;
		if (hospitalizedEndDate == null) {
			if (other.hospitalizedEndDate != null)
				return false;
		} else if (!hospitalizedEndDate.equals(other.hospitalizedEndDate))
			return false;
		if (hospitalizedStartDate == null) {
			if (other.hospitalizedStartDate != null)
				return false;
		} else if (!hospitalizedStartDate.equals(other.hospitalizedStartDate))
			return false;
		if (medicalPlace == null) {
			if (other.medicalPlace != null)
				return false;
		} else if (!medicalPlace.equals(other.medicalPlace))
			return false;
		return true;
	}

}

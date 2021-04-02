package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.medical.claim.DisabilityClaimICD10;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;

public class MedicationClaimDTO extends MedicalClaimDTO {
	private Date receivedDate;
	private double medicationFee;
	private String medication;
	private ICD10 medicationReason;
	private List<AttachmentDTO> attachmentList;
	private List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList;
	private boolean existEntity;
	private List<DisabilityClaimICD10> disabilityClaimICD10List;

	public MedicationClaimDTO() {
	}

	public List<SurveyQuestionAnswerDTO> getSurveyQuestionAnswersList() {
		return surveyQuestionAnswersList;
	}

	public void setSurveyQuestionAnswersList(List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList) {
		this.surveyQuestionAnswersList = surveyQuestionAnswersList;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public double getMedicationFee() {
		return medicationFee;
	}

	public String getMedication() {
		return medication;
	}

	public ICD10 getMedicationReason() {
		return medicationReason;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setMedicationFee(double medicationFee) {
		this.medicationFee = medicationFee;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public void setMedicationReason(ICD10 medicationReason) {
		this.medicationReason = medicationReason;
	}

	public boolean isExistEntity() {
		return existEntity;
	}

	public void setExistEntity(boolean existEntity) {
		this.existEntity = existEntity;
	}

	public void addAttachment(AttachmentDTO attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<AttachmentDTO>();
		}
		attachmentList.add(attachment);
	}

	public List<AttachmentDTO> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<AttachmentDTO>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<AttachmentDTO> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addSurveyQuestionList(SurveyQuestionAnswerDTO surveyQuestionAnswer) {
		if (surveyQuestionAnswersList == null) {
			surveyQuestionAnswersList = new ArrayList<SurveyQuestionAnswerDTO>();
		}
		surveyQuestionAnswersList.add(surveyQuestionAnswer);
	}

	public List<DisabilityClaimICD10> getDisabilityClaimICD10List() {
		if (disabilityClaimICD10List == null) {
			disabilityClaimICD10List = new ArrayList<DisabilityClaimICD10>();
		}
		return disabilityClaimICD10List;
	}

	public void addDisabilityClaimICD10(DisabilityClaimICD10 disabilityClaimICD10) {
		if (disabilityClaimICD10List == null) {
			disabilityClaimICD10List = new ArrayList<DisabilityClaimICD10>();
		}
		disabilityClaimICD10List.add(disabilityClaimICD10);
	}

	public void setDisabilityClaimICD10List(List<DisabilityClaimICD10> disabilityClaimICD10List) {
		this.disabilityClaimICD10List = disabilityClaimICD10List;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (existEntity ? 1231 : 1237);
		result = prime * result + ((medication == null) ? 0 : medication.hashCode());
		long temp;
		temp = Double.doubleToLongBits(medicationFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((medicationReason == null) ? 0 : medicationReason.hashCode());
		result = prime * result + ((receivedDate == null) ? 0 : receivedDate.hashCode());
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
		MedicationClaimDTO other = (MedicationClaimDTO) obj;
		if (existEntity != other.existEntity)
			return false;
		if (medication == null) {
			if (other.medication != null)
				return false;
		} else if (!medication.equals(other.medication))
			return false;
		if (Double.doubleToLongBits(medicationFee) != Double.doubleToLongBits(other.medicationFee))
			return false;
		if (medicationReason == null) {
			if (other.medicationReason != null)
				return false;
		} else if (!medicationReason.equals(other.medicationReason))
			return false;
		if (receivedDate == null) {
			if (other.receivedDate != null)
				return false;
		} else if (!receivedDate.equals(other.receivedDate))
			return false;
		return true;
	}

}

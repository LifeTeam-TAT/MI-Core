package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.medical.claim.DeathClaimICD10;
import org.ace.insurance.medical.claim.DeathClaimType;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;

public class DeathClaimDTO extends MedicalClaimDTO {
	private String deathPlace;
	private String otherPlace;
	private Date deathDate;
	private List<AttachmentDTO> attachmentList;
	private List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList;
	private boolean existEntity;
	private double deathClaimAmount;
	private List<DeathClaimICD10> deathClaimICD10List;
	private DeathClaimType deathClaimType;

	public DeathClaimDTO(MedicalClaimDTO medicalClaimDTO) {
	}

	public DeathClaimDTO() {
	}

	public DeathClaimDTO(DeathClaimDTO deathClaimDTO) {
		super();
		this.deathPlace = deathClaimDTO.getDeathPlace();
		this.otherPlace = deathClaimDTO.getOtherPlace();
		this.deathDate = deathClaimDTO.getDeathDate();
		if (deathClaimDTO.getDeathClaimICD10List() != null) {
			this.deathClaimICD10List = deathClaimDTO.getDeathClaimICD10List();
		}
		this.attachmentList = deathClaimDTO.getAttachmentList();
		this.surveyQuestionAnswersList = deathClaimDTO.getSurveyQuestionAnswersList();
		this.existEntity = deathClaimDTO.isExistEntity();
		this.deathClaimAmount = deathClaimDTO.getDeathClaimAmount();
	}

	public DeathClaimType getDeathClaimType() {
		return deathClaimType;
	}

	public void setDeathClaimType(DeathClaimType deathClaimType) {
		this.deathClaimType = deathClaimType;
	}

	public String getDeathPlace() {
		return deathPlace;
	}

	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}

	public String getOtherPlace() {
		return otherPlace;
	}

	public void setOtherPlace(String otherPlace) {
		this.otherPlace = otherPlace;
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

	public boolean isExistEntity() {
		return existEntity;
	}

	public void setExistEntity(boolean existEntity) {
		this.existEntity = existEntity;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public List<DeathClaimICD10> getDeathClaimICD10List() {

		if (deathClaimICD10List == null) {
			deathClaimICD10List = new ArrayList<DeathClaimICD10>();
		}

		return deathClaimICD10List;
	}

	public void addDeathClaimICD10(DeathClaimICD10 deathClaimICD10) {
		if (deathClaimICD10List == null) {
			deathClaimICD10List = new ArrayList<DeathClaimICD10>();
		}
		deathClaimICD10List.add(deathClaimICD10);
	}

	public void setDeathClaimICD10List(List<DeathClaimICD10> deathClaimICD10List) {
		this.deathClaimICD10List = deathClaimICD10List;
	}

	public double getDeathClaimAmount() {
		return deathClaimAmount;
	}

	public void setDeathClaimAmount(double deathClaimAmount) {
		this.deathClaimAmount = deathClaimAmount;
	}

	public void addAttachment(AttachmentDTO attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<AttachmentDTO>();
		}
		attachmentList.add(attachment);
	}

	public List<SurveyQuestionAnswerDTO> getSurveyQuestionAnswersList() {
		return surveyQuestionAnswersList;
	}

	public void setSurveyQuestionAnswersList(List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList) {
		this.surveyQuestionAnswersList = surveyQuestionAnswersList;
	}

	public void addSurveyQuestionList(SurveyQuestionAnswerDTO surveyQuestionAnswer) {
		if (surveyQuestionAnswersList == null) {
			surveyQuestionAnswersList = new ArrayList<SurveyQuestionAnswerDTO>();
		}
		surveyQuestionAnswersList.add(surveyQuestionAnswer);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(deathClaimAmount);
		result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result + ((deathPlace == null) ? 0 : deathPlace.hashCode());
		result = prime * result + (existEntity ? 1231 : 1237);
		result = prime * result + ((otherPlace == null) ? 0 : otherPlace.hashCode());
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
		DeathClaimDTO other = (DeathClaimDTO) obj;
		if (Double.doubleToLongBits(deathClaimAmount) != Double.doubleToLongBits(other.deathClaimAmount))
			return false;
		if (deathDate == null) {
			if (other.deathDate != null)
				return false;
		} else if (!deathDate.equals(other.deathDate))
			return false;
		if (deathPlace == null) {
			if (other.deathPlace != null)
				return false;
		} else if (!deathPlace.equals(other.deathPlace))
			return false;
		if (existEntity != other.existEntity)
			return false;
		if (otherPlace == null) {
			if (other.otherPlace != null)
				return false;
		} else if (!otherPlace.equals(other.otherPlace))
			return false;
		return true;
	}
}

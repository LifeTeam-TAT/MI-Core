package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.medical.claim.OperationClaimICD10;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.system.common.operation.Operation;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;

public class OperationClaimDTO extends MedicalClaimDTO {
	private Date operationDate;
	private double operationFee;
	private Operation operation;
	private ICD10 operationReason;
	private String operationRemark;
	private List<OperationClaimICD10> operationClaimICD10List;
	private List<AttachmentDTO> attachmentList;
	private List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList;
	private boolean existEntity;

	public OperationClaimDTO() {
	}

	public OperationClaimDTO(OperationClaimDTO operationClaimDTO) {
		super();
		this.operationDate = operationClaimDTO.getOperationDate();
		this.operationFee = operationClaimDTO.getOperationFee();
		this.operation = operationClaimDTO.getOperation();
		this.operationReason = operationClaimDTO.getOperationReason();
		this.operationRemark = operationClaimDTO.getOperationRemark();
		this.attachmentList = operationClaimDTO.getAttachmentList();
		this.surveyQuestionAnswersList = operationClaimDTO.getSurveyQuestionAnswersList();
		this.existEntity = operationClaimDTO.isExistEntity();
	}

	public List<SurveyQuestionAnswerDTO> getSurveyQuestionAnswersList() {
		return surveyQuestionAnswersList;
	}

	public void setSurveyQuestionAnswersList(List<SurveyQuestionAnswerDTO> surveyQuestionAnswersList) {
		this.surveyQuestionAnswersList = surveyQuestionAnswersList;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public double getOperationFee() {
		return operationFee;
	}

	public Operation getOperation() {
		return operation;
	}

	public ICD10 getOperationReason() {
		return operationReason;
	}

	public String getOperationRemark() {
		return operationRemark;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public void setOperationReason(ICD10 operationReason) {
		this.operationReason = operationReason;
	}

	public void setOperationRemark(String operationRemark) {
		this.operationRemark = operationRemark;
	}

	public boolean isExistEntity() {
		return existEntity;
	}

	public void setExistEntity(boolean existEntity) {
		this.existEntity = existEntity;
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

	public List<OperationClaimICD10> getOperationClaimICD10List() {
		if (operationClaimICD10List == null) {
			operationClaimICD10List = new ArrayList<OperationClaimICD10>();
		}
		return operationClaimICD10List;
	}

	public void setOperationClaimICD10List(List<OperationClaimICD10> operationClaimICD10List) {
		this.operationClaimICD10List = operationClaimICD10List;
	}

	public void addOperationClaimICD10(OperationClaimICD10 operationClaimICD10) {
		if (operationClaimICD10List == null) {
			operationClaimICD10List = new ArrayList<OperationClaimICD10>();
		}
		operationClaimICD10List.add(operationClaimICD10);
	}

	public void setHospitalizedClaimICD10List(List<OperationClaimICD10> operationClaimICD10List) {
		this.operationClaimICD10List = operationClaimICD10List;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (existEntity ? 1231 : 1237);
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((operationDate == null) ? 0 : operationDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(operationFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((operationReason == null) ? 0 : operationReason.hashCode());
		result = prime * result + ((operationRemark == null) ? 0 : operationRemark.hashCode());
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
		OperationClaimDTO other = (OperationClaimDTO) obj;
		if (existEntity != other.existEntity)
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (operationDate == null) {
			if (other.operationDate != null)
				return false;
		} else if (!operationDate.equals(other.operationDate))
			return false;
		if (Double.doubleToLongBits(operationFee) != Double.doubleToLongBits(other.operationFee))
			return false;
		if (operationReason == null) {
			if (other.operationReason != null)
				return false;
		} else if (!operationReason.equals(other.operationReason))
			return false;
		if (operationRemark == null) {
			if (other.operationRemark != null)
				return false;
		} else if (!operationRemark.equals(other.operationRemark))
			return false;
		return true;
	}

}

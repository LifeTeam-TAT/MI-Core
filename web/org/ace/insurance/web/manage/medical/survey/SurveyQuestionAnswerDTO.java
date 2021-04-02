package org.ace.insurance.web.manage.medical.survey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.medical.claim.ClaimType;
import org.ace.insurance.medical.claim.ShowSurveyQuestionAnswerDTO;
import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.medical.proposal.SurveyType;
import org.ace.insurance.medical.surveyquestion.InputType;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.ResourceQuestion;
import org.ace.insurance.web.common.CommonDTO;
import org.ace.insurance.web.manage.medical.claim.MedicalClaimSurveyDTO;

public class SurveyQuestionAnswerDTO extends CommonDTO implements Comparable {
	private boolean option;
	private boolean deleteFlag;
	private int priority;
	private String questionId;
	private String description;
	private String frontLabel;
	private String behindLabel;
	private String tureLabel;
	private String falseLabel;
	private InputType inputType;
	private ProductProcess productProcess;
	private List<ResourceQuestionAnswerDTO> resourceQuestionList;
	private MedicalSurveyDTO medicalSurveyDTO;
	private MedicalClaimSurveyDTO medicalClaimSurveyDTO;
	private SurveyType surveyType;
	private ClaimType claimType;

	// Form Data
	private Date answerDate;
	private ResourceQuestionAnswerDTO selectedResourceQAnsDTO;
	private List<ResourceQuestionAnswerDTO> selectedResourceQAnsDTOList;
	private boolean tureLabelValue;

	// For Answer Showing
	private String answer;

	private ShowSurveyQuestionAnswerDTO showSurveyQuestionAnswerDTO;

	public SurveyQuestionAnswerDTO() {
		super();
	}

	public SurveyQuestionAnswerDTO(ProductProcessQuestionLink ppQLink) {
		this.option = ppQLink.isOption();
		this.priority = ppQLink.getPriority();
		this.deleteFlag = ppQLink.getSurveyQuestion().isDeleteFlag();
		this.description = ppQLink.getSurveyQuestion().getDescription();
		this.frontLabel = ppQLink.getSurveyQuestion().getFrontLabel();
		this.behindLabel = ppQLink.getSurveyQuestion().getBehindLabel();
		this.tureLabel = ppQLink.getSurveyQuestion().getTureLabel();
		this.falseLabel = ppQLink.getSurveyQuestion().getFalseLabel();
		this.inputType = ppQLink.getSurveyQuestion().getInputType();
		this.productProcess = ppQLink.getProductProcess();
		if (inputType.equals(InputType.TEXT) || inputType.equals(InputType.NUMBER) || inputType.equals(InputType.DATE)) {
			ResourceQuestionAnswerDTO answer = new ResourceQuestionAnswerDTO();
			addResourceQuestionList(answer);
		}
		if (inputType.equals(InputType.BOOLEAN)) {
			ResourceQuestionAnswerDTO answerTrue = new ResourceQuestionAnswerDTO();
			answerTrue.setName(ppQLink.getSurveyQuestion().getTureLabel());
			answerTrue.setValue(0);
			ResourceQuestionAnswerDTO answerFalse = new ResourceQuestionAnswerDTO();
			answerFalse.setName(ppQLink.getSurveyQuestion().getFalseLabel());
			answerFalse.setValue(1);
			addResourceQuestionList(answerTrue);
			addResourceQuestionList(answerFalse);
		}
		for (ResourceQuestion resourceQuestion : ppQLink.getSurveyQuestion().getResourceQuestionList()) {
			ResourceQuestionAnswerDTO rqa = new ResourceQuestionAnswerDTO(resourceQuestion.getName());
			addResourceQuestionList(rqa);
		}
	}

	public boolean isOption() {
		return option;
	}

	public void setOption(boolean option) {
		this.option = option;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrontLabel() {
		return frontLabel;
	}

	public void setFrontLabel(String frontLabel) {
		this.frontLabel = frontLabel;
	}

	public String getBehindLabel() {
		return behindLabel;
	}

	public void setBehindLabel(String behindLabel) {
		this.behindLabel = behindLabel;
	}

	public String getTureLabel() {
		return tureLabel;
	}

	public void setTureLabel(String tureLabel) {
		this.tureLabel = tureLabel;
	}

	public String getFalseLabel() {
		return falseLabel;
	}

	public void setFalseLabel(String falseLabel) {
		this.falseLabel = falseLabel;
	}

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public ProductProcess getProductProcess() {
		return productProcess;
	}

	public void setProductProcess(ProductProcess productProcess) {
		this.productProcess = productProcess;
	}

	public List<ResourceQuestionAnswerDTO> getResourceQuestionList() {
		if (resourceQuestionList == null) {
			resourceQuestionList = new ArrayList<ResourceQuestionAnswerDTO>();
		}
		return resourceQuestionList;
	}

	public void setResourceQuestionList(List<ResourceQuestionAnswerDTO> resourceQuestionList) {
		this.resourceQuestionList = resourceQuestionList;
	}

	public void addResourceQuestionList(ResourceQuestionAnswerDTO resourceAnswerDTO) {
		resourceAnswerDTO.setSurveyQuestionAnswerDTO(this);
		getResourceQuestionList().add(resourceAnswerDTO);
	}

	public MedicalSurveyDTO getMedicalSurveyDTO() {
		return medicalSurveyDTO;
	}

	public void setMedicalSurveyDTO(MedicalSurveyDTO medicalSurveyDTO) {
		this.medicalSurveyDTO = medicalSurveyDTO;
	}

	public SurveyType getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(SurveyType surveyType) {
		this.surveyType = surveyType;
	}

	public List<ResourceQuestionAnswerDTO> getSelectedResourceQAnsDTOList() {
		if (selectedResourceQAnsDTOList == null) {
			selectedResourceQAnsDTOList = new ArrayList<ResourceQuestionAnswerDTO>();
		}
		return selectedResourceQAnsDTOList;
	}

	public void setSelectedResourceQAnsDTOList(List<ResourceQuestionAnswerDTO> selectedResourceQAnsDTOList) {
		this.selectedResourceQAnsDTOList = selectedResourceQAnsDTOList;
	}

	public ResourceQuestionAnswerDTO getSelectedResourceQAnsDTO() {
		return selectedResourceQAnsDTO;
	}

	public void setSelectedResourceQAnsDTO(ResourceQuestionAnswerDTO selectedResourceQAnsDTO) {
		this.selectedResourceQAnsDTO = selectedResourceQAnsDTO;
	}

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	public boolean isTureLabelValue() {
		return tureLabelValue;
	}

	public void setTureLabelValue(boolean tureLabelValue) {
		this.tureLabelValue = tureLabelValue;
	}

	public ClaimType getClaimType() {
		return claimType;
	}

	public void setClaimType(ClaimType claimType) {
		this.claimType = claimType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ShowSurveyQuestionAnswerDTO getShowSurveyQuestionAnswerDTO() {
		return showSurveyQuestionAnswerDTO;
	}

	public void setShowSurveyQuestionAnswerDTO(ShowSurveyQuestionAnswerDTO showSQADTO) {
		this.showSurveyQuestionAnswerDTO = showSQADTO;
	}

	public MedicalClaimSurveyDTO getMedicalClaimSurveyDTO() {
		return medicalClaimSurveyDTO;
	}

	public void setMedicalClaimSurveyDTO(MedicalClaimSurveyDTO medicalClaimSurveyDTO) {
		this.medicalClaimSurveyDTO = medicalClaimSurveyDTO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((answerDate == null) ? 0 : answerDate.hashCode());
		result = prime * result + ((behindLabel == null) ? 0 : behindLabel.hashCode());
		result = prime * result + ((claimType == null) ? 0 : claimType.hashCode());
		result = prime * result + (deleteFlag ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((falseLabel == null) ? 0 : falseLabel.hashCode());
		result = prime * result + ((frontLabel == null) ? 0 : frontLabel.hashCode());
		result = prime * result + ((inputType == null) ? 0 : inputType.hashCode());
		result = prime * result + ((medicalSurveyDTO == null) ? 0 : medicalSurveyDTO.hashCode());
		result = prime * result + (option ? 1231 : 1237);
		result = prime * result + priority;
		result = prime * result + ((productProcess == null) ? 0 : productProcess.hashCode());
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((selectedResourceQAnsDTO == null) ? 0 : selectedResourceQAnsDTO.hashCode());
		result = prime * result + ((showSurveyQuestionAnswerDTO == null) ? 0 : showSurveyQuestionAnswerDTO.hashCode());
		result = prime * result + ((surveyType == null) ? 0 : surveyType.hashCode());
		result = prime * result + ((tureLabel == null) ? 0 : tureLabel.hashCode());
		result = prime * result + (tureLabelValue ? 1231 : 1237);
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
		SurveyQuestionAnswerDTO other = (SurveyQuestionAnswerDTO) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (answerDate == null) {
			if (other.answerDate != null)
				return false;
		} else if (!answerDate.equals(other.answerDate))
			return false;
		if (behindLabel == null) {
			if (other.behindLabel != null)
				return false;
		} else if (!behindLabel.equals(other.behindLabel))
			return false;
		if (claimType != other.claimType)
			return false;
		if (deleteFlag != other.deleteFlag)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (falseLabel == null) {
			if (other.falseLabel != null)
				return false;
		} else if (!falseLabel.equals(other.falseLabel))
			return false;
		if (frontLabel == null) {
			if (other.frontLabel != null)
				return false;
		} else if (!frontLabel.equals(other.frontLabel))
			return false;
		if (inputType != other.inputType)
			return false;
		if (medicalSurveyDTO == null) {
			if (other.medicalSurveyDTO != null)
				return false;
		} else if (!medicalSurveyDTO.equals(other.medicalSurveyDTO))
			return false;
		if (option != other.option)
			return false;
		if (priority != other.priority)
			return false;
		if (productProcess == null) {
			if (other.productProcess != null)
				return false;
		} else if (!productProcess.equals(other.productProcess))
			return false;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (selectedResourceQAnsDTO == null) {
			if (other.selectedResourceQAnsDTO != null)
				return false;
		} else if (!selectedResourceQAnsDTO.equals(other.selectedResourceQAnsDTO))
			return false;
		if (showSurveyQuestionAnswerDTO == null) {
			if (other.showSurveyQuestionAnswerDTO != null)
				return false;
		} else if (!showSurveyQuestionAnswerDTO.equals(other.showSurveyQuestionAnswerDTO))
			return false;
		if (surveyType != other.surveyType)
			return false;
		if (tureLabel == null) {
			if (other.tureLabel != null)
				return false;
		} else if (!tureLabel.equals(other.tureLabel))
			return false;
		if (tureLabelValue != other.tureLabelValue)
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		SurveyQuestionAnswerDTO otherQueAnsDTO = (SurveyQuestionAnswerDTO) o;
		return this.priority - otherQueAnsDTO.priority;
	}

}

package org.ace.insurance.web.manage.medical.survey;

import org.ace.insurance.medical.surveyAnswer.ResourceQuestionAnswer;
import org.ace.insurance.web.common.CommonDTO;

public class ResourceQuestionAnswerDTO extends CommonDTO {
	private boolean existsEntity;
	private int value;
	private String result;
	private int version;
	private String name;
	private String id;
	// This is not include equal and hashcode method.
	private SurveyQuestionAnswerDTO surveyQuestionAnswerDTO;

	public ResourceQuestionAnswerDTO() {
	}

	public ResourceQuestionAnswerDTO(String name) {
		this.name = name;
	}

	public ResourceQuestionAnswerDTO(ResourceQuestionAnswer rqanswer) {
		this.value = rqanswer.getValue();
		this.result = rqanswer.getResult();
		this.name = rqanswer.getName();
	}

	public String getId() {
		return id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SurveyQuestionAnswerDTO getSurveyQuestionAnswerDTO() {
		return surveyQuestionAnswerDTO;
	}

	public void setSurveyQuestionAnswerDTO(SurveyQuestionAnswerDTO surveyQuestionAnswerDTO) {
		this.surveyQuestionAnswerDTO = surveyQuestionAnswerDTO;
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

	public String getResult() {
		if (result == null || result.isEmpty()) {
			result = "";
		}
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isExistsEntity() {
		return existsEntity;
	}

	public void setExistsEntity(boolean existsEntity) {
		this.existsEntity = existsEntity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (existsEntity ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + value;
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
		ResourceQuestionAnswerDTO other = (ResourceQuestionAnswerDTO) obj;
		if (existsEntity != other.existsEntity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (value != other.value)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}

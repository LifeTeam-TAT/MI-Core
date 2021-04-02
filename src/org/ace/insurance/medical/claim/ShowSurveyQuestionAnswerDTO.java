package org.ace.insurance.medical.claim;

public class ShowSurveyQuestionAnswerDTO {
	private int priority;
	private String description;
	private String result;
	
	public ShowSurveyQuestionAnswerDTO(int priority, String description,
			String result) {
		super();
		this.priority = priority;
		this.description = description;
		this.result = result;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
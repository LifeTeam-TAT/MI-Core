package org.ace.insurance.web.manage.surveyquestion.factory;

import org.ace.insurance.medical.surveyquestion.ResourceQuestion;
import org.ace.insurance.web.manage.surveyquestion.ResourceQuestionDTO;

public class ResourceQuestionFactory {
	public static ResourceQuestion getResourceQuestion(ResourceQuestionDTO resourceQuestionDTO) {
		ResourceQuestion resourceQuestion = new ResourceQuestion();
		if (resourceQuestionDTO.isExitingEntity()) {
			resourceQuestion.setId(resourceQuestionDTO.getId());
			resourceQuestion.setVersion(resourceQuestionDTO.getVersion());
		}
		resourceQuestion.setName(resourceQuestionDTO.getName());
		if (resourceQuestionDTO.getRecorder() != null) {
			resourceQuestion.setRecorder(resourceQuestionDTO.getRecorder());
		}

		return resourceQuestion;
	}

	public static ResourceQuestionDTO getResourceQuestionDTO(ResourceQuestion resourceQuestion) {
		ResourceQuestionDTO resourceQuestionDTO = new ResourceQuestionDTO();
		if (resourceQuestion.getId() != null) {
			resourceQuestionDTO.setExitingEntity(true);
			resourceQuestionDTO.setId(resourceQuestion.getId());
			resourceQuestionDTO.setVersion(resourceQuestion.getVersion());
		}
		resourceQuestionDTO.setName(resourceQuestion.getName());
		if (resourceQuestion.getRecorder() != null) {
			resourceQuestionDTO.setRecorder(resourceQuestion.getRecorder());
		}
		return resourceQuestionDTO;
	}
}

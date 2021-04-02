package org.ace.insurance.web.manage.medical.survey.factory;

import org.ace.insurance.medical.surveyAnswer.ResourceQuestionAnswer;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.web.manage.medical.survey.ResourceQuestionAnswerDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;

public class SurveyQuestionAnswerDTOFactroy {
	public static SurveyQuestionAnswerDTO getSurveyQuestionAnswerDTO(SurveyQuestionAnswer surveyQuestionAnswer) {
		SurveyQuestionAnswerDTO surveyQuestionAnswerDTO = new SurveyQuestionAnswerDTO();
		if (surveyQuestionAnswer.getId() != null && (!surveyQuestionAnswer.getId().isEmpty())) {
			surveyQuestionAnswerDTO.setId(surveyQuestionAnswer.getId());
			surveyQuestionAnswerDTO.setVersion(surveyQuestionAnswer.getVersion());
			surveyQuestionAnswerDTO.setExistsEntity(true);
		}
		surveyQuestionAnswerDTO.setBehindLabel(surveyQuestionAnswer.getBehindLabel());
		surveyQuestionAnswerDTO.setDeleteFlag(surveyQuestionAnswer.isDeleteFlag());
		surveyQuestionAnswerDTO.setDescription(surveyQuestionAnswer.getDescription());
		surveyQuestionAnswerDTO.setFalseLabel(surveyQuestionAnswer.getFalseLabel());
		surveyQuestionAnswerDTO.setFrontLabel(surveyQuestionAnswer.getFrontLabel());
		surveyQuestionAnswerDTO.setInputType(surveyQuestionAnswer.getInputType());
		surveyQuestionAnswerDTO.setOption(surveyQuestionAnswer.isOption());
		surveyQuestionAnswerDTO.setPriority(surveyQuestionAnswer.getPriority());
		surveyQuestionAnswerDTO.setProductProcess(surveyQuestionAnswer.getProductProcess());
		surveyQuestionAnswerDTO.setQuestionId(surveyQuestionAnswer.getQuestionId());
		surveyQuestionAnswerDTO.setTureLabel(surveyQuestionAnswer.getTureLabel());
		surveyQuestionAnswerDTO.setSurveyType(surveyQuestionAnswer.getSurveyType());
		surveyQuestionAnswerDTO.setClaimType(surveyQuestionAnswer.getClaimType());

		for (ResourceQuestionAnswer rs : surveyQuestionAnswer.getResourceQuestionList()) {
			ResourceQuestionAnswerDTO resourceQuestionAnswerDTO = ResourceQuestionAnswerDTOFactory.getResourceQuestionAnswerDTO(rs);
			surveyQuestionAnswerDTO.addResourceQuestionList(resourceQuestionAnswerDTO);
		}
		if (surveyQuestionAnswer.getRecorder() != null) {
			surveyQuestionAnswerDTO.setRecorder(surveyQuestionAnswer.getRecorder());
		}
		return surveyQuestionAnswerDTO;
	}

	public static SurveyQuestionAnswer getSurveyQuestionAnswer(SurveyQuestionAnswerDTO surveyQuestionAnswerDTO) {
		SurveyQuestionAnswer surveyQuestionAnswer = new SurveyQuestionAnswer();
		if (surveyQuestionAnswerDTO.isExistsEntity()) {
			surveyQuestionAnswer.setId(surveyQuestionAnswerDTO.getId());
			surveyQuestionAnswer.setVersion(surveyQuestionAnswerDTO.getVersion());
		}
		surveyQuestionAnswer.setBehindLabel(surveyQuestionAnswerDTO.getBehindLabel());
		surveyQuestionAnswer.setDeleteFlag(surveyQuestionAnswerDTO.isDeleteFlag());
		surveyQuestionAnswer.setDescription(surveyQuestionAnswerDTO.getDescription());
		surveyQuestionAnswer.setFalseLabel(surveyQuestionAnswerDTO.getFalseLabel());
		surveyQuestionAnswer.setFrontLabel(surveyQuestionAnswerDTO.getFrontLabel());
		surveyQuestionAnswer.setInputType(surveyQuestionAnswerDTO.getInputType());
		surveyQuestionAnswer.setOption(surveyQuestionAnswerDTO.isOption());
		surveyQuestionAnswer.setPriority(surveyQuestionAnswerDTO.getPriority());
		surveyQuestionAnswer.setProductProcess(surveyQuestionAnswerDTO.getProductProcess());
		surveyQuestionAnswer.setQuestionId(surveyQuestionAnswerDTO.getQuestionId());
		surveyQuestionAnswer.setTureLabel(surveyQuestionAnswerDTO.getTureLabel());
		surveyQuestionAnswer.setSurveyType(surveyQuestionAnswerDTO.getSurveyType());
		surveyQuestionAnswer.setClaimType(surveyQuestionAnswerDTO.getClaimType());

		for (ResourceQuestionAnswerDTO rsDTO : surveyQuestionAnswerDTO.getResourceQuestionList()) {
			ResourceQuestionAnswer resourceQuestionAnswer = ResourceQuestionAnswerDTOFactory.getResourceQuestionAnswer(rsDTO);
			surveyQuestionAnswer.addResourceQuestionList(resourceQuestionAnswer);
		}
		if (surveyQuestionAnswerDTO.getRecorder() != null) {
			surveyQuestionAnswer.setRecorder(surveyQuestionAnswerDTO.getRecorder());
		}
		return surveyQuestionAnswer;
	}

}

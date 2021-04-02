package org.ace.insurance.web.manage.surveyquestion.factory;

import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.ResourceQuestion;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.web.manage.surveyquestion.ProductProcessQuestionLinkDTO;
import org.ace.insurance.web.manage.surveyquestion.ResourceQuestionDTO;
import org.ace.insurance.web.manage.surveyquestion.SurveyQuestionDTO;

public class SurveyQuestionFactory {

	public static SurveyQuestion getSurveyQuestion(SurveyQuestionDTO surveyQuestionDTO) {
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		if (surveyQuestionDTO.isExistsEntity()) {
			surveyQuestion.setId(surveyQuestionDTO.getId());
			surveyQuestion.setVersion(surveyQuestionDTO.getVersion());
			switch (surveyQuestionDTO.getInputType()) {
				case BOOLEAN:
					surveyQuestionDTO.setFrontLabel(null);
					surveyQuestionDTO.setBehindLabel(null);
					surveyQuestionDTO.setResourceQuestionList(null);
					break;
				case TEXT:
				case NUMBER:
					surveyQuestionDTO.setTureLabel(null);
					surveyQuestionDTO.setFalseLabel(null);
					surveyQuestionDTO.setResourceQuestionList(null);
					break;
				case DATE:
					surveyQuestionDTO.setFrontLabel(null);
					surveyQuestionDTO.setBehindLabel(null);
					surveyQuestionDTO.setTureLabel(null);
					surveyQuestionDTO.setFalseLabel(null);
					surveyQuestionDTO.setResourceQuestionList(null);
					break;
				case SELECT_ONE_RADIO:
				case SELECT_ONE_MENU:
				case SELECT_MANY_CHECKBOX:
				case SELECT_MANY_MENU:
					surveyQuestionDTO.setFrontLabel(null);
					surveyQuestionDTO.setBehindLabel(null);
					surveyQuestionDTO.setTureLabel(null);
					surveyQuestionDTO.setFalseLabel(null);
					break;
				default:
					break;
			}
		}
		surveyQuestion.setQuestionNo(surveyQuestionDTO.getQuestionNo());
		surveyQuestion.setDeleteFlag(surveyQuestionDTO.isDeleteFlag());
		surveyQuestion.setDescription(surveyQuestionDTO.getDescription());
		surveyQuestion.setFrontLabel(surveyQuestionDTO.getFrontLabel());
		surveyQuestion.setBehindLabel(surveyQuestionDTO.getBehindLabel());
		surveyQuestion.setTureLabel(surveyQuestionDTO.getTureLabel());
		surveyQuestion.setFalseLabel(surveyQuestionDTO.getFalseLabel());
		surveyQuestion.setInputType(surveyQuestionDTO.getInputType());
		for (ProductProcessQuestionLinkDTO linkDTO : surveyQuestionDTO.getQuestionLinks()) {
			ProductProcessQuestionLink questionLink = ProductProcessQuestionLinkFactory.getProductProcessQuestionLink(linkDTO);
			surveyQuestion.addProductProcessQuestionLink(questionLink);
		}
		for (ResourceQuestionDTO resourceQuestionDTO : surveyQuestionDTO.getResourceQuestionList()) {
			ResourceQuestion resourceQuestion = ResourceQuestionFactory.getResourceQuestion(resourceQuestionDTO);
			surveyQuestion.addResourceQuestion(resourceQuestion);
		}
		if (surveyQuestionDTO.getRecorder() != null) {
			surveyQuestion.setRecorder(surveyQuestionDTO.getRecorder());
		}
		return surveyQuestion;
	}

	public static SurveyQuestionDTO getSurveyQuestionDTO(SurveyQuestion surveyQuestion) {
		SurveyQuestionDTO surveyQuestionDTO = new SurveyQuestionDTO();
		if (surveyQuestion.getId() != null) {
			surveyQuestionDTO.setExistsEntity(true);
			surveyQuestionDTO.setId(surveyQuestion.getId());
			surveyQuestionDTO.setVersion(surveyQuestion.getVersion());
		}
		surveyQuestionDTO.setId(surveyQuestion.getId());
		surveyQuestionDTO.setDeleteFlag(surveyQuestion.isDeleteFlag());
		surveyQuestionDTO.setDescription(surveyQuestion.getDescription());
		surveyQuestionDTO.setFrontLabel(surveyQuestion.getFrontLabel());
		surveyQuestionDTO.setBehindLabel(surveyQuestion.getBehindLabel());
		surveyQuestionDTO.setTureLabel(surveyQuestion.getTureLabel());
		surveyQuestionDTO.setFalseLabel(surveyQuestion.getFalseLabel());
		surveyQuestionDTO.setInputType(surveyQuestion.getInputType());
		surveyQuestionDTO.setVersion(surveyQuestion.getVersion());
		surveyQuestionDTO.setQuestionNo(surveyQuestion.getQuestionNo());
		for (ProductProcessQuestionLink link : surveyQuestion.getProductProcessQuestionLinkList()) {
			ProductProcessQuestionLinkDTO questionLinkDTO = ProductProcessQuestionLinkFactory.getProductProcessQuestionLinkDTO(link);
			surveyQuestionDTO.addProductProcessQuestionLink(questionLinkDTO);
		}
		for (ResourceQuestion resourceQuestion : surveyQuestion.getResourceQuestionList()) {
			ResourceQuestionDTO resourceQuestionDTO = ResourceQuestionFactory.getResourceQuestionDTO(resourceQuestion);
			surveyQuestionDTO.addResourceQuestion(resourceQuestionDTO);
		}
		if (surveyQuestion.getRecorder() != null) {
			surveyQuestionDTO.setRecorder(surveyQuestion.getRecorder());
		}
		return surveyQuestionDTO;
	}

}

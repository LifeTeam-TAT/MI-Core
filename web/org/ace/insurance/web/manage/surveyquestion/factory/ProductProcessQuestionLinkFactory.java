package org.ace.insurance.web.manage.surveyquestion.factory;

import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.web.manage.surveyquestion.ProductProcessQuestionLinkDTO;

public class ProductProcessQuestionLinkFactory {
	public static ProductProcessQuestionLink getProductProcessQuestionLink(ProductProcessQuestionLinkDTO questionLinkDTO) {
		ProductProcessQuestionLink questionLink = new ProductProcessQuestionLink();
		if (questionLinkDTO.isExitingEntity()) {
			questionLink.setId(questionLinkDTO.getId());
			questionLink.setVersion(questionLinkDTO.getVersion());
		}
		questionLink.setOption(questionLinkDTO.isOption());
		questionLink.setPriority(questionLinkDTO.getPriority());
		questionLink.setProductProcess(questionLinkDTO.getProductProcess());
		questionLink.setSurveyQuestion(questionLinkDTO.getSurveyQuestion());
		if (questionLinkDTO.getRecorder() != null) {
			questionLink.setRecorder(questionLinkDTO.getRecorder());
		}
		return questionLink;

	}

	public static ProductProcessQuestionLinkDTO getProductProcessQuestionLinkDTO(ProductProcessQuestionLink questionLink) {
		ProductProcessQuestionLinkDTO questionLinkDTO = new ProductProcessQuestionLinkDTO();
		if (questionLink.getId() != null) {
			questionLinkDTO.setExitingEntity(true);
			questionLinkDTO.setId(questionLink.getId());
			questionLinkDTO.setVersion(questionLink.getVersion());
		}
		questionLinkDTO.setOption(questionLink.isOption());
		questionLinkDTO.setPriority(questionLink.getPriority());
		questionLinkDTO.setProductProcess(questionLink.getProductProcess());
		questionLinkDTO.setSurveyQuestion(questionLink.getSurveyQuestion());
		if (questionLink.getRecorder() != null) {
			questionLinkDTO.setRecorder(questionLink.getRecorder());
		}
		return questionLinkDTO;

	}
}

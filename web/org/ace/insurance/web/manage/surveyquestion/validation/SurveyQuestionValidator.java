package org.ace.insurance.web.manage.surveyquestion.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.surveyquestion.SurveyQuestionDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "SurveyQuestionValidator")
public class SurveyQuestionValidator implements DTOValidator<SurveyQuestionDTO> {

	@Override
	public ValidationResult validate(SurveyQuestionDTO dto) {
		ValidationResult result = new ValidationResult();
		String formID = "surveyQuestionEntryForm";
		switch (dto.getInputType()) {
			case SELECT_MANY_CHECKBOX:
			case SELECT_ONE_MENU:
			case SELECT_MANY_MENU:
				if (dto.getResourceQuestionList() == null || dto.getResourceQuestionList().size() < 1) {
					result.addErrorMessage(formID + ":resourceQuestionList", MessageId.RESOURCE_QUESTION,"one");
				}
				break;
			case SELECT_ONE_RADIO:
				if (dto.getResourceQuestionList() == null || dto.getResourceQuestionList().size() < 2) {
					result.addErrorMessage(formID + ":resourceQuestionList", MessageId.RESOURCE_QUESTION,"two");
				}
				break;
			default:
				break;
		}
		return result;
	}
}

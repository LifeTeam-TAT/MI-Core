package org.ace.insurance.web.manage.medical.survey.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.medical.survey.MedicalSurveyDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "MedicalSurveyValidator")
public class MedicalSurveyValidator implements DTOValidator<MedicalSurveyDTO> {

	@Override
	public ValidationResult validate(MedicalSurveyDTO medicalSurveyDTO) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalSurveyEntryForm";

		if (medicalSurveyDTO.getSurveyDate() != null && medicalSurveyDTO.getSurveyDate().before(medicalSurveyDTO.getMedicalProposalDTO().getSubmittedDate())) {
			result.addErrorMessage(formID + ":surveyDate", MessageId.INVALID_SURVEY_DATE);
		}

		return result;
	}
}

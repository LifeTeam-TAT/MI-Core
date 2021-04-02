package org.ace.insurance.web.manage.medical.claim.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.medical.claim.MedicalClaimProposalDTO;

@ViewScoped
@ManagedBean(name = "MedicalClaimSurveyValidator")
public class MedicalClaimSurveyValidator implements DTOValidator<MedicalClaimProposalDTO> {
	// TODO FIXME PSH Claim Case

	@Override
	public ValidationResult validate(MedicalClaimProposalDTO mClaimProposalDTO) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalClaimSurveyEntryForm";
		// TODO FIXME
		// if (mClaimProposalDTO.getMedicalClaimSurvey().getSurveyDate() != null
		// &&
		// mClaimProposalDTO.getMedicalClaimSurvey().getSurveyDate().before(mClaimProposalDTO.getSubmittedDate()))
		// {
		// result.addErrorMessage(formID + ":surveyDate",
		// MessageId.INVALID_SURVEY_DATE);
		// }

		return result;
	}
}

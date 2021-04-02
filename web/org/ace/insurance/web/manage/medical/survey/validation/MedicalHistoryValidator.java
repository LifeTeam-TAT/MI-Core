package org.ace.insurance.web.manage.medical.survey.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.Utils;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.medical.survey.MedicalHistoryDTO;

@ViewScoped
@ManagedBean(name = "MedicalHistoryValidator")
public class MedicalHistoryValidator implements DTOValidator<MedicalHistoryDTO> {

	@Override
	public ValidationResult validate(MedicalHistoryDTO dto) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalSurveyEntryForm";

		if (dto.getHospital() == null) {
			result.addErrorMessage(formID + ":hospital", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (Utils.isEmpty(dto.getCauseOfHospitalization())) {
			result.addErrorMessage(formID + ":causeOfHospitalization", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (Utils.isEmpty(dto.getResult())) {
			result.addErrorMessage(formID + ":result", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (dto.getIcd10() == null) {
			result.addErrorMessage(formID + ":disease", UIInput.REQUIRED_MESSAGE_ID);
		}
		return result;
	}
}

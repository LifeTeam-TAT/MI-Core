package org.ace.insurance.web.manage.medical.claim.request;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.common.ValidationUtil;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyInsuredPersonBeneficiaryDTO;

@ViewScoped
@ManagedBean(name = "DeathBeneficiaryValidator")
public class DeathBeneficiaryValidator implements DTOValidator<MedicalPolicyInsuredPersonBeneficiaryDTO> {
	public ValidationResult validate(MedicalPolicyInsuredPersonBeneficiaryDTO medicalPolicyInsuredPersonBeneficiaryDTO) {
		ValidationResult result = new ValidationResult();
		String formID = "beneficiaryDeathDialogForm";

		if (medicalPolicyInsuredPersonBeneficiaryDTO.getDeathDate() == null) {
			result.addErrorMessage(formID + ":benDeathDate", UIInput.REQUIRED_MESSAGE_ID);
		}

		if (ValidationUtil.isStringEmpty(medicalPolicyInsuredPersonBeneficiaryDTO.getDeathReason())) {
			result.addErrorMessage(formID + ":benDeathReason", UIInput.REQUIRED_MESSAGE_ID);
		}

		return result;
	}
}

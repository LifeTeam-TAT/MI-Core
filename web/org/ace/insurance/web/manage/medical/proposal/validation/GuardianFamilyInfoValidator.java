package org.ace.insurance.web.manage.medical.proposal.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.FamilyInfo;
import org.ace.insurance.common.IdType;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.common.ValidationUtil;

@ViewScoped
@ManagedBean(name = "GuardianFamilyInfoValidator")
public class GuardianFamilyInfoValidator implements DTOValidator<FamilyInfo> {
	public ValidationResult validate(FamilyInfo familyInfo) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalProposalEntryForm";
		if (ValidationUtil.isStringEmpty(familyInfo.getInitialId())) {
			result.addErrorMessage(formID + ":guardianfamilyInitialId", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (ValidationUtil.isStringEmpty(familyInfo.getName().getFirstName())) {
			result.addErrorMessage(formID + ":GuardianRegFamilyfirstName", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (familyInfo.getDateOfBirth() == null) {
			result.addErrorMessage(formID + ":GuardianRegFamilydob", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (familyInfo.getRelationShip().getName().equals("---------")) {
			result.addErrorMessage(formID + ":GuardianRegfamilyRelationShip", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (!familyInfo.getIdType().equals(IdType.STILL_APPLYING)) {
			if (familyInfo.getIdNo() == null) {
				result.addErrorMessage(formID + ":GuardianRegFamilyidType", UIInput.REQUIRED_MESSAGE_ID);
			}
		}
		if (ValidationUtil.isStringEmpty(familyInfo.getIdNo())) {
			result.addErrorMessage(formID + ":GuardianRegFamilyidNo", UIInput.REQUIRED_MESSAGE_ID);
		}
		return result;
	}
}

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
@ManagedBean(name = "FamilyInfoValidator")
public class FamilyInfoValidator implements DTOValidator<FamilyInfo> {
	public ValidationResult validate(FamilyInfo familyInfo) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalProposalEntryForm";
		if (ValidationUtil.isStringEmpty(familyInfo.getInitialId())) {
			result.addErrorMessage(formID + ":familyInitialId", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (ValidationUtil.isStringEmpty(familyInfo.getName().getFirstName())) {
			result.addErrorMessage(formID + ":customerRegFamilyfirstName", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (familyInfo.getDateOfBirth() == null) {
			result.addErrorMessage(formID + ":customerRegFamilydob", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (familyInfo.getRelationShip().getName().equals("---------")) {
			result.addErrorMessage(formID + ":customerRegfamilyRelationShip", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (!familyInfo.getIdType().equals(IdType.STILL_APPLYING)) {
			if (familyInfo.getIdNo() == null) {
				result.addErrorMessage(formID + ":customerRegFamilyidType", UIInput.REQUIRED_MESSAGE_ID);
			}
		}
		if (ValidationUtil.isStringEmpty(familyInfo.getIdNo())) {
			result.addErrorMessage(formID + ":customerRegFamilyidNo", UIInput.REQUIRED_MESSAGE_ID);
		}
		return result;
	}
}

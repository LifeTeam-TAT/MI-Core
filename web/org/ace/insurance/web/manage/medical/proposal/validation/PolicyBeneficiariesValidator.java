package org.ace.insurance.web.manage.medical.proposal.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.IdType;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.common.ValidationUtil;
import org.ace.insurance.web.common.Validator;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyInsuredPersonBeneficiaryDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "PolicyBeneficiariesValidator")
public class PolicyBeneficiariesValidator implements Validator<MedicalPolicyInsuredPersonBeneficiaryDTO> {

	@Override
	public ValidationResult validate(MedicalPolicyInsuredPersonBeneficiaryDTO dto) {
		ValidationResult result = new ValidationResult();
		String formID = "beneficiaryInfoEntryForm";
		if (ValidationUtil.isStringEmpty(dto.getName().getFirstName())) {
			result.addErrorMessage(formID + ":firstName", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (dto.getDateOfBirth() == null) {
			result.addErrorMessage(formID + ":benificiaryDateOfBirth", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (!dto.getIdType().equals(IdType.STILL_APPLYING)) {
			if (dto.getIdType().equals(IdType.NRCNO)) {
				if (ValidationUtil.isStringEmpty(dto.getIdNo())) {
					result.addErrorMessage(formID + ":BeneRegidNo", UIInput.REQUIRED_MESSAGE_ID);
				} else if ((dto.getStateCode() == null && dto.getTownshipCode() == null) || (dto.getStateCode() == null && dto.getTownshipCode() != null)
						|| (dto.getStateCode() != null && dto.getTownshipCode() == null)) {
					result.addErrorMessage(formID + ":BeneRegidNo", MessageId.NRC_STATE_AND_TOWNSHIP_ERROR);
				} else if (dto.getIdNo().length() != 6 && dto.getStateCode() != null && dto.getTownshipCode() != null) {
					result.addErrorMessage(formID + ":BeneRegidNo", MessageId.NRC_FORMAT_INCORRECT);
				}
			} else {
				dto.setStateCode(null);
				dto.setIdConditionType(null);
				dto.setTownshipCode(null);
				if (ValidationUtil.isStringEmpty(dto.getIdNo())) {
					result.addErrorMessage(formID + ":idNo", UIInput.REQUIRED_MESSAGE_ID);
				}
			}
		} else {
			dto.setStateCode(null);
			dto.setIdConditionType(null);
			dto.setTownshipCode(null);
			dto.setIdNo("");
		}

		if (dto.getRelationship() == null) {
			result.addErrorMessage(formID + ":beneficiaryRelationShip", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (ValidationUtil.isStringEmpty(dto.getContentInfo().getMobile())) {
			result.addErrorMessage(formID + ":beneRegmobile", UIInput.REQUIRED_MESSAGE_ID);
		} else if (!dto.getContentInfo().getMobile().matches("[0-9]+")) {
			result.addErrorMessage(formID + ":beneRegmobile", MessageId.INVALID_NUMBER);
		}
		if (!ValidationUtil.isStringEmpty(dto.getContentInfo().getPhone())) {
			if (!dto.getContentInfo().getMobile().matches("[0-9]+"))
				result.addErrorMessage(formID + ":beneRegphone", MessageId.INVALID_NUMBER);
		}
		if (ValidationUtil.isStringEmpty(dto.getResidentAddress().getResidentAddress())) {
			result.addErrorMessage(formID + ":address", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (dto.getResidentAddress().getTownship() == null || ValidationUtil.isStringEmpty(dto.getResidentAddress().getTownship().getName())) {
			result.addErrorMessage(formID + ":townShip", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (dto.getPercentage() == 0.0) {
			result.addErrorMessage(formID + ":percentage", MessageId.VALUE_IS_REQUIRED);
		} else if (dto.getPercentage() < 1 || dto.getPercentage() > 100) {
			result.addErrorMessage(formID + ":percentage", MessageId.BENEFICIARY_PERCENTAGE);
		}
		return result;
	}

}

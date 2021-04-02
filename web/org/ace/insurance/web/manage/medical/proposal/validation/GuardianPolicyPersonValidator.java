package org.ace.insurance.web.manage.medical.proposal.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.CommonSettingConfig;
import org.ace.insurance.common.Utils;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.common.Validator;
import org.ace.insurance.web.manage.medical.proposal.CustomerDTO;
import org.ace.insurance.web.manage.medical.proposal.PolicyGuardianDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "GuardianPolicyPersonValidator")
public class GuardianPolicyPersonValidator implements Validator<PolicyGuardianDTO> {

	@Override
	public ValidationResult validate(PolicyGuardianDTO dto) {
		CustomerDTO customer = dto.getCustomerDTO();
		ValidationResult result = new ValidationResult();
		String formID = "guradianInfoEntryForm";
		if (Utils.getAgeForNextYear(customer.getDateOfBirth()) < CommonSettingConfig.getMinimumGuardianAge()) {
			result.addErrorMessage(formID + ":guarDateOfBirth", MessageId.MEDICAL_INSURED_PERSON_MINAGE, CommonSettingConfig.getMinimumGuardianAge());
		}
		return result;
	}
}

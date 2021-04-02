package org.ace.insurance.web.manage.medical.proposal.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.CommonSettingConfig;
import org.ace.insurance.common.Utils;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.medical.proposal.CustomerDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProGuardianDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "GuardianPersonValidator")
public class GuardianPersonValidator implements DTOValidator<MedProGuardianDTO> {

	@Override
	public ValidationResult validate(MedProGuardianDTO dto) {
		CustomerDTO customer = dto.getCustomer();
		ValidationResult result = new ValidationResult();
		String formID = "guradianInfoEntryForm";
		if (Utils.getAgeForNextYear(customer.getDateOfBirth()) < CommonSettingConfig.getMinimumGuardianAge()) {
			result.addErrorMessage(formID + ":guarDateOfBirth", MessageId.MEDICAL_INSURED_PERSON_MINAGE, CommonSettingConfig.getMinimumGuardianAge());
		}
		return result;
	}

}

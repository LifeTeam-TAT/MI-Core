package org.ace.insurance.web.manage.medical.proposal.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuDTO;

@ViewScoped
@ManagedBean(name = "GuardianValidator")
public class GuardianValidator implements DTOValidator<MedProInsuDTO> {
	@Override
	public ValidationResult validate(MedProInsuDTO dto) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalProposalEntryForm";
		return result;
	}
}

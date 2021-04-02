package org.ace.insurance.web.manage.medical.proposal.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.CommonSettingConfig;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.medical.proposal.MedProDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ProposalValidator")
public class ProposalValidator implements DTOValidator<MedProDTO> {
	public ValidationResult validate(MedProDTO dto) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalProposalEntryForm";
 
		return result;
	}
}

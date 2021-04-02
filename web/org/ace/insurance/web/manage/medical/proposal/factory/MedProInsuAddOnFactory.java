package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.medical.proposal.MedicalKeyFactorValue;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAddOn;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuAddOnDTO;

public class MedProInsuAddOnFactory {

	public static MedicalProposalInsuredPersonAddOn getMedicalProposalInsuredPersonAddOn(MedProInsuAddOnDTO dto) {
		MedicalProposalInsuredPersonAddOn insuredPersonAddOn = new MedicalProposalInsuredPersonAddOn();
		if (dto.isExistsEntity()) {
			insuredPersonAddOn.setId(dto.getId());
			insuredPersonAddOn.setVersion(dto.getVersion());
		}
		insuredPersonAddOn.setPremium(dto.getPremium());
		insuredPersonAddOn.setAddOn(dto.getAddOn());
		insuredPersonAddOn.setUnit(dto.getUnit());
		insuredPersonAddOn.setSumInsured(dto.getSumInsured());

		for (MedicalKeyFactorValue inskf : dto.getKeyFactorValueList()) {
			insuredPersonAddOn.addKeyFactorValue(inskf);
		}
		if (dto.getRecorder() != null) {
			insuredPersonAddOn.setRecorder(dto.getRecorder());
		}

		return insuredPersonAddOn;
	}

	public static MedProInsuAddOnDTO getMedProInsuAddOnDTO(MedicalProposalInsuredPersonAddOn insuredPersonAddOn) {
		MedProInsuAddOnDTO dto = new MedProInsuAddOnDTO();
		if (insuredPersonAddOn.getId() != null) {
			dto.setExistsEntity(true);
			dto.setId(insuredPersonAddOn.getId());
			dto.setVersion(insuredPersonAddOn.getVersion());
		}
		dto.setPremium(insuredPersonAddOn.getPremium());
		dto.setAddOn(insuredPersonAddOn.getAddOn());
		dto.setUnit(insuredPersonAddOn.getUnit());
		dto.setSumInsured(insuredPersonAddOn.getSumInsured());

		for (MedicalKeyFactorValue inskf : insuredPersonAddOn.getKeyFactorValueList()) {
			dto.addKeyFactorValue(inskf);
		}
		if (insuredPersonAddOn.getRecorder() != null) {
			dto.setRecorder(insuredPersonAddOn.getRecorder());
		}
		return dto;
	}
}

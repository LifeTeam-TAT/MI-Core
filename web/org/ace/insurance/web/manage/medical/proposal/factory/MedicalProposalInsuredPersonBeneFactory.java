package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonBeneficiaries;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuBeneDTO;

public class MedicalProposalInsuredPersonBeneFactory {
	public static MedicalProposalInsuredPersonBeneficiaries getProposalInsuredPersonBeneficiaries(MedProInsuBeneDTO dto) {
		MedicalProposalInsuredPersonBeneficiaries beneficiaries = new MedicalProposalInsuredPersonBeneficiaries();
		if (dto.isExistsEntity()) {
			beneficiaries.setId(dto.getTempId());
			beneficiaries.setVersion(dto.getVersion());
		}
		beneficiaries.setDateOfBirth(dto.getDateOfBirth());
		beneficiaries.setPercentage(dto.getPercentage());
		beneficiaries.setBeneficiaryNo(dto.getBeneficiaryNo());
		beneficiaries.setInitialId(dto.getInitialId());
		beneficiaries.setFatherName(dto.getFatherName());
		beneficiaries.setIdNo(dto.getIdNo());
		beneficiaries.setGender(dto.getGender());
		beneficiaries.setIdType(dto.getIdType());
		beneficiaries.setResidentAddress(dto.getResidentAddress());
		beneficiaries.setContentInfo(dto.getContentInfo());
		beneficiaries.setName(dto.getName());
		beneficiaries.setRelationship(dto.getRelationship());
		beneficiaries.setStateCode(dto.getStateCode());
		beneficiaries.setTownshipCode(dto.getTownshipCode());
		beneficiaries.setFullIdNo(dto.getFullIdNo());
		beneficiaries.setOrganization(dto.getOrganization());
		beneficiaries.setCustomer(dto.getCustomer());

		if (dto.getRecorder() != null) {
			beneficiaries.setRecorder(dto.getRecorder());
		}

		return beneficiaries;
	}

	public static MedProInsuBeneDTO getMedProInsuBeneDTO(MedicalProposalInsuredPersonBeneficiaries beneficiaries) {
		MedProInsuBeneDTO dto = new MedProInsuBeneDTO();
		if (dto.getId() != null) {
			dto.setTempId(beneficiaries.getId());
			dto.setVersion(beneficiaries.getVersion());
			dto.setExistsEntity(true);
		}
		dto.setDateOfBirth(beneficiaries.getDateOfBirth());
		dto.setPercentage(beneficiaries.getPercentage());
		dto.setBeneficiaryNo(beneficiaries.getBeneficiaryNo());
		dto.setInitialId(beneficiaries.getInitialId());
		dto.setFatherName(beneficiaries.getFatherName());
		dto.setIdNo(beneficiaries.getIdNo());
		dto.setGender(beneficiaries.getGender());
		dto.setIdType(beneficiaries.getIdType());
		dto.setResidentAddress(beneficiaries.getResidentAddress());
		dto.setContentInfo(beneficiaries.getContentInfo());
		dto.setName(beneficiaries.getName());
		dto.setRelationship(beneficiaries.getRelationship());
		dto.setFullIdNo(beneficiaries.getFullIdNo());

		if (beneficiaries.getRecorder() != null) {
			dto.setRecorder(beneficiaries.getRecorder());
		}

		return dto;
	}
}
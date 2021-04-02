package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonBeneficiaries;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyInsuredPersonBeneficiaryDTO;

public class MedicalPolicyInsuredPersonBeneficiaryFactory {

	public static MedicalPolicyInsuredPersonBeneficiaries createMedicalPolicyInsuredPersonBeneficiary(MedicalPolicyInsuredPersonBeneficiaryDTO benificiaryDTO) {
		MedicalPolicyInsuredPersonBeneficiaries medicalPolicyInsuredPersonBeneficiary = new MedicalPolicyInsuredPersonBeneficiaries();
		if (benificiaryDTO.isExistsEntity()) {
			medicalPolicyInsuredPersonBeneficiary.setId(benificiaryDTO.getId());
			medicalPolicyInsuredPersonBeneficiary.setVersion(benificiaryDTO.getVersion());
		}
		benificiaryDTO.setFullIdNo();
		medicalPolicyInsuredPersonBeneficiary.setDateOfBirth(benificiaryDTO.getDateOfBirth());
		medicalPolicyInsuredPersonBeneficiary.setPercentage(benificiaryDTO.getPercentage());
		medicalPolicyInsuredPersonBeneficiary.setBeneficiaryNo(benificiaryDTO.getBeneficiaryNo());
		medicalPolicyInsuredPersonBeneficiary.setInitialId(benificiaryDTO.getInitialId());
		medicalPolicyInsuredPersonBeneficiary.setFatherName(benificiaryDTO.getFatherName());
		medicalPolicyInsuredPersonBeneficiary.setIdNo(benificiaryDTO.getIdNo());
		medicalPolicyInsuredPersonBeneficiary.setGender(benificiaryDTO.getGender());
		medicalPolicyInsuredPersonBeneficiary.setIdType(benificiaryDTO.getIdType());
		medicalPolicyInsuredPersonBeneficiary.setFullIdNo(benificiaryDTO.getFullIdNo());
		medicalPolicyInsuredPersonBeneficiary.setClaimStatus(benificiaryDTO.getClaimStatus());
		medicalPolicyInsuredPersonBeneficiary.setResidentAddress(benificiaryDTO.getResidentAddress());
		medicalPolicyInsuredPersonBeneficiary.setName(benificiaryDTO.getName());
		medicalPolicyInsuredPersonBeneficiary.setRelationship(benificiaryDTO.getRelationship());
		medicalPolicyInsuredPersonBeneficiary.setDeathDate(benificiaryDTO.getDeathDate());
		medicalPolicyInsuredPersonBeneficiary.setDeathReason(benificiaryDTO.getDeathReason());
		medicalPolicyInsuredPersonBeneficiary.setDeath(benificiaryDTO.isDeath());
		if (benificiaryDTO.getRecorder() != null) {
			medicalPolicyInsuredPersonBeneficiary.setRecorder(benificiaryDTO.getRecorder());
		}
		return medicalPolicyInsuredPersonBeneficiary;
	}

	public static MedicalPolicyInsuredPersonBeneficiaryDTO createMedicalPolicyInsuredPersonBeneficiaryDTO(
			MedicalPolicyInsuredPersonBeneficiaries medicalPolicyInsuredPersonBeneficiary) {
		MedicalPolicyInsuredPersonBeneficiaryDTO benificiaryDTO = new MedicalPolicyInsuredPersonBeneficiaryDTO();

		if (medicalPolicyInsuredPersonBeneficiary.getId() != null && (!medicalPolicyInsuredPersonBeneficiary.getId().isEmpty())) {
			benificiaryDTO.setId(medicalPolicyInsuredPersonBeneficiary.getId());
			benificiaryDTO.setVersion(medicalPolicyInsuredPersonBeneficiary.getVersion());
			benificiaryDTO.setExistsEntity(true);
		}
		medicalPolicyInsuredPersonBeneficiary.loadTransientIdNo();
		benificiaryDTO.setStateCode(medicalPolicyInsuredPersonBeneficiary.getStateCode());
		benificiaryDTO.setTownshipCode(medicalPolicyInsuredPersonBeneficiary.getTownshipCode());
		benificiaryDTO.setIdConditionType(medicalPolicyInsuredPersonBeneficiary.getIdConditionType());
		benificiaryDTO.setDateOfBirth(medicalPolicyInsuredPersonBeneficiary.getDateOfBirth());
		benificiaryDTO.setAge(medicalPolicyInsuredPersonBeneficiary.getAgeForNextYear());
		benificiaryDTO.setPercentage(medicalPolicyInsuredPersonBeneficiary.getPercentage());
		benificiaryDTO.setBeneficiaryNo(medicalPolicyInsuredPersonBeneficiary.getBeneficiaryNo());
		benificiaryDTO.setInitialId(medicalPolicyInsuredPersonBeneficiary.getInitialId());
		benificiaryDTO.setFatherName(medicalPolicyInsuredPersonBeneficiary.getFatherName());
		benificiaryDTO.setIdNo(medicalPolicyInsuredPersonBeneficiary.getIdNo());
		benificiaryDTO.setGender(medicalPolicyInsuredPersonBeneficiary.getGender());
		benificiaryDTO.setIdType(medicalPolicyInsuredPersonBeneficiary.getIdType());
		benificiaryDTO.setClaimStatus(medicalPolicyInsuredPersonBeneficiary.getClaimStatus());
		benificiaryDTO.setResidentAddress(medicalPolicyInsuredPersonBeneficiary.getResidentAddress());
		benificiaryDTO.setName(medicalPolicyInsuredPersonBeneficiary.getName());
		benificiaryDTO.setRelationship(medicalPolicyInsuredPersonBeneficiary.getRelationship());
		benificiaryDTO.setDeathDate(medicalPolicyInsuredPersonBeneficiary.getDeathDate());
		benificiaryDTO.setDeathReason(medicalPolicyInsuredPersonBeneficiary.getDeathReason());
		benificiaryDTO.setDeath(medicalPolicyInsuredPersonBeneficiary.isDeath());
		benificiaryDTO.setContentInfo(medicalPolicyInsuredPersonBeneficiary.getContentInfo());
		if (medicalPolicyInsuredPersonBeneficiary.getRecorder() != null) {
			benificiaryDTO.setRecorder(medicalPolicyInsuredPersonBeneficiary.getRecorder());
		}
		return benificiaryDTO;
	}

}

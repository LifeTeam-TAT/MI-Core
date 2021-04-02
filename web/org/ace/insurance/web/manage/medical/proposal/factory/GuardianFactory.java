package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonGuardian;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonGuardian;
import org.ace.insurance.web.manage.medical.proposal.MedProGuardianDTO;
import org.ace.insurance.web.manage.medical.proposal.PolicyGuardianDTO;

public class GuardianFactory {
	public static MedicalProposalInsuredPersonGuardian getGuardian(MedProGuardianDTO dto) {
		MedicalProposalInsuredPersonGuardian guardian = new MedicalProposalInsuredPersonGuardian();
		if (dto.isExistsEntity()) {
			guardian.setId(dto.getId());
			guardian.setVersion(dto.getVersion());
		}
		guardian.setCustomer(CustomerFactory.getCustomer(dto.getCustomer()));
		guardian.setGuardianNo(dto.getGuardianNo());
		guardian.setRelationship(dto.getRelationship());
		if (dto.getRecorder() != null) {
			guardian.setRecorder(dto.getRecorder());
		}
		return guardian;
	}

	public static MedProGuardianDTO getGuardianDTO(MedicalProposalInsuredPersonGuardian guardian) {
		MedProGuardianDTO dto = new MedProGuardianDTO();
		if (guardian.getId() != null) {
			dto.setId(guardian.getId());
			dto.setVersion(guardian.getVersion());
			dto.setExistsEntity(true);
		}
		dto.setCustomer(CustomerFactory.getCustomerDTO(guardian.getCustomer()));
		dto.setGuardianNo(guardian.getGuardianNo());
		dto.setRelationship(guardian.getRelationship());
		if (dto.getRecorder() != null) {
			guardian.setRecorder(dto.getRecorder());
		}
		return dto;
	}

	public static MedicalPolicyInsuredPersonGuardian getPolicyGuardian(PolicyGuardianDTO dto) {
		MedicalPolicyInsuredPersonGuardian guardian = new MedicalPolicyInsuredPersonGuardian();
		if (dto.isExistsEntity()) {
			guardian.setId(dto.getId());
			guardian.setVersion(dto.getVersion());
		}
		guardian.setCustomer(CustomerFactory.getCustomer(dto.getCustomerDTO()));
		guardian.setGuardianNo(dto.getGuardianNo());
		guardian.setRelationship(dto.getRelationship());
		guardian.setDeath(dto.isDeath());
		if (dto.getRecorder() != null) {
			guardian.setRecorder(dto.getRecorder());
		}
		return guardian;
	}

	public static PolicyGuardianDTO getPolicyGuardianDTO(MedicalPolicyInsuredPersonGuardian guardian) {
		PolicyGuardianDTO dto = new PolicyGuardianDTO();
		if (guardian.getId() != null) {
			dto.setId(guardian.getId());
			dto.setVersion(guardian.getVersion());
			dto.setExistsEntity(true);
		}
		dto.setCustomerDTO(CustomerFactory.getCustomerDTO(guardian.getCustomer()));
		dto.setGuardianNo(guardian.getGuardianNo());
		dto.setRelationship(guardian.getRelationship());
		dto.setDeath(guardian.isDeath());
		if (dto.getRecorder() != null) {
			guardian.setRecorder(dto.getRecorder());
		}
		return dto;
	}
}

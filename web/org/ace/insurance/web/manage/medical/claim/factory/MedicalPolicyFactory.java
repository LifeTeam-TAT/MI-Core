package org.ace.insurance.web.manage.medical.claim.factory;

import java.util.ArrayList;
import java.util.List;

import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyDTO;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyInsuredPersonDTO;
import org.ace.insurance.web.manage.medical.proposal.factory.MedicalProposalFactory;

public class MedicalPolicyFactory {

	public static MedicalPolicy createMedicalPolicy(MedicalPolicyDTO medicalPolicyDTO) {
		MedicalPolicy medicalPolicy = new MedicalPolicy();
		if (medicalPolicyDTO.isExitsEntity()) {
			medicalPolicy.setId(medicalPolicyDTO.getId());
			medicalPolicy.setVersion(medicalPolicyDTO.getVersion());
		}
		medicalPolicy.setDelFlag(medicalPolicyDTO.isDelFlag());
		medicalPolicy.setLastPaymentTerm(medicalPolicyDTO.getLastPaymentTerm());
		medicalPolicy.setPrintCount(medicalPolicyDTO.getPrintCount());
		medicalPolicy.setPolicyNo(medicalPolicyDTO.getPolicyNo());
		medicalPolicy.setCommenmanceDate(medicalPolicyDTO.getCommenmanceDate());
		medicalPolicy.setActivedPolicyStartDate(medicalPolicyDTO.getActivedPolicyStartDate());
		medicalPolicy.setActivedPolicyEndDate(medicalPolicyDTO.getActivedPolicyEndDate());
		medicalPolicy.setPolicyStatus(medicalPolicyDTO.getPolicyStatus());
		medicalPolicy.setCustomer(medicalPolicyDTO.getCustomer());
		medicalPolicy.setOrganization(medicalPolicyDTO.getOrganization());
		medicalPolicy.setBranch(medicalPolicyDTO.getBranch());
		medicalPolicy.setPaymentType(medicalPolicyDTO.getPaymentType());
		medicalPolicy.setAgent(medicalPolicyDTO.getAgent());
		medicalPolicy.setHealthType(medicalPolicyDTO.getHealthType());
		medicalPolicy.setCustomerType(medicalPolicyDTO.getCustomerType());
		medicalPolicy.setSaleChannelType(medicalPolicyDTO.getSaleChannelType());
		medicalPolicy.setSalesPoints(medicalPolicyDTO.getSalesPoints());
		medicalPolicy.setMedicalProposal(MedicalProposalFactory.getMedicalProposal(medicalPolicyDTO.getMedProDTO()));
		List<MedicalPolicyInsuredPerson> personList = new ArrayList<>();
		for (MedicalPolicyInsuredPersonDTO personDTO : medicalPolicyDTO.getPolicyInsuredPersonDtoList()) {
			MedicalPolicyInsuredPerson person = MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPerson(personDTO);
			personList.add(person);
		}
		medicalPolicy.setPolicyInsuredPersonList(personList);

		if (medicalPolicyDTO.getAttachmentList() != null && medicalPolicyDTO.getAttachmentList().size() != 0) {
			medicalPolicy.setAttachmentList(medicalPolicyDTO.getAttachmentList());
		}
		if (medicalPolicyDTO.getRecorder() != null) {
			medicalPolicy.setRecorder(medicalPolicyDTO.getRecorder());
		}
		return medicalPolicy;
	}

	public static MedicalPolicyDTO createMedicalPolicyDTO(MedicalPolicy medicalPolicy) {
		MedicalPolicyDTO medicalPolicyDTO = new MedicalPolicyDTO();
		if (medicalPolicy.getId() != null && (!medicalPolicy.getId().isEmpty())) {
			medicalPolicyDTO.setId(medicalPolicy.getId());
			medicalPolicyDTO.setVersion(medicalPolicy.getVersion());
			medicalPolicyDTO.setExitsEntity(true);
		}
		medicalPolicyDTO.setMedProDTO(MedicalProposalFactory.getMedProDTO(medicalPolicy.getMedicalProposal()));
		medicalPolicyDTO.setDelFlag(medicalPolicy.isDelFlag());
		medicalPolicyDTO.setLastPaymentTerm(medicalPolicy.getLastPaymentTerm());
		medicalPolicyDTO.setPrintCount(medicalPolicy.getPrintCount());
		medicalPolicyDTO.setPolicyNo(medicalPolicy.getPolicyNo());
		medicalPolicyDTO.setCommenmanceDate(medicalPolicy.getCommenmanceDate());
		medicalPolicyDTO.setActivedPolicyStartDate(medicalPolicy.getActivedPolicyStartDate());
		medicalPolicyDTO.setActivedPolicyEndDate(medicalPolicy.getActivedPolicyEndDate());
		medicalPolicyDTO.setPolicyStatus(medicalPolicy.getPolicyStatus());
		medicalPolicyDTO.setCustomer(medicalPolicy.getCustomer());
		medicalPolicyDTO.setOrganization(medicalPolicy.getOrganization());
		medicalPolicyDTO.setBranch(medicalPolicy.getBranch());
		medicalPolicyDTO.setPaymentType(medicalPolicy.getPaymentType());
		medicalPolicyDTO.setAgent(medicalPolicy.getAgent());
		medicalPolicyDTO.setHealthType(medicalPolicy.getHealthType());
		medicalPolicyDTO.setCustomerType(medicalPolicy.getCustomerType());
		medicalPolicyDTO.setSaleChannelType(medicalPolicy.getSaleChannelType());
		medicalPolicyDTO.setSalesPoints(medicalPolicy.getSalesPoints());
		List<MedicalPolicyInsuredPersonDTO> personList = new ArrayList<>();
		for (MedicalPolicyInsuredPerson person : medicalPolicy.getPolicyInsuredPersonList()) {
			MedicalPolicyInsuredPersonDTO personDTO = MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPersonDTO(person);
			personList.add(personDTO);
		}
		medicalPolicyDTO.setPolicyInsuredPersonDtoList(personList);
		if (medicalPolicy.getAttachmentList() != null && medicalPolicy.getAttachmentList().size() != 0) {
			medicalPolicyDTO.setAttachmentList(medicalPolicy.getAttachmentList());
		}
		if (medicalPolicy.getRecorder() != null) {
			medicalPolicyDTO.setRecorder(medicalPolicy.getRecorder());
		}
		return medicalPolicyDTO;
	}
}

package org.ace.insurance.web.manage.medical.proposal.factory;

import java.util.ArrayList;
import java.util.List;

import org.ace.insurance.medical.proposal.MedicalKeyFactorValue;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalAttachment;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.manage.medical.claim.factory.MedicalPolicyFactory;
import org.ace.insurance.web.manage.medical.proposal.MedProAttDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuDTO;

public class MedicalProposalFactory {
	public static MedicalProposal getMedicalProposal(MedProDTO proposalDTO) {
		MedicalProposal proposal = new MedicalProposal();
		if (proposalDTO.isExistsEntity()) {
			proposal.setVersion(proposalDTO.getVersion());
			proposal.setId(proposalDTO.getId());
		}
		proposal.setProposalNo(proposalDTO.getProposalNo());
		proposal.setProposalType(proposalDTO.getProposalType());
		proposal.setSubmittedDate(proposalDTO.getSubmittedDate());
		proposal.setBranch(proposalDTO.getBranch());
		proposal.setPaymentType(proposalDTO.getPaymentType());
		proposal.setAgent(proposalDTO.getAgent());
		proposal.setCustomerType(proposalDTO.getCustomerType());
		proposal.setHealthType(proposalDTO.getHealthType());
		proposal.setPeriodMonth(proposalDTO.getPeriodOfMonth());
		proposal.setPaymentTerm(proposalDTO.getPaymentTerm());
		proposal.setSaleChannelType(proposalDTO.getSaleChannelType());
		proposal.setAgent(proposalDTO.getAgent());
		proposal.setStartDate(proposalDTO.getStartDate());
		proposal.setEndDate(proposalDTO.getEndDate());

		if (proposalDTO.getMedicalPolicyDTO() != null) {
			proposal.setOldMedicalPolicy(MedicalPolicyFactory.createMedicalPolicy(proposalDTO.getMedicalPolicyDTO()));
		}
		// populate InsuredPerson Data
		for (MedProInsuDTO insuredPersonDTO : proposalDTO.getMedProInsuDTOList()) {
			MedicalProposalInsuredPerson insuredPerson = MedicalProposalInsuredPersonFactory.getProposalInsuredPerson(insuredPersonDTO);
			proposal.addMedicalProposalInsuredPerson(insuredPerson);
		}
		// populate Customer or Organization Data
		if (proposalDTO.getCustomer() != null) {
			proposal.setCustomer(CustomerFactory.getCustomer(proposalDTO.getCustomer()));
		}
		if (proposalDTO.getOrganization() != null) {
			proposal.setOrganization(OrganizationFactory.getOrganization(proposalDTO.getOrganization()));
		}
		// populate proposal attached
		for (MedProAttDTO attachmentDTO : proposalDTO.getAttachmentList()) {
			MedicalProposalAttachment attachment = MedicalProposalAttachmentFactory.getMedicalProposalAttachment(attachmentDTO);
			proposal.addAttachment(attachment);
		}
		if (proposalDTO.getRecorder() != null) {
			proposal.setRecorder(proposalDTO.getRecorder());
		}
		return proposal;
	}

	public static MedProDTO getMedProDTO(MedicalProposal proposal) {
		MedProDTO proposalDTO = new MedProDTO();
		if (proposal.getId() != null) {
			proposalDTO.setExistsEntity(true);
			proposalDTO.setVersion(proposal.getVersion());
			proposalDTO.setId(proposal.getId());
		}
		proposalDTO.setProposalNo(proposal.getProposalNo());
		proposalDTO.setProposalType(proposal.getProposalType());
		proposalDTO.setSubmittedDate(proposal.getSubmittedDate());
		proposalDTO.setBranch(proposal.getBranch());
		proposalDTO.setPaymentType(proposal.getPaymentType());
		proposalDTO.setAgent(proposal.getAgent());
		proposalDTO.setCustomerType(proposal.getCustomerType());
		proposalDTO.setHealthType(proposal.getHealthType());
		proposalDTO.setPeriodOfMonth(proposal.getPeriodMonth());
		proposalDTO.setPaymentTerm(proposal.getPaymentTerm());
		proposalDTO.setSaleChannelType(proposal.getSaleChannelType());
		proposalDTO.setAgent(proposal.getAgent());
		proposalDTO.setStartDate(proposal.getStartDate());
		proposalDTO.setEndDate(proposal.getEndDate());

		if (proposal.getOldMedicalPolicy() != null) {
			proposalDTO.setMedicalPolicyDTO(MedicalPolicyFactory.createMedicalPolicyDTO(proposal.getOldMedicalPolicy()));
		}
		// populate InsuredPerson Data
		List<MedProInsuDTO> medProInsuDTOList = new ArrayList<MedProInsuDTO>();
		for (MedicalProposalInsuredPerson insuredPerson : proposal.getMedicalProposalInsuredPersonList()) {
			MedProInsuDTO insuredPersonDTO = MedicalProposalInsuredPersonFactory.getMedProInsuDTO(insuredPerson);
			insuredPersonDTO.getKeyFactorValueList().clear();
			for (MedicalKeyFactorValue inskf : insuredPerson.getKeyFactorValueList()) {
				KeyFactor kf = inskf.getKeyFactor();
				if (KeyFactorChecker.isPaymentType(kf)) {
					inskf.setValue(proposalDTO.getPaymentType() + "");
				} else if (KeyFactorChecker.isMedicalAge(kf)) {
					inskf.setValue(insuredPersonDTO.getAge() + "");
				} else if (KeyFactorChecker.isGender(kf)) {
					inskf.setValue(insuredPersonDTO.getCustomer().getGender() + "");
				}
				insuredPersonDTO.addMedicalKeyFactorValue(inskf);
			}
			medProInsuDTOList.add(insuredPersonDTO);
			proposalDTO.addInsuredPerson(insuredPersonDTO);
		}
		// populate Customer or Organization Data
		if (proposal.getCustomer() != null) {
			proposalDTO.setCustomer(CustomerFactory.getCustomerDTO(proposal.getCustomer()));
		}
		if (proposal.getOrganization() != null) {
			proposalDTO.setOrganization(OrganizationFactory.getOrganizationDTO(proposal.getOrganization()));
		}
		// populate proposal attached
		for (MedicalProposalAttachment attachment : proposal.getAttachmentList()) {
			MedProAttDTO attachmentDTO = MedicalProposalAttachmentFactory.getMedicalProposalAttachmentDTO(attachment);
			proposalDTO.addAttachment(attachmentDTO);
		}
		if (proposal.getRecorder() != null) {
			proposalDTO.setRecorder(proposal.getRecorder());
		}
		return proposalDTO;
	}
}

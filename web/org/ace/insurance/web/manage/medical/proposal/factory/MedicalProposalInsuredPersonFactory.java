package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.medical.proposal.MedicalKeyFactorValue;
import org.ace.insurance.medical.proposal.MedicalPersonHistoryRecord;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAddOn;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAttachment;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonBeneficiaries;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuAddOnDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuAttDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuBeneDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;
import org.ace.insurance.web.manage.medical.survey.factory.SurveyQuestionAnswerDTOFactroy;

public class MedicalProposalInsuredPersonFactory {
	public static MedicalProposalInsuredPerson getProposalInsuredPerson(MedProInsuDTO insuredPersonDTO) {
		MedicalProposalInsuredPerson insuredPerson = new MedicalProposalInsuredPerson();
		if (insuredPersonDTO.isExistsEntity()) {
			insuredPerson.setId(insuredPersonDTO.getId());
			insuredPerson.setVersion(insuredPersonDTO.getVersion());
		}
		insuredPerson.setIsPaidPremiumForPaidup(insuredPersonDTO.isPaidPremiumForPaidup());
		insuredPerson.setApproved(insuredPersonDTO.isApproved());
		insuredPerson.setNeedMedicalCheckup(insuredPersonDTO.isNeedMedicalCheckup());
		insuredPerson.setAge(insuredPersonDTO.getAge());
		insuredPerson.setPremium(insuredPersonDTO.getPremium());
		insuredPerson.setBasicTermPremium(insuredPersonDTO.getBasicTermPremium());
		insuredPerson.setAddOnTermPremium(insuredPersonDTO.getAddOnTermPremium());
		insuredPerson.setRejectReason(insuredPersonDTO.getRejectReason());
		insuredPerson.setUnit(insuredPersonDTO.getUnit());
		insuredPerson.setRelationship(insuredPersonDTO.getRelationship());
		insuredPerson.setProduct(insuredPersonDTO.getProduct());
		insuredPerson.setSameCustomer(insuredPersonDTO.isSameInsuredPerson());
		insuredPerson.setSumInsured(insuredPersonDTO.getSumInsured());

		insuredPerson.setKeyFactorValueList(null);
		if (insuredPersonDTO.getCustomer() != null) {
			insuredPerson.setCustomer(CustomerFactory.getCustomer(insuredPersonDTO.getCustomer()));
		}
		if (insuredPersonDTO.getGuardianDTO() != null) {
			insuredPerson.setGuardian(GuardianFactory.getGuardian(insuredPersonDTO.getGuardianDTO()));
		}

		for (MedicalKeyFactorValue inskf : insuredPersonDTO.getKeyFactorValueList()) {
			insuredPerson.addMedicalKeyFactorValue(inskf);
		}

		/**
		 * populate InsuredPersonBeneficiary data
		 */
		for (MedProInsuBeneDTO beneficiaryDto : insuredPersonDTO.getInsuredPersonBeneficiariesList()) {
			MedicalProposalInsuredPersonBeneficiaries beneficiary = MedicalProposalInsuredPersonBeneFactory.getProposalInsuredPersonBeneficiaries(beneficiaryDto);
			insuredPerson.addBeneficiaries(beneficiary);
		}

		/**
		 * populate InsuredPersonAddOn data
		 */
		for (MedProInsuAddOnDTO addOnDtO : insuredPersonDTO.getInsuredPersonAddOnList()) {
			MedicalProposalInsuredPersonAddOn addOn = MedProInsuAddOnFactory.getMedicalProposalInsuredPersonAddOn(addOnDtO);
			insuredPerson.addInsuredPersonAddon(addOn);
		}

		for (MedicalPersonHistoryRecord historyRecord : insuredPersonDTO.getHistoryRecordList()) {
			insuredPerson.addHistoryRecord(historyRecord);
		}

		/**
		 * populate InsuredPersonAttachment data
		 */
		for (MedProInsuAttDTO attachmentDTO : insuredPersonDTO.getAttachmentList()) {
			MedicalProposalInsuredPersonAttachment attachment = MedicalProposalInsuPersonAttachmentFactory.getMedicalProposalInsuredPersonAttachment(attachmentDTO);
			insuredPerson.addAttachment(attachment);
		}

		for (SurveyQuestionAnswerDTO questionDTO : insuredPersonDTO.getSurveyQuestionAnsweDTOList()) {
			SurveyQuestionAnswer question = SurveyQuestionAnswerDTOFactroy.getSurveyQuestionAnswer(questionDTO);
			insuredPerson.addSurveyQuestion(question);
		}

		if (insuredPersonDTO.getRecorder() != null) {
			insuredPerson.setRecorder(insuredPersonDTO.getRecorder());
		}

		return insuredPerson;
	}

	public static MedProInsuDTO getMedProInsuDTO(MedicalProposalInsuredPerson insuredPerson) {
		MedProInsuDTO insuredPersonDTO = new MedProInsuDTO();
		if (insuredPerson.getId() != null) {
			insuredPersonDTO.setExistsEntity(true);
			insuredPersonDTO.setId(insuredPerson.getId());
			insuredPersonDTO.setVersion(insuredPerson.getVersion());
		}
		insuredPersonDTO.setPaidPremiumForPaidup(insuredPerson.getIsPaidPremiumForPaidup());
		insuredPersonDTO.setApproved(insuredPerson.isApproved());
		insuredPersonDTO.setNeedMedicalCheckup(insuredPerson.isNeedMedicalCheckup());
		insuredPersonDTO.setAge(insuredPerson.getAge());
		insuredPersonDTO.setPremium(insuredPerson.getPremium());
		insuredPersonDTO.setBasicTermPremium(insuredPerson.getBasicTermPremium());
		insuredPersonDTO.setAddOnTermPremium(insuredPerson.getAddOnTermPremium());
		insuredPersonDTO.setRejectReason(insuredPerson.getRejectReason());
		insuredPersonDTO.setUnit(insuredPerson.getUnit());
		insuredPersonDTO.setRelationship(insuredPerson.getRelationship());
		insuredPersonDTO.setProduct(insuredPerson.getProduct());
		insuredPersonDTO.setSameInsuredPerson(insuredPerson.isSameCustomer());
		insuredPersonDTO.setSumInsured(insuredPerson.getSumInsured());

		if (insuredPerson.getCustomer() != null) {
			insuredPersonDTO.setCustomer(CustomerFactory.getCustomerDTO(insuredPerson.getCustomer()));
		}
		if (insuredPerson.getGuardian() != null) {
			insuredPersonDTO.setGuardianDTO(GuardianFactory.getGuardianDTO(insuredPerson.getGuardian()));
		}
		/**
		 * populate InsuredPersonBeneficiary data
		 */
		for (MedicalProposalInsuredPersonBeneficiaries beneficiary : insuredPerson.getInsuredPersonBeneficiariesList()) {
			MedProInsuBeneDTO beneficiaryDTO = MedicalProposalInsuredPersonBeneFactory.getMedProInsuBeneDTO(beneficiary);
			insuredPersonDTO.addBeneficiaries(beneficiaryDTO);
		}

		/**
		 * populate InsuredPersonAddOn data
		 */
		for (MedicalProposalInsuredPersonAddOn addOn : insuredPerson.getInsuredPersonAddOnList()) {
			MedProInsuAddOnDTO addOnDTO = MedProInsuAddOnFactory.getMedProInsuAddOnDTO(addOn);
			insuredPersonDTO.addInsuredPersonAddon(addOnDTO);
		}

		/**
		 * populate InsuredPersonKeyFactorValue data
		 */
		for (MedicalKeyFactorValue inskf : insuredPerson.getKeyFactorValueList()) {
			KeyFactor kf = inskf.getKeyFactor();
			if (KeyFactorChecker.isMedicalAge(kf)) {
				inskf.setValue(insuredPerson.getAge() + "");
			}
			insuredPersonDTO.addMedicalKeyFactorValue(inskf);
		}

		/**
		 * populate InsuredPersonAttachment data
		 */

		for (MedicalPersonHistoryRecord record : insuredPerson.getMedicalPersonHistoryRecordList()) {
			insuredPersonDTO.addHistoryRecord(record);
		}

		for (MedicalProposalInsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			MedProInsuAttDTO attachmentDTO = MedicalProposalInsuPersonAttachmentFactory.getMedicalProposalInsuredPersonAttachmentDTO(attachment);
			insuredPersonDTO.addInsuredPersonAttachment(attachmentDTO);
		}

		for (SurveyQuestionAnswer question : insuredPerson.getSurveyQuestionAnswerList()) {
			SurveyQuestionAnswerDTO questionDTO = SurveyQuestionAnswerDTOFactroy.getSurveyQuestionAnswerDTO(question);
			insuredPersonDTO.addSurveyQuestion(questionDTO);
		}

		if (insuredPerson.getRecorder() != null) {
			insuredPersonDTO.setRecorder(insuredPerson.getRecorder());
		}
		return insuredPersonDTO;
	}

}

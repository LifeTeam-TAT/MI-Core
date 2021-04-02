package org.ace.insurance.web.manage.medical.survey.factory;

import org.ace.insurance.medical.proposal.MedicalHistory;
import org.ace.insurance.medical.proposal.MedicalSurvey;
import org.ace.insurance.web.manage.medical.proposal.factory.MedicalProposalFactory;
import org.ace.insurance.web.manage.medical.survey.MedicalHistoryDTO;
import org.ace.insurance.web.manage.medical.survey.MedicalSurveyDTO;

public class MedicalSurveyDTOFactory {
	public static MedicalSurveyDTO getMedicalSurveyDTO(MedicalSurvey medicalSurvey) {
		MedicalSurveyDTO medicalSurveyDTO = new MedicalSurveyDTO();
		if (medicalSurvey.getId() != null && (!medicalSurvey.getId().isEmpty())) {
			medicalSurveyDTO.setId(medicalSurvey.getId());
			medicalSurveyDTO.setVersion(medicalSurvey.getVersion());
			medicalSurveyDTO.setExistsEntity(true);
		}
		medicalSurveyDTO.setRankAndQualification(medicalSurvey.getRankAndQualification());
		medicalSurveyDTO.setRemark(medicalSurvey.getRemark());
		medicalSurveyDTO.setSurveyDate(medicalSurvey.getSurveyDate());
		medicalSurveyDTO.setMedicalProposalDTO(MedicalProposalFactory.getMedProDTO(medicalSurvey.getMedicalProposal()));
		for (MedicalHistory medicalHistory : medicalSurvey.getMedicalHistoryList()) {
			MedicalHistoryDTO medicalHistoryDTO = MedicalHistoryDTOFactory.getMedicalHistoryDTO(medicalHistory);
			medicalSurveyDTO.addMedicalHistoryDTO(medicalHistoryDTO);

		}
		if (medicalSurvey.getRecorder() != null) {
			medicalSurveyDTO.setRecorder(medicalSurvey.getRecorder());
		}
		return medicalSurveyDTO;
	}

	public static MedicalSurvey getMedicalSurvey(MedicalSurveyDTO medicalSurveyDTO) {
		MedicalSurvey medicalSurvey = new MedicalSurvey();
		if (medicalSurveyDTO.isExistsEntity()) {
			medicalSurvey.setId(medicalSurveyDTO.getId());
			medicalSurvey.setVersion(medicalSurveyDTO.getVersion());
		}
		medicalSurvey.setRankAndQualification(medicalSurveyDTO.getRankAndQualification());
		medicalSurvey.setRemark(medicalSurveyDTO.getRemark());
		medicalSurvey.setConditionOfHealth(medicalSurveyDTO.getConditionOfHealth());
		medicalSurvey.setSurveyDate(medicalSurveyDTO.getSurveyDate());
		medicalSurvey.setMedicalProposal(MedicalProposalFactory.getMedicalProposal(medicalSurveyDTO.getMedicalProposalDTO()));

		for (MedicalHistoryDTO medicalHistoryDTO : medicalSurveyDTO.getMedicalHistoryList()) {
			MedicalHistory medicalHistory = MedicalHistoryDTOFactory.getMedicalHistory(medicalHistoryDTO);
			medicalSurvey.addMedicalHistory(medicalHistory);

		}

		if (medicalSurveyDTO.getRecorder() != null) {
			medicalSurvey.setRecorder(medicalSurveyDTO.getRecorder());
		}

		return medicalSurvey;
	}

}

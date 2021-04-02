package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.medical.claim.MedicalClaimSurvey;
import org.ace.insurance.web.manage.medical.claim.MedicalClaimSurveyDTO;

public class MedicalClaimSurveyFactory {
	public static MedicalClaimSurvey createMedicalClaimSurvey(MedicalClaimSurveyDTO medicalClaimSurveyDTO) {
		MedicalClaimSurvey medicalClaimSurvey = new MedicalClaimSurvey();
		if (medicalClaimSurveyDTO.isExistsEntity()) {
			medicalClaimSurvey.setId(medicalClaimSurveyDTO.getId());
			medicalClaimSurvey.setVersion(medicalClaimSurveyDTO.getVersion());
		}
		medicalClaimSurvey.setBoardOrNot(medicalClaimSurveyDTO.isBoardOrNot());
		medicalClaimSurvey.setMedicalOfficerName(medicalClaimSurveyDTO.getMedicalOfficerName());
		medicalClaimSurvey.setRankAndQualification(medicalClaimSurveyDTO.getRankAndQualification());
		medicalClaimSurvey.setAddress(medicalClaimSurveyDTO.getAddress());
		medicalClaimSurvey.setRemark(medicalClaimSurveyDTO.getRemark());
		medicalClaimSurvey.setSurveyDate(medicalClaimSurveyDTO.getSurveyDate());
		medicalClaimSurvey.setTownship(medicalClaimSurveyDTO.getTownship());
		medicalClaimSurvey.setHospital(medicalClaimSurveyDTO.getMedicalPlace());

		if (medicalClaimSurveyDTO.getRecorder() != null) {
			medicalClaimSurvey.setRecorder(medicalClaimSurveyDTO.getRecorder());
		}
		return medicalClaimSurvey;
	}

	public static MedicalClaimSurveyDTO createMedicalClaimSurveyDTO(MedicalClaimSurvey medicalClaimSurvey) {
		MedicalClaimSurveyDTO medicalClaimSurveyDTO = new MedicalClaimSurveyDTO();
		if (medicalClaimSurvey.getId() != null && !medicalClaimSurvey.getId().isEmpty()) {
			medicalClaimSurveyDTO.setId(medicalClaimSurvey.getId());
			medicalClaimSurveyDTO.setVersion(medicalClaimSurvey.getVersion());
			medicalClaimSurveyDTO.setExistsEntity(true);
		}
		medicalClaimSurveyDTO.setBoardOrNot(medicalClaimSurvey.isBoardOrNot());
		medicalClaimSurveyDTO.setMedicalOfficerName(medicalClaimSurvey.getMedicalOfficerName());
		medicalClaimSurveyDTO.setRankAndQualification(medicalClaimSurvey.getRankAndQualification());
		medicalClaimSurveyDTO.setAddress(medicalClaimSurvey.getAddress());
		medicalClaimSurveyDTO.setRemark(medicalClaimSurvey.getRemark());
		medicalClaimSurveyDTO.setSurveyDate(medicalClaimSurvey.getSurveyDate());
		medicalClaimSurveyDTO.setTownship(medicalClaimSurvey.getTownship());
		medicalClaimSurveyDTO.setMedicalPlace(medicalClaimSurvey.getHospital());

		if (medicalClaimSurvey.getRecorder() != null) {
			medicalClaimSurveyDTO.setRecorder(medicalClaimSurvey.getRecorder());
		}
		return medicalClaimSurveyDTO;
	}
}

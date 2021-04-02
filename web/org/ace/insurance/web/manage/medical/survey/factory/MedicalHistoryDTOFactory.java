package org.ace.insurance.web.manage.medical.survey.factory;

import org.ace.insurance.common.Utils;
import org.ace.insurance.medical.proposal.MedicalHistory;
import org.ace.insurance.web.manage.medical.survey.MedicalHistoryDTO;

public class MedicalHistoryDTOFactory {
	public static MedicalHistoryDTO getMedicalHistoryDTO(MedicalHistory medicalHistory) {
		MedicalHistoryDTO medicalHistoryDTO = new MedicalHistoryDTO();
		if (Utils.isEmpty(medicalHistory.getId())) {
			medicalHistoryDTO.setId(medicalHistory.getId());
			medicalHistoryDTO.setVersion(medicalHistory.getVersion());
			medicalHistoryDTO.setExistsEntity(true);
		}
		medicalHistoryDTO.setCauseOfHospitalization(medicalHistory.getCauseOfHospitalization());
		medicalHistoryDTO.setIcd10(medicalHistory.getIcd10());
		medicalHistoryDTO.setMedicalOfficer(medicalHistory.getMedicalOfficer());
		medicalHistoryDTO.setResult(medicalHistory.getResult());
		medicalHistoryDTO.setHospital(medicalHistory.getHospital());

		return medicalHistoryDTO;
	}

	public static MedicalHistory getMedicalHistory(MedicalHistoryDTO medicalHistoryDTO) {
		MedicalHistory medicalHistory = new MedicalHistory();
		if (medicalHistoryDTO.isExistsEntity()) {
			medicalHistory.setId(medicalHistoryDTO.getId());
			medicalHistory.setVersion(medicalHistoryDTO.getVersion());
		}

		medicalHistory.setCauseOfHospitalization(medicalHistoryDTO.getCauseOfHospitalization());
		medicalHistory.setIcd10(medicalHistoryDTO.getIcd10());
		medicalHistory.setMedicalOfficer(medicalHistoryDTO.getMedicalOfficer());
		medicalHistory.setResult(medicalHistoryDTO.getResult());
		medicalHistory.setHospital(medicalHistoryDTO.getHospital());
		return medicalHistory;
	}

}

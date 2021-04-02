package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.medical.claim.MedicalClaimAttachment;
import org.ace.insurance.web.manage.medical.claim.MedicalClaimAttachmentDTO;

public class MedicalClaimAttachmentDTOFactory {
	public static MedicalClaimAttachmentDTO getMedicalClaimAttachmentDTO(MedicalClaimAttachment medicalClaimAttachment) {
		MedicalClaimAttachmentDTO medicalClaimAttachmentDTO = new MedicalClaimAttachmentDTO();
		if (medicalClaimAttachment.getId() != null && (!medicalClaimAttachment.getId().isEmpty())) {
			medicalClaimAttachmentDTO.setId(medicalClaimAttachment.getId());
			medicalClaimAttachmentDTO.setVersion(medicalClaimAttachment.getVersion());
			medicalClaimAttachmentDTO.setExitsEntity(true);
		}
		medicalClaimAttachmentDTO.setFilePath(medicalClaimAttachment.getFilePath());
		medicalClaimAttachmentDTO.setName(medicalClaimAttachment.getName());
		if (medicalClaimAttachment.getRecorder() != null) {
			medicalClaimAttachmentDTO.setRecorder(medicalClaimAttachment.getRecorder());
		}
		return medicalClaimAttachmentDTO;
	}

	public static MedicalClaimAttachment getMedicalClaimAttachment(MedicalClaimAttachmentDTO medicalClaimAttachmentDTO) {
		MedicalClaimAttachment medicalClaimAttachment = new MedicalClaimAttachment();
		if (medicalClaimAttachmentDTO.isExitsEntity()) {
			medicalClaimAttachment.setId(medicalClaimAttachmentDTO.getId());
			medicalClaimAttachment.setVersion(medicalClaimAttachmentDTO.getVersion());
		}
		medicalClaimAttachment.setFilePath(medicalClaimAttachmentDTO.getFilePath());
		medicalClaimAttachment.setName(medicalClaimAttachmentDTO.getName());
		if (medicalClaimAttachmentDTO.getRecorder() != null) {
			medicalClaimAttachment.setRecorder(medicalClaimAttachmentDTO.getRecorder());
		}
		return medicalClaimAttachment;
	}

}

package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.medical.proposal.MedicalProposalAttachment;
import org.ace.insurance.web.manage.medical.proposal.MedProAttDTO;

public class MedicalProposalAttachmentFactory {

	public static MedProAttDTO getMedicalProposalAttachmentDTO(MedicalProposalAttachment attachment) {
		MedProAttDTO medProAttDTO = new MedProAttDTO();
		if (attachment.getId() != null && (!attachment.getId().isEmpty())) {
			medProAttDTO.setId(attachment.getId());
			medProAttDTO.setVersion(attachment.getVersion());
			medProAttDTO.setExistsEntity(true);
		}
		medProAttDTO.setFilePath(attachment.getFilePath());
		medProAttDTO.setName(attachment.getName());
		if (attachment.getRecorder() != null) {
			medProAttDTO.setRecorder(attachment.getRecorder());
		}

		return medProAttDTO;

	}

	public static MedicalProposalAttachment getMedicalProposalAttachment(MedProAttDTO attDTO) {
		MedicalProposalAttachment medProAtt = new MedicalProposalAttachment();
		if (attDTO.isExistsEntity()) {
			medProAtt.setId(attDTO.getId());
			medProAtt.setVersion(attDTO.getVersion());
		}
		medProAtt.setFilePath(attDTO.getFilePath());
		medProAtt.setName(attDTO.getName());
		if (attDTO.getRecorder() != null) {
			medProAtt.setRecorder(attDTO.getRecorder());
		}
		return medProAtt;
	}

}

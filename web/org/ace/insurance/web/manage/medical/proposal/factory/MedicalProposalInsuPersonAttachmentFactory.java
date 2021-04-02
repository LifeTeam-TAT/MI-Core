package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAttachment;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuAttDTO;

public class MedicalProposalInsuPersonAttachmentFactory {
	public static MedProInsuAttDTO getMedicalProposalInsuredPersonAttachmentDTO(MedicalProposalInsuredPersonAttachment attachment) {
		MedProInsuAttDTO medProInsuAttDTO = new MedProInsuAttDTO();
		if (attachment.getId() != null && (!attachment.getId().isEmpty())) {
			medProInsuAttDTO.setId(attachment.getId());
			medProInsuAttDTO.setVersion(attachment.getVersion());
			medProInsuAttDTO.setExistsEntity(true);
		}
		medProInsuAttDTO.setFilePath(attachment.getFilePath());
		medProInsuAttDTO.setName(attachment.getName());
		if (attachment.getRecorder() != null) {
			medProInsuAttDTO.setRecorder(attachment.getRecorder());
		}
		return medProInsuAttDTO;

	}

	public static MedicalProposalInsuredPersonAttachment getMedicalProposalInsuredPersonAttachment(MedProInsuAttDTO medProInsuAttDTO) {
		MedicalProposalInsuredPersonAttachment medProInsuAtt = new MedicalProposalInsuredPersonAttachment();
		if (medProInsuAttDTO.isExistsEntity()) {
			medProInsuAtt.setId(medProInsuAttDTO.getId());
			medProInsuAtt.setVersion(medProInsuAttDTO.getVersion());
		}
		medProInsuAtt.setFilePath(medProInsuAttDTO.getFilePath());
		medProInsuAtt.setName(medProInsuAttDTO.getName());
		if (medProInsuAttDTO.getRecorder() != null) {
			medProInsuAtt.setRecorder(medProInsuAttDTO.getRecorder());
		}
		return medProInsuAtt;
	}

}

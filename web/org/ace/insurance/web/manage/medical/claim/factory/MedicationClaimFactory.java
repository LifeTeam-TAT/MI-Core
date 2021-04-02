package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.medical.claim.MedicationClaim;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.claim.MedicationClaimDTO;

public class MedicationClaimFactory {
	public static MedicationClaim createMedicationClaim(MedicationClaimDTO medicationClaimDTO) {
		MedicationClaim medicationClaim = new MedicationClaim();
		if (medicationClaimDTO.isExistEntity()) {
			medicationClaim.setId(medicationClaimDTO.getId());
			medicationClaim.setVersion(medicationClaimDTO.getVersion());
		}
		medicationClaim.setClaimRole(medicationClaimDTO.getClaimRole());
		medicationClaim.setApproved(medicationClaimDTO.isApproved());
		medicationClaim.setMedication(medicationClaimDTO.getMedication());
		medicationClaim.setMedicationFee(medicationClaimDTO.getMedicationFee());
		medicationClaim.setMedicationReason(medicationClaimDTO.getMedicationReason());
		medicationClaim.setReceivedDate(medicationClaimDTO.getReceivedDate());
		medicationClaim.setRejectReason(medicationClaimDTO.getRejectReason());
		for (AttachmentDTO mca : medicationClaimDTO.getAttachmentList()) {
			medicationClaim.addAttachment(new Attachment(mca));
		}
		if (medicationClaimDTO.getRecorder() != null) {
			medicationClaim.setRecorder(medicationClaimDTO.getRecorder());
		}
		return medicationClaim;
	}

	public static MedicationClaimDTO createMedicationClaimDTO(MedicationClaim medicationClaim) {
		MedicationClaimDTO medicationClaimDTO = new MedicationClaimDTO();
		if (medicationClaim.getId() != null) {
			medicationClaimDTO.setId(medicationClaim.getId());
			medicationClaimDTO.setVersion(medicationClaim.getVersion());
			medicationClaimDTO.setExistEntity(true);
		}
		medicationClaimDTO.setClaimRole(medicationClaim.getClaimRole());
		medicationClaimDTO.setApproved(medicationClaim.isApproved());
		medicationClaimDTO.setMedication(medicationClaim.getMedication());
		medicationClaimDTO.setMedicationFee(medicationClaim.getMedicationFee());
		medicationClaimDTO.setMedicationReason(medicationClaim.getMedicationReason());
		medicationClaimDTO.setReceivedDate(medicationClaim.getReceivedDate());
		medicationClaimDTO.setRejectReason(medicationClaim.getRejectReason());
		for (Attachment mca : medicationClaim.getAttachmentList()) {
			medicationClaimDTO.addAttachment(new AttachmentDTO(mca));
		}
		if (medicationClaim.getRecorder() != null) {
			medicationClaimDTO.setRecorder(medicationClaim.getRecorder());
		}
		return medicationClaimDTO;
	}
}

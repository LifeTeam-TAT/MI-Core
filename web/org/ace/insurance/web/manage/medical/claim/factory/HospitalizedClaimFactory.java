package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.HospitalizedClaimICD10;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.claim.HospitalizedClaimDTO;

public class HospitalizedClaimFactory {
	public static HospitalizedClaim createHospitalizedClaim(HospitalizedClaimDTO hospitalizedClaimDTO) {
		HospitalizedClaim hospitalizedClaim = new HospitalizedClaim();
		if (hospitalizedClaimDTO.isExistEntity()) {

			hospitalizedClaim.setVersion(hospitalizedClaimDTO.getVersion());
		}
		hospitalizedClaim.setId(hospitalizedClaimDTO.getId());
		hospitalizedClaim.setHospitalizedEndDate(hospitalizedClaimDTO.getHospitalizedEndDate());
		hospitalizedClaim.setHospitalizedStartDate(hospitalizedClaimDTO.getHospitalizedStartDate());
		hospitalizedClaim.setApproved(hospitalizedClaimDTO.isApproved());
		hospitalizedClaim.setMedicalPlace(hospitalizedClaimDTO.getMedicalPlace());
		hospitalizedClaim.setRejectReason(hospitalizedClaimDTO.getRejectReason());
		hospitalizedClaim.setHospitalizedAmount(hospitalizedClaimDTO.getHospitalizedAmount());
		hospitalizedClaim.setActualHospitalizedAmount(hospitalizedClaimDTO.getActualHospitalizedAmount());
		hospitalizedClaim.setClaimRole(hospitalizedClaimDTO.getClaimRole());
		if (hospitalizedClaimDTO.getRecorder() != null) {
			hospitalizedClaim.setRecorder(hospitalizedClaimDTO.getRecorder());
		}

		if (hospitalizedClaimDTO.getHospitalizedClaimICD10List() != null) {
			for (HospitalizedClaimICD10 hIcd10 : hospitalizedClaimDTO.getHospitalizedClaimICD10List()) {
				hospitalizedClaim.addHospitalizedClaimICD10(hIcd10);
			}
		}

		for (AttachmentDTO hca : hospitalizedClaimDTO.getattachmentList()) {
			hospitalizedClaim.addAttachment(new Attachment(hca));
		}
		return hospitalizedClaim;
	}

	public static HospitalizedClaimDTO createHospitalizedClaimDTO(HospitalizedClaim hospitalizedClaim) {
		HospitalizedClaimDTO hospitalizedClaimDTO = new HospitalizedClaimDTO();
		if (hospitalizedClaim.getId() != null) {
			hospitalizedClaimDTO.setId(hospitalizedClaim.getId());
			hospitalizedClaimDTO.setVersion(hospitalizedClaim.getVersion());
			hospitalizedClaimDTO.setExistEntity(true);
		}
		hospitalizedClaimDTO.setHospitalizedEndDate(hospitalizedClaim.getHospitalizedEndDate());
		hospitalizedClaimDTO.setHospitalizedStartDate(hospitalizedClaim.getHospitalizedStartDate());
		hospitalizedClaimDTO.setApproved(hospitalizedClaim.isApproved());
		hospitalizedClaimDTO.setMedicalPlace(hospitalizedClaim.getMedicalPlace());
		hospitalizedClaimDTO.setRejectReason(hospitalizedClaim.getRejectReason());
		hospitalizedClaimDTO.setHospitalizedAmount(hospitalizedClaim.getHospitalizedAmount());
		hospitalizedClaimDTO.setActualHospitalizedAmount(hospitalizedClaim.getActualHospitalizedAmount());
		hospitalizedClaimDTO.setClaimRole(hospitalizedClaim.getClaimRole());
		if (hospitalizedClaim.getRecorder() != null) {
			hospitalizedClaimDTO.setRecorder(hospitalizedClaim.getRecorder());
		}

		if (hospitalizedClaim.getHospitalizedClaimICD10List() != null) {
			hospitalizedClaimDTO.setHospitalizedClaimICD10List(hospitalizedClaim.getHospitalizedClaimICD10List());
		}

		for (Attachment hca : hospitalizedClaim.getAttachmentList()) {
			hospitalizedClaimDTO.addAttachment(new AttachmentDTO(hca));
		}
		return hospitalizedClaimDTO;
	}
}

package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.claim.DeathClaimICD10;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.claim.DeathClaimDTO;

public class DeathClaimFactory {
	public static DeathClaim createDeathClaim(DeathClaimDTO deathClaimDTO) {
		DeathClaim deathClaim = new DeathClaim();
		if (deathClaimDTO.isExistEntity()) {
			deathClaim.setId(deathClaimDTO.getId());
			deathClaim.setVersion(deathClaimDTO.getVersion());
		}
		deathClaim.setClaimRole(deathClaimDTO.getClaimRole());
		deathClaim.setApproved(deathClaimDTO.isApproved());
		deathClaim.setRejectReason(deathClaimDTO.getRejectReason());
		deathClaim.setDeathDate(deathClaimDTO.getDeathDate());
		deathClaim.setDeathClaimAmount(deathClaimDTO.getDeathClaimAmount());
		deathClaim.setDeathPlace(deathClaimDTO.getDeathPlace());
		deathClaim.setOtherPlace(deathClaimDTO.getOtherPlace());

		for (AttachmentDTO dca : deathClaimDTO.getAttachmentList()) {
			deathClaim.addAttachment(new Attachment(dca));
		}

		if (deathClaimDTO.getDeathClaimICD10List() != null) {
			for (DeathClaimICD10 dIcd10 : deathClaimDTO.getDeathClaimICD10List()) {
				deathClaim.addDeathClaimICD10(dIcd10);
			}
		}

		if (deathClaimDTO.getRecorder() != null) {
			deathClaim.setRecorder(deathClaimDTO.getRecorder());
		}
		return deathClaim;
	}

	public static DeathClaimDTO createDeathClaimDTO(DeathClaim deathClaim) {
		DeathClaimDTO deathClaimDTO = new DeathClaimDTO();
		if (deathClaim.getId() != null) {
			deathClaimDTO.setId(deathClaim.getId());
			deathClaimDTO.setVersion(deathClaim.getVersion());
			deathClaimDTO.setExistEntity(true);
		}
		deathClaimDTO.setClaimRole(deathClaim.getClaimRole());
		deathClaimDTO.setApproved(deathClaim.isApproved());
		deathClaimDTO.setRejectReason(deathClaim.getRejectReason());
		deathClaimDTO.setDeathDate(deathClaim.getDeathDate());
		deathClaimDTO.setDeathClaimAmount(deathClaim.getDeathClaimAmount());
		deathClaimDTO.setDeathPlace(deathClaim.getDeathPlace());
		deathClaimDTO.setOtherPlace(deathClaim.getOtherPlace());
		for (Attachment dca : deathClaim.getAttachmentList()) {
			deathClaimDTO.addAttachment(new AttachmentDTO(dca));
		}
		if (deathClaim.getDeathClaimICD10List() != null) {
			deathClaimDTO.setDeathClaimICD10List(deathClaim.getDeathClaimICD10List());
		}
		if (deathClaim.getRecorder() != null) {
			deathClaimDTO.setRecorder(deathClaim.getRecorder());
		}
		return deathClaimDTO;
	}
}

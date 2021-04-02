package org.ace.insurance.web.manage.medical.claim.factory;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.medical.claim.OperationClaim;
import org.ace.insurance.web.manage.life.proposal.AttachmentDTO;
import org.ace.insurance.web.manage.medical.claim.OperationClaimDTO;

public class OperationClaimFactory {
	public static OperationClaim createOperationClaim(OperationClaimDTO operationClaimDTO) {
		OperationClaim operationClaim = new OperationClaim();
		if (operationClaimDTO.isExistEntity()) {

			operationClaim.setVersion(operationClaimDTO.getVersion());
		}
		operationClaim.setId(operationClaimDTO.getId());
		operationClaim.setClaimRole(operationClaimDTO.getClaimRole());
		operationClaim.setApproved(operationClaimDTO.isApproved());
		operationClaim.setRejectReason(operationClaimDTO.getRejectReason());
		operationClaim.setOperation(operationClaimDTO.getOperation());
		operationClaim.setOperationDate(operationClaimDTO.getOperationDate());
		operationClaim.setOperationFee(operationClaimDTO.getOperationFee());
		operationClaim.setOperationReason(operationClaimDTO.getOperationReason());
		operationClaim.setOperationRemark(operationClaimDTO.getOperationRemark());
		for (AttachmentDTO oca : operationClaimDTO.getAttachmentList()) {
			operationClaim.addAttachment(new Attachment(oca));
		}
		if (operationClaimDTO.getRecorder() != null) {
			operationClaim.setRecorder(operationClaimDTO.getRecorder());
		}

		return operationClaim;
	}

	public static OperationClaimDTO createOperationClaimDTO(OperationClaim operationClaim) {
		OperationClaimDTO operationClaimDTO = new OperationClaimDTO();
		if (operationClaim.getId() != null) {
			operationClaimDTO.setId(operationClaim.getId());
			operationClaimDTO.setVersion(operationClaim.getVersion());
			operationClaimDTO.setExistEntity(true);
		}
		operationClaimDTO.setClaimRole(operationClaim.getClaimRole());
		operationClaimDTO.setApproved(operationClaim.isApproved());
		operationClaimDTO.setRejectReason(operationClaim.getRejectReason());
		operationClaimDTO.setOperation(operationClaim.getOperation());
		operationClaimDTO.setOperationDate(operationClaim.getOperationDate());
		operationClaimDTO.setOperationFee(operationClaim.getOperationFee());
		operationClaimDTO.setOperationReason(operationClaim.getOperationReason());
		operationClaimDTO.setOperationRemark(operationClaim.getOperationRemark());
		for (Attachment oca : operationClaim.getAttachmentList()) {
			operationClaimDTO.addAttachment(new AttachmentDTO(oca));
		}
		if (operationClaim.getRecorder() != null) {
			operationClaimDTO.setRecorder(operationClaim.getRecorder());
		}
		return operationClaimDTO;
	}
}

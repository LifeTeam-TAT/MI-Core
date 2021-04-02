package org.ace.insurance.web.manage.medical.claim;

import org.ace.insurance.web.common.CommonDTO;

public class MedicalClaimDTO extends CommonDTO {
	private MedicalClaimProposalDTO medicalClaimProposal;
	private boolean isApproved;
	private String rejectReason;
	private String claimRole;

	public MedicalClaimDTO() {

	}

	public MedicalClaimDTO(MedicalClaimDTO medicalClaimDTO) {
		super(medicalClaimDTO.isExistsEntity(), medicalClaimDTO.getVersion(), medicalClaimDTO.getId(), medicalClaimDTO.getRecorder());
		this.medicalClaimProposal = medicalClaimDTO.getMedicalClaimProposal();
		this.isApproved = medicalClaimDTO.isApproved;
		this.rejectReason = medicalClaimDTO.getRejectReason();
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public MedicalClaimProposalDTO getMedicalClaimProposal() {
		return medicalClaimProposal;
	}

	public void setMedicalClaimProposal(MedicalClaimProposalDTO medicalClaimProposal) {
		this.medicalClaimProposal = medicalClaimProposal;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getClaimRole() {
		return claimRole;
	}

	public void setClaimRole(String claimRole) {
		this.claimRole = claimRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((claimRole == null) ? 0 : claimRole.hashCode());
		result = prime * result + (isApproved ? 1231 : 1237);
		result = prime
				* result
				+ ((medicalClaimProposal == null) ? 0 : medicalClaimProposal
						.hashCode());
		result = prime * result
				+ ((rejectReason == null) ? 0 : rejectReason.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalClaimDTO other = (MedicalClaimDTO) obj;
		if (claimRole == null) {
			if (other.claimRole != null)
				return false;
		} else if (!claimRole.equals(other.claimRole))
			return false;
		if (isApproved != other.isApproved)
			return false;
		if (medicalClaimProposal == null) {
			if (other.medicalClaimProposal != null)
				return false;
		} else if (!medicalClaimProposal.equals(other.medicalClaimProposal))
			return false;
		if (rejectReason == null) {
			if (other.rejectReason != null)
				return false;
		} else if (!rejectReason.equals(other.rejectReason))
			return false;
		return true;
	}
}

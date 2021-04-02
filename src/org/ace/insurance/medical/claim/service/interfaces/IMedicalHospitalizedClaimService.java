package org.ace.insurance.medical.claim.service.interfaces;

import org.ace.insurance.medical.claim.HospitalizedClaim;

public interface IMedicalHospitalizedClaimService {
	public HospitalizedClaim findMedicalClaimById(String id);

	// public HospitalizedClaimDTO findMedicalClaimBeneficiaryByRefundNo(String
	// refundNo);

	// public HospitalizedClaimDTO findHospitalizedClaimById(String id);
}

package org.ace.insurance.medical.claim.service.interfaces;

import org.ace.insurance.medical.claim.DeathClaim;

public interface IMedicalDeathClaimService {

	public DeathClaim findMedicalClaimById(String id);

	// public DeathClaimDTO findDeathClaimById(String id);

	// public DeathClaimDTO findMedicalDeathClaimPayment(String refundNo);
}

package org.ace.insurance.medical.claim.frontService.approved.interfaces;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.medical.claim.DeathClaim;

public interface IApprovedMedicalDeathClaimFrontService {
	public void approveMedicalDeathClaim(DeathClaim deathClaim, WorkFlowDTO workFlowDTO);
}

package org.ace.insurance.medical.claim.frontService.request.interfaces;

import java.util.List;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonBeneficiaries;

public interface IMedicalDeathClaimRequestFrontService {

	public DeathClaim addNewDeathClaim(DeathClaim deathClaim, List<MedicalPolicyInsuredPersonBeneficiaries> polInsuPersonBeneficiaryList, WorkFlowDTO workflowDTO);

	public DeathClaim updatEditDeathClaimConfrim(DeathClaim deathClaim, List<MedicalPolicyInsuredPersonBeneficiaries> polInsuPersonBeneficiaryList, WorkFlowDTO workflowDTO);
}

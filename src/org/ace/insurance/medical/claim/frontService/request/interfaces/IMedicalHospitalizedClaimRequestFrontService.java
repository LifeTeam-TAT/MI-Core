package org.ace.insurance.medical.claim.frontService.request.interfaces;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.medical.claim.HospitalizedClaim;

public interface IMedicalHospitalizedClaimRequestFrontService {

	public HospitalizedClaim addNewHospitalizedClaim(HospitalizedClaim hospitalizedClaim, WorkFlowDTO workflowDTO);

	public HospitalizedClaim updatEditHospitalizedClaimConfrim(HospitalizedClaim hospitalizedClaim, WorkFlowDTO workflowDTO);
}

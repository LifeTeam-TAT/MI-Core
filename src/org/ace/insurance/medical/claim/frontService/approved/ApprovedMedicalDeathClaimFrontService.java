package org.ace.insurance.medical.claim.frontService.approved;

import javax.annotation.Resource;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.claim.frontService.approved.interfaces.IApprovedMedicalDeathClaimFrontService;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimBeneDAO;
import org.ace.insurance.medical.policy.persistence.interfaces.IMedicalPolicyDAO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ApprovedMedicalDeathClaimFrontService")
public class ApprovedMedicalDeathClaimFrontService extends BaseService implements IApprovedMedicalDeathClaimFrontService {
	@Resource(name = "MedicalClaimBeneDAO")
	private IMedicalClaimBeneDAO medicalClaimBeneDAO;

	@Resource(name = "MedicalPolicyDAO")
	private IMedicalPolicyDAO medicalPolicyDAO;
	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void approveMedicalDeathClaim(DeathClaim deathClaim, WorkFlowDTO workFlowDTO) {
		try {
			workFlowService.updateWorkFlow(workFlowDTO);
			// medicalClaimBeneDAO.update(deathClaim.getDeathPerson());
			/*
			 * for (MedicalClaimBeneficiary mBeneficiaries :
			 * deathClaim.getMedicalClaimBeneficiariesList()) { String[]
			 * beneficiaries = mBeneficiaries.getBeneficiaryNo().split(","); for
			 * (String benef : beneficiaries) {
			 * medicalPolicyDAO.updateBeneficiaryClaimStatusByBeneficiaryNo
			 * (benef, ClaimStatus.WAITING); } }
			 */
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MedicalClaim", e);
		}
	}
}

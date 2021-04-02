package org.ace.insurance.medical.claim.frontService.request;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.claim.frontService.request.interfaces.IMedicalDeathClaimRequestFrontService;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonBeneficiaries;
import org.ace.insurance.medical.policy.persistence.interfaces.IMedicalPolicyDAO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalDeathClaimRequestFrontService")
public class MedicalDeathClaimRequestFrontService extends BaseService implements IMedicalDeathClaimRequestFrontService {

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "MedicalPolicyDAO")
	private IMedicalPolicyDAO medicalPolicyDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public DeathClaim addNewDeathClaim(DeathClaim deathClaim, List<MedicalPolicyInsuredPersonBeneficiaries> polInsuPersonBeneficiaryList, WorkFlowDTO workflowDTO) {
		try {
			// deathClaim.setClaimRequestId(customIDGenerator.getNextId(SystemConstants.DEATHCLAIM_ID_NO,
			// null));
			calculateClaimAmount(deathClaim);
			for (MedicalPolicyInsuredPersonBeneficiaries mBeneficiaries : polInsuPersonBeneficiaryList) {
				medicalPolicyDAO.updatePolicyInsuBeneByBeneficiaryNo(mBeneficiaries);
			}
			// deathClaim = claimDAO.insert(deathClaim);
			// workflowDTO.setReferenceNo(deathClaim.getClaimRequestId());
			workFlowDTOService.addNewWorkFlow(workflowDTO);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a Death Claim", e);
		}
		return deathClaim;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public DeathClaim updatEditDeathClaimConfrim(DeathClaim deathClaim, List<MedicalPolicyInsuredPersonBeneficiaries> polInsuPersonBeneficiaryList, WorkFlowDTO workflowDTO) {
		try {
			calculateClaimAmount(deathClaim);
			for (MedicalPolicyInsuredPersonBeneficiaries mBeneficiaries : polInsuPersonBeneficiaryList) {
				medicalPolicyDAO.updatePolicyInsuBeneByBeneficiaryNo(mBeneficiaries);
			}
			// claimDAO.update(deathClaim);
			// workflowDTO.setReferenceNo(deathClaim.getClaimRequestId());
			workFlowDTOService.updateWorkFlow(workflowDTO, workflowDTO.getWorkflowTask());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Death Claim", e);
		}
		return deathClaim;
	}

	public void calculateClaimAmount(DeathClaim deathClaim) {/*
															 * double
															 * claimAmount =
															 * 0.0; //Unit unit
															 * = deathClaim.
															 * getDeathPerson().
															 * getPolicyInsuredPerson
															 * ().getUnit();
															 * 
															 * if
															 * (unit.equals(Unit
															 * .ONE)) {
															 * claimAmount =
															 * CommonSettingConfig
															 * .
															 * getDeathClaimAmountForOneUnit
															 * (); } else if
															 * (unit
															 * .equals(Unit.
															 * TWO)) {
															 * claimAmount =
															 * CommonSettingConfig
															 * .
															 * getDeathClaimAmountForTwoUnit
															 * (); } else {
															 * claimAmount =
															 * CommonSettingConfig
															 * .
															 * getDeathClaimAmountForThreeUnit
															 * (); }
															 * 
															 * for (
															 * MedicalClaimBeneficiary
															 * mcb : deathClaim.
															 * getMedicalClaimBeneficiariesList
															 * ()) { double
															 * claimAmountByPer
															 * = claimAmount *
															 * (mcb
															 * .getPercentage()
															 * / 100);
															 * mcb.setClaimAmount
															 * (
															 * claimAmountByPer)
															 * ;
															 * mcb.setUnit(unit
															 * ); }
															 */
	}

}

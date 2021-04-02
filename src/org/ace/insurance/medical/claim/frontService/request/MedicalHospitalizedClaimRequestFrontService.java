package org.ace.insurance.medical.claim.frontService.request;

import javax.annotation.Resource;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.frontService.request.interfaces.IMedicalHospitalizedClaimRequestFrontService;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalHospitalizedClaimRequestFrontService")
public class MedicalHospitalizedClaimRequestFrontService extends BaseService implements IMedicalHospitalizedClaimRequestFrontService {

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "MedicalHospitalizedClaimDAO")
	private IMedicalHospitalizedClaimDAO claimDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public HospitalizedClaim addNewHospitalizedClaim(HospitalizedClaim hospitalizedClaim, WorkFlowDTO workflowDTO) {
		try {
			// hospitalizedClaim.setClaimRequestId(customIDGenerator.getNextId(SystemConstants.MEDICALCLAIM_ID_NO,
			// null));
			calculateClaimAmount(hospitalizedClaim);
			hospitalizedClaim = claimDAO.insert(hospitalizedClaim);
			// workflowDTO.setReferenceNo(hospitalizedClaim.getClaimRequestId());
			workFlowDTOService.addNewWorkFlow(workflowDTO);
		} catch (DAOException e) {

			throw new SystemException(e.getErrorCode(), "Faield to add a newClaim", e);
		}
		return hospitalizedClaim;
	}

	public void calculateClaimAmount(HospitalizedClaim hospitalizedClaim) {/*
																			 * double
																			 * claimAmount
																			 * =
																			 * 0.0
																			 * ;
																			 * Unit
																			 * unit
																			 * =
																			 * hospitalizedClaim
																			 * .
																			 * getHospitalizedPerson
																			 * (
																			 * )
																			 * .
																			 * getPolicyInsuredPerson
																			 * (
																			 * )
																			 * .
																			 * getUnit
																			 * (
																			 * )
																			 * ;
																			 * int
																			 * totalHospitalizedDays
																			 * =
																			 * Utils
																			 * .
																			 * daysBetween
																			 * (
																			 * hospitalizedClaim
																			 * .
																			 * getHospitalizedPerson
																			 * (
																			 * )
																			 * .
																			 * getHospitalizedStartDate
																			 * (
																			 * )
																			 * ,
																			 * hospitalizedClaim
																			 * .
																			 * getHospitalizedPerson
																			 * (
																			 * )
																			 * .
																			 * getHospitalizedEndDate
																			 * (
																			 * )
																			 * ,
																			 * false
																			 * ,
																			 * true
																			 * )
																			 * ;
																			 * 
																			 * if(
																			 * totalHospitalizedDays
																			 * >
																			 * CommonSettingConfig
																			 * .
																			 * getTotalHospitalizationDays
																			 * (
																			 * )
																			 * )
																			 * {
																			 * totalHospitalizedDays
																			 * =
																			 * CommonSettingConfig
																			 * .
																			 * getTotalHospitalizationDays
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * 
																			 * if
																			 * (
																			 * unit
																			 * .
																			 * equals
																			 * (
																			 * Unit
																			 * .
																			 * ONE
																			 * )
																			 * )
																			 * {
																			 * claimAmount
																			 * =
																			 * totalHospitalizedDays
																			 * *
																			 * (
																			 * MedicalHospitalizedClaimPremiumConfig
																			 * .
																			 * getMedHosClaimCalAmount
																			 * (
																			 * Unit
																			 * .
																			 * ONE
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * else
																			 * if
																			 * (
																			 * unit
																			 * .
																			 * equals
																			 * (
																			 * Unit
																			 * .
																			 * TWO
																			 * )
																			 * )
																			 * {
																			 * claimAmount
																			 * =
																			 * totalHospitalizedDays
																			 * *
																			 * (
																			 * MedicalHospitalizedClaimPremiumConfig
																			 * .
																			 * getMedHosClaimCalAmount
																			 * (
																			 * Unit
																			 * .
																			 * TWO
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * else
																			 * {
																			 * claimAmount
																			 * =
																			 * totalHospitalizedDays
																			 * *
																			 * (
																			 * MedicalHospitalizedClaimPremiumConfig
																			 * .
																			 * getMedHosClaimCalAmount
																			 * (
																			 * Unit
																			 * .
																			 * THREE
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * hospitalizedClaim
																			 * .
																			 * getMedicalClaimBeneficiariesList
																			 * (
																			 * )
																			 * .
																			 * get
																			 * (
																			 * 0
																			 * )
																			 * .
																			 * setClaimAmount
																			 * (
																			 * claimAmount
																			 * )
																			 * ;
																			 * hospitalizedClaim
																			 * .
																			 * getMedicalClaimBeneficiariesList
																			 * (
																			 * )
																			 * .
																			 * get
																			 * (
																			 * 0
																			 * )
																			 * .
																			 * setNoOfHospDays
																			 * (
																			 * totalHospitalizedDays
																			 * )
																			 * ;
																			 * hospitalizedClaim
																			 * .
																			 * getMedicalClaimBeneficiariesList
																			 * (
																			 * )
																			 * .
																			 * get
																			 * (
																			 * 0
																			 * )
																			 * .
																			 * setUnit
																			 * (
																			 * unit
																			 * )
																			 * ;
																			 */
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public HospitalizedClaim updatEditHospitalizedClaimConfrim(HospitalizedClaim hospitalizedClaim, WorkFlowDTO workflowDTO) {
		try {
			calculateClaimAmount(hospitalizedClaim);
			claimDAO.update(hospitalizedClaim);
			// workflowDTO.setReferenceNo(hospitalizedClaim.getClaimRequestId());
			workFlowDTOService.updateWorkFlow(workflowDTO, workflowDTO.getWorkflowTask());
		} catch (DAOException e) {

			throw new SystemException(e.getErrorCode(), "Faield to add a newClaim", e);
		}
		return hospitalizedClaim;
	}
}

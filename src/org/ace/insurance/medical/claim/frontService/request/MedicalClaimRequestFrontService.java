package org.ace.insurance.medical.claim.frontService.request;

import javax.annotation.Resource;

import org.ace.insurance.medical.claim.frontService.request.interfaces.IMedicalClaimRequestFrontService;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimInitialRepDAO;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimProposalDAO;
import org.ace.insurance.medical.policy.persistence.interfaces.IMedicalPolicyDAO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "MedicalClaimRequestFrontService")
public class MedicalClaimRequestFrontService extends BaseService implements IMedicalClaimRequestFrontService {

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "MedicalClaimProposalDAO")
	private IMedicalClaimProposalDAO medicalClaimProposalDAO;

	@Resource(name = "MedicalPolicyDAO")
	private IMedicalPolicyDAO medicalPolicyDAO;

	@Resource(name = "MedicalClaimInitialRepDAO")
	private IMedicalClaimInitialRepDAO medicalClaimInitialRepDAO;
//MZP
	// @Transactional(propagation = Propagation.REQUIRED)
	// public MedicalClaimProposalDTO addNewMedicalClaim(MedicalClaimProposalDTO
	// medicalClaimProposalDTO, MedicalClaimInitialReportDTO
	// medicalClaimInitialReportDTO,
	// List<MedicalPolicyInsuredPersonBeneficiaries>
	// polInsuPersonBeneficiaryList, WorkFlowDTO workflowDTO) {
	// MedicalClaimProposal medicalClaimProposal;
	// try {
	// medicalClaimProposalDTO.setClaimRequestId(customIDGenerator.getNextId(SystemConstants.MEDICALCLAIMPROPOSAL_ID_NO,
	// null));
	// calculateClaimAmount(medicalClaimProposalDTO);
	// MedicalPolicyInsuredPerson policyInsuredPerson =
	// MedicalPolicyInsuredPersonFactory
	// .createMedicalPolicyInsuredPerson(medicalClaimProposalDTO.getPolicyInsuredPersonDTO());
	// policyInsuredPerson.setPolicyInsuredPersonBeneficiariesList(new
	// ArrayList<MedicalPolicyInsuredPersonBeneficiaries>());
	// for (MedicalPolicyInsuredPersonBeneficiaries mBeneficiaries :
	// polInsuPersonBeneficiaryList) {
	// policyInsuredPerson.addInsuredPersonBeneficiaries(mBeneficiaries);
	// }
	// medicalClaimProposal =
	// medicalClaimProposalDAO.insert(MedicalClaimProposalFactory.createMedicalClaimProposal(medicalClaimProposalDTO));
	// workflowDTO.setReferenceNo(medicalClaimProposal.getId());
	// workFlowDTOService.addNewWorkFlow(workflowDTO);
	//
	// medicalClaimInitialReportDTO.setClaimStatus(ClaimStatus.CLAIM_APPLIED);
	// medicalClaimInitialRepDAO.update(MedicalClaimInitialReportFactory.createMedicalClaimInitialReport(medicalClaimInitialReportDTO));
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to add a Death
	// Claim", e);
	// }
	// return medicalClaimProposalDTO;
	// }

	// @Transactional(propagation = Propagation.REQUIRED)
	// public MedicalClaimProposalDTO updatMedicalClaim(MedicalClaimProposalDTO
	// medicalClaimProposalDTO, WorkFlowDTO workflowDTO) {
	// MedicalClaimProposal medicalClaimProposal;
	// try {
	// medicalClaimProposal =
	// medicalClaimProposalDAO.update(MedicalClaimProposalFactory.createMedicalClaimProposal(medicalClaimProposalDTO));
	// List<MedicalPolicyInsuredPersonBeneficiaries>
	// polInsuPersonBeneficiaryList =
	// medicalClaimProposal.getPolicyInsuredPerson().getPolicyInsuredPersonBeneficiariesList();
	// for (MedicalPolicyInsuredPersonBeneficiaries mBeneficiaries :
	// polInsuPersonBeneficiaryList) {
	// medicalPolicyDAO.updatePolicyInsuBeneByBeneficiaryNo(mBeneficiaries);
	// }
	// workflowDTO.setReferenceNo(medicalClaimProposal.getId());
	// workFlowDTOService.updateWorkFlow(workflowDTO,
	// workflowDTO.getWorkflowTask());
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to update a Death
	// Claim", e);
	// }
	// return medicalClaimProposalDTO;
	// }

	// public void calculateClaimAmount(MedicalClaimProposalDTO
	// medicalClaimProposalDTO) {
	// double claimAmount = 0.0;
	//
	// double total_hospClaimAmount = 0.0;
	// double total_deathClaimAmount = 0.0;
	// double total_medicationClaimAmount = 0.0;
	// double total_operationClaimAmount = 0.0;
	//
	// int totalHospitalizedDays = 0;
	// MedicalPolicyInsuredPersonDTO claimPersonDTO =
	// medicalClaimProposalDTO.getPolicyInsuredPersonDTO();
	// int unit = claimPersonDTO.getUnit();
	// int basicPlusUnit = claimPersonDTO.getBasicPlusUnit();
	// int addOnOneUnit = 0;
	// int addOnTwoUnit = 0;
	//
	// for (MedicalPolicyInsuredPersonAddOn addOn :
	// claimPersonDTO.getAddOnList()) {
	// if (KeyFactorIDConfig.getMedAddOn2().equals(addOn.getAddOn().getId())) {
	// addOnOneUnit = addOn.getUnit();
	// } else {
	// addOnTwoUnit = addOn.getUnit();
	// }
	// }
	//
	// if (medicalClaimProposalDTO.isDeath()) {
	// DeathClaimDTO deathClaimDTO = medicalClaimProposalDTO.getDeathClaimDTO();
	// total_deathClaimAmount =
	// CommonSettingConfig.getDeathClaimAmountForBased() * unit;
	// if
	// (DeathClaimType.ACCIDENT_DEATH.equals(deathClaimDTO.getDeathClaimType()))
	// {
	// total_deathClaimAmount =
	// CommonSettingConfig.getDeathClaimAmountForNotAccident() * unit;
	// }
	// total_deathClaimAmount +=
	// CommonSettingConfig.getDeathClaimAmountForBasicPlus() * basicPlusUnit;
	// deathClaimDTO.setDeathClaimAmount(total_deathClaimAmount);
	// claimAmount += total_deathClaimAmount;
	// }
	// if (medicalClaimProposalDTO.isHospitalized()) {
	// HospitalizedClaimDTO hospitalizedClaimDTO =
	// medicalClaimProposalDTO.getHospitalizedClaimDTO();
	// totalHospitalizedDays =
	// Utils.daysBetween(hospitalizedClaimDTO.getHospitalizedStartDate(),
	// hospitalizedClaimDTO.getHospitalizedEndDate(), false, true);
	// total_hospClaimAmount +=
	// CommonSettingConfig.getHospitalizedClaimAmountForBased() * unit *
	// totalHospitalizedDays;
	// total_hospClaimAmount +=
	// CommonSettingConfig.getHospitalizedClaimAmountForBasicPlus() *
	// basicPlusUnit * totalHospitalizedDays;
	// total_hospClaimAmount +=
	// CommonSettingConfig.getHospitalizedClaimAmountForAddOnTwo() *
	// addOnTwoUnit;
	// hospitalizedClaimDTO.setHospitalizedAmount(total_hospClaimAmount);
	// claimAmount += total_hospClaimAmount;
	// }
	// if (medicalClaimProposalDTO.isMedication()) {
	// MedicationClaimDTO medicationClaimDTO =
	// medicalClaimProposalDTO.getMedicationClaimDTO();
	// total_medicationClaimAmount +=
	// CommonSettingConfig.getDisabilityAmountForBased() * unit;
	// total_medicationClaimAmount +=
	// CommonSettingConfig.getDisabilityAmountForBasicPlus() * basicPlusUnit;
	// medicationClaimDTO.setMedicationFee(total_medicationClaimAmount);
	// claimAmount += total_medicationClaimAmount;
	// }
	// if (medicalClaimProposalDTO.isOperation()) {
	// OperationClaimDTO operationClaimDTO =
	// medicalClaimProposalDTO.getOperationClaimDTO();
	// total_operationClaimAmount +=
	// CommonSettingConfig.getOperationClaimForAddOnOne() * addOnOneUnit;
	// total_operationClaimAmount +=
	// CommonSettingConfig.getOperationClaimForAddOnTwo() * addOnTwoUnit;
	// operationClaimDTO.setOperationFee(total_operationClaimAmount);
	// claimAmount += total_operationClaimAmount;
	// }
	// if (medicalClaimProposalDTO.getMedicalClaimBeneficiariesList() != null &&
	// medicalClaimProposalDTO.getMedicalClaimBeneficiariesList().size() != 0) {
	// for (MedicalClaimBeneficiaryDTO dto :
	// medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) {
	// if (dto.getMedPolicyInsuBeneDTO() != null) {
	// double claimAmountByPer = claimAmount *
	// dto.getMedPolicyInsuBeneDTO().getPercentage() / 100;
	// double hospitalizedAmountByPer = total_hospClaimAmount *
	// dto.getMedPolicyInsuBeneDTO().getPercentage() / 100;
	// double operationAmountByPer = total_operationClaimAmount *
	// dto.getMedPolicyInsuBeneDTO().getPercentage() / 100;
	// double medicationAmountByPer = total_medicationClaimAmount *
	// dto.getMedPolicyInsuBeneDTO().getPercentage() / 100;
	// double deathClaimAmountByPer = total_deathClaimAmount *
	// dto.getMedPolicyInsuBeneDTO().getPercentage() / 100;
	// dto.setClaimAmount(claimAmountByPer);
	// dto.setHospClaimAmount(hospitalizedAmountByPer);
	// dto.setOperClaimAmount(operationAmountByPer);
	// dto.setMediClaimAmount(medicationAmountByPer);
	// dto.setDeathClaimAmount(deathClaimAmountByPer);
	// dto.setUnit(unit);
	// dto.setNoOfHospDays(totalHospitalizedDays);
	// } else if (dto.getMedicalPolicyInsuredPersonDTO() != null) {
	// dto.setClaimAmount(claimAmount);
	// dto.setHospClaimAmount(total_hospClaimAmount);
	// dto.setOperClaimAmount(total_operationClaimAmount);
	// dto.setMediClaimAmount(total_medicationClaimAmount);
	// dto.setDeathClaimAmount(total_deathClaimAmount);
	// dto.setUnit(unit);
	// dto.setNoOfHospDays(totalHospitalizedDays);
	// } else {
	// double claimAmountByPer = claimAmount * dto.getPercentage() / 100;
	// double hospitalizedAmountByPer = total_hospClaimAmount *
	// dto.getPercentage() / 100;
	// double operationAmountByPer = total_operationClaimAmount *
	// dto.getPercentage() / 100;
	// double medicationAmountByPer = total_medicationClaimAmount *
	// dto.getPercentage() / 100;
	// double deathClaimAmountByPer = total_deathClaimAmount *
	// dto.getPercentage() / 100;
	// dto.setClaimAmount(claimAmountByPer);
	// dto.setHospClaimAmount(hospitalizedAmountByPer);
	// dto.setOperClaimAmount(operationAmountByPer);
	// dto.setMediClaimAmount(medicationAmountByPer);
	// dto.setDeathClaimAmount(deathClaimAmountByPer);
	// dto.setUnit(unit);
	// dto.setNoOfHospDays(totalHospitalizedDays);
	// }
	//
	// }
	// }
	// }
}

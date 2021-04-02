package org.ace.insurance.medical.claim.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.claim.MC001;
import org.ace.insurance.medical.claim.MedicalClaimProposal;
import org.ace.insurance.medical.claim.frontService.initialReport.interfaces.IMedicalClaimInitialReportFrontService;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimProposalDAO;
import org.ace.insurance.medical.claim.service.interfaces.IMedicalClaimProposalService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.report.claim.MedicalClaimMonthlyReport;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.web.manage.enquires.ClaimEnquiryCriteria;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalClaimProposalService")
public class MedicalClaimProposalService implements IMedicalClaimProposalService {

	@Resource(name = "ClaimAcceptedInfoService")
	private IClaimAcceptedInfoService claimAcceptedInfoService;

	@Resource(name = "MedicalClaimInitialReportFrontService")
	private IMedicalClaimInitialReportFrontService medicalClaimInitialReportFrontService;

	@Resource(name = "MedicalClaimProposalDAO")
	private IMedicalClaimProposalDAO claimDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalClaimProposal findMedicalClaimProposalById(String id) {
		MedicalClaimProposal medicalClaim = null;
		try {
			medicalClaim = claimDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a HospitalizedClaim(ID : " + id + ")", e);
		}
		return medicalClaim;
	}
//MZP
	// @Transactional(propagation = Propagation.REQUIRED)
	// public HospitalizedClaimDTO findHospitalizedClaimById(String id) {
	// HospitalizedClaimDTO result = null;
	// try {
	// HospitalizedClaim hospitalizedClaim =
	// claimDAO.findHospitalizedClaimById(id);
	// result = changeHospitalizedClaimDataToClaimDTO(hospitalizedClaim);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to find a
	// HospitalizedClaim(ID : " + id + ")", e);
	// }
	// return result;
	// }
	//
	// @Transactional(propagation = Propagation.REQUIRED)
	// public MedicationClaimDTO findMedicationClaimById(String id) {
	// MedicationClaimDTO result = null;
	// try {
	// MedicationClaim medicationClaim = claimDAO.findMedicationClaimById(id);
	// result = changeMedicationClaimDataToClaimDTO(medicationClaim);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to find a
	// MedicationClaim(ID : " + id + ")", e);
	// }
	// return result;
	// }

	// @Transactional(propagation = Propagation.REQUIRED)
	// public OperationClaimDTO findOperationClaimById(String id) {
	// OperationClaimDTO result = null;
	// try {
	// OperationClaim operationClaim = claimDAO.findOperationClaimById(id);
	// result = changeOperationClaimDataToClaimDTO(operationClaim);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to find a
	// OperationClaim(ID : " + id + ")", e);
	// }
	// return result;
	// }

	// @Transactional(propagation = Propagation.REQUIRED)
	// public DeathClaimDTO findDeathClaimById(String id) {
	// DeathClaimDTO result = null;
	// try {
	// DeathClaim deathClaim = claimDAO.findDeathClaimById(id);
	// result = changeDeathClaimDataToClaimDTO(deathClaim);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to find a
	// DeathClaim(ID : " + id + ")", e);
	// }
	// return result;
	// }

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMedicalClaimProposal(MedicalClaimProposal medicalClaimProposal, WorkFlowDTO workFlow) {
		try {
			if (workFlow.getWorkflowTask().equals(WorkflowTask.APPROVAL)) {
				workFlowDTOService.updateWorkFlow(workFlow);
				claimDAO.update(medicalClaimProposal);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update medical claim proposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MC001> findAllMedicalClaimProposal(ClaimEnquiryCriteria enquiryCriteria) {
		List<MC001> result = null;
		try {
			result = claimDAO.findByEnquiryCriteria(enquiryCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MedicalPolicy)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MC001> findAllMedicalClaimProposalByPolicyId(String id) {
		List<MC001> result = null;
		try {
			result = claimDAO.findByPolicyId(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MedicalPolicy)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void informMedicalClaimProposal(WorkFlowDTO workflowDTO, ClaimAcceptedInfo claimAcceptedInfo) {
		try {
			workFlowDTOService.updateWorkFlow(workflowDTO);
			if (claimAcceptedInfo != null) {
				claimAcceptedInfoService.addNewClaimAcceptedInfo(claimAcceptedInfo);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to inform Medical Claim", e);
		}
	}
//MZP
	// @Transactional(propagation = Propagation.REQUIRED)
	// public void paymentMedicalClaimProposal(Payment payment,
	// MedicalClaimProposalDTO medicalClaimProposalDTO) {
	// try {
	// workFlowDTOService.deleteWorkFlowByRefNo(medicalClaimProposalDTO.getId());
	// //
	// medicalClaimInitialReportFrontService.updateByInsuredPersonId(medicalClaimProposalDTO.getPolicyInsuredPersonDTO().getId(),
	// // ClaimStatus.PAID);
	// paymentService.activateMedicalClaimPayment(payment);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to Payment Medical
	// Claim", e);
	// }
	//
	// }

	// private HospitalizedClaimDTO
	// changeHospitalizedClaimDataToClaimDTO(HospitalizedClaim
	// hospitalizedClaim) {
	// HospitalizedClaimDTO hospitalizedClaimDTO =
	// HospitalizedClaimFactory.createHospitalizedClaimDTO(hospitalizedClaim);
	// return hospitalizedClaimDTO;
	// }
	//
	// private MedicationClaimDTO
	// changeMedicationClaimDataToClaimDTO(MedicationClaim medicationClaim) {
	// MedicationClaimDTO medicationClaimDTO =
	// MedicationClaimFactory.createMedicationClaimDTO(medicationClaim);
	// return medicationClaimDTO;
	// }
	//
	// private OperationClaimDTO
	// changeOperationClaimDataToClaimDTO(OperationClaim operationClaim) {
	// OperationClaimDTO operationClaimDTO =
	// OperationClaimFactory.createOperationClaimDTO(operationClaim);
	// return operationClaimDTO;
	// }

	// private DeathClaimDTO changeDeathClaimDataToClaimDTO(DeathClaim
	// deathClaim) {
	// DeathClaimDTO deathClaimDTO =
	// DeathClaimFactory.createDeathClaimDTO(deathClaim);
	// return deathClaimDTO;
	// }

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalClaimMonthlyReport> findMedicalClaimMonthlyReport(MonthlyReportCriteria criteria) {
		List<MedicalClaimMonthlyReport> result = null;
		try {
			result = claimDAO.findMedicalClaimMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MedicalPolicy)", e);
		}
		return result;
	}

	// @Override
	// public void paymentMedicalClaimProposal(Payment payment,
	// MedicalClaimProposalDTO medicalClaimProposalDTO) {
	// TODO Auto-generated method stub

}

package org.ace.insurance.medical.claim.service;

import javax.annotation.Resource;

import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimBeneDAO;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO;
import org.ace.insurance.medical.claim.service.interfaces.IMedicalHospitalizedClaimService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalHospitalizedClaimService")
public class MedicalHospitalizedClaimService implements IMedicalHospitalizedClaimService {

	@Resource(name = "MedicalClaimBeneDAO")
	private IMedicalClaimBeneDAO medicalClaimBeneDAO;

	@Resource(name = "MedicalHospitalizedClaimDAO")
	private IMedicalHospitalizedClaimDAO claimDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public HospitalizedClaim findMedicalClaimById(String id) {
		HospitalizedClaim result = null;
		try {
			result = claimDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a MedicalProposal (ID : " + id + ")", e);
		}
		return result;
	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public
	 * HospitalizedClaimDTO findMedicalClaimBeneficiaryByRefundNo(String
	 * refundNo) { HospitalizedClaimDTO hospitalizedClaimDTO = null; try {
	 * HospitalizedClaim hospitalizedClaim =
	 * medicalClaimBeneDAO.findByRefundNo(refundNo); hospitalizedClaimDTO =
	 * changeHospClaimDataToClaimDTO(hospitalizedClaim); if
	 * (hospitalizedClaim.isDeath()) { for (MedicalClaimBeneficiary mcb :
	 * hospitalizedClaim.getMedicalClaimBeneficiariesList()) {
	 * SuccessorClaimBeneficiary successorClaimBeneficiary =
	 * claimDAO.findSuccessorClaimBeneficiaryById(mcb.getId());
	 * SuccessorClaimBeneficiaryDTO sucClaimBeneDto =
	 * changeSucceClaimBeneDataToClaimDTO(successorClaimBeneficiary);
	 * hospitalizedClaimDTO.addSuccessorClaimBeneficiaryDTO(sucClaimBeneDto); }
	 * } else { for (MedicalClaimBeneficiary mcb :
	 * hospitalizedClaim.getMedicalClaimBeneficiariesList()) {
	 * HospitalizedClaimBeneficiary hospitalizedClaimBeneficiary =
	 * claimDAO.findHospitalizedClaimBeneficiaryById(mcb.getId());
	 * HospitalizedClaimBeneficiaryDTO hospClaimBeneDto =
	 * changeHospClaimBeneDataToClaimDTO(hospitalizedClaimBeneficiary);
	 * hospitalizedClaimDTO
	 * .addHospitalizedClaimBeneficiaryDTO(hospClaimBeneDto); } } } catch
	 * (DAOException e) { throw new SystemException(e.getErrorCode(),
	 * "Faield to find MedicalClaimBeneficiary by RefundNo : " + refundNo, e); }
	 * return hospitalizedClaimDTO; }
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED) public
	 * HospitalizedClaimDTO findHospitalizedClaimById(String id) {
	 * HospitalizedClaimDTO result = null; try { HospitalizedClaim
	 * hospitalizedClaim = claimDAO.findById(id); result =
	 * changeHospClaimDataToClaimDTO(hospitalizedClaim); if
	 * (hospitalizedClaim.isDeath()) { for (MedicalClaimBeneficiary mcb :
	 * hospitalizedClaim.getMedicalClaimBeneficiariesList()) {
	 * SuccessorClaimBeneficiary successorClaimBeneficiary =
	 * claimDAO.findSuccessorClaimBeneficiaryById(mcb.getId());
	 * SuccessorClaimBeneficiaryDTO sucClaimBeneDto =
	 * changeSucceClaimBeneDataToClaimDTO(successorClaimBeneficiary);
	 * result.addSuccessorClaimBeneficiaryDTO(sucClaimBeneDto); } } else { for
	 * (MedicalClaimBeneficiary mcb :
	 * hospitalizedClaim.getMedicalClaimBeneficiariesList()) {
	 * HospitalizedClaimBeneficiary hospitalizedClaimBeneficiary =
	 * claimDAO.findHospitalizedClaimBeneficiaryById(mcb.getId());
	 * HospitalizedClaimBeneficiaryDTO hospClaimBeneDto =
	 * changeHospClaimBeneDataToClaimDTO(hospitalizedClaimBeneficiary);
	 * result.addHospitalizedClaimBeneficiaryDTO(hospClaimBeneDto); } } } catch
	 * (DAOException e) { throw new SystemException(e.getErrorCode(),
	 * "Faield to find a HospitalizedClaim(ID : " + id + ")", e); } return
	 * result; }
	 * 
	 * private HospitalizedClaimDTO
	 * changeHospClaimDataToClaimDTO(HospitalizedClaim hospitalizedClaim) {
	 * HospitalizedClaimDTO HospitalizedClaimDTO =
	 * HospitalizedClaimFactory.createHospitalizedClaimDTO(hospitalizedClaim);
	 * return HospitalizedClaimDTO; }
	 * 
	 * private SuccessorClaimBeneficiaryDTO
	 * changeSucceClaimBeneDataToClaimDTO(SuccessorClaimBeneficiary
	 * successorClaimBeneficiary) { SuccessorClaimBeneficiaryDTO
	 * successorClaimBeneficiaryDTO =
	 * SuccessorClaimBeneFactory.createSuccessorClaimBeneficiaryDTO
	 * (successorClaimBeneficiary); return successorClaimBeneficiaryDTO; }
	 * 
	 * private HospitalizedClaimBeneficiaryDTO
	 * changeHospClaimBeneDataToClaimDTO(HospitalizedClaimBeneficiary
	 * hospitalizedClaimBeneficiary) { HospitalizedClaimBeneficiaryDTO
	 * hospitalizedClaimBeneficiaryDTO =
	 * MedicalClaimBeneFactoy.createHospitalizedClaimBeneficiaryDTO
	 * (hospitalizedClaimBeneficiary); return hospitalizedClaimBeneficiaryDTO; }
	 */
}

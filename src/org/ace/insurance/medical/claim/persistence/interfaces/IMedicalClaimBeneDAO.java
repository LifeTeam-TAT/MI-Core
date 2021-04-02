package org.ace.insurance.medical.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.MedicalClaimBeneficiary;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalClaimBeneDAO {
	public MedicalClaimBeneficiary findById(String id);

	public List<MedicalClaimBeneficiary> findAll() throws DAOException;

	public void insert(MedicalClaimBeneficiary medicalClaimBeneficiary) throws DAOException;

	public void delete(MedicalClaimBeneficiary medicalClaimBeneficiary) throws DAOException;

	// public void update(MedicalClaimInsuredPerson medicalClaimInsuredPerson)
	// throws DAOException;

	public void update(MedicalClaimBeneficiary medicalClaimBeneficiary) throws DAOException;

	public HospitalizedClaim findByRefundNo(String refundNo) throws DAOException;

	public DeathClaim findDeathClaimByRefundNo(String refundNo) throws DAOException;

	// public HospitalizedClaimBeneficiary
	// findByHospClaimBenewithMedClaimBene(String id) throws DAOException;

	public void updateApproveMedicalClaimBene(String id) throws DAOException;
}

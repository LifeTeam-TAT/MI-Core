package org.ace.insurance.medical.claim.persistence.interfaces;

import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalHospitalizedClaimDAO {

	public HospitalizedClaim insert(HospitalizedClaim claim) throws DAOException;

	public void update(HospitalizedClaim claim) throws DAOException;

	public void delete(HospitalizedClaim claim) throws DAOException;

	public HospitalizedClaim findById(String id) throws DAOException;

	// public SuccessorClaimBeneficiary findSuccessorClaimBeneficiaryById(String
	// id) throws DAOException;

	// public HospitalizedClaimBeneficiary
	// findHospitalizedClaimBeneficiaryById(String id) throws DAOException;

	// public void updateHosPersonMedicalStatus(HospitalizationPerson
	// hospitalizationPerson) throws DAOException;

	public void addHospitalizedAttachment(HospitalizedClaim hospitalizedClaim) throws DAOException;

	// public void insertHospitalizedClaimSurvey(HospitalizedClaimSurvey
	// hospitalizedClaimSurvey) throws DAOException;

}

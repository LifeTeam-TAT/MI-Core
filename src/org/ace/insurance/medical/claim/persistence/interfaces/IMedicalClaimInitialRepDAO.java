package org.ace.insurance.medical.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.medical.claim.ClaimInitialReport;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalClaimInitialRepDAO {

	public ClaimInitialReport insert(ClaimInitialReport medicalClaimInitialReport) throws DAOException;

	public void update(ClaimInitialReport claimInitialReport) throws DAOException;

	public List<ClaimInitialReport> findAll() throws DAOException;

	public List<ClaimInitialReport> findAllActiveClaim() throws DAOException;

	public ClaimInitialReport findByPolicyInsuredPersonId(String id) throws DAOException;

	public void updateByPolicyInsured(String insuredPersonId, ClaimStatus claimStatus) throws DAOException;

}

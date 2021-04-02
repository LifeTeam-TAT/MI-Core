package org.ace.insurance.medical.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.MC001;
import org.ace.insurance.medical.claim.MedicalClaimProposal;
import org.ace.insurance.medical.claim.MedicationClaim;
import org.ace.insurance.medical.claim.OperationClaim;
import org.ace.insurance.report.claim.MedicalClaimMonthlyReport;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.web.manage.enquires.ClaimEnquiryCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalClaimProposalDAO {

	public MedicalClaimProposal findById(String id);

	public List<MedicalClaimProposal> findAll() throws DAOException;

	public MedicalClaimProposal insert(MedicalClaimProposal medicalClaimProposalDTO) throws DAOException;

	public void delete(MedicalClaimProposal medicalClaim) throws DAOException;

	public MedicalClaimProposal update(MedicalClaimProposal medicalClaimProposal) throws DAOException;

	public HospitalizedClaim findHospitalizedClaimById(String id) throws DAOException;

	public DeathClaim findDeathClaimById(String id) throws DAOException;

	public OperationClaim findOperationClaimById(String id) throws DAOException;

	public MedicationClaim findMedicationClaimById(String id) throws DAOException;

	public List<MC001> findByEnquiryCriteria(ClaimEnquiryCriteria criteria) throws DAOException;

	public List<MC001> findByPolicyId(String id) throws DAOException;

	public List<MedicalClaimMonthlyReport> findMedicalClaimMonthlyReport(MonthlyReportCriteria criteria) throws DAOException;
}

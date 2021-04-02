package org.ace.insurance.medical.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.medical.claim.ClaimInitialReport;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimInitialRepDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalClaimInitialRepDAO")
public class MedicalClaimInitialRepDAO extends BasicDAO implements IMedicalClaimInitialRepDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimInitialReport insert(ClaimInitialReport medicalClaimInitialReport) throws DAOException {
		try {
			em.persist(medicalClaimInitialReport);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("failed to insert medicalClaimInitialReport", pe);
		}
		return medicalClaimInitialReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimInitialReport> findAll() throws DAOException {
		List<ClaimInitialReport> result = null;
		try {
			Query q = em.createNamedQuery("ClaimInitialReport.findAll");
			result = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of CliamInitialReport", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimInitialReport> findAllActiveClaim() throws DAOException {
		List<ClaimInitialReport> result = null;
		try {
			Query q = em.createNamedQuery("ClaimInitialReport.findAllActiveClaim");
			result = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of CliamInitialReport", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimInitialReport findByPolicyInsuredPersonId(String id) throws DAOException {
		ClaimInitialReport result;
		try {
			result = em.find(ClaimInitialReport.class, id);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of CliamInitialReport", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ClaimInitialReport claimInitialReport) throws DAOException {
		try {
			em.merge(claimInitialReport);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update CliamInitialReport", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateByPolicyInsured(String insuredPersonId, ClaimStatus claimStatus) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE ClaimInitialReport p SET p.claimStatus =:claimStatus WHERE p.policyInsuredPerson.id =: insuredPersonId");
			q.setParameter("claimStatus", claimStatus.PAID);
			q.setParameter("insuredPersonId", insuredPersonId);
			q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update CliamInitialReport", pe);
		}
	}
}

package org.ace.insurance.medical.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.MedicalClaimBeneficiary;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimBeneDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalClaimBeneDAO")
public class MedicalClaimBeneDAO extends BasicDAO implements IMedicalClaimBeneDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(MedicalClaimBeneficiary medicalClaimBeneficiary) throws DAOException {
		try {
			em.persist(medicalClaimBeneficiary);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("failed to insert medicalClaimBeneficiary", pe);
		}
	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * update(MedicalClaimInsuredPerson medicalClaimInsuredPerson) throws
	 * DAOException { try { em.merge(medicalClaimInsuredPerson);
	 * em.flush();System.gc(); } catch (PersistenceException pe) { throw
	 * translate("failed to update medicalClaimInsuredPerson", pe); } }
	 */

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * updateDeathPerson(DeathPerson deathPerson) throws DAOException { try {
	 * em.merge(deathPerson); em.flush();System.gc(); } catch
	 * (PersistenceException pe) { throw
	 * translate("failed to update medicalClaimInsuredPerson", pe); } }
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(MedicalClaimBeneficiary medicalClaimBeneficiary) throws DAOException {
		try {
			em.merge(medicalClaimBeneficiary);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("failed to update medicalClaimBeneficiary", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(MedicalClaimBeneficiary medicalClaimBeneficiary) throws DAOException {
		try {
			medicalClaimBeneficiary = em.merge(medicalClaimBeneficiary);
			em.remove(medicalClaimBeneficiary);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update medicalClaimBeneficiary", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalClaimBeneficiary findById(String id) throws DAOException {
		MedicalClaimBeneficiary result = null;
		try {
			result = em.find(MedicalClaimBeneficiary.class, id);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaimBeneficiary", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalClaimBeneficiary> findAll() throws DAOException {
		List<MedicalClaimBeneficiary> result = null;
		try {
			Query q = em.createNamedQuery("MedicalClaimBeneficiary.findAll");
			result = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of medicalClaimBeneficiary", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public HospitalizedClaim findByRefundNo(String refundNo) throws DAOException {
		HospitalizedClaim result = null;
		try {
			Query q = em.createQuery("Select mc from MedicalClaimBeneficiary mcb , MedicalClaim mc where mcb.medicalClaim.id = mc.id and mcb.refundNo=:refundNo");
			q.setParameter("refundNo", refundNo);
			result = (HospitalizedClaim) q.getSingleResult();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find claimBeneficiary by RefundNo for Medical Claim : " + refundNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public DeathClaim findDeathClaimByRefundNo(String refundNo) throws DAOException {
		DeathClaim result = null;
		try {
			Query q = em.createQuery("Select mc from MedicalClaimBeneficiary mcb , MedicalClaim mc where mcb.medicalClaim.id = mc.id and mcb.refundNo=:refundNo");
			q.setParameter("refundNo", refundNo);
			result = (DeathClaim) q.getSingleResult();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find claimBeneficiary by RefundNo for Death Claim : " + refundNo, pe);
		}
		return result;
	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public
	 * HospitalizedClaimBeneficiary findByHospClaimBenewithMedClaimBene(String
	 * id) throws DAOException { HospitalizedClaimBeneficiary result = null; try
	 * { Query q = em.createNamedQuery(
	 * "HospitalizedClaimBeneficiary.findByMedialClaimBeneficiaryId");
	 * q.setParameter("id", id); result = (HospitalizedClaimBeneficiary)
	 * q.getSingleResult(); em.flush();System.gc(); } catch
	 * (PersistenceException pe) { throw
	 * translate("Failed to find HospitalizedClaimBeneficiary by RefundNo : " +
	 * id, pe); } return result; }
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateApproveMedicalClaimBene(String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("MedicalClaimBeneficiary.updateApproveStatus");
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find HospitalizedClaimBeneficiary by RefundNo : " + id, pe);
		}
	}

}

package org.ace.insurance.medical.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalHospitalizedClaimDAO")
public class MedicalHospitalizedClaimDAO extends BasicDAO implements IMedicalHospitalizedClaimDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public HospitalizedClaim insert(HospitalizedClaim claim) throws DAOException {
		try {
			em.persist(claim);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("failed to insert Claim", pe);
		}
		return claim;
	}

	/**
	 * @org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO #
	 *                                                                                      update
	 *                                                                                      (
	 *                                                                                      org
	 *                                                                                      .
	 *                                                                                      ace
	 *                                                                                      .
	 *                                                                                      insurance
	 *                                                                                      .
	 *                                                                                      medical
	 *                                                                                      .
	 *                                                                                      proposal
	 *                                                                                      .
	 *                                                                                      MedicalProposal
	 *                                                                                      )
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(HospitalizedClaim claim) throws DAOException {
		try {
			em.merge(claim);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MedicalProposal", pe);
		}
	}

	/**
	 * @org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO #
	 *                                                                                      delete
	 *                                                                                      (
	 *                                                                                      org
	 *                                                                                      .
	 *                                                                                      ace
	 *                                                                                      .
	 *                                                                                      insurance
	 *                                                                                      .
	 *                                                                                      medical
	 *                                                                                      .
	 *                                                                                      proposal
	 *                                                                                      .
	 *                                                                                      MedicalProposal
	 *                                                                                      )
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(HospitalizedClaim claim) throws DAOException {
		try {
			claim = em.merge(claim);
			em.remove(claim);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete MedicalProposal", pe);
		}
	}

	/**
	 * @org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO #
	 *                                                                                      findById
	 *                                                                                      (
	 *                                                                                      String
	 *                                                                                      )
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public HospitalizedClaim findById(String id) throws DAOException {
		HospitalizedClaim result = null;
		try {
			result = em.find(HospitalizedClaim.class, id);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MedicalProposal", pe);
		}
		return result;
	}

	/**
	 * @org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HospitalizedClaim> findAll() throws DAOException {
		List<HospitalizedClaim> result = null;
		try {
			Query q = em.createNamedQuery("HospitalizedClaim.findAll");
			result = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MedicalProposal", pe);
		}
		return result;
	}

	@Override
	public void addHospitalizedAttachment(HospitalizedClaim hospitalizedClaim) throws DAOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO
	 */
	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * updateHosPersonMedicalStatus(HospitalizationPerson hospitalizationPerson)
	 * throws DAOException { try {
	 * 
	 * String queryString =
	 * "UPDATE HospitalizationPerson p SET p.clsOfHealth = :clsOfHealth WHERE p.id = :insuPersonId"
	 * ; Query query = em.createQuery(queryString);
	 * query.setParameter("clsOfHealth",
	 * hospitalizationPerson.getClsOfHealth());
	 * query.setParameter("insuPersonId", hospitalizationPerson.getId());
	 * query.executeUpdate(); } catch (PersistenceException pe) { throw
	 * translate("Failed to update medical status for hospitalized person", pe);
	 * } }
	 */

	/**
	 * @org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO
	 */

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * addHospitalizedAttachment(HospitalizedClaim hospitalizedClaim) throws
	 * DAOException { try { Query delQuery = em.createQuery(
	 * "DELETE FROM MedicalClaimAttachment l WHERE l.id = :hospitalizedClaimId"
	 * ); delQuery.setParameter("hospitalizedClaimId",
	 * hospitalizedClaim.getId()); delQuery.executeUpdate(); for
	 * (MedicalClaimAttachment att :
	 * hospitalizedClaim.getMedicalClaimAttachmentList()) { em.persist(att);
	 * em.flush();System.gc(); } HospitalizationPerson pin =
	 * hospitalizedClaim.getHospitalizedPerson(); Query query = em.createQuery(
	 * "DELETE FROM MedicalClaimInsuredPersonAttachment l WHERE l.medicalClaimInsuredPerson.id = :medicalClaimInsuredPerson"
	 * ); query.setParameter("medicalClaimInsuredPerson", pin.getId());
	 * query.executeUpdate();
	 * 
	 * for (MedicalClaimInsuredPersonAttachment att :
	 * hospitalizedClaim.getHospitalizedPerson
	 * ().getClaimInsuredPersonAttachmentList()) { em.flush();System.gc();
	 * em.persist(att); } em.flush();System.gc(); } catch (PersistenceException
	 * pe) { throw translate("Failed to insert Attachment", pe); } }
	 *//**
		 * @org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO
		 */
	/*
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * insertHospitalizedClaimSurvey(HospitalizedClaimSurvey
	 * hospitalizedClaimSurvey) throws DAOException { try { Query delQuery =
	 * em.createQuery
	 * ("DELETE FROM HospitalizedClaimSurvey l WHERE l.id = :hospitalizedClaimId"
	 * ); delQuery.setParameter("hospitalizedClaimId",
	 * hospitalizedClaimSurvey.getHospitalizedClaim().getId());
	 * delQuery.executeUpdate(); em.persist(hospitalizedClaimSurvey);
	 * em.flush();System.gc(); } catch (PersistenceException pe) { throw
	 * translate("Failed to insert hospitalized claim survey", pe); } }
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED) public
	 * SuccessorClaimBeneficiary findSuccessorClaimBeneficiaryById(String id)
	 * throws DAOException { SuccessorClaimBeneficiary result = null; try {
	 * result = em.find(SuccessorClaimBeneficiary.class, id);
	 * em.flush();System.gc(); } catch (PersistenceException pe) { throw
	 * translate("Failed to find MedicalProposal", pe); } return result; }
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED) public
	 * HospitalizedClaimBeneficiary findHospitalizedClaimBeneficiaryById( String
	 * id) throws DAOException { HospitalizedClaimBeneficiary result = null; try
	 * { result = em.find(HospitalizedClaimBeneficiary.class, id);
	 * em.flush();System.gc(); } catch (PersistenceException pe) { throw
	 * translate("Failed to find MedicalProposal", pe); } return result; }
	 */

}

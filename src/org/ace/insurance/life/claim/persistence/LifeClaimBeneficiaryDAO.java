package org.ace.insurance.life.claim.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonAttachment;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiaryAttachment;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimBeneficiaryDAO;
import org.ace.insurance.life.policy.BeneficiaryStatus;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Repository("LifeClaimBeneficiaryDAO")
public class LifeClaimBeneficiaryDAO extends BasicDAO implements ILifeClaimBeneficiaryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifeClaimBeneficiary lifeClaimBeneficiary) throws DAOException {
		try {
			em.persist(lifeClaimBeneficiary);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert ClaimBeneficiary", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeClaimBeneficiary lifeClaimBeneficiary) throws DAOException {
		try {
			em.merge(lifeClaimBeneficiary);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update ClaimBeneficiary", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeClaimBeneficiary lifeClaimBeneficiary) throws DAOException {
		try {
			lifeClaimBeneficiary = em.merge(lifeClaimBeneficiary);
			em.remove(lifeClaimBeneficiary);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update ClaimBeneficiary", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimBeneficiary findById(String id) throws DAOException {
		LifeClaimBeneficiary result = null;
		try {
			result = em.find(LifeClaimBeneficiary.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to find ClaimBeneficiary", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimBeneficiary> findAll() throws DAOException {
		List<LifeClaimBeneficiary> result = null;
		try {
			Query q = em.createNamedQuery("ClaimBeneficiary.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to find all of ClaimBeneficiary", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addAttachment(LifeClaimInsuredPerson lifeClaimInsuredPerson) throws DAOException {
		try {
			for (LifeClaimInsuredPersonAttachment att : lifeClaimInsuredPerson.getClaimInsuredPersonAttachmentList()) {
				em.persist(att);
			}
			for (LifeClaimInsuredPersonBeneficiary pin : lifeClaimInsuredPerson.getClaimInsuredPersonBeneficiaryList()) {
				for (LifeClaimInsuredPersonBeneficiaryAttachment att : pin.getClaimInsuredPersonBeneficiaryAttachmentList()) {
					em.persist(att);
				}
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert Attachment", pe);
		}
	}

	@Override
	public void updateApprovalStatus(boolean approvalStatus, boolean needSomeDocument, String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("ClaimBeneficiary.updateApprovalStatus");
			q.setParameter("approvalStatus", approvalStatus);
			q.setParameter("needSomeDocument", needSomeDocument);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update approval status : ", pe);
		}

	}

	@Override
	public void updateBeneficiaryStatus(BeneficiaryStatus beneficiaryStatus, String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("ClaimBeneficiary.updateBeneficiaryStatus");
			q.setParameter("beneficiaryStatus", beneficiaryStatus);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update approval status : ", pe);
		}

	}

	@Override
	public void updateClaimAmount(int claimAmount, String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("ClaimBeneficiary.updateClaimAmount");
			q.setParameter("claimAmount", claimAmount);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update claim amount : ", pe);
		}

	}

	// 2013/07/09 TDS Add for retrieving ClaimBeneficiary by defined
	// RefundNo(Used at Claim) Start
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimBeneficiary findByRefundNo(String refundNo) throws DAOException {
		LifeClaimBeneficiary result = null;
		try {
			Query q = em.createNamedQuery("ClaimBeneficiary.findByRefundNo");
			q.setParameter("refundNo", refundNo);
			result = (LifeClaimBeneficiary) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return result;
		} catch (PersistenceException pe) {
			throw translate("Failed to find claimBeneficiary by RefundNo : " + refundNo, pe);
		}
		return result;
	}

	// 2013/07/09 TDS Add for retrieving ClaimBeneficiary by defined
	// RefundNo(Used at Claim) End

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimBeneficiary> findBySuccessorId(String successorId) throws DAOException {
		List<LifeClaimBeneficiary> result = null;
		try {
			Query q = em.createNamedQuery("ClaimBeneficiary.findBySuccessorId");
			q.setParameter("successorId", successorId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to find all of ClaimBeneficiary By SuccessorId", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimInsuredPersonBeneficiary> findByInsuredRelationshipId(String relationshipId) throws DAOException {
		List<LifeClaimInsuredPersonBeneficiary> result = null;
		try {
			Query q = em.createNamedQuery("ClaimBeneficiary.findByRelationshipId");
			q.setParameter("relationshipId", relationshipId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to find all of ClaimBeneficiary By relationshipId", pe);
		}
		return result;
	}
}

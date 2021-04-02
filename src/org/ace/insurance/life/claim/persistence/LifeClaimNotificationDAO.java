package org.ace.insurance.life.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.claim.LifeClaimNotiCriteria;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimNotificationDAO;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifeClaimNotificationDAO")
public class LifeClaimNotificationDAO extends BasicDAO implements ILifeClaimNotificationDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifeClaimNotification notification) throws DAOException {
		try {
			em.persist(notification);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifeClaimNotification", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeClaimNotification notification) throws DAOException {
		try {
			notification = em.merge(notification);
			em.remove(notification);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimInsuredPerson", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeClaimNotification notification) throws DAOException {
		try {
			em.merge(notification);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifeClaimNotification", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimNotification> findLifeClaimNotification() throws DAOException {
		List<LifeClaimNotification> result = null;
		try {
			Query q = em.createQuery("SELECT n FROM LifeClaimNotification n");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifeClaimNotification", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimNotification> findLifeClaimNotificationByCriteria(LifeClaimNotiCriteria criteria) throws DAOException {
		List<LifeClaimNotification> result = null;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("SELECT r FROM LifeClaimNotification r WHERE r.id IS NOT NULL");
			if (!criteria.getNotificationNo().isEmpty()) {
				buffer.append(" AND r.notificationNo LIKE :notificationNo");
			}
			if (criteria.getPolicyNo() != null) {
				buffer.append(" AND r.lifePolicyNo = :policyNo");
			}
			if (criteria.getStartDate() != null) {
				buffer.append(" AND r.reportedDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				buffer.append(" AND r.reportedDate <= :endDate");
			}
			buffer.append(" AND r.claimStatus !=:claimStatus");
			Query q = em.createQuery(buffer.toString());

			if (!criteria.getNotificationNo().isEmpty()) {
				q.setParameter("notificationNo", "%" + criteria.getNotificationNo() + "%");
			}
			if (criteria.getPolicyNo() != null) {
				q.setParameter("policyNo", criteria.getPolicyNo());
			}
			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}
			q.setParameter("claimStatus", ClaimStatus.CLAIM_APPLIED);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifeClaimNotification", pe);
		}

		return result;
	}

}

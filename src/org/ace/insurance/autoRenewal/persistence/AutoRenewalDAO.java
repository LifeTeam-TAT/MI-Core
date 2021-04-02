package org.ace.insurance.autoRenewal.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.autoRenewal.AutoRenewal;
import org.ace.insurance.autoRenewal.AutoRenewalCriteria;
import org.ace.insurance.autoRenewal.AutoRenewalStatus;
import org.ace.insurance.autoRenewal.persistence.interfaces.IAutoRenewalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AutoRenewalDAO")
public class AutoRenewalDAO extends BasicDAO implements IAutoRenewalDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public AutoRenewal insert(AutoRenewal autoRenewal) throws DAOException {
		try {
			em.persist(autoRenewal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert AutoRenewal", pe);
		}
		return autoRenewal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(AutoRenewal autoRenewal) throws DAOException {
		try {
			em.merge(autoRenewal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update AutoRenewal", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateStatus(AutoRenewalStatus status, String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("AutoRenewal.updateStatus");
			q.setParameter("status", status);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update status of AutoRenewal", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateStatusToDeactivate() throws DAOException {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE AUTORENEWAL SET STATUS='DEACTIVATE' WHERE DATEADD(DD, DATEDIFF(DD, 0, (DATEADD(DAY, 1, SCHEDULEENDDATE)))+0, 0) ="
					+ " DATEADD(DD, DATEDIFF(DD, 0, GETDATE())+0, 0)");
			Query q = em.createNativeQuery(buffer.toString());
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update status to 'DEACTIVATE' of AutoRenewal", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(AutoRenewal autoRenewal) throws DAOException {
		try {
			autoRenewal = em.merge(autoRenewal);
			em.remove(autoRenewal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete AutoRenewal", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AutoRenewal findById(String id) throws DAOException {
		AutoRenewal result = null;
		try {
			result = em.find(AutoRenewal.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AutoRenewal", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AutoRenewal> findAll() throws DAOException {
		List<AutoRenewal> result = null;
		try {
			Query q = em.createNamedQuery("AutoRenewal.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AutoRenewal", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AutoRenewal> findAllActiveInstance() throws DAOException {
		List<AutoRenewal> result = null;
		try {
			Query q = em.createNamedQuery("AutoRenewal.findAllActiveInstance");
			q.setParameter("status", AutoRenewalStatus.ACTIVE);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AutoRenewal", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AutoRenewal> findByCriteria(AutoRenewalCriteria criteria) throws DAOException {
		List<AutoRenewal> result = new ArrayList<AutoRenewal>();
		try {
			/* create query */
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT ar FROM AutoRenewal ar WHERE ar.status = :status");

			if (criteria.getInsuranceType() != null) {
				queryString.append(" AND ar.insuranceType = :insuranceType");
			}
			if (criteria.getPolicyEndDate() != null) {
				queryString.append(" AND ar.activedPolicyEndDate = :endDate");
			}
			if (criteria.getPolicyNo() != null) {
				queryString.append(" AND ar.policyNo = :policyNo");
			}
			/* Executed query */
			Query query = em.createQuery(queryString.toString());
			query.setParameter("status", AutoRenewalStatus.ACTIVE);
			if (criteria.getInsuranceType() != null) {
				query.setParameter("insuranceType", criteria.getInsuranceType());
			}
			if (criteria.getPolicyEndDate() != null) {
				query.setParameter("endDate", criteria.getPolicyEndDate());
			}
			if (criteria.getPolicyNo() != null) {
				query.setParameter("policyNo", criteria.getPolicyNo());
			}
			result = query.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Auto Renewal by Criteria : ", pe);
		}
		return result;
	}

}

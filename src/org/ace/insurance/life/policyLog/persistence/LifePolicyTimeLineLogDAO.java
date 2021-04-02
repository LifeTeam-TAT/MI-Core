package org.ace.insurance.life.policyLog.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.policyLog.LifePolicyClaimLog;
import org.ace.insurance.life.policyLog.LifePolicyEndorseLog;
import org.ace.insurance.life.policyLog.LifePolicyIdLog;
import org.ace.insurance.life.policyLog.LifePolicyTimeLineLog;
import org.ace.insurance.life.policyLog.persistence.interfaces.ILifePolicyTimeLineLogDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePolicyTimeLineLogDAO")
public class LifePolicyTimeLineLogDAO extends BasicDAO implements ILifePolicyTimeLineLogDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog) throws DAOException {
		try {
			em.persist(lifePolicyEndorseLog);
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicyEndorseLog", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog) throws DAOException {
		try {
			em.merge(lifePolicyEndorseLog);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicyEndorseLog", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyIdLog(LifePolicyIdLog lifePolicyIdLog) throws DAOException {
		try {
			em.persist(lifePolicyIdLog);
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicyIdLog", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) throws DAOException {
		try {
			em.persist(lifePolicyTimeLineLog);
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicyTimeLineLog", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) throws DAOException {
		try {
			em.merge(lifePolicyTimeLineLog);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicyTimeLineLog", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyTimeLineLog findLifePolicyTimeLineLog(String id) throws DAOException {
		LifePolicyTimeLineLog result = new LifePolicyTimeLineLog();
		try {
			result = em.find(LifePolicyTimeLineLog.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicyTimeLineLog", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) throws DAOException {
		LifePolicyTimeLineLog result = new LifePolicyTimeLineLog();
		try {
			result = em.merge(lifePolicyTimeLineLog);
			em.remove(result);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicyTimeLineLog", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int findMaxPolicyLifeCountByPolicyNo(String policyNo) throws DAOException {
		int result = 0;
		try {
			StringBuffer buffer = new StringBuffer("SELECT MAX(m.policyLifeCount) FROM LifePolicyTimeLineLog m WHERE m.policyNo = :policyNo");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("policyNo", policyNo);
			result = (Integer) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to findMaxPolicyLifeCountByPolicyNo : " + policyNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyTimeLineLog findLifePolicyTimeLineLogByPolicyNo(String policyNo) throws DAOException {
		LifePolicyTimeLineLog result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT m FROM LifePolicyTimeLineLog m WHERE m.policyNo = :policyNo AND"
					+ " m.policyLifeCount = (SELECT MAX(mm.policyLifeCount) FROM LifePolicyTimeLineLog mm WHERE mm.policyNo = m.policyNo)");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("policyNo", policyNo);
			result = (LifePolicyTimeLineLog) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to find LifePolicyTimeLineLog by PolicyNo : " + policyNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyTimeLineLog findLifePolicyTimeLineOf(String policyNo, int lastYear) throws DAOException {
		LifePolicyTimeLineLog result = null;
		try {

			StringBuffer buffer = new StringBuffer("SELECT m.policyLifeCount FROM LifePolicyTimeLineLog m WHERE m.policyNo = :policyNo AND"
					+ " m.policyLifeCount = (SELECT MAX(mm.policyLifeCount) FROM LifePolicyTimeLineLog mm WHERE mm.policyNo = m.policyNo)");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("policyNo", policyNo);
			long maxCount = (Long) q.getSingleResult();

			if (((lastYear - 1) > maxCount) || (maxCount - (lastYear - 1) <= 0)) {
				return null;
			}
			String queryString = "SELECT m FROM LifePolicyTimeLineLog m WHERE m.policyNo = :policyNo AND m.policyLifeCount = :policyLifeCount";
			q = em.createQuery(queryString);
			q.setParameter("policyNo", policyNo);
			q.setParameter("policyLifeCount", maxCount - (lastYear - 1));
			result = (LifePolicyTimeLineLog) q.getSingleResult();

			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("failed to find LifePolicyTimeLineLog by year : " + lastYear, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyClaimLog> findLifePolicyClaimLogByPolicyTimeLineId(String lifePolicyTimeLineLogId) throws DAOException {
		List<LifePolicyClaimLog> resultList = null;
		try {

			String queryString = "SELECT m FROM LifePolicyClaimLog m WHERE m.lifePolicyTimeLineLog.id = :id";
			Query query = em.createQuery(queryString);
			query.setParameter("id", lifePolicyTimeLineLogId);
			resultList = query.getResultList();

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to find LifePolicyClaimLog by LifePolicyTimeLineLogId : " + lifePolicyTimeLineLogId, pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog) throws DAOException {
		try {
			em.persist(lifePolicyClaimLog);
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicyClaimLog", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyClaimLog updateLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog) throws DAOException {
		try {
			lifePolicyClaimLog = em.merge(lifePolicyClaimLog);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicyClaimLog", pe);
		}

		return lifePolicyClaimLog;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyClaimLog findLifePolicyClaimLogByLifeClaimId(String lifeClaimId) throws DAOException {
		LifePolicyClaimLog result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT m FROM LifePolicyClaimLog m WHERE m.lifeClaim.id = :lifeClaimId");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("lifeClaimId", lifeClaimId);
			result = (LifePolicyClaimLog) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to find LifePolicyClaimLog by lifeClaimId : " + lifeClaimId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyIdLog findLifePolicyIdLogByToId(String toId) {
		LifePolicyIdLog result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT f FROM LifePolicyIdLog f WHERE f.toId = :toId");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("toId", toId);
			result = (LifePolicyIdLog) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("failed to find LifePolicyIdLog by toId : " + toId, pe);
		}
		return result;
	}
}

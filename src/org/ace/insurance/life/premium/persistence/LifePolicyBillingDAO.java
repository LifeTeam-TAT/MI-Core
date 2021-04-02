package org.ace.insurance.life.premium.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.ace.insurance.life.premium.LifePolicyBilling;
import org.ace.insurance.life.premium.persistence.interfaces.ILifePolicyBillingDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/01
 */

@Repository("LifePolicyBillingDAO")
public class LifePolicyBillingDAO extends BasicDAO implements ILifePolicyBillingDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyBilling findById(String id) throws DAOException {
		LifePolicyBilling result = null;
		try {
			result = em.find(LifePolicyBilling.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicyBilling", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyBilling> findAll() throws DAOException {
		List<LifePolicyBilling> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyBilling.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyBilling", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifePolicyBilling lifePolicyBilling) throws DAOException {
		try {
			em.persist(lifePolicyBilling);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifePolicyBilling", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifePolicyBilling lifePolicyBilling) throws DAOException {
		try {
			em.merge(lifePolicyBilling);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePolicyBilling", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifePolicyBilling lifePolicyBilling) throws DAOException {
		try {
			lifePolicyBilling = em.merge(lifePolicyBilling);
			em.remove(lifePolicyBilling);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePolicyBilling", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyBilling> findLifePolicyBilling(String lifePolicyNo, String customerID, String agentID, Date startDate, Date endDate) throws DAOException {

		List<LifePolicyBilling> result = new ArrayList<LifePolicyBilling>();

		try {

			if (lifePolicyNo == null && customerID == null && agentID == null && startDate == null && endDate == null) {
				return result;
			}

			/* create query */
			StringBuffer queryString = new StringBuffer();
			queryString.append("     SELECT DISTINCT lpb ");
			queryString.append("       FROM LifePolicyBilling lpb ");
			queryString.append(" INNER JOIN lpb.lifePolicyPremium lpp ");
			queryString.append("      WHERE ");

			queryString.append("  lpb.billingNo IS NULL    ");

			if (lifePolicyNo != null && !lifePolicyNo.isEmpty()) {
				queryString.append(" AND lpp.lifePolicy.policyNo = :lifePolicyId ");
			}
			if (customerID != null && !customerID.isEmpty()) {
				queryString.append(" AND lpp.lifePolicy.customer.id = :customerId  ");
			}
			if (agentID != null && !agentID.isEmpty()) {
				queryString.append(" AND lpp.lifePolicy.agent.id = :agentId  ");
			}
			if (startDate != null) {
				queryString.append(" AND lpb.billingDate >= :startDate  ");
			}
			if (endDate != null) {
				queryString.append(" AND lpb.billingDate <= :endDate  ");
			}

			Query query = em.createQuery(queryString.toString());
			if (lifePolicyNo != null && !lifePolicyNo.isEmpty()) {
				query.setParameter("lifePolicyId", lifePolicyNo);
			}
			if (customerID != null && !customerID.isEmpty()) {
				query.setParameter("customerId", customerID);
			}
			if (agentID != null && !agentID.isEmpty()) {
				query.setParameter("agentId", agentID);
			}
			if (startDate != null) {
				query.setParameter("startDate", startDate, TemporalType.DATE);
			}
			if (endDate != null) {
				query.setParameter("endDate", endDate, TemporalType.DATE);
			}

			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicyPremium by LifePolicyPremium : ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyBilling findByBillingNo(String billingNo) throws DAOException {
		LifePolicyBilling result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyBilling.findByBillingNo");
			q.setParameter("billingNo", billingNo);
			result = (LifePolicyBilling) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicyBilling", pe);
		}
		return result;
	}
}

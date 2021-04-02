package org.ace.insurance.life.premium.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.ace.insurance.life.premium.LifePolicyPremium;
import org.ace.insurance.life.premium.persistence.interfaces.ILifePolicyPremiumDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/02
 */

@Repository("LifePolicyPremiumDAO")
public class LifePolicyPremiumDAO extends BasicDAO implements ILifePolicyPremiumDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyPremium findById(String id) throws DAOException {
		LifePolicyPremium result = null;
		try {
			result = em.find(LifePolicyPremium.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicyPremium", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyPremium> findAll() throws DAOException {
		List<LifePolicyPremium> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyPremium.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyPremium", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifePolicyPremium lifePolicyPremium) throws DAOException {
		try {
			em.persist(lifePolicyPremium);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifePolicyPremium", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifePolicyPremium lifePolicyPremium) throws DAOException {
		try {
			em.merge(lifePolicyPremium);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePolicyPremium", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifePolicyPremium lifePolicyPremium) throws DAOException {
		try {
			lifePolicyPremium = em.merge(lifePolicyPremium);
			em.remove(lifePolicyPremium);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePolicyPremium", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyPremium> findByLifePolicyPremium(String lifePolicyNo, String customerID, String agentID, Date startDate, Date endDate) throws DAOException {

		List<LifePolicyPremium> result = new ArrayList<LifePolicyPremium>();

		try {

			if (lifePolicyNo == null && customerID == null && agentID == null && startDate == null && endDate == null) {
				return result;
			}

			/* create query */
			StringBuffer queryString = new StringBuffer();
			queryString.append("     SELECT DISTINCT lpp ");
			queryString.append("       FROM LifePolicyPremium lpp ");
			queryString.append(" INNER JOIN lpp.lifePolicyBillingList lpb ");
			queryString.append("      WHERE ");

			if (lifePolicyNo != null && !lifePolicyNo.isEmpty()) {
				queryString.append(" lpp.lifePolicy.id = :lifePolicyId AND    ");
			}
			if (customerID != null && !customerID.isEmpty()) {
				queryString.append(" lpp.lifePolicy.customer.id = :customerId AND ");
			}
			if (agentID != null && !agentID.isEmpty()) {
				queryString.append(" lpp.lifePolicy.agent.id = :agentId AND ");
			}
			if (startDate != null) {
				queryString.append(" lpb.billingDate >= :startDate AND ");
			}
			if (endDate != null) {
				queryString.append(" lpb.billingDate <= :endDate AND ");
			}
			queryString.delete(queryString.length() - 5, queryString.length());

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
}

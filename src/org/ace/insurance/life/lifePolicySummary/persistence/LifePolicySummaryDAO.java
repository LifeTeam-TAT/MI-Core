package org.ace.insurance.life.lifePolicySummary.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.persistence.interfaces.ILifePolicySummaryDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePolicySummaryDAO")
public class LifePolicySummaryDAO extends BasicDAO implements ILifePolicySummaryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifePolicySummary lifePolicySummary) throws DAOException {
		try {
			em.persist(lifePolicySummary);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicySummary", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifePolicySummary lifePolicySummary) throws DAOException {

		try {
			em.merge(lifePolicySummary);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicySummary", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifePolicySummary lifePolicySummary) throws DAOException {

		try {
			lifePolicySummary = em.merge(lifePolicySummary);
			em.remove(lifePolicySummary);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePolicySummary", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicySummary findByPolicyNo(String policyNo) throws DAOException {
		LifePolicySummary result = null;
		try {
			TypedQuery<LifePolicySummary> q = em.createNamedQuery("LifePolicySummary.findByPolicyNo",LifePolicySummary.class);
			q.setParameter("policyNo", policyNo);

			result = q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicySummary", pe);
		}
		return result;
	}

	public LifePolicySummary findById(String id) throws DAOException {
		LifePolicySummary result = null;
		try {
			result = em.find(LifePolicySummary.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicySummary", pe);
		}
		return result;
	}

	public List<LifePolicySummary> findAll() throws DAOException {
		List<LifePolicySummary> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicySummary.findAll");

			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicySummary : ", pe);
		}

		return result;
	}
}

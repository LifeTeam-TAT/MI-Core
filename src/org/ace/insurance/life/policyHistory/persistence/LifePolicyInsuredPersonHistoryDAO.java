package org.ace.insurance.life.policyHistory.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.life.policyHistory.persistence.interfaces.ILifePolicyInsuredPersonInfoHistoryDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PolicyInsuredPersonInfoHistoryDAO")
public class LifePolicyInsuredPersonHistoryDAO extends BasicDAO implements ILifePolicyInsuredPersonInfoHistoryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(PolicyInsuredPersonHistory policyInsuredPerson) throws DAOException {
		try {
			em.persist(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert PolicyInsuredPersonInfo", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(PolicyInsuredPersonHistory policyInsuredPerson) throws DAOException {
		try {
			em.merge(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PolicyInsuredPersonInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(PolicyInsuredPersonHistory policyInsuredPerson) throws DAOException {
		try {
			policyInsuredPerson = em.merge(policyInsuredPerson);
			em.remove(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PolicyInsuredPersonInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonHistory findById(String id) throws DAOException {
		PolicyInsuredPersonHistory result = null;
		try {
			result = em.find(PolicyInsuredPersonHistory.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find PolicyInsuredPersonInfo", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPersonHistory> findAll() throws DAOException {
		List<PolicyInsuredPersonHistory> result = null;
		try {
			Query q = em.createNamedQuery("PolicyInsuredPersonHistory.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of PolicyInsuredPersonInfo", pe);
		}
		return result;
	}

}

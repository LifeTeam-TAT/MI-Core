package org.ace.insurance.life.policyEditHistory.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;
import org.ace.insurance.life.policyEditHistory.persistence.interfaces.ILifePolicyInsuredPersonInfoEditHistoryDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PolicyInsuredPersonInfoEditHistoryDAO")
public class LifePolicyInsuredPersonEditHistoryDAO extends BasicDAO implements ILifePolicyInsuredPersonInfoEditHistoryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(PolicyInsuredPersonEditHistory policyInsuredPerson) throws DAOException {
		try {
			em.persist(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert PolicyInsuredPersonInfo", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(PolicyInsuredPersonEditHistory policyInsuredPerson) throws DAOException {
		try {
			em.merge(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PolicyInsuredPersonInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(PolicyInsuredPersonEditHistory policyInsuredPerson) throws DAOException {
		try {
			policyInsuredPerson = em.merge(policyInsuredPerson);
			em.remove(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PolicyInsuredPersonInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonEditHistory findById(String id) throws DAOException {
		PolicyInsuredPersonEditHistory result = null;
		try {
			result = em.find(PolicyInsuredPersonEditHistory.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find PolicyInsuredPersonInfo", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPersonEditHistory> findAll() throws DAOException {
		List<PolicyInsuredPersonEditHistory> result = null;
		try {
			Query q = em.createNamedQuery("PolicyInsuredPersonEditHistory.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of PolicyInsuredPersonInfo", pe);
		}
		return result;
	}

}

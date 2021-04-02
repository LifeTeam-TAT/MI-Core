package org.ace.insurance.life.policy.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyInsuredPersonInfoDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PolicyInsuredPersonInfoDAO")
public class LifePolicyInsuredPersonDAO extends BasicDAO implements ILifePolicyInsuredPersonInfoDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(PolicyInsuredPerson policyInsuredPerson) throws DAOException {
		try {
			em.persist(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert PolicyInsuredPersonInfo", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(PolicyInsuredPerson policyInsuredPerson) throws DAOException {
		try {
			em.merge(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PolicyInsuredPersonInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(PolicyInsuredPerson policyInsuredPerson) throws DAOException {
		try {
			policyInsuredPerson = em.merge(policyInsuredPerson);
			em.remove(policyInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PolicyInsuredPersonInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPerson findById(String id) throws DAOException {
		PolicyInsuredPerson result = null;
		try {
			result = em.find(PolicyInsuredPerson.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find PolicyInsuredPersonInfo", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPerson> findAll() throws DAOException {
		List<PolicyInsuredPerson> result = null;
		try {
			Query q = em.createNamedQuery("PolicyInsuredPerson.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of PolicyInsuredPersonInfo", pe);
		}
		return result;
	}

}

package org.ace.insurance.system.common.classofinsurance.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.classofinsurance.ClassOfInsurance;
import org.ace.insurance.system.common.classofinsurance.persistence.interfaces.IClassOfInsuranceDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ClassOfInsuranceDAO")
public class ClassOfInsuranceDAO extends BasicDAO implements IClassOfInsuranceDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(ClassOfInsurance classofinsurance) throws DAOException {
		try {
			em.persist(classofinsurance);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ClassOfInsurance", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ClassOfInsurance classofinsurance) throws DAOException {
		try {
			em.merge(classofinsurance);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClassOfInsurance", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ClassOfInsurance classofinsurance) throws DAOException {
		try {
			classofinsurance = em.merge(classofinsurance);
			em.remove(classofinsurance);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete ClassOfInsurance", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClassOfInsurance findById(String id) throws DAOException {
		ClassOfInsurance result = null;
		try {
			result = em.find(ClassOfInsurance.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ClassOfInsurance", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClassOfInsurance> findAll() throws DAOException {
		List<ClassOfInsurance> result = null;
		try {
			Query q = em.createNamedQuery("ClassOfInsurance.findAll");
			result = q.getResultList();
			q.setMaxResults(50);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClassOfInsurance", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClassOfInsurance> findByCriteria(String criteria) throws DAOException {
		List<ClassOfInsurance> result = null;
		try {
			// Query q = em.createNamedQuery("Township.findByCriteria");
			Query q = em.createQuery("Select c from ClassOfInsurance c where c.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of ClassOfInsurance.", pe);
		}
		return result;
	}

}

package org.ace.insurance.system.common.icd10.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.system.common.icd10.persistence.interfaces.IICD10DAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ICD10DAO")
public class ICD10DAO extends BasicDAO implements IICD10DAO {

	/**
	 * @see org.ace.insurance.system.common.icd10.persistence.interfaces.Iicd10DAO
	 *      #insert(org.ace.insurance.system.common.icd10.icd10)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(ICD10 icd10) throws DAOException {
		try {
			em.persist(icd10);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update icd10", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.icd10.persistence.interfaces.Iicd10DAO
	 *      #update(org.ace.insurance.system.common.icd10.icd10)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ICD10 icd10) throws DAOException {
		try {
			em.merge(icd10);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update icd10", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.icd10.persistence.interfaces.Iicd10DAO
	 *      #delete(org.ace.insurance.system.common.icd10.icd10)
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ICD10 icd10) throws DAOException {
		try {
			icd10 = em.merge(icd10);
			em.remove(icd10);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update icd10", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.icd10.persistence.interfaces.Iicd10DAO
	 *      #findById(String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ICD10 findById(String id) throws DAOException {
		ICD10 result = null;
		try {
			result = em.find(ICD10.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find icd10", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ICD10> findAll() throws DAOException {
		List<ICD10> result = null;
		try {
			Query q = em.createNamedQuery("ICD10.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of icd10", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ICD10> findByCriteria(String criteria) throws DAOException {
		List<ICD10> result = null;
		try {
			Query q = em.createQuery("Select c from ICD10 c where c.code Like '" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of icd10.", pe);
		}
		return result;
	}

}

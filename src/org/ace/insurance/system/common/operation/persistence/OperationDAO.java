package org.ace.insurance.system.common.operation.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.operation.Operation;
import org.ace.insurance.system.common.operation.persistence.interfaces.IOperationDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("OperationDAO")
public class OperationDAO extends BasicDAO implements IOperationDAO {

	/**
	 * @see org.ace.insurance.system.common.operation.persistence.interfaces.IoperationDAO
	 *      #insert(org.ace.insurance.system.common.operation.operation)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(Operation operation) throws DAOException {
		String s = "";
		try {
			em.persist(operation);
			em.flush();
		} catch (PersistenceException pe) {
			s = pe.getMessage();
		}
		return s;
	}

	/**
	 * @see org.ace.insurance.system.common.operation.persistence.interfaces.IoperationDAO
	 *      #update(org.ace.insurance.system.common.operation.operation)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Operation operation) throws DAOException {
		try {
			em.merge(operation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update operation", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.operation.persistence.interfaces.IoperationDAO
	 *      #delete(org.ace.insurance.system.common.operation.operation)
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Operation operation) throws DAOException {
		try {
			operation = em.merge(operation);
			em.remove(operation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update operation", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.operation.persistence.interfaces.IoperationDAO
	 *      #findById(String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Operation findById(String id) throws DAOException {
		Operation result = null;
		try {
			result = em.find(Operation.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find operation", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Operation> findAll() throws DAOException {
		List<Operation> result = null;
		try {
			Query q = em.createNamedQuery("Operation.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of operation", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Operation> findByCriteria(String criteria) throws DAOException {
		List<Operation> result = null;
		try {
			Query q = em.createQuery("Select c from Operation c where c.code Like '" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of operation.", pe);
		}
		return result;
	}

}
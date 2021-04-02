/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.religion.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.system.common.religion.persistence.interfaces.IReligionDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ReligionDAO")
public class ReligionDAO extends BasicDAO implements IReligionDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Religion religion) throws DAOException {
		try {
			em.persist(religion);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Religion", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Religion religion) throws DAOException {
		try {
			em.merge(religion);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Religion", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Religion religion) throws DAOException {
		try {
			religion = em.merge(religion);
			em.remove(religion);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Religion", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Religion findById(String id) throws DAOException {
		Religion result = null;
		try {
			result = em.find(Religion.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Religion", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Religion> findAll() throws DAOException {
		List<Religion> result = null;
		try {
			Query q = em.createNamedQuery("Religion.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Religion", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Religion> findByCriteria(String criteria) throws DAOException {
		List<Religion> result = null;
		try {
			// Query q = em.createNamedQuery("Religion.findByCriteria");
			Query q = em.createQuery("Select t from Religion t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Religion.", pe);
		}
		return result;
	}

}

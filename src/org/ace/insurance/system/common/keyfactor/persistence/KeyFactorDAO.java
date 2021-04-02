/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.keyfactor.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.keyfactor.persistence.interfaces.IKeyFactorDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("KeyFactorDAO")
public class KeyFactorDAO extends BasicDAO implements IKeyFactorDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(KeyFactor keyFactor) throws DAOException {
		try {
			em.persist(keyFactor);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert KeyFactor", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(KeyFactor keyFactor) throws DAOException {
		try {
			em.merge(keyFactor);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update KeyFactor", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(KeyFactor keyFactor) throws DAOException {
		try {
			keyFactor = em.merge(keyFactor);
			em.remove(keyFactor);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update KeyFactor", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public KeyFactor findById(String id) throws DAOException {
		KeyFactor result = null;
		try {
			result = em.find(KeyFactor.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find KeyFactor", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<KeyFactor> findAll() throws DAOException {
		List<KeyFactor> result = null;
		try {
			Query q = em.createNamedQuery("KeyFactor.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of KeyFactor", pe);
		}
		return result;
	}
}

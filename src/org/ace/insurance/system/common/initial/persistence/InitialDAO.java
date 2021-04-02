package org.ace.insurance.system.common.initial.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.initial.Initial;
import org.ace.insurance.system.common.initial.persistence.interfaces.IInitialDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("InitialDAO")
public class InitialDAO extends BasicDAO implements IInitialDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Initial initial) throws DAOException {
		try {
			em.persist(initial);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Initial", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Initial initial) throws DAOException {
		try {
			em.merge(initial);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Initial", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Initial initial) throws DAOException {
		try {
			initial = em.merge(initial);
			em.remove(initial);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Initial", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Initial findById(String id) throws DAOException {
		Initial result = null;
		try {
			result = em.find(Initial.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Initial", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Initial> findAll() throws DAOException {
		List<Initial> result = null;
		try {
			Query q = em.createNamedQuery("Initial.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Initial", pe);
		}
		return result;
	}
}

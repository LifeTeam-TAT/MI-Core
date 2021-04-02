package org.ace.insurance.system.common.packing.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.packing.Packing;
import org.ace.insurance.system.common.packing.persistence.interfaces.IPackingDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PackingDAO")
public class PackingDAO extends BasicDAO implements IPackingDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Packing packing) throws DAOException {
		try {
			em.persist(packing);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Packing", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Packing packing) throws DAOException {
		try {
			em.merge(packing);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Packing", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Packing packing) throws DAOException {
		try {
			packing = em.merge(packing);
			em.remove(packing);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Packing", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Packing findById(String id) throws DAOException {
		Packing result = null;
		try {
			result = em.find(Packing.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Packing", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Packing findByName(String name) throws DAOException, NoResultException {
		Packing result = null;
		try {
			Query q = em.createNamedQuery("Packing.findByName");
			q.setParameter("name", name);
			result = (Packing) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			throw translate("No Result in finding single Packing", e);
		} catch (PersistenceException pe) {
			throw translate("Failed to find Packing", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Packing> findAll() throws DAOException {
		List<Packing> result = null;
		try {
			Query q = em.createNamedQuery("Packing.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Packing", pe);
		}
		return result;
	}

}

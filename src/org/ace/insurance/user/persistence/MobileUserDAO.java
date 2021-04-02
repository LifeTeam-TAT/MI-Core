/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.user.MobileUser;
import org.ace.insurance.user.persistence.interfaces.IMobileUserDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MobileUserDAO")
public class MobileUserDAO extends BasicDAO implements IMobileUserDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(MobileUser mobileUser) throws DAOException {
		try {
			em.persist(mobileUser);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert MobileUser", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(MobileUser mobileUser) throws DAOException {
		try {
			em.merge(mobileUser);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MobileUser", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(MobileUser mobileUser) throws DAOException {
		try {
			mobileUser = em.merge(mobileUser);
			em.remove(mobileUser);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MobileUser", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MobileUser findById(String id) throws DAOException {
		MobileUser result = null;
		try {
			result = em.find(MobileUser.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MobileUser", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MobileUser> findAll() throws DAOException {
		List<MobileUser> result = null;
		try {
			Query q = em.createQuery("SELECT m FROM MobileUser m ORDER BY m.name ASC");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MobileUser", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MobileUser> findByCriteria(String criteria) throws DAOException {
		List<MobileUser> result = null;
		try {
			Query q = em.createQuery("Select m from MobileUser m where m.name Like '%" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of MobileUser.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MobileUser findByUserCode(String userCode) throws DAOException {
		MobileUser result = null;
		try {
			Query q = em.createQuery("SELECT m FROM MobileUser m WHERE m.userCode = :userCode ");
			q.setParameter("userCode", userCode);
			result = (MobileUser) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find User(Username = " + userCode + ")", pe);
		}
		return result;
	}
}

package org.ace.insurance.userlog.persistences;


import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.userlog.UserLog;
import org.ace.insurance.userlog.persistences.interfaces.IUserLogDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("UserLogDAO")
public class UserLogDAO extends BasicDAO implements IUserLogDAO  {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertUserLost(UserLog userlog) throws DAOException {
		try {
			em.persist(userlog);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Fail to insert user log", pe);
		}	
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserLog> findAll() throws DAOException {
		List<UserLog> result = null;
		try {
			Query q = em.createNamedQuery("UserLog.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of User.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserLog(UserLog userLog) {
		try {
			em.merge(userLog);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Fail to update user log", pe);
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public UserLog findUserLogById(String id) {
		UserLog result = null;
		try {
			StringBuffer queryBuffer = new StringBuffer();
			queryBuffer.append("SELECT u FROM UserLog u where u.id=:id");
			Query query = em.createQuery(queryBuffer.toString());
			query.setParameter("id", id);
			result = (UserLog) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find UserLog By Id", pe);
		}

		return result;
	}

/*	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertUserLost(UserLog userlog) throws DAOException {
		try {
			em.persist(userlog);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Fail to insert user log", pe);
		}	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserLog> findAll() {
		List<UserLog> result = null;
		try {
			Query q = em.createNamedQuery("UserLog.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of User.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserLog(UserLog userLog) {
		try {
			em.merge(userLog);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Fail to update user log", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public UserLog findUserLogById(String id) {
		UserLog result = null;
		try {
			StringBuffer queryBuffer = new StringBuffer();
			queryBuffer.append("SELECT u FROM UserLog u where u.id=:id");
			Query query = em.createQuery(queryBuffer.toString());
			query.setParameter("id", id);
			result = (UserLog) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find UserLog By Id", pe);
		}

		return result;
	}*/

	
}

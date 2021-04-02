/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.accept.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.persistence.interfaces.IAcceptedInfoDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AcceptedInfoDAO")
public class AcceptedInfoDAO extends BasicDAO implements IAcceptedInfoDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(AcceptedInfo acceptedInfo) throws DAOException {
		try {
			Query delQuery = em.createQuery("DELETE FROM AcceptedInfo a WHERE a.referenceNo = :referenceNo AND a.referenceType = :referenceType");
			delQuery.setParameter("referenceNo", acceptedInfo.getReferenceNo());
			delQuery.setParameter("referenceType", acceptedInfo.getReferenceType());
			delQuery.executeUpdate();
			em.persist(acceptedInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert AcceptedInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(AcceptedInfo acceptedInfo) throws DAOException {
		try {
			em.merge(acceptedInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update AcceptedInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(AcceptedInfo acceptedInfo) throws DAOException {
		try {
			acceptedInfo = em.merge(acceptedInfo);
			em.remove(acceptedInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update AcceptedInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AcceptedInfo findByReferenceNo(String referenceNo) throws DAOException {
		AcceptedInfo result = null;
		try {
			Query q = em.createNamedQuery("AcceptedInfo.findByReferenceNo");
			q.setParameter("referenceNo", referenceNo);
			result = (AcceptedInfo) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AcceptedInfo", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AcceptedInfo> findAll() throws DAOException {
		List<AcceptedInfo> result = null;
		try {
			Query q = em.createNamedQuery("AcceptedInfo.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AcceptedInfo", pe);
		}
		return result;
	}
}

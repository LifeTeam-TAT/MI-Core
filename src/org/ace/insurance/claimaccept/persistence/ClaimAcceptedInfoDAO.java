/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimaccept.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.persistence.interfaces.IClaimAcceptedInfoDAO;
import org.ace.insurance.common.ReferenceType;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ClaimAcceptedInfoDAO")
public class ClaimAcceptedInfoDAO extends BasicDAO implements IClaimAcceptedInfoDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(ClaimAcceptedInfo claimAcceptedInfo) throws DAOException {
		try {
			Query delQuery = em.createQuery("DELETE FROM ClaimAcceptedInfo a WHERE a.referenceNo = :referenceNo AND a.referenceType = :referenceType");
			delQuery.setParameter("referenceNo", claimAcceptedInfo.getReferenceNo());
			delQuery.setParameter("referenceType", claimAcceptedInfo.getReferenceType());
			delQuery.executeUpdate();
			em.persist(claimAcceptedInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ClaimAcceptedInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ClaimAcceptedInfo claimAcceptedInfo) throws DAOException {
		try {
			em.merge(claimAcceptedInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimAcceptedInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ClaimAcceptedInfo claimAcceptedInfo) throws DAOException {
		try {
			claimAcceptedInfo = em.merge(claimAcceptedInfo);
			em.remove(claimAcceptedInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimAcceptedInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimAcceptedInfo findByReferenceNo(String referenceNo, ReferenceType referenceType) throws DAOException {
		ClaimAcceptedInfo result = null;
		try {
			Query q = em.createNamedQuery("ClaimAcceptedInfo.findByReferenceNo");
			q.setParameter("referenceNo", referenceNo);
			q.setParameter("referenceType", referenceType);
			result = (ClaimAcceptedInfo) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ClaimAcceptedInfo", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimAcceptedInfo> findAll() throws DAOException {
		List<ClaimAcceptedInfo> result = null;
		try {
			Query q = em.createNamedQuery("ClaimAcceptedInfo.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimAcceptedInfo", pe);
		}
		return result;
	}
}

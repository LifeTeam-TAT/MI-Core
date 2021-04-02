
/**
 * 
 */
package org.ace.insurance.life.claim.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.insurance.life.claim.persistence.interfaces.ILifePolicyClaimDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ASUS
 *
 */
@Repository("LifePolicyClaimDAO")
public class LifePolicyClaimDAO extends BasicDAO implements ILifePolicyClaimDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyClaim> findLifepolicyClaimByPolicyNo(String policyNo) throws DAOException {
		try {
			TypedQuery<LifePolicyClaim> query = em.createNamedQuery("LifePolicyClaim.findCountByPolicyNo", LifePolicyClaim.class);
			query.setParameter("policyNo", policyNo);
			return query.getResultList();
		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("failed to find Life Polic Claim", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifePolicyClaim lifePolicyClaim) throws DAOException {
		try {
			em.persist(lifePolicyClaim);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert Life Polic Claim", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifePolicyClaim lifePolicyClaim) {
		try {
			em.merge(lifePolicyClaim);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update Life Polic Claim", pe);
		}
	}

	@Override
	public int findCountByPolicyNo(String policyNo) throws DAOException {
		try {
			TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM LifePolicyClaim l WHERE l.policyNo = :policyNo and l.isApproved=true", Long.class);
			query.setParameter("policyNo", policyNo);
			long result = query.getSingleResult();
			return (int) result;
		} catch (PersistenceException e) {
			throw translate("Fail to find claim count by policy no", e);
		}
	}
}

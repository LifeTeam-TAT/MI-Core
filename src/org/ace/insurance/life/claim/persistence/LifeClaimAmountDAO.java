package org.ace.insurance.life.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.claim.LifeClaimAmount;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimAmountDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Repository("LifeClaimAmountDAO")
public class LifeClaimAmountDAO extends BasicDAO implements ILifeClaimAmountDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifeClaimAmount lifeClaimAmount) throws DAOException {
		try {
			em.persist(lifeClaimAmount);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ClaimAmount", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeClaimAmount lifeClaimAmount) throws DAOException {
		try {
			em.merge(lifeClaimAmount);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimAmount", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeClaimAmount lifeClaimAmount) throws DAOException {
		try {
			lifeClaimAmount = em.merge(lifeClaimAmount);
			em.remove(lifeClaimAmount);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimAmount", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimAmount findById(String id) throws DAOException {
		LifeClaimAmount result = null;
		try {
			result = em.find(LifeClaimAmount.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ClaimAmount", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimAmount> findAll() throws DAOException {
		List<LifeClaimAmount> result = null;
		try {
			Query q = em.createNamedQuery("ClaimAmount.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimAmount", pe);
		}
		return result;
	}
}

package org.ace.insurance.life.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimInsuredPersonDAO;
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

@Repository("ClaimInsuredPersonDAO")
public class LifeClaimInsuredPersonDAO extends BasicDAO implements ILifeClaimInsuredPersonDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifeClaimInsuredPerson lifeclaimInsuredPerson) throws DAOException {
		try {
			em.persist(lifeclaimInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ClaimInsuredPerson", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeClaimInsuredPerson lifeclaimInsuredPerson) throws DAOException {
		try {
			em.merge(lifeclaimInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimInsuredPerson", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeClaimInsuredPerson lifeclaimInsuredPerson) throws DAOException {
		try {
			lifeclaimInsuredPerson = em.merge(lifeclaimInsuredPerson);
			em.remove(lifeclaimInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimInsuredPerson", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimInsuredPerson findById(String id) throws DAOException {
		LifeClaimInsuredPerson result = null;
		try {
			result = em.find(LifeClaimInsuredPerson.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ClaimInsuredPerson", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimInsuredPerson> findAll() throws DAOException {
		List<LifeClaimInsuredPerson> result = null;
		try {
			Query q = em.createNamedQuery("ClaimInsuredPerson.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimInsuredPerson", pe);
		}
		return result;
	}
}

package org.ace.insurance.life.paidUp.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.insurance.life.paidUp.persistence.interfaces.ILifePaidUpProposalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePaidUpProposalDAO")
public class LifePaidUpProposalDAO extends BasicDAO implements ILifePaidUpProposalDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePaidUpProposal insert(LifePaidUpProposal lifePaidUpProposal) throws DAOException {
		try {
			em.persist(lifePaidUpProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifePaidUpProposal", pe);
		}
		return lifePaidUpProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifePaidUpProposal lifePaidUpProposal) throws DAOException {
		try {
			em.merge(lifePaidUpProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePaidUpProposal", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(String policyNo) throws DAOException {
		try {

			Query q = em.createQuery("UPDATE  LifePaidUpProposal SET complete ='TRUE' WHERE policyNo =:policyNo");
			q.setParameter("policyNo", policyNo);
			q.executeUpdate();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePaidUpProposal", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifePaidUpProposal lifePaidUpProposal) throws DAOException {
		try {
			lifePaidUpProposal = em.merge(lifePaidUpProposal);
			em.remove(lifePaidUpProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete LifePaidUpProposal", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePaidUpProposal> findAll() throws DAOException {
		List<LifePaidUpProposal> result = null;
		try {
			Query q = em.createNamedQuery("LifePaidUpProposal.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePaidUpProposal", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePaidUpProposal findById(String id) throws DAOException {
		LifePaidUpProposal result = null;
		try {
			result = em.find(LifePaidUpProposal.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePaidUpProposal By Id : " + id, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePaidUpProposal findByProposalNo(String proposalNo) throws DAOException {
		LifePaidUpProposal result = null;
		try {
			Query q = em.createNamedQuery("LifePaidUpProposal.findByProposalNo");
			q.setParameter("proposalNo", proposalNo);
			result = (LifePaidUpProposal) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePaidUpProposal By ProposalNo" + proposalNo, pe);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePaidUpProposal findByPolicyNo(String policyNo) throws DAOException {
		LifePaidUpProposal result = null;
		try {
			Query q = em.createNamedQuery("LifePaidUpProposal.findByPolicyNo");
			q.setParameter("policyNo", policyNo);
			result = (LifePaidUpProposal) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePaidUpProposal By ProposalNo" + policyNo, pe);
		}
		return result;
	}
}

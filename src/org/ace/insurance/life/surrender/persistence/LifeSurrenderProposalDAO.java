package org.ace.insurance.life.surrender.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.life.surrender.persistence.interfaces.ILifeSurrenderProposalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2016-03-03
 * @Version 1.0
 * @Purpose This class serves as the Data Access Object For Life Surrender
 *          Proposal
 * 
 ***************************************************************************************/
@Repository("LifeSurrenderProposalDAO")
public class LifeSurrenderProposalDAO extends BasicDAO implements ILifeSurrenderProposalDAO {

	/**
	 * @see org.ace.insurance.life.surrender.persistence.interfaces.ILifeSurrenderProposalDAO
	 *      #insert(org.ace.insurance.life.surrender.LifeSurrenderProposal)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeSurrenderProposal insert(LifeSurrenderProposal lifeSurrenderProposal) throws DAOException {
		try {
			em.persist(lifeSurrenderProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifeSurrenderProposal", pe);
		}
		return lifeSurrenderProposal;
	}

	/**
	 * @see org.ace.insurance.life.surrender.persistence.interfaces.ILifeSurrenderProposalDAO
	 *      #update(org.ace.insurance.life.surrender.LifeSurrenderProposal)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeSurrenderProposal lifeSurrenderProposal) throws DAOException {
		try {
			em.merge(lifeSurrenderProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifeSurrenderProposal", pe);
		}
	}

	/**
	 * @see org.ace.insurance.life.surrender.persistence.interfaces.ILifeSurrenderProposalDAO
	 *      #delete(org.ace.insurance.life.surrender.LifeSurrenderProposal)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeSurrenderProposal lifeSurrenderProposal) throws DAOException {
		try {
			lifeSurrenderProposal = em.merge(lifeSurrenderProposal);
			em.remove(lifeSurrenderProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete LifeSurrenderProposal", pe);
		}
	}

	/**
	 * @see org.ace.insurance.life.surrender.persistence.
	 *      ILifeSurrenderProposalDAO #find All Life Surrender Proposal List
	 *      <(org.ace.insurance.life.surrender.LifeSurrenderProposal)>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeSurrenderProposal> findAll() throws DAOException {
		List<LifeSurrenderProposal> result = null;
		try {
			Query q = em.createNamedQuery("LifeSurrenderProposal.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifeSurrenderProposal", pe);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.life.surrender.persistence.
	 *      ILifeSurrenderProposalDAO #find Life Surrender Proposal According to
	 *      Id (org.ace.insurance.life.surrender.LifeSurrenderProposal)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeSurrenderProposal findById(String id) throws DAOException {
		LifeSurrenderProposal result = null;
		try {
			result = em.find(LifeSurrenderProposal.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeSurrenderProposal By Id : " + id, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeSurrenderProposal findByProposalNo(String proposalNo) throws DAOException {
		LifeSurrenderProposal result = null;
		try {
			Query q = em.createNamedQuery("LifeSurrenderProposal.findByProposalNo");
			q.setParameter("proposalNo", proposalNo);
			result = (LifeSurrenderProposal) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeSurrenderProposal By ProposalNo" + proposalNo, pe);
		}
		return result;

	}
}

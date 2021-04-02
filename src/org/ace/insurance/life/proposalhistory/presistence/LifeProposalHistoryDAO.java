package org.ace.insurance.life.proposalhistory.presistence;

import javax.persistence.PersistenceException;

import org.ace.insurance.life.proposalhistory.LifeProposalHistory;
import org.ace.insurance.life.proposalhistory.presistence.interfaces.ILifeProposalHistoryDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifeProposalHistoryDAO")
public class LifeProposalHistoryDAO extends BasicDAO implements ILifeProposalHistoryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposalHistory insert(LifeProposalHistory lifeProposalHistory) throws DAOException {
		try {
			em.persist(lifeProposalHistory);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifeProposalHistory", pe);
		}
		return lifeProposalHistory;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeProposalHistory lifeProposalHistory) throws DAOException {
		try {
			em.merge(lifeProposalHistory);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifeProposalHistory", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeProposalHistory lifeProposalHistory) throws DAOException {
		try {
			lifeProposalHistory = em.merge(lifeProposalHistory);
			em.remove(lifeProposalHistory);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifeProposalHistory", pe);
		}
	}
}
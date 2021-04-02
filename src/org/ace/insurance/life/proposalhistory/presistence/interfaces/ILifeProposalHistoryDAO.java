package org.ace.insurance.life.proposalhistory.presistence.interfaces;

import org.ace.insurance.life.proposalhistory.LifeProposalHistory;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeProposalHistoryDAO {
	public LifeProposalHistory insert(LifeProposalHistory lifeProposalHistory) throws DAOException;
	public void update(LifeProposalHistory lifeProposalHistory) throws DAOException;
	public void delete(LifeProposalHistory lifeProposalHistory) throws DAOException;
}

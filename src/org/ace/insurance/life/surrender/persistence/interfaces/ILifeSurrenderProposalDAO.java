package org.ace.insurance.life.surrender.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.java.component.persistence.exception.DAOException;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2016-03-03
 * @Version 1.0
 * @Purpose This class serves as the Data Access Object Interface For Life
 *          Surrender Proposal
 * 
 ***************************************************************************************/

public interface ILifeSurrenderProposalDAO {

	public LifeSurrenderProposal insert(LifeSurrenderProposal lifeSurrenderProposal) throws DAOException;

	public void update(LifeSurrenderProposal lifeSurrenderProposal) throws DAOException;

	public void delete(LifeSurrenderProposal lifeSurrenderProposal) throws DAOException;

	public List<LifeSurrenderProposal> findAll() throws DAOException;

	public LifeSurrenderProposal findById(String id) throws DAOException;

	public LifeSurrenderProposal findByProposalNo(String proposalNo) throws DAOException;

}

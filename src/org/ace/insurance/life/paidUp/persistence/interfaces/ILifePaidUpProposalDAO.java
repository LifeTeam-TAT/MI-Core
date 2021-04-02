package org.ace.insurance.life.paidUp.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePaidUpProposalDAO {
	public LifePaidUpProposal insert(LifePaidUpProposal lifePaidUpProposal) throws DAOException;

	public void update(LifePaidUpProposal lifePaidUpProposal) throws DAOException;

	public void delete(LifePaidUpProposal lifePaidUpProposal) throws DAOException;

	public List<LifePaidUpProposal> findAll() throws DAOException;

	public LifePaidUpProposal findById(String id) throws DAOException;

	public LifePaidUpProposal findByProposalNo(String proposalNo) throws DAOException;

	public void update(String policyNo) throws DAOException;

	public LifePaidUpProposal findByPolicyNo(String policyNo) throws DAOException;
}

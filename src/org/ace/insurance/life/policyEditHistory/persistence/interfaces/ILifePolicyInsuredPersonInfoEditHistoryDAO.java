package org.ace.insurance.life.policyEditHistory.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyInsuredPersonInfoEditHistoryDAO {
	public void insert(PolicyInsuredPersonEditHistory policyInsuredPerson) throws DAOException;

	public void update(PolicyInsuredPersonEditHistory policyInsuredPerson) throws DAOException;

	public void delete(PolicyInsuredPersonEditHistory policyInsuredPerson) throws DAOException;

	public PolicyInsuredPersonEditHistory findById(String id) throws DAOException;

	public List<PolicyInsuredPersonEditHistory> findAll() throws DAOException;
}

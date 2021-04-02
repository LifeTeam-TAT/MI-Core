package org.ace.insurance.life.policyHistory.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyInsuredPersonInfoHistoryDAO {
	public void insert(PolicyInsuredPersonHistory policyInsuredPerson) throws DAOException;

	public void update(PolicyInsuredPersonHistory policyInsuredPerson) throws DAOException;

	public void delete(PolicyInsuredPersonHistory policyInsuredPerson) throws DAOException;

	public PolicyInsuredPersonHistory findById(String id) throws DAOException;

	public List<PolicyInsuredPersonHistory> findAll() throws DAOException;
}

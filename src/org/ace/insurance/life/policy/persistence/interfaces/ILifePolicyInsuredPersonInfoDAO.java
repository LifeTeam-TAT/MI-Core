package org.ace.insurance.life.policy.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyInsuredPersonInfoDAO {
	public void insert(PolicyInsuredPerson policyInsuredPerson)
			throws DAOException;

	public void update(PolicyInsuredPerson policyInsuredPerson)
			throws DAOException;

	public void delete(PolicyInsuredPerson policyInsuredPerson)
			throws DAOException;

	public PolicyInsuredPerson findById(String id) throws DAOException;

	public List<PolicyInsuredPerson> findAll() throws DAOException;
}

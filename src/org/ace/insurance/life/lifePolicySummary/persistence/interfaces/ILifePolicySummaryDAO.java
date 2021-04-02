package org.ace.insurance.life.lifePolicySummary.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicySummaryDAO  {

	public void insert(LifePolicySummary lifePolicySummary) throws DAOException;

	public void update(LifePolicySummary lifePolicySummary) throws DAOException;

	public void delete(LifePolicySummary lifePolicySummary) throws DAOException;

	public LifePolicySummary findByPolicyNo(String policyNo) throws DAOException;
	
	public LifePolicySummary findById(String id) throws DAOException;

	public List<LifePolicySummary> findAll() throws DAOException;

}

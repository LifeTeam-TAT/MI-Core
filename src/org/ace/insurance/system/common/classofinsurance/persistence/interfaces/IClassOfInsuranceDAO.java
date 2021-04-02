package org.ace.insurance.system.common.classofinsurance.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.classofinsurance.ClassOfInsurance;
import org.ace.java.component.persistence.exception.DAOException;

public interface IClassOfInsuranceDAO {
	public void insert(ClassOfInsurance classofinsurance) throws DAOException;

	public void update(ClassOfInsurance classofinsurance) throws DAOException;

	public void delete(ClassOfInsurance classofinsurance) throws DAOException;

	public ClassOfInsurance findById(String id) throws DAOException;

	public List<ClassOfInsurance> findAll() throws DAOException;
	
	public List<ClassOfInsurance> findByCriteria(String criteria) throws DAOException;
	
}

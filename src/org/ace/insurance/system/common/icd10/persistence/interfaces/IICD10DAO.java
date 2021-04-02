package org.ace.insurance.system.common.icd10.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.java.component.persistence.exception.DAOException;

public interface IICD10DAO {
	public void insert(ICD10 icd10) throws DAOException;

	public void update(ICD10 icd10) throws DAOException;

	public void delete(ICD10 icd10) throws DAOException;

	public ICD10 findById(String id) throws DAOException;

	public List<ICD10> findAll() throws DAOException;

	public List<ICD10> findByCriteria(String criteria) throws DAOException;
}

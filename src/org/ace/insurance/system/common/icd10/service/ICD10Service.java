package org.ace.insurance.system.common.icd10.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.system.common.icd10.persistence.interfaces.IICD10DAO;
import org.ace.insurance.system.common.icd10.service.interfaces.IICD10Service;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ICD10Service")
public class ICD10Service extends BaseService implements IICD10Service {

	@Resource(name = "ICD10DAO")
	private IICD10DAO icd10dao;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewICD10(ICD10 idIcd10) {
		try {
			icd10dao.insert(idIcd10);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new idIcd10", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateICD10(ICD10 idIcd10) {
		try {
			icd10dao.update(idIcd10);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a idIcd10", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteICD10(ICD10 idIcd10) {
		try {
			icd10dao.delete(idIcd10);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a idIcd10", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ICD10> findAllICD10() {
		List<ICD10> result = null;
		try {
			result = icd10dao.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of idIcd10)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ICD10 findbyId(String id) {
		ICD10 icd10;
		try {
			icd10 = icd10dao.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of idIcd10)", e);
		}
		return icd10;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ICD10> findByCriteria(String criteria) {
		List<ICD10> result = null;
		try {
			result = icd10dao.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find medicalPlace by criteria " + criteria, e);
		}
		return result;
	}
}

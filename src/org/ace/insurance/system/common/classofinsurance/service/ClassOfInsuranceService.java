package org.ace.insurance.system.common.classofinsurance.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.classofinsurance.ClassOfInsurance;
import org.ace.insurance.system.common.classofinsurance.persistence.interfaces.IClassOfInsuranceDAO;
import org.ace.insurance.system.common.classofinsurance.service.interfaces.IClassOfInsuranceService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ClassOfInsuranceService")
public class ClassOfInsuranceService extends BaseService implements IClassOfInsuranceService {
	@Resource(name = "ClassOfInsuranceDAO")
	private IClassOfInsuranceDAO classOfInsuranceDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewClassOfInsurance(ClassOfInsurance classOfInsurance) {
		try {
			classOfInsuranceDAO.insert(classOfInsurance);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new ClassOfInsurance", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateClassOfInsurance(ClassOfInsurance classOfInsurance) {
		try {
			classOfInsuranceDAO.update(classOfInsurance);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update ClassOfInsurance", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteClassOfInsurance(ClassOfInsurance classOfInsurance) {
		try {
			classOfInsuranceDAO.delete(classOfInsurance);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete a ClassOfInsurance", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClassOfInsurance> findAllClassOfInsurance() {
		List<ClassOfInsurance> result = null;
		try {
			result = classOfInsuranceDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all  ClassOfInsurance", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClassOfInsurance findClassOfInsuranceById(String id) {
		ClassOfInsurance result = null;
		try {
			result = classOfInsuranceDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ClassOfInsurance (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClassOfInsurance> findByCriteria(String criteria) {
		List<ClassOfInsurance> result = null;
		try {
			result = classOfInsuranceDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find ClassOfInsurance by criteria " + criteria, e);
		}
		return result;
	}

}

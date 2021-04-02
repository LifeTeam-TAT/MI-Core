package org.ace.insurance.system.common.operation.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.operation.Operation;
import org.ace.insurance.system.common.operation.persistence.interfaces.IOperationDAO;
import org.ace.insurance.system.common.operation.service.interfaces.IOperationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "OperationService")
public class OperationService extends BaseService implements IOperationService{

	@Resource(name = "OperationDAO")
	private IOperationDAO operationdao;

	@Transactional(propagation = Propagation.REQUIRED)
	public String addNewOperation(Operation idoperation) {
		String s = "";
		try {
			s = operationdao.insert(idoperation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new idoperation", e);
		}
		return s;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateOperation(Operation idoperation) {
		try {
			operationdao.update(idoperation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a idoperation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOperation(Operation idoperation) {
		try {
			operationdao.delete(idoperation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a idoperation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Operation> findAllOperation() {
		List<Operation> result = null;
		try {
			result = operationdao.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of idoperation)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Operation findbyId(String id) {
		Operation operation;
		try {
			operation = operationdao.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of idoperation)", e);
		}
		return operation;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Operation> findByCriteria(String criteria) {
		List<Operation> result = null;
		try {
			result = operationdao.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find medicalPlace by criteria " + criteria, e);
		}
		return result;
	}
}

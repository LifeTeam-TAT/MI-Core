package org.ace.insurance.system.common.initial.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.initial.Initial;
import org.ace.insurance.system.common.initial.persistence.interfaces.IInitialDAO;
import org.ace.insurance.system.common.initial.service.interfaces.IInitialService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "InitialService")
public class InitialService extends BaseService implements IInitialService {

	@Resource(name = "InitialDAO")
	private IInitialDAO initialDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewInitial(Initial initial) {
		try {
			initialDAO.insert(initial);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Initial", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateInitial(Initial initial) {
		try {
			initialDAO.update(initial);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Initial", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteInitial(Initial initial) {
		try {
			initialDAO.delete(initial);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Initial", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Initial> findAllInitial() {
		List<Initial> result = null;
		try {
			result = initialDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Initial)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Initial findInitialById(String id) {
		Initial result = null;
		try {
			result = initialDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Initial (ID : " + id + ")", e);
		}
		return result;
	}
}

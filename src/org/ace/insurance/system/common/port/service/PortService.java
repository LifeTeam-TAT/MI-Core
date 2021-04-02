package org.ace.insurance.system.common.port.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.PortCriteria;
import org.ace.insurance.system.common.port.Port;
import org.ace.insurance.system.common.port.persistence.interfaces.IPortDAO;
import org.ace.insurance.system.common.port.service.interfaces.IPortService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PortService")
public class PortService extends BaseService implements IPortService {

	@Resource(name = "PortDAO")
	private IPortDAO portDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewPort(Port port) {
		try {
			portDAO.insert(port);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Port", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePort(Port port) {
		try {
			portDAO.update(port);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Port", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePort(Port port) {
		try {
			portDAO.delete(port);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Port", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Port findPortById(String id) {
		Port result = null;
		try {
			result = portDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Port (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Port> findAllPort() {
		List<Port> result = null;
		try {
			result = portDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Port)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Port> findByCriteria(String criteria) {
		List<Port> result = null;
		try {
			result = portDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Port by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Port> findPortByCriteria(PortCriteria criteria, int max) {
		List<Port> result = null;
		try {
			result = portDAO.findPortByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Port (criteriaValue : " + criteria.getCriteriaValue() + ")", e);
		}
		return result;
	}

}

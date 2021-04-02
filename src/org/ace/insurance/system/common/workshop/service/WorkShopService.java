package org.ace.insurance.system.common.workshop.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.insurance.system.common.workshop.persistence.interfaces.IWorkShopDAO;
import org.ace.insurance.system.common.workshop.service.interfaces.IWorkShopService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "WorkShopService")
public class WorkShopService extends BaseService implements IWorkShopService {

	@Resource(name = "WorkShopDAO")
	private IWorkShopDAO workshopDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(WorkShop workshop) {
		try {
			workshopDAO.insert(workshop);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new WorkShop", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(WorkShop workshop) {
		try {
			workshopDAO.update(workshop);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a WorkShop", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(WorkShop workshop) {
		try {
			workshopDAO.delete(workshop);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a WorkShop", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public WorkShop findById(String id) {
		WorkShop result = null;
		try {
			result = workshopDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to findById a WorkShop", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkShop> findAll() {
		List<WorkShop> result = null;
		try {
			result = workshopDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to findAll a WorkShop", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkShop> findByCriteria(String criteria) {
		List<WorkShop> result = null;
		try {
			result = workshopDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkShop by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkShop> findByCriteria(String criteria, String criteriaValue) {
		List<WorkShop> result = null;
		try {
			result = workshopDAO.findByCriteria(criteria, criteriaValue);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkShop by criteria " + criteria, e);
		}
		return result;
	}

}

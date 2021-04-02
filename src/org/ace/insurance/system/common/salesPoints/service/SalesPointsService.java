package org.ace.insurance.system.common.salesPoints.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.system.common.salesPoints.persistence.interfaces.ISalesPointsDAO;
import org.ace.insurance.system.common.salesPoints.service.interfaces.ISalesPointsService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SalesPointsService")
public class SalesPointsService extends BaseService implements ISalesPointsService {
	@Resource(name = "SalesPointsDAO")
	private ISalesPointsDAO salesPonitsDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSalesPoints(SalesPoints salesPoints) throws SystemException {
		try {
			salesPonitsDAO.insert(salesPoints);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SalesPoints", e);
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSalesPoints(SalesPoints salesPoints) throws SystemException {
		try {
			salesPonitsDAO.update(salesPoints);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a SalesPoints", e);
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSalesPoints(SalesPoints salesPoints) throws SystemException {
		try {
			salesPonitsDAO.delete(salesPoints);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a SalesPoints", e);
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SalesPoints> findAllSalesPoints()throws SystemException{
		List<SalesPoints> result = null;
		try {
			result = salesPonitsDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of SalesPoints)", e);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public SalesPoints findSalesPointsByName(String name) throws SystemException {
		SalesPoints result = null;
		try {
			result = salesPonitsDAO.findByName(name);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a SalesPoints (Name : " + name + ")", e);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SalesPoints> findSalesPointsByBranch(String id) throws SystemException{
		List<SalesPoints> result = null;
		try{
			result = salesPonitsDAO.findSalesPointsByBranch(id);
		}catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of SalesPoints by (Branch:" + id + ")", e);
		}
		return result;
	}

}

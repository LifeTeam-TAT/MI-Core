package org.ace.insurance.system.common.coinsurancecompany.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuredProductGroup;
import org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO;
import org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This serves as the implementation of the {@link ICoinsuranceCompanyService}
 * to manipulate the Co-insurance Company object.
 * 
 * @author ACN
 * @version 1.0.0
 * @Date 2013/05/07
 */
@Service(value = "CoinsuranceCompanyService")
public class CoinsuranceCompanyService extends BaseService implements ICoinsuranceCompanyService {

	@Resource(name = "CoinsuranceCompanyDAO")
	ICoinsuranceCompanyDAO dao;

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#add(org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public CoinsuranceCompany add(CoinsuranceCompany coinsuranceCompany) {
		try {
			dao.insert(coinsuranceCompany);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Co-insurance Company (ID : " + coinsuranceCompany.getId() + ")", e);
		}
		return coinsuranceCompany;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#update(org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(CoinsuranceCompany coinsuranceCompany) {
		try {
			dao.update(coinsuranceCompany);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update Co-insurance Company", e);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#delete(org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(CoinsuranceCompany coinsuranceCompany) {
		try {
			dao.delete(coinsuranceCompany);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete Co-insurance Company", e);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#findById(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public CoinsuranceCompany findById(String id) {
		CoinsuranceCompany ret = null;
		try {
			ret = dao.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to retrieve the Co-insurance Company (ID : " + id + ")", e);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#findAll()
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceCompany> findAll() {
		List<CoinsuranceCompany> ret = null;
		try {
			ret = dao.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to retrieve all Co-insurance Companies", e);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#findByProductGroupId(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceCompany> findByProductGroupId(String productGroupId) {
		List<CoinsuranceCompany> ret = null;
		try {
			ret = dao.findByProductGroupId(productGroupId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to retrieve Co-insurance Companies by Product Group (ID : " + productGroupId + ")", e);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#findCoinsuredProductGroupsByProductGroupId(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuredProductGroup> findCoinsuredProductGroupsByProductGroupId(String productGroupId) {
		List<CoinsuredProductGroup> ret = null;
		try {
			ret = dao.findCoinsuredProductGroupsByProductGroupId(productGroupId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to retrieve Coinsured Product Groups by Product Group (ID : " + productGroupId + ")", e);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService#isPercentageAvailable(ProductGroup
	 *      productGroup, int percentage)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isPercentageAvailable(ProductGroup productGroup, double percentage, CoinsuranceCompany company) {
		boolean ret = false;
		try {
			ret = dao.isPercentageAvailable(productGroup, percentage, company);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed checking percentage availability.", e);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceCompany> findByCriteria(String criteria) {
		List<CoinsuranceCompany> result = null;
		try {
			result = dao.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find CoinsuranceCompany by criteria " + criteria, e);
		}
		return result;
	}
}

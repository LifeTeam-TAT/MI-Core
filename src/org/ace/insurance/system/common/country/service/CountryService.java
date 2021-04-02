/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.country.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.country.CTY001;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.country.persistence.interfaces.ICountryDAO;
import org.ace.insurance.system.common.country.service.interfaces.ICountryService;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "CountryService")
public class CountryService extends BaseService implements ICountryService {

	@Resource(name = "CountryDAO")
	private ICountryDAO countryDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewCountry(Country country) {
		try {
			countryDAO.insert(country);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Country", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCountry(Country country) {
		try {
			countryDAO.update(country);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Country", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCountry(Country country) {
		try {
			countryDAO.delete(country);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Country", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Country> findAllCountry() {
		List<Country> result = null;
		try {
			result = countryDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Country)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Country findCountryById(String id) {
		Country result = null;
		try {
			result = countryDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Country (ID : " + id + ")", e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CTY001> findAll_CTY001() {
		List<CTY001> result = null;
		try {
			result = countryDAO.findAll_CTY001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all CTY001 ", e);
		}
		return result != null ? result : Collections.EMPTY_LIST;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Country findCountryByName(String name) {
		Country result = null;
		try {
			result = countryDAO.findCountryByName(name);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a Country (Name : " + name + ")", e);
		}
		return result;
	}

}
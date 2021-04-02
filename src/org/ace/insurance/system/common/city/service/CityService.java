/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.city.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.city.persistence.interfaces.ICityDAO;
import org.ace.insurance.system.common.city.service.interfaces.ICityService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "CityService")
public class CityService extends BaseService implements ICityService {

	@Resource(name = "CityDAO")
	private ICityDAO cityDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewCity(City city) {
		try {
			cityDAO.insert(city);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new City", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateCity(City city) {
		try {
			cityDAO.update(city);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a City", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteCity(City city) {
		try {
			cityDAO.delete(city);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a City", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<City> findAllCity() {
		List<City> result = null;
		try {
			result = cityDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of City)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public City findCityById(String id) {
		City result = null;
		try {
			result = cityDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a City (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public City findCityByName(String name) {
		City result = null;
		try {
			result = cityDAO.findByName(name);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a City (Name : " + name + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<City> findByCriteria(String criteria) {
		List<City> result = null;
		try {
			result = cityDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find City by criteria " + criteria, e);
		}
		return result;
	}
}
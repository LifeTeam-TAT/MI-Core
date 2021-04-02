/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.township.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.township.TSP001;
import org.ace.insurance.system.common.township.TSP002;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.persistence.interfaces.ITownshipDAO;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "TownshipService")
public class TownshipService extends BaseService implements ITownshipService {

	@Resource(name = "TownshipDAO")
	private ITownshipDAO townshipDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTownship(Township township) {
		try {
			townshipDAO.insert(township);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Township", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateTownship(Township township) {
		try {
			townshipDAO.update(township);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Township", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTownship(Township township) {
		try {
			townshipDAO.delete(township);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Township", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Township> findAllTownship() {
		List<Township> result = null;
		try {
			result = townshipDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Township)", e);
		}
		return result;
	}

	public List<Township> findTownshipByProvince(Province province) {
		List<Township> result = null;
		try {
			result = townshipDAO.findByProvince(province);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Township by province " + province.getName(), e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Township findTownshipById(String id) {
		Township result = null;
		try {
			result = townshipDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Township (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TSP001> findAll_TSP001() {
		List<TSP001> result = null;
		try {
			result = townshipDAO.findAll_TSP001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find  all TSP001", e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TSP002> findAll_TSP002() {
		List<TSP002> result = null;
		try {
			result = townshipDAO.findAll_TSP002();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find  all TSP001", e);
		}
		return result != null ? result : Collections.EMPTY_LIST;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findTspShortNameByProvinceNo(String provinceNo) {
		List<String> result = null;
		try {
			result = townshipDAO.findTspShortNameByProvinceNo(provinceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find TownshipShortName by provinceNo " + provinceNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Township findTownshipByName(String name) {
		Township result = null;
		try {
			result = townshipDAO.findTownshipByName(name);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a Township (Name : " + name + ")", e);
		}
		return result;
	}
}
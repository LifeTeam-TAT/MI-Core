/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.province.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.province.PRV001;
import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.province.persistence.interfaces.IProvinceDAO;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProvinceService")
public class ProvinceService extends BaseService implements IProvinceService {

	@Resource(name = "ProvinceDAO")
	private IProvinceDAO provinceDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProvince(Province province) {
		try {
			provinceDAO.insert(province);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Province", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProvince(Province province) {
		try {
			provinceDAO.update(province);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Province", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProvince(Province province) {
		try {
			provinceDAO.delete(province);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Province", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Province> findAllProvince() {
		List<Province> result = null;
		try {
			result = provinceDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Province)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Province findProvinceById(String id) {
		Province result = null;
		try {
			result = provinceDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Province (ID : " + id + ")", e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<PRV001> findAll_PRV001() {
		List<PRV001> result = null;
		try {
			result = provinceDAO.findAll_PRV001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all PRV001. ", e);
		}

		return result != null ? result : Collections.EMPTY_LIST;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findAllProvinceNo() {
		List<String> result = null;
		try {
			result = provinceDAO.findAllProvinceNo();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all provinceNo of province ", e);
		}
		return result;
	}
}
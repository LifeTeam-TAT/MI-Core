/***************************************************************************************
 * @author YYK
 * @Date 2016-05-06
 * @Version 1.0
 * @Purpose This serves as the implementation of the {@link IDistrictService} to
 * manipulate the <code>District</code> object.
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.district.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.district.DIS001;
import org.ace.insurance.system.common.district.District;
import org.ace.insurance.system.common.district.DistrictCriteria;
import org.ace.insurance.system.common.district.persistence.interfaces.IDistrictDAO;
import org.ace.insurance.system.common.district.service.interfaces.IDistrictService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "DistrictService")
public class DistrictService extends BaseService implements IDistrictService {

	@Resource(name = "DistrictDAO")
	private IDistrictDAO districtDAO;

	/**
	 * @see org.ace.insurance.system.common.district.service.interfaces.IDistrictService
	 *      #addNewDistrict(org.ace.insurance.system.common.district.District)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewDistrict(District district) {
		try {
			districtDAO.insert(district);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new District", e);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.district.service.interfaces.IDistrictService
	 *      #updateDistrict(org.ace.insurance.system.common.district.District)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDistrict(District district) {
		try {
			districtDAO.update(district);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a District", e);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.district.service.interfaces.IDistrictService
	 *      #deleteDistrict(org.ace.insurance.system.common.district.District)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDistrict(District district) {
		try {
			districtDAO.delete(district);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a District", e);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.district.service.interfaces.IDistrictService
	 *      #findAllDistrict()
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<District> findAllDistrict() {
		List<District> result = null;
		try {
			result = districtDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of District)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.system.common.district.service.interfaces.IDistrictService
	 *      #findDistrictById(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public District findDistrictById(String id) {
		District result = null;
		try {
			result = districtDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a District (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DIS001> findByCriteria(DistrictCriteria criteria) {
		List<DIS001> result = null;
		try {
			result = districtDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find District001 by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DIS001> findAll_DIS001() {
		List<DIS001> result = null;
		try {
			result = districtDAO.findAll_DIS001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all District001 ");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDistrictById(String districtId) {
		try {
			districtDAO.deleteDistrictById(districtId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a District by id : " + districtId, e);
		}
	}
}
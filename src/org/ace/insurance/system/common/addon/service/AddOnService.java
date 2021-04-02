/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.addon.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.addon.persistence.interfaces.IAddOnDAO;
import org.ace.insurance.system.common.addon.service.interfaces.IAddOnService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AddOnService")
public class AddOnService extends BaseService implements IAddOnService {

	@Resource(name = "AddOnDAO")
	private IAddOnDAO addOnDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewAddOn(AddOn addOn) {
		try {
			addOnDAO.insert(addOn);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new AddOn", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateAddOn(AddOn addOn) {
		try {
			addOnDAO.update(addOn);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a AddOn", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteAddOn(AddOn addOn) {
		try {
			addOnDAO.delete(addOn);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a AddOn", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<AddOn> findAllAddOn() {
		List<AddOn> result = null;
		try {
			result = addOnDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of AddOn)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AddOn findAddOnById(String id) {
		AddOn result = null;
		try {
			result = addOnDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a AddOn (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AddOn> findByCriteria(String criteria) {
		List<AddOn> result = null;
		try {
			result = addOnDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AddOn by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AddOn> findPremiumRate() {
		List<AddOn> result = null;
		try {
			result = addOnDAO.findPremiumRateOfAddOn();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AddOn with Premium Rate", e);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AddOn> findPremiumRateByProductId(String productId) {
		List<AddOn> result = null;
		try {
			result = addOnDAO.findAddOnByProductId(productId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AddOn with Premium Rate By Product Id", e);
		}

		return result;
	}
}
/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.industry.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.industry.Industry;
import org.ace.insurance.system.common.industry.persistence.interfaces.IIndustryDAO;
import org.ace.insurance.system.common.industry.service.interfaces.IIndustryService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "IndustryService")
public class IndustryService extends BaseService implements IIndustryService {

	@Resource(name = "IndustryDAO")
	private IIndustryDAO industryDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewIndustry(Industry Industry) {
		try {
			industryDAO.insert(Industry);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Industry", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateIndustry(Industry Industry) {
		try {
			industryDAO.update(Industry);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Industry", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteIndustry(Industry Industry) {
		try {
			industryDAO.delete(Industry);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Industry", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Industry> findAllIndustry() {
		List<Industry> result = null;
		try {
			result = industryDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Industry)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Industry findIndustryById(String id) {
		Industry result = null;
		try {
			result = industryDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Industry (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Industry> findByCriteria(String criteria) {
		List<Industry> result = null;
		try {
			result = industryDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Industry by criteria " + criteria, e);
		}
		return result;
	}

}
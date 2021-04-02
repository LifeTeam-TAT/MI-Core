/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.life.lifePolicySummary.Service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.lifePolicySummary.persistence.interfaces.ILifePolicySummaryDAO;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifePolicySummaryService")
public class LifePolicySummaryService extends BaseService implements ILifePolicySummaryService {

	@Resource(name = "LifePolicySummaryDAO")
	private ILifePolicySummaryDAO lifePolicySummaryDAO;

	public void addNewLifePolicySummary(LifePolicySummary lifePolicySummary) {
		try {
			lifePolicySummaryDAO.insert(lifePolicySummary);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicySummary", e);
		}
	}

	public void updateLifePolicySummary(LifePolicySummary lifePolicySummary) {
		try {
			lifePolicySummaryDAO.update(lifePolicySummary);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicySummary", e);
		}
	}

	public void deleteLifePolicySummary(LifePolicySummary lifePolicySummary) {
		try {
			lifePolicySummaryDAO.delete(lifePolicySummary);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifePolicySummary", e);
		}
	}

	public List<LifePolicySummary> findAllLifePolicySummary() {
		List<LifePolicySummary> result = null;
		try {
			result = lifePolicySummaryDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicySummary)", e);
		}
		return result;
	}

	public LifePolicySummary findLifePolicySummaryById(String id) {
		LifePolicySummary result = null;
		try {
			result = lifePolicySummaryDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicySummary (ID : " + id + ")", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED)
	public LifePolicySummary findLifePolicyByPolicyNo(String policyNo) {
		LifePolicySummary result = null;
		try {
			result = lifePolicySummaryDAO.findByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicySummary)", e);
		}
		return result;
	}

}
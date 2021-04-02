/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.religion.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.system.common.religion.persistence.interfaces.IReligionDAO;
import org.ace.insurance.system.common.religion.service.interfaces.IReligionService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ReligionService")
public class ReligionService extends BaseService implements IReligionService {

	@Resource(name = "ReligionDAO")
	private IReligionDAO religionDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewReligion(Religion religion) {
		try {
			religionDAO.insert(religion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Religion", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateReligion(Religion religion) {
		try {
			religionDAO.update(religion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Religion", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteReligion(Religion religion) {
		try {
			religionDAO.delete(religion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Religion", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Religion> findAllReligion() {
		List<Religion> result = null;
		try {
			result = religionDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Religion)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Religion findReligionById(String id) {
		Religion result = null;
		try {
			result = religionDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Religion (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Religion> findByCriteria(String criteria) {
		List<Religion> result = null;
		try {
			result = religionDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Religion by criteria " + criteria, e);
		}
		return result;
	}

}
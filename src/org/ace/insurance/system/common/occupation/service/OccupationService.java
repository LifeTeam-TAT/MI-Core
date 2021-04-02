/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.occupation.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.persistence.interfaces.IOccupationDAO;
import org.ace.insurance.system.common.occupation.service.interfaces.IOccupationService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "OccupationService")
public class OccupationService extends BaseService implements IOccupationService {

	@Resource(name = "OccupationDAO")
	private IOccupationDAO occupationDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewOccupation(Occupation occupation) {
		try {
			occupationDAO.insert(occupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Occupation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateOccupation(Occupation occupation) {
		try {
			occupationDAO.update(occupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Occupation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOccupation(Occupation occupation) {
		try {
			occupationDAO.delete(occupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Occupation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occupation> findAllOccupation() {
		List<Occupation> result = null;
		try {
			result = occupationDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Occupation)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Occupation findOccupationById(String id) {
		Occupation result = null;
		try {
			result = occupationDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Occupation (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occupation> findOccupationByInsuranceType(InsuranceType insuranceType) {
		List<Occupation> result = null;
		try {
			result = occupationDAO.findByInsuranceType(insuranceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Occupation by Insurance Type ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occupation> findByCriteria(String criteria) {
		List<Occupation> result = null;
		try {
			result = occupationDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Occupation by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Occupation findOccupationByName(String name) {
		Occupation result = null;
		try {
			result = occupationDAO.findOccupationByName(name);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a Occupation (Name : " + name + ")", e);
		}
		return result;
	}

}
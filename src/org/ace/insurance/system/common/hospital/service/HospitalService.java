/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.hospital.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.hospital.persistence.interfaces.IHospitalDAO;
import org.ace.insurance.system.common.hospital.service.interfaces.IHospitalService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "HospitalService")
public class HospitalService extends BaseService implements IHospitalService {

	@Resource(name = "HospitalDAO")
	private IHospitalDAO hospitalDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewHospital(Hospital hospital) {
		try {
			hospitalDAO.insert(hospital);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Hospital", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateHospital(Hospital hospital) {
		try {
			hospitalDAO.update(hospital);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Hospital", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteHospital(Hospital hospital) {
		try {
			hospitalDAO.delete(hospital);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Hospital", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Hospital> findAllHospital() {
		List<Hospital> result = null;
		try {
			result = hospitalDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Hospital)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Hospital findHospitalById(String id) {
		Hospital result = null;
		try {
			result = hospitalDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Hospital (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Hospital> findByCriteria(String criteria) {
		List<Hospital> result = null;
		try {
			result = hospitalDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Hospital by criteria " + criteria, e);
		}
		return result;
	}

}
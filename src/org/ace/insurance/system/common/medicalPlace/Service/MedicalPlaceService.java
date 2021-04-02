package org.ace.insurance.system.common.medicalPlace.Service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.medicalPlace.MedicalPlace;
import org.ace.insurance.system.common.medicalPlace.Service.interfaces.IMedicalPlaceService;
import org.ace.insurance.system.common.medicalPlace.persistence.interfaces.IMedicalPlaceDAO;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalPlaceService")
public class MedicalPlaceService extends BaseService implements IMedicalPlaceService {

	@Resource(name = "MedicalPlaceDAO")
	private IMedicalPlaceDAO medicalPlaceDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewMedicalPlace(MedicalPlace medicalPlace) {
		try {
			medicalPlaceDAO.insert(medicalPlace);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MedicalPlace", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMedicalPlace(MedicalPlace medicalPlace) {
		try {
			medicalPlaceDAO.update(medicalPlace);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MedicalPlace", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMedicalPlace(MedicalPlace medicalPlace) {
		try {
			medicalPlaceDAO.delete(medicalPlace);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a MedicalPlace", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPlace> findAllMedicalPlace() {
		List<MedicalPlace> result = null;
		try {
			result = medicalPlaceDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MedicalPlace)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalPlace findbyId(String id) {
		MedicalPlace medicalPlace;
		try {
			medicalPlace = medicalPlaceDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MedicalPlace)", e);
		}
		return medicalPlace;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPlace> findByCriteria(String criteria) {
		List<MedicalPlace> result = null;
		try {
			result = medicalPlaceDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find medicalPlace by criteria " + criteria, e);
		}
		return result;
	}
}

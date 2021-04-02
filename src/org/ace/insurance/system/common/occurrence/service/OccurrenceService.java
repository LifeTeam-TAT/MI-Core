package org.ace.insurance.system.common.occurrence.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.occurrence.Occurrence;
import org.ace.insurance.system.common.occurrence.persistence.interfaces.IOccurrenceDAO;
import org.ace.insurance.system.common.occurrence.service.interfaces.IOccurrenceService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "OccurrenceService")
public class OccurrenceService extends BaseService implements IOccurrenceService {

	@Resource(name = "OccurrenceDAO")
	private IOccurrenceDAO occurrenceDAO;

	public void addNewOccurrence(Occurrence occurrence) {
		try {
			// For Desc
			String fromCityName = occurrence.getFromCity().getName().substring(0, 1).toUpperCase()
					+ occurrence.getFromCity().getName().substring(1, occurrence.getFromCity().getName().length()).toLowerCase();
			String toCityName = occurrence.getToCity().getName().substring(0, 1).toUpperCase()
					+ occurrence.getToCity().getName().substring(1, occurrence.getToCity().getName().length()).toLowerCase();
			occurrence.setDescription(fromCityName + " - " + toCityName);

			occurrenceDAO.insert(occurrence);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Occurrence", e);
		}
	}

	public void updateOccurrence(Occurrence occurrence) {
		try {
			// For Desc
			String fromCityName = occurrence.getFromCity().getName().substring(0, 1).toUpperCase()
					+ occurrence.getFromCity().getName().substring(1, occurrence.getFromCity().getName().length()).toLowerCase();
			String toCityName = occurrence.getToCity().getName().substring(0, 1).toUpperCase()
					+ occurrence.getToCity().getName().substring(1, occurrence.getToCity().getName().length()).toLowerCase();
			occurrence.setDescription(fromCityName + " - " + toCityName);
			occurrenceDAO.update(occurrence);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Occurrence", e);
		}
	}

	public void deleteOccurrence(Occurrence occurrence) {
		try {
			occurrenceDAO.delete(occurrence);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Occurrence", e);
		}
	}

	public List<Occurrence> findAllOccurrence() {
		List<Occurrence> result = null;
		try {
			result = occurrenceDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Occurrence)", e);
		}
		return result;
	}

	public Occurrence findOccurrenceById(String id) {
		Occurrence result = null;
		try {
			result = occurrenceDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Occurrence (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occurrence> findByCriteria(String criteria) {
		List<Occurrence> result = null;
		try {
			result = occurrenceDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Occurrence by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Occurrence findByFromCityToCity(Occurrence occurrence) {
		Occurrence result = null;
		try {
			result = occurrenceDAO.findByFromCityToCity(occurrence);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Occurrence by FromCityToCity " + occurrence.getDescription(), e);
		}
		return result;
	}

}

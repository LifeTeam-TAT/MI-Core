/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.typesOfSport.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.insurance.system.common.typesOfSport.persistence.interfaces.ITypesOfSportDAO;
import org.ace.insurance.system.common.typesOfSport.service.interfaces.ITypesOfSportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "TypesOfSportService")
public class TypesOfSportService extends BaseService implements ITypesOfSportService {

	@Resource(name = "TypesOfSportDAO")
	private ITypesOfSportDAO typesOfSportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTypesOfSport(TypesOfSport typesOfSport) {
		try {
			typesOfSportDAO.insert(typesOfSport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TypesOfSport", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateTypesOfSport(TypesOfSport typesOfSport) {
		try {
			typesOfSportDAO.update(typesOfSport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a TypesOfSport", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTypesOfSport(TypesOfSport typesOfSport) {
		try {
			typesOfSportDAO.delete(typesOfSport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a TypesOfSport", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TypesOfSport> findAllTypesOfSport() {
		List<TypesOfSport> result = null;
		try {
			result = typesOfSportDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of TypesOfSport)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TypesOfSport findTypesOfSportById(String id) {
		TypesOfSport result = null;
		try {
			result = typesOfSportDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a TypesOfSport (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TypesOfSport> findByCriteria(String criteria, int max) {
		List<TypesOfSport> result = null;
		try {
			result = typesOfSportDAO.findByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find TypesOfSport by criteria " + criteria, e);
		}
		return result;
	}

}
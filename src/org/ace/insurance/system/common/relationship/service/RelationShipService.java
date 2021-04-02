/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.relationship.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.persistence.interfaces.IRelationShipDAO;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "RelationShipService")
public class RelationShipService extends BaseService implements IRelationShipService {

	@Resource(name = "RelationShipDAO")
	private IRelationShipDAO relationShipDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewRelationShip(RelationShip relationShip) {
		try {
			relationShipDAO.insert(relationShip);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new RelationShip", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateRelationShip(RelationShip relationShip) {
		try {
			relationShipDAO.update(relationShip);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a RelationShip", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRelationShip(RelationShip relationShip) {
		try {
			relationShipDAO.delete(relationShip);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a RelationShip", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RelationShip> findAllRelationShip() {
		List<RelationShip> result = null;
		try {
			result = relationShipDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of RelationShip)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public RelationShip findRelationShipById(String id) {
		RelationShip result = null;
		try {
			result = relationShipDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a RelationShip (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RelationShip> findByCriteria(String criteria) {
		List<RelationShip> result = null;
		try {
			result = relationShipDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find RelationShip by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public RelationShip findRelationShipByName(String name) {
		RelationShip result = null;
		try {
			result = relationShipDAO.findRelationShipByName(name);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a RelationShip (Name : " + name + ")", e);
		}
		return result;
	}

}
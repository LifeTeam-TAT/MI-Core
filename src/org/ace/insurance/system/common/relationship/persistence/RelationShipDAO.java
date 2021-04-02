/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.relationship.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.persistence.interfaces.IRelationShipDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("RelationShipDAO")
public class RelationShipDAO extends BasicDAO implements IRelationShipDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(RelationShip relationShip) throws DAOException {
		try {
			em.persist(relationShip);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert RelationShip", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(RelationShip relationShip) throws DAOException {
		try {
			em.merge(relationShip);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update RelationShip", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(RelationShip relationShip) throws DAOException {
		try {
			relationShip = em.merge(relationShip);
			em.remove(relationShip);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update RelationShip", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public RelationShip findById(String id) throws DAOException {
		RelationShip result = null;
		try {
			result = em.find(RelationShip.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find RelationShip", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RelationShip> findAll() throws DAOException {
		List<RelationShip> result = null;
		try {
			Query q = em.createNamedQuery("RelationShip.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of RelationShip", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RelationShip> findByCriteria(String criteria) throws DAOException {
		List<RelationShip> result = null;
		try {
			// Query q = em.createNamedQuery("RelationShip.findByCriteria");
			Query q = em.createQuery("Select t from RelationShip t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of RelationShip.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public RelationShip findRelationShipByName(String name) {
		RelationShip result = null;
		List<RelationShip> relationshipList = null;
		try {
			Query query = em.createQuery("SELECT r FROM RelationShip r where r.name = :name");
			query.setParameter("name", name);
			relationshipList = query.getResultList();
			if (relationshipList.isEmpty()) {
				result = null;
			} else {
				result = relationshipList.get(0);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find RelationShip", pe);
		}
		return result;
	}

}

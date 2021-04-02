/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.typesOfSport.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.insurance.system.common.typesOfSport.persistence.interfaces.ITypesOfSportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("TypesOfSportDAO")
public class TypesOfSportDAO extends BasicDAO implements ITypesOfSportDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(TypesOfSport typesOfSport) throws DAOException {
		try {
			em.persist(typesOfSport);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert TypesOfSport", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(TypesOfSport typesOfSport) throws DAOException {
		try {
			em.merge(typesOfSport);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update TypesOfSport", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(TypesOfSport typesOfSport) throws DAOException {
		try {
			typesOfSport = em.merge(typesOfSport);
			em.remove(typesOfSport);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update TypesOfSport", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TypesOfSport findById(String id) throws DAOException {
		TypesOfSport result = null;
		try {
			result = em.find(TypesOfSport.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find TypesOfSport", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TypesOfSport> findAll() throws DAOException {
		List<TypesOfSport> result = null;
		try {
			Query q = em.createNamedQuery("TypesOfSport.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of TypesOfSport", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TypesOfSport> findByCriteria(String criteria, int max) throws DAOException {
		List<TypesOfSport> result = null;
		try {
			// Query q = em.createNamedQuery("Township.findByCriteria");
			Query q = em.createQuery("Select t from TypesOfSport t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			q.setMaxResults(max);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of TypesOfSport.", pe);
		}
		return result;
	}

}

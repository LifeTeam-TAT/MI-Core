/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.city.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.city.persistence.interfaces.ICityDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CityDAO")
public class CityDAO extends BasicDAO implements ICityDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(City city) throws DAOException {
		try {
			em.persist(city);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert City", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(City city) throws DAOException {
		try {
			em.merge(city);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update City", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(City city) throws DAOException {
		try {
			city = em.merge(city);
			em.remove(city);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update City", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public City findById(String id) throws DAOException {
		City result = null;
		try {
			result = em.find(City.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find City", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<City> findAll() throws DAOException {
		List<City> result = null;
		try {
			Query q = em.createNamedQuery("City.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of City", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<City> findByCriteria(String criteria) throws DAOException {
		List<City> result = null;
		try {
			// Query q = em.createNamedQuery("Township.findByCriteria");
			Query q = em.createQuery("Select t from City t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of City.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public City findByName(String name) throws DAOException {
		List<City> result = null;
		try {
			Query q = em.createQuery("Select c from City c where c.name = :name ");
			q.setParameter("name", name);
			result = q.getResultList();
			if (result.size() > 0) {
				return result.get(0);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by name of City.", pe);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findNameById(String id) throws DAOException {
		String name = null;
		try {
			Query query = em.createQuery("SELECT c.name FROM City c where c.id = :id");
			query.setParameter("id", id);
			name = (String) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Township", pe);
		}
		return name;
	}
}

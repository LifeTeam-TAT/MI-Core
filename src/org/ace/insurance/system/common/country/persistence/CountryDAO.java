/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.country.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.country.CTY001;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.country.persistence.interfaces.ICountryDAO;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CountryDAO")
public class CountryDAO extends BasicDAO implements ICountryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Country country) throws DAOException {
		try {
			em.persist(country);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Country", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Country country) throws DAOException {
		try {
			em.merge(country);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Country", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Country country) throws DAOException {
		try {
			country = em.merge(country);
			em.remove(country);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete Country", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Country findById(String id) throws DAOException {
		Country result = null;
		try {
			result = em.find(Country.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Country", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Country> findAll() throws DAOException {
		List<Country> result = null;
		try {
			Query q = em.createNamedQuery("Country.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Country", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CTY001> findAll_CTY001() throws DAOException {
		List<CTY001> result = null;
		try {
			Query q = em.createQuery("SELECT NEW " + CTY001.class.getName() + " (t.id, t.name, t.description, t.code) FROM Country t ORDER BY t.name");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Country.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findNameById(String id) throws DAOException {
		String name = null;
		try {
			Query query = em.createQuery("SELECT c.name FROM Country c where c.id = :id");
			query.setParameter("id", id);
			name = (String) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Township", pe);
		}
		return name;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Country findCountryByName(String name) {
		Country result = null;
		List<Country> countryList = null;
		try {
			Query query = em.createQuery("SELECT c FROM Country c where c.name = :name");
			query.setParameter("name", name);
			countryList = query.getResultList();
			if (countryList.isEmpty()) {
				result = null;
			} else {
				result = countryList.get(0);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Country", pe);
		}
		return result;
	}
}

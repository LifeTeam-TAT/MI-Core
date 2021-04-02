/***************************************************************************************
 * @author YYK
 * @Date 2016-May-6
 * @Version 1.0
 * @Purpose This serves as the implementation of the {@link IDistrictDAO} to
 * manipulate the <code>District</code> object.
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.district.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.district.DIS001;
import org.ace.insurance.system.common.district.District;
import org.ace.insurance.system.common.district.DistrictCriteria;
import org.ace.insurance.system.common.district.persistence.interfaces.IDistrictDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("DistrictDAO")
@SuppressWarnings("unchecked")
public class DistrictDAO extends BasicDAO implements IDistrictDAO {

	/**
	 * @see org.ace.insurance.system.common.district.persistence.interfaces.IDistrictDAO
	 *      #insert(org.ace.insurance.system.common.district.District)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(District district) throws DAOException {
		try {
			em.persist(district);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert District", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.district.persistence.interfaces.IDistrictDAO
	 *      #update(org.ace.insurance.system.common.district.District)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(District district) throws DAOException {
		try {
			em.merge(district);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update District", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.district.persistence.interfaces.IDistrictDAO
	 *      #delete(org.ace.insurance.system.common.district.District)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(District district) throws DAOException {
		try {
			district = em.merge(district);
			em.remove(district);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update District", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.district.persistence.interfaces.IDistrictDAO
	 *      #findById(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public District findById(String id) throws DAOException {
		District result = null;
		try {
			result = em.find(District.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find District", pe);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.system.common.district.persistence.interfaces.IDistrictDAO
	 *      #findAll()
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<District> findAll() throws DAOException {
		List<District> result = null;
		try {
			Query q = em.createNamedQuery("District.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of District", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DIS001> findByCriteria(DistrictCriteria criteria) throws DAOException {
		List<DIS001> result = null;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT new org.ace.insurance.system.common.district.District001");
			queryString.append("(d.id, d.name, d.code, d.province.name, d.description )");
			queryString.append(" FROM District d INNER JOIN d.province p WHERE d.id IS NOT NULL");
			if (criteria.getName() != null) {
				queryString.append(" AND LOWER(d.name) LIKE :name");
			}
			if (criteria.getCode() != null) {
				queryString.append(" AND LOWER(d.code) LIKE :code");
			}
			if (criteria.getProvince() != null) {
				queryString.append(" AND LOWER(p.name) LIKE :province");
			}
			Query q = em.createQuery(queryString.toString());
			if (criteria.getName() != null) {
				q.setParameter("name", criteria.getName().toLowerCase() + "%");
			}
			if (criteria.getCode() != null) {
				q.setParameter("code", "%" + criteria.getCode().toLowerCase() + "%");
			}
			if (criteria.getProvince() != null) {
				q.setParameter("province", criteria.getProvince().toLowerCase() + "%");
			}
			if (criteria.getLimit() > 0) {
				q.setMaxResults(criteria.getLimit());
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of District001.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DIS001> findAll_DIS001() throws DAOException {
		List<DIS001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + DIS001.class.getName());
			buffer.append("(d.id, d.name, d.code, d.province.name, d.description )");
			buffer.append(" FROM District d ");
			Query q = em.createQuery(buffer.toString());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all DIS001.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDistrictById(String districtId) throws DAOException {
		try {
			Query query = em.createNamedQuery("District.deleteById");
			query.setParameter("id", districtId);
			query.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete District by id", pe);
		}
	}
}

/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.hospital.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.hospital.persistence.interfaces.IHospitalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("HospitalDAO")
public class HospitalDAO extends BasicDAO implements IHospitalDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Hospital hospital) throws DAOException {
		try {
			em.persist(hospital);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Hospital", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Hospital hospital) throws DAOException {
		try {
			em.merge(hospital);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Hospital", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Hospital hospital) throws DAOException {
		try {
			hospital = em.merge(hospital);
			em.remove(hospital);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Hospital", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Hospital findById(String id) throws DAOException {
		Hospital result = null;
		try {
			result = em.find(Hospital.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Hospital", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Hospital> findAll() throws DAOException {
		List<Hospital> result = null;
		try {
			Query q = em.createNamedQuery("Hospital.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Hospital", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Hospital> findByCriteria(String criteria) throws DAOException {
		List<Hospital> result = null;
		try {
			// Query q = em.createNamedQuery("Hospital.findByCriteria");
			Query q = em.createQuery("Select t from Hospital t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Hospital.", pe);
		}
		return result;
	}

}

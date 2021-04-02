package org.ace.insurance.system.common.medicalPlace.persistence;

import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.ace.insurance.system.common.medicalPlace.MedicalPlace;
import org.ace.insurance.system.common.medicalPlace.persistence.interfaces.IMedicalPlaceDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author Kyaw Myat Htut
 * @Date 2014-7-25
 * @Version 1.0
 * @Purpose This DAO serves as the Database Access Layer to manipulate the
 *          <code>MedicalPlace</code> object.
 * 
 * 
 ***************************************************************************************/
@Repository("MedicalPlaceDAO")
public class MedicalPlaceDAO extends BasicDAO implements IMedicalPlaceDAO {

	/**
	 * @see org.ace.insurance.system.common.medicalPlace.persistence.interfaces.IMedicalPlaceDAO
	 *      #insert(org.ace.insurance.system.common.medicalPlace.MedicalPlace)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(MedicalPlace medicalPlace) throws DAOException {
		try {
			em.persist(medicalPlace);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert medicalPlace", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.medicalPlace.persistence.interfaces.IMedicalPlaceDAO
	 *      #update(org.ace.insurance.system.common.medicalPlace.MedicalPlace)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(MedicalPlace medicalPlace) throws DAOException {
		try {
			em.merge(medicalPlace);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update medicalPlace", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.medicalPlace.persistence.interfaces.IMedicalPlaceDAO
	 *      #delete(org.ace.insurance.system.common.medicalPlace.MedicalPlace)
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(MedicalPlace medicalPlace) throws DAOException {
		try {
			medicalPlace = em.merge(medicalPlace);
			em.remove(medicalPlace);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update medicalPlace", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.medicalPlace.persistence.interfaces.IMedicalPlaceDAO
	 *      #findById(String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalPlace findById(String id) throws DAOException {
		MedicalPlace result = null;
		try {
			result = em.find(MedicalPlace.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MedicalPlace", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPlace> findAll() throws DAOException {
		List<MedicalPlace> result = null;
		try {
			Query q = em.createNamedQuery("MedicalPlace.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MedicalPlace", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPlace> findByCriteria(String criteria) throws DAOException {
		List<MedicalPlace> result = null;
		try {
			Query q = em.createQuery("Select c from MedicalPlace c where c.name Like '" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of MedicalPlace.", pe);
		}
		return result;
	}
}

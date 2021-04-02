/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.occupation.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.persistence.interfaces.IOccupationDAO;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("OccupationDAO")
public class OccupationDAO extends BasicDAO implements IOccupationDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Occupation occupation) throws DAOException {
		try {
			em.persist(occupation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Occupation", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Occupation occupation) throws DAOException {
		try {
			em.merge(occupation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Occupation", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Occupation occupation) throws DAOException {
		try {
			occupation = em.merge(occupation);
			em.remove(occupation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Occupation", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Occupation findById(String id) throws DAOException {
		Occupation result = null;
		try {
			result = em.find(Occupation.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Occupation", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occupation> findByInsuranceType(InsuranceType insuranceType) throws DAOException {
		List<Occupation> result = null;
		try {
			Query q = em.createNamedQuery("Occupation.findByInsuranceType");
			q.setParameter("insuranceType", insuranceType);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Occupation by Insurance Type", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occupation> findAll() throws DAOException {
		List<Occupation> result = null;
		try {
			Query q = em.createNamedQuery("Occupation.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Occupation", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occupation> findByCriteria(String criteria) throws DAOException {
		List<Occupation> result = null;
		try {
			Query q = em.createQuery("Select t from Occupation t where t.name Like '" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Occupation.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Occupation findOccupationByName(String name) {
		Occupation result = null;
		List<Occupation> occupationList = null;
		try {
			Query query = em.createQuery("SELECT o FROM Occupation o where o.name = :name");
			query.setParameter("name", name);
			occupationList = query.getResultList();
			if (occupationList.isEmpty()) {
				result = null;
			} else {
				result = occupationList.get(0);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Township", pe);
		}
		return result;
	}

}

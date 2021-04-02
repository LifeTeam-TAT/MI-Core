package org.ace.insurance.system.common.buildingOccupation.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupation;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.insurance.system.common.buildingOccupation.persistence.interfaces.IBuildingOccupationDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("BuildingOccupationDAO")
public class BuildingOccupationDAO extends BasicDAO implements IBuildingOccupationDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(BuildingOccupation buildingOccupation) throws DAOException {
		try {
			em.persist(buildingOccupation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert BuildingOccupation", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(BuildingOccupation buildingOccupation) throws DAOException {
		try {
			em.merge(buildingOccupation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update BuildingOccupation", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(BuildingOccupation buildingOccupation) throws DAOException {
		try {
			buildingOccupation = em.merge(buildingOccupation);
			em.remove(buildingOccupation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update BuildingOccupation", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public BuildingOccupation findById(String id) throws DAOException {
		BuildingOccupation result = null;
		try {
			result = em.find(BuildingOccupation.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find BuildingOccupation", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BuildingOccupation> findByInsuranceType(InsuranceType insuranceType) throws DAOException {
		List<BuildingOccupation> result = null;
		try {
			Query q = em.createNamedQuery("BuildingOccupation.findByInsuranceType");
			q.setParameter("insuranceType", insuranceType);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of BuildingOccupation by Insurance Type", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BuildingOccupation> findAll() throws DAOException {
		List<BuildingOccupation> result = null;
		try {
			Query q = em.createNamedQuery("BuildingOccupation.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of BuildingOccupation", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BuildingOccupation> findByCriteria(String searchName, BuildingOccupationType buildingOccupationType) throws DAOException {
		List<BuildingOccupation> result = null;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("Select t from BuildingOccupation t where t.name is not null");
			if (searchName != null && !searchName.equals("")) {
				queryString.append(" And t.name Like :searchName");
			}
			if (buildingOccupationType != null) {
				queryString.append(" And t.buildingOccupationType =:buildingOccupationType");
			}
			Query q = em.createQuery(queryString.toString());
			if (searchName != null && !searchName.equals("")) {
				q.setParameter("searchName", "%" + searchName + "%");
			}
			if (buildingOccupationType != null) {
				q.setParameter("buildingOccupationType", buildingOccupationType);
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of BuildingOccupation.", pe);
		}
		return result;
	}

}

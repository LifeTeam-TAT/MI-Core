package org.ace.insurance.system.common.occurrence.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.occurrence.Occurrence;
import org.ace.insurance.system.common.occurrence.persistence.interfaces.IOccurrenceDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("OccurrenceDAO")
public class OccurrenceDAO extends BasicDAO implements IOccurrenceDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Occurrence occurrence) throws DAOException {
		try {
			em.persist(occurrence);
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Occurrence", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Occurrence occurrence) throws DAOException {
		try {
			em.merge(occurrence);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Occurrence", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Occurrence occurrence) throws DAOException {
		try {
			occurrence = em.merge(occurrence);
			em.remove(occurrence);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete Occurrence", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Occurrence findById(String id) throws DAOException {
		Occurrence result = null;
		try {
			result = em.find(Occurrence.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Occurrence", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occurrence> findAll() throws DAOException {
		List<Occurrence> result = null;
		try {
			Query q = em.createNamedQuery("Occurrence.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Occurrence", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Occurrence> findByCriteria(String criteria) throws DAOException {
		List<Occurrence> result = null;
		try {
			// Query q = em.createNamedQuery("Township.findByCriteria");
			Query q = em.createQuery("Select t from Occurrence t where t.description Like '" + criteria + "%'");
			q.setMaxResults(50);
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Occurrence.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Occurrence findByFromCityToCity(Occurrence occurrence) throws DAOException {
		Occurrence result = null;
		try {
			// Query q =
			// em.createQuery("Select o from Occurrence o where o.fromCity.id =
			// fromCityId AND o.toCity.id = toCityId");
			// q.setParameter("fromCityId", occurrence.getFromCity().getId());
			// q.setParameter("toCityId", occurrence.getToCity().getId());

			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT o FROM Occurrence o where o.id is not null ");
			if (occurrence.getFromCity() != null) {
				queryString.append(" AND o.fromCity.id = :fromCity");
			}
			if (occurrence.getToCity() != null) {
				queryString.append(" AND o.toCity.id = :toCity");
			}
			Query query = em.createQuery(queryString.toString());
			if (occurrence.getFromCity() != null) {
				query.setParameter("fromCity", occurrence.getFromCity().getId());
			}
			if (occurrence.getToCity() != null) {
				query.setParameter("toCity", occurrence.getToCity().getId());
			}

			result = (Occurrence) query.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return result;
		} catch (PersistenceException pe) {
			throw translate("Failed to find by FromCityToCity of Occurrence.", pe);
		}
		return result;
	}

}

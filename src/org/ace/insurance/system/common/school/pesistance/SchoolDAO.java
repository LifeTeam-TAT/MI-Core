package org.ace.insurance.system.common.school.pesistance;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.school.School;
import org.ace.insurance.system.common.school.pesistance.interfaces.ISchoolDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("SchoolDAO")
public class SchoolDAO extends BasicDAO implements ISchoolDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(School school) throws DAOException {
		try {
			em.persist(school);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert School", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(School school) throws DAOException {
		try {

			school = em.merge(school);
			em.remove(school);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete School", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(School school) throws DAOException {
		try {
			em.merge(school);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update School", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public School findById(String SchoolId) throws DAOException {
		School result = null;
		try {
			result = em.find(School.class, SchoolId);
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find school", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<School> findAllSchool() throws DAOException {
		List<School> result = null;
		try {
			Query q = em.createQuery("SELECT s FROM School s");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to Find All School", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public School findBySchoolCode(String schoolCodeNo) throws DAOException {
		School result = null;
		try {
			Query q = em.createQuery("SELECT S FROM School s WHERE s.schoolCodeNo  = :schoolCodeNo ");
			q.setParameter("schoolCodeNo", schoolCodeNo);
			result = (School) q.getSingleResult();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find school", pe);
		}
		return result;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkExistingSchool(School school) throws DAOException {
		boolean exist = false;
		String schoolName = school.getName().replaceAll("\\s+", "");
		try {
			StringBuffer buffer = new StringBuffer();
			Query query = null;
			buffer = new StringBuffer("SELECT CASE WHEN (COUNT(c.name) > 0) THEN TRUE ELSE FALSE END FROM School c ");
			buffer.append(" WHERE LOWER(FUNCTION('REPLACE',c.name,' ','')) = :name ");
			buffer.append(" AND c.township.id = :townshipId ");
			buffer.append(" AND LOWER(c.address) = :address");
			buffer.append(school.getId() != null ? " AND c.id != :id" : "");
			query = em.createQuery(buffer.toString());
			if (school.getId() != null)
				query.setParameter("id", school.getId());
			query.setParameter("name", schoolName.toLowerCase());
			query.setParameter("townshipId", school.getTownship().getId());
			query.setParameter("address", school.getAddress().toLowerCase());
			exist = (Boolean) query.getSingleResult();

			if (!exist) {
				buffer = new StringBuffer("SELECT CASE WHEN (COUNT(c.id) > 0) THEN TRUE ELSE FALSE END FROM School c");
				buffer.append(" WHERE c.id != :id");
				buffer.append(" AND LOWER(FUNCTION('REPLACE',c.name,' ','')) = :name ");
				buffer.append(" AND c.township.id = :townshipId ");
				buffer.append(" AND LOWER(c.address) = :address");
				query = em.createQuery(buffer.toString());
				query.setParameter("id", school.getId());
				query.setParameter("name", school.getName());
				query.setParameter("townshipId", schoolName.toLowerCase());
				query.setParameter("address", school.getAddress());
				exist = (Boolean) query.getSingleResult();
			}
			em.flush();
			return exist;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Existing Name and Address No.", pe);
		}
	}
}

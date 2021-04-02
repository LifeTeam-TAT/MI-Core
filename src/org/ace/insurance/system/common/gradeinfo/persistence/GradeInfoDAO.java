package org.ace.insurance.system.common.gradeinfo.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.gradeinfo.GradeInfo;
import org.ace.insurance.system.common.gradeinfo.persistence.interfaces.IGradeInfoDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("GradeInfoDAO")
public class GradeInfoDAO extends BasicDAO implements IGradeInfoDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(GradeInfo gradeInfo) throws DAOException {
		try {
			em.persist(gradeInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert GradeInfo", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(GradeInfo gradeInfo) throws DAOException {
		try {
			gradeInfo = em.merge(gradeInfo);
			em.remove(gradeInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete GradeInfo", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(GradeInfo gradeInfo) throws DAOException {
		try {
			em.merge(gradeInfo);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update GradeInfo", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GradeInfo findById(String gradeInfoId) throws DAOException {
		GradeInfo result = null;
		try {
			result = em.find(GradeInfo.class, gradeInfoId);
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find GradeInfo", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<GradeInfo> findAllGradeInfo() throws DAOException {
		List<GradeInfo> result = null;
		try {
			Query q = em.createQuery("SELECT g FROM GradeInfo g ");
			result = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find All GradeInfo", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkExistingGradeInfo(GradeInfo grade) throws DAOException {
		boolean exist = false;
		String gradeName = grade.getName().replaceAll("\\s+", "");
		try {
			StringBuffer buffer = new StringBuffer();
			Query query = null;

			buffer = new StringBuffer("SELECT CASE WHEN (COUNT(c.name) > 0) THEN TRUE ELSE FALSE END FROM GradeInfo c ");
			buffer.append(" WHERE LOWER(FUNCTION('REPLACE',c.name,' ','')) = :name ");
			buffer.append(grade.getId() != null ? "AND c.id != :id" : "");
			query = em.createQuery(buffer.toString());
			if (grade.getId() != null)
				query.setParameter("id", grade.getId());
			query.setParameter("name", gradeName.toLowerCase());
			exist = (Boolean) query.getSingleResult();
			em.flush();

			return exist;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Existing Name ", pe);
		}
	}
}

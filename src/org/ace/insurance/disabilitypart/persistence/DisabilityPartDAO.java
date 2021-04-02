package org.ace.insurance.disabilitypart.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.insurance.common.TableName;
import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.insurance.disabilitypart.persistence.interfaces.IDisabilityPartDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("DisabilityPartDAO")
public class DisabilityPartDAO extends BasicDAO implements IDisabilityPartDAO  {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(DisabilityPart disabilitypart) throws DAOException {
		try {
			em.persist(disabilitypart);
			insertProcessLog(TableName.DISABILITYPART, disabilitypart.getId());
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert GradeInfo", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(DisabilityPart disabilitypart) throws DAOException {
		try {
			disabilitypart = em.merge(disabilitypart);
			em.remove(disabilitypart);
			insertProcessLog(TableName.DISABILITYPART, disabilitypart.getId());
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete DisabilityPart", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(DisabilityPart disabilitypart) throws DAOException {
		try {
			em.merge(disabilitypart);
			insertProcessLog(TableName.DISABILITYPART, disabilitypart.getId());
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update DisabilityPart", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DisabilityPart findById(String disabilitypartId) throws DAOException {
		DisabilityPart result = null;
		try {
			result = em.find(DisabilityPart.class, disabilitypartId);
			insertProcessLog(TableName.DISABILITYPART, disabilitypartId);
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find DisabilityPart", pe);
		}
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DisabilityPart> findAllDisabilitypart() throws DAOException {
		List<DisabilityPart> result = null;
		try {
			Query q = em.createQuery("SELECT d FROM DisabilityPart d ORDER BY d.name ASC");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Disability Part", pe);
		}
		return result;
	}
	

}

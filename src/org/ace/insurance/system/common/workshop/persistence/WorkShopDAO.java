package org.ace.insurance.system.common.workshop.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.insurance.system.common.workshop.persistence.interfaces.IWorkShopDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("WorkShopDAO")
public class WorkShopDAO extends BasicDAO implements IWorkShopDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(WorkShop workshop) throws DAOException {
		try {
			em.persist(workshop);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert WorkShop", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(WorkShop workshop) throws DAOException {
		try {
			em.merge(workshop);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update WorkShop", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(WorkShop workshop) throws DAOException {
		try {
			WorkShop margedworkShop = em.merge(workshop);
			em.remove(margedworkShop);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Organization", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public WorkShop findById(String id) throws DAOException {
		WorkShop result = null;
		try {
			result = em.find(WorkShop.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Organization", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkShop> findAll() throws DAOException {
		List<WorkShop> result = null;
		try {
			Query query = em.createNamedQuery("WorkShop.findAll");
			result = query.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Organization", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkShop> findByCriteria(String criteria) throws DAOException {
		List<WorkShop> result = null;
		try {
			// Query q = em.createNamedQuery("Township.findByCriteria");
			Query q = em.createQuery("Select t from WorkShop t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of WorkShop.", pe);
		}
		return result;
	}

	// This method is only for CashierDialogActionBean
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkShop> findByCriteria(String criteria, String criteriaValue) throws DAOException {
		List<WorkShop> result = null;
		try {
			StringBuffer query = new StringBuffer();

			query.append("SELECT w FROM WorkShop w");

			if ("NAME".equals(criteria)) {
				query.append(" WHERE w.name LIKE :name");
			}
			if ("CODE_NO".equals(criteria)) {
				query.append(" WHERE w.codeNo LIKE :codeNo");
			}

			Query q = em.createQuery(query.toString());
			if ("NAME".equals(criteria)) {
				q.setParameter("name", "%" + criteriaValue + "%");
			}
			if ("CODE_NO".equals(criteria)) {
				q.setParameter("codeNo", "%" + criteriaValue + "%");
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of WorkShop.", pe);
		}
		return result;
	}
}

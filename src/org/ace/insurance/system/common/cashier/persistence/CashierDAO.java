/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.cashier.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.cashier.Cashier;
import org.ace.insurance.system.common.cashier.persistence.interfaces.ICashierDAO;
import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CashierDAO")
public class CashierDAO extends BasicDAO implements ICashierDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Cashier cashier) throws DAOException {
		try {
			em.persist(cashier);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Cashier" + cashier.getName(), pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Cashier cashier) throws DAOException {
		try {
			em.merge(cashier);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Cashier", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Cashier cashier) throws DAOException {
		try {
			cashier = em.merge(cashier);
			em.remove(cashier);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Cashier", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Cashier findById(String id) throws DAOException {
		Cashier result = null;
		try {
			result = em.find(Cashier.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Cashier", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Cashier> findAll() throws DAOException {
		List<Cashier> result = null;
		try {
			Query q = em.createNamedQuery("Cashier.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Cashier", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkExistingCashier(Cashier cashier) throws DAOException {
		boolean existCashier = false;
		long count = 0;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(c.id) FROM Cashier c ");
			query.append(" WHERE c.name=:name ");
			query.append(" AND c.idConditionType=:idConditionType ");
			query.append(" AND c.idType=:idType ");
			query.append(" AND c.workshop=:workshop ");
			Query q = em.createQuery(query.toString());

			q.setParameter("name", cashier.getName());
			q.setParameter("idConditionType", cashier.getIdConditionType());
			q.setParameter("idType", cashier.getIdType());
			q.setParameter("workshop", cashier.getWorkshop());
			count = (Long) q.getSingleResult();
			if (count > 0)
				existCashier = true;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}

		return existCashier;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Cashier> findByWorkShop(WorkShop workShop) throws DAOException {
		List<Cashier> result = null;
		try {
			Query query = em.createQuery("SELECT c FROM Cashier c WHERE c.workshop.id = :workShopId");
			query.setParameter("workShopId", workShop.getId());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Cashier", pe);
		}
		return result;
	}
}

/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.paymenttype.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.paymenttype.persistence.interfaces.IPaymentTypeDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PaymentTypeDAO")
public class PaymentTypeDAO extends BasicDAO implements IPaymentTypeDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(PaymentType paymentType) throws DAOException {
		try {
			em.persist(paymentType);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert PaymentType", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(PaymentType paymentType) throws DAOException {
		try {
			em.merge(paymentType);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PaymentType", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(PaymentType paymentType) throws DAOException {
		try {
			paymentType = em.merge(paymentType);
			em.remove(paymentType);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update PaymentType", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PaymentType findById(String id) throws DAOException {
		PaymentType result = null;
		try {
			result = em.find(PaymentType.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find PaymentType", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PaymentType findByName(String name) throws DAOException, NoResultException {
		PaymentType result = null;
		try {
			Query q = em.createNamedQuery("PaymentType.findByName");
			q.setParameter("name", name);
			result = (PaymentType) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			throw translate("No Result in finding single PaymentType", e);
		} catch (PersistenceException pe) {
			throw translate("Failed to find PaymentType", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentType> findAll() throws DAOException {
		List<PaymentType> result = null;
		try {
			Query q = em.createNamedQuery("PaymentType.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of PaymentType", pe);
		}
		return result;
	}
}

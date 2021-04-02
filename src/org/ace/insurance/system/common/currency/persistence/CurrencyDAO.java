/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.currency.persistence;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.persistence.interfaces.ICurrencyDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CurrencyDAO")
public class CurrencyDAO extends BasicDAO implements ICurrencyDAO {

	@Resource(name = "CSC_CUR_CONFIG")
	private Properties properties;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Currency currency) throws DAOException {
		try {
			em.persist(currency);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Currency", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Currency currency) throws DAOException {
		try {
			em.merge(currency);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Currency", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Currency currency) throws DAOException {
		try {
			currency = em.merge(currency);
			em.remove(currency);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Currency", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Currency findById(String id) throws DAOException {
		Currency result = null;
		try {
			result = em.find(Currency.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Currency", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Currency findCurrencyByCurrencyCode(String currencyCode) throws DAOException {
		Currency result = null;
		try {
			Query query = em.createNamedQuery("Currency.findByCurrencyCode");
			query.setParameter("currencyCode", currencyCode);
			result = (Currency) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Currency", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Currency> findAll() throws DAOException {
		List<Currency> result = null;
		try {
			Query q = em.createNamedQuery("Currency.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Currency", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Currency findHomeCurrency() throws DAOException {
		Currency result = null;
		try {
			Query q = em.createQuery("SELECT c FROM Currency c WHERE c.currencyCode = :currencyCode");
			q.setParameter("currencyCode", "KYT");
			result = (Currency) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find home currency.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Currency findCurrencyByCSCCurrencyCode(String cscCurCode) throws DAOException {
		Currency result = null;
		try {
			String currencyCode = properties.getProperty(cscCurCode);
			result = findCurrencyByCurrencyCode(currencyCode);
		} catch (PersistenceException pe) {
			throw translate("Failed to find Currency", pe);
		}
		return result;
	}
}

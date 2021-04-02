/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.currency.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.persistence.interfaces.ICurrencyDAO;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "CurrencyService")
public class CurrencyService extends BaseService implements ICurrencyService {

	@Resource(name = "CurrencyDAO")
	private ICurrencyDAO currencyDAO;

	public void addNewCurrency(Currency currency) throws SystemException {
		try {
			currencyDAO.insert(currency);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Currency", e);
		}
	}

	public void updateCurrency(Currency currency) throws SystemException {
		try {
			currencyDAO.update(currency);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Currency", e);
		}
	}

	public void deleteCurrency(Currency currency) throws SystemException {
		try {
			currencyDAO.delete(currency);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Currency", e);
		}
	}

	public List<Currency> findAllCurrency() throws SystemException {
		List<Currency> result = null;
		try {
			result = currencyDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Currency)", e);
		}
		return result;
	}

	public Currency findCurrencyById(String id) throws SystemException {
		Currency result = null;
		try {
			result = currencyDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Currency (ID : " + id + ")", e);
		}
		return result;
	}

	public Currency findCurrencyByCurrencyCode(String currrencyCode) throws SystemException {
		Currency result = null;
		try {
			result = currencyDAO.findCurrencyByCurrencyCode(currrencyCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Currency (currrencyCode : " + currrencyCode + ")", e);
		}
		return result;
	}

	public Currency findHomeCurrency() throws SystemException {
		Currency result = null;
		try {
			result = currencyDAO.findHomeCurrency();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find home currency.", e);
		}
		return result;
	}

	public Currency findCurrencyByCSCCurCode(String cscCurCode) throws SystemException {
		Currency result = null;
		try {
			result = currencyDAO.findCurrencyByCSCCurrencyCode(cscCurCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Currency  (CSC CURRENCY CODE : " + cscCurCode + ")", e);
		}
		return result;
	}
}

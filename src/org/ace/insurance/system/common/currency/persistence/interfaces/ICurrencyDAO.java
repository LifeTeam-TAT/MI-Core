/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.currency.persistence.interfaces;

import java.util.List;

import org.ace.java.component.persistence.exception.DAOException;

import org.ace.insurance.system.common.currency.Currency;

public interface ICurrencyDAO {
	public void insert(Currency currency) throws DAOException;

	public void update(Currency currency) throws DAOException;

	public void delete(Currency currency) throws DAOException;

	public Currency findById(String id) throws DAOException;

	public Currency findCurrencyByCurrencyCode(String currencyCode) throws DAOException;

	public List<Currency> findAll() throws DAOException;

	public Currency findHomeCurrency() throws DAOException;

	public Currency findCurrencyByCSCCurrencyCode(String cscCurCode) throws DAOException;
}
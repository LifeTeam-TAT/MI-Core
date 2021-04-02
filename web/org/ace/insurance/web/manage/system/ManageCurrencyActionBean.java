/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageCurrencyActionBean")
public class ManageCurrencyActionBean extends BaseBean implements Serializable {                                              private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	private Currency currency;
	private boolean createNew;
	private List<Currency> currencyList;

	@PostConstruct
	public void init() {
		createNewCurrency();
		loadCurrency();
	}
	
	private void loadCurrency() {
		currencyList = currencyService.findAllCurrency();
	}

	public void createNewCurrency() {
		createNew = true;
		currency = new Currency();
	}

	public void prepareUpdateCurrency(Currency currency) {
		createNew = false;
		this.currency = currency;
	}

	public void addNewCurrency() {
		try {
			if (currency.getIsHomeCur() == null) {
				addInfoMessage(null, MessageId.REQUIRED_CURRENCY, currency.getId());
				return;
			}
			currencyService.addNewCurrency(currency);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, currency.getCurrencyCode());
			createNewCurrency();
			loadCurrency();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateCurrency() {
		try {
			currencyService.updateCurrency(currency);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, currency.getCurrencyCode());
			createNewCurrency();
			loadCurrency();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteCurrency() {
		try {
			currencyService.deleteCurrency(currency);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, currency.getCurrencyCode());
			createNewCurrency();
			loadCurrency();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}

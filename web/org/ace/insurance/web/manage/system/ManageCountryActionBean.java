/***************************************************************************************
 * @author HS
 * @Date 2019-01-22
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

import org.ace.insurance.system.common.country.CTY001;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.country.service.interfaces.ICountryService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageCountryActionBean")
public class ManageCountryActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CountryService}")
	private ICountryService countryService;

	public void setCountryService(ICountryService countryService) {
		this.countryService = countryService;
	}

	private Country country;
	private boolean createNew;
	private List<CTY001> coutryList;

	@PostConstruct
	public void init() {
		createNewCountry();
		loadCountry();
	}

	public void loadCountry() {
		coutryList = countryService.findAll_CTY001();
	}

	public void createNewCountry() {
		createNew = true;
		country = new Country();
	}

	public void prepareUpdateCountry(CTY001 cty001) {
		createNew = false;
		this.country = countryService.findCountryById(cty001.getId());
	}

	public void addNewCountry() {
		try {
			countryService.addNewCountry(country);
			coutryList.add(new CTY001(country));
			addInfoMessage(null, MessageId.INSERT_SUCCESS, country.getName());
			createNewCountry();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateCountry() {
		try {
			countryService.updateCountry(country);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, country.getName());
			createNewCountry();
			loadCountry();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteCountry(CTY001 cty001) {
		try {
			country = countryService.findCountryById(cty001.getId());
			countryService.deleteCountry(country);
			coutryList.remove(cty001);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, country.getName());
			createNewCountry();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<CTY001> getCountryList() {
		return coutryList;
	}

	public Country getCountry() {
		return country;
	}

}

package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.country.service.interfaces.ICountryService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "CountryDialogActionBean")
@ViewScoped
public class CountryDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CountryService}")
	private ICountryService countryService;

	public void setCountryService(ICountryService countryService) {
		this.countryService = countryService;
	}

	private List<Country> countryList;

	@PostConstruct
	public void init() {
		countryList = countryService.findAllCountry();
	}

	public List<Country> getCountryList() {
		return countryList;
	}

	public void selectCountry(Country country) {
		RequestContext.getCurrentInstance().closeDialog(country);
	}
}

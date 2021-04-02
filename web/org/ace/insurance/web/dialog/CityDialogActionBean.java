package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.city.service.interfaces.ICityService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "CityDialogActionBean")
@ViewScoped
public class CityDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CityService}")
	private ICityService cityService;

	public void setCityService(ICityService cityService) {
		this.cityService = cityService;
	}

	private String criteria;
	private List<City> cityList;

	@PostConstruct
	public void init() {
		cityList = cityService.findAllCity();
	}

	public void search() {
		cityList = cityService.findByCriteria(criteria);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void selectCity(City city) {
		RequestContext.getCurrentInstance().closeDialog(city);
	}
}

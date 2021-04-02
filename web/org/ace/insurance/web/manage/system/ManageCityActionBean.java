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

import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.city.service.interfaces.ICityService;
import org.ace.insurance.system.common.province.Province;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageCityActionBean")
public class ManageCityActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CityService}")
	private ICityService cityService;

	public void setCityService(ICityService cityService) {
		this.cityService = cityService;
	}

	private City city;
	private String oldCityName;
	private List<City> cityList;
	private boolean createNew;
	private String criteria;

	@PostConstruct
	public void init() {
		createNewCity();
		loadCity();
	}

	public void createNewCity() {
		createNew = true;
		city = new City();
	}

	public void loadCity() {
		cityList = cityService.findAllCity();
	}

	public void prepareUpdateCity(City city) {
		createNew = false;
		oldCityName = city.getName();
		this.city = city;

	}

	public void addNewCity() {
		try {
			City result = cityService.findCityByName(city.getName());
			if (result == null) {
				cityService.addNewCity(city);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, city.getName());
			} else {
				addWranningMessage(null, MessageId.ALREADY_INSERT, city.getName());
			}
			createNewCity();
			loadCity();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateCity() {
		try {
			City result = null;
			if (oldCityName != city.getName()) {
				result = cityService.findCityByName(city.getName());
			}
			if (result == null) {
				cityService.updateCity(city);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, city.getName());
			} else {
				addWranningMessage(null, MessageId.ALREADY_INSERT, city.getName());
			}
			createNewCity();
			loadCity();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteCity() {
		try {
			cityService.deleteCity(city);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, city.getName());
			createNewCity();
			loadCity();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void returnProvince(SelectEvent event) {
		Province province = (Province) event.getObject();
		city.setProvince(province);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
}

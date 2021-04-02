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

import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.province.PRV001;
import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageProvinceActionBean")
public class ManageProvinceActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	private Province province;
	private boolean createNew;
	private List<PRV001> coutryList;

	@PostConstruct
	public void init() {
		createNewProvince();
		loadProvince();
	}

	public void loadProvince() {
		coutryList = provinceService.findAll_PRV001();
	}

	public void createNewProvince() {
		createNew = true;
		province = new Province();
	}

	public void prepareUpdateProvince(PRV001 prv001) {
		createNew = false;
		this.province = provinceService.findProvinceById(prv001.getId());
	}

	public void addNewProvince() {
		try {
			provinceService.addNewProvince(province);
			coutryList.add(new PRV001(province));
			addInfoMessage(null, MessageId.INSERT_SUCCESS, province.getName());
			createNewProvince();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateProvince() {
		try {
			provinceService.updateProvince(province);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, province.getName());
			createNewProvince();
			loadProvince();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteProvince(PRV001 prv001) {
		try {
			province = provinceService.findProvinceById(prv001.getId());
			provinceService.deleteProvince(province);
			coutryList.remove(prv001);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, province.getName());
			createNewProvince();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void returnCountry(SelectEvent event) {
		Country country = (Country) event.getObject();
		province.setCountry(country);
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<PRV001> getProvinceList() {
		return coutryList;
	}

	public Province getProvince() {
		return province;
	}

}

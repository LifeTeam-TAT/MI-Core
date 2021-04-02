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

import org.ace.insurance.system.common.district.DIS001;
import org.ace.insurance.system.common.district.District;
import org.ace.insurance.system.common.district.service.interfaces.IDistrictService;
import org.ace.insurance.system.common.province.Province;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageDistrictActionBean")
public class ManageDistrictActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{DistrictService}")
	private IDistrictService districtService;

	public void setDistrictService(IDistrictService districtService) {
		this.districtService = districtService;
	}

	private District district;
	private boolean createNew;
	private List<DIS001> districtList;

	@PostConstruct
	public void init() {
		createNewDistrict();
		loadDistrict();
	}

	public void loadDistrict() {
		districtList = districtService.findAll_DIS001();
	}

	public void createNewDistrict() {
		createNew = true;
		district = new District();
	}

	public void prepareUpdateDistrict(DIS001 dis001) {
		createNew = false;
		this.district = districtService.findDistrictById(dis001.getId());
	}

	public void addNewDistrict() {
		try {
			districtService.addNewDistrict(district);
			districtList.add(new DIS001(district));
			addInfoMessage(null, MessageId.INSERT_SUCCESS, district.getName());
			createNewDistrict();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateDistrict() {
		try {
			districtService.updateDistrict(district);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, district.getName());
			createNewDistrict();
			loadDistrict();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteDistrict(DIS001 dis001) {
		try {
			districtService.deleteDistrictById(dis001.getId());
			districtList.remove(dis001);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, dis001.getName());
			createNewDistrict();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void returnProvince(SelectEvent event) {
		Province province = (Province) event.getObject();
		district.setProvince(province);
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<DIS001> getDistrictList() {
		return districtList;
	}

	public District getDistrict() {
		return district;
	}

}

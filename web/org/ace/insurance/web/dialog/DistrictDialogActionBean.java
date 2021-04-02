package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.district.District;
import org.ace.insurance.system.common.district.service.interfaces.IDistrictService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "DistrictDialogActionBean")
@ViewScoped
public class DistrictDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{DistrictService}")
	private IDistrictService districtService;

	public void setDistrictService(IDistrictService districtService) {
		this.districtService = districtService;
	}

	private List<District> districtList;

	@PostConstruct
	public void init() {
		districtList = districtService.findAllDistrict();
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void selectDistrict(District district) {
		RequestContext.getCurrentInstance().closeDialog(district);
	}

}

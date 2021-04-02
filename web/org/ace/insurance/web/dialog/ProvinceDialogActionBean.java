package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ProvinceDialogActionBean")
@ViewScoped
public class ProvinceDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	private List<Province> provinceList;

	@PostConstruct
	public void init() {
		provinceList = provinceService.findAllProvince();
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void selectProvince(Province province) {
		RequestContext.getCurrentInstance().closeDialog(province);
	}

}

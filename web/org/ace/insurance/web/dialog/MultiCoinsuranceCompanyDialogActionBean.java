package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "MultiCoinsuranceCompanyDialogActionBean")
@ViewScoped
public class MultiCoinsuranceCompanyDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CoinsuranceCompanyService}")
	private ICoinsuranceCompanyService coinsuranceCompanyService;

	public void setCoinsuranceCompanyService(ICoinsuranceCompanyService coinsuranceCompanyService) {
		this.coinsuranceCompanyService = coinsuranceCompanyService;
	}

	private List<CoinsuranceCompany> coinsuranceCompanyList;
	private List<CoinsuranceCompany> selectedCoinsuranceCompanyList;

	@PostConstruct
	public void init() {
		selectedCoinsuranceCompanyList = (List<CoinsuranceCompany>) getParam("selectedCoinsuranceCompany");
		selectedCoinsuranceCompanyList = selectedCoinsuranceCompanyList == null ? new ArrayList<>() : selectedCoinsuranceCompanyList;
		coinsuranceCompanyList = coinsuranceCompanyService.findAll();
	}

	@PreDestroy
	public void destroy() {
		removeParam("selectedCoinsuranceCompany");
	}

	// TODO FIXME PSH remove dialog control from actionbean

	/*
	 * public void selectCoinsuranceCompany() {
	 * RequestContext.getCurrentInstance().closeDialog(
	 * selectedCoinsuranceCompanyList); }
	 */
	public List<CoinsuranceCompany> getCoinsuranceCompanyList() {
		return coinsuranceCompanyList;
	}

	public List<CoinsuranceCompany> getSelectedCoinsuranceCompanyList() {
		return selectedCoinsuranceCompanyList;
	}

	public void setSelectedCoinsuranceCompanyList(List<CoinsuranceCompany> selectedCoinsuranceCompanyList) {
		this.selectedCoinsuranceCompanyList = selectedCoinsuranceCompanyList;
	}

}

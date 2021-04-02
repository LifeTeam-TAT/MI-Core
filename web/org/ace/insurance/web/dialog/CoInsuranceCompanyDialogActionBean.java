package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "CoInsuranceCompanyDialogActionBean")
@ViewScoped
public class CoInsuranceCompanyDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{CoinsuranceCompanyService}")
	private ICoinsuranceCompanyService coinsuranceCompanyService;

	public void setCoinsuranceCompanyService(ICoinsuranceCompanyService coinsuranceCompanyService) {
		this.coinsuranceCompanyService = coinsuranceCompanyService;
	}

	private List<CoinsuranceCompany> coinsuranceCompanyList;

	@PostConstruct
	public void init() {
		coinsuranceCompanyList = coinsuranceCompanyService.findAll();
	}

	public void selectCoinsuranceCompany(CoinsuranceCompany coinsuranceCompany) {
		PrimeFaces.current().dialog().closeDynamic(coinsuranceCompany);
	}

	public List<CoinsuranceCompany> getCoinsuranceCompanyList() {
		return coinsuranceCompanyList;
	}

}

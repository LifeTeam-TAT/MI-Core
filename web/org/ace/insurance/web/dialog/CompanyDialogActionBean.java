package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.company.Company;
import org.ace.insurance.system.common.company.service.interfaces.ICompanyService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "CompanyDialogActionBean")
@ViewScoped
public class CompanyDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CompanyService}")
	private ICompanyService companyService;

	public void setCompanyService(ICompanyService companyService) {
		this.companyService = companyService;
	}

	private List<Company> companyList;

	@PostConstruct
	public void init() {
		companyList = companyService.findAllCompany();
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void selectCompany(Company company) {
		RequestContext.getCurrentInstance().closeDialog(company);
	}

}

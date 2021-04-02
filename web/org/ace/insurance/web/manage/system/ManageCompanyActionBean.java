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

import org.ace.insurance.system.common.company.CMY001;
import org.ace.insurance.system.common.company.Company;
import org.ace.insurance.system.common.company.service.interfaces.ICompanyService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageCompanyActionBean")
public class ManageCompanyActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CompanyService}")
	private ICompanyService companyService;

	public void setCompanyService(ICompanyService companyService) {
		this.companyService = companyService;
	}

	private boolean createNew;
	private Company company;
	private List<CMY001> companyList;

	@PostConstruct
	public void init() {
		createNewCompany();
		loadCompany();
	}

	public void loadCompany() {
		companyList = companyService.findAll_CMY001();
	}

	public void createNewCompany() {
		createNew = true;
		company = new Company();
	}

	public void prepareUpdateCompany(CMY001 cmy001) {
		createNew = false;
		this.company = companyService.findCompanyById(cmy001.getId());
	}

	public void addNewCompany() {
		try {
			companyService.addNewCompany(company);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, company.getName());
			companyList.add(new CMY001(company));
			createNewCompany();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateCompany() {
		try {
			companyService.updateCompany(company);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, company.getName());
			createNewCompany();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadCompany();
	}

	public void deleteCompany(CMY001 cmy001) {
		try {
			this.company = companyService.findCompanyById(cmy001.getId());
			companyService.deleteCompany(company);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, company.getName());
			createNewCompany();
			companyList.remove(cmy001);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<CMY001> getCompanyList() {
		return companyList;
	}

	public Company getCompany() {
		return company;
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		company.getAddress().setTownship(township);
	}
}

package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageCoinsuranceCompanyActionBean")
public class ManageCoinsuranceCompanyActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CoinsuranceCompanyService}")
	private ICoinsuranceCompanyService coinsuranceCompanyService;

	public void setCoinsuranceCompanyService(ICoinsuranceCompanyService coinsuranceCompanyService) {
		this.coinsuranceCompanyService = coinsuranceCompanyService;
	}

	private boolean createNew;
	private CoinsuranceCompany coinsuranceCompany;
	private List<CoinsuranceCompany> coinsuranceCompanies;

	@PostConstruct
	public void init() {
		createNewCompany();
		loadCompany();
	}

	private void loadCompany() {
		coinsuranceCompanies = coinsuranceCompanyService.findAll();
	}

	public void createNewCompany() {
		coinsuranceCompany = new CoinsuranceCompany();
		createNew = true;
	}

	public void prepareUpdateCoinsuranceCompany(CoinsuranceCompany coinsuranceCompany) {
		createNew = false;
		this.coinsuranceCompany = coinsuranceCompany;
	}

	public CoinsuranceCompany getCoinsuranceCompany() {
		return coinsuranceCompany;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void addNewCoinsuranceCompany() {
		try {

			coinsuranceCompanyService.add(coinsuranceCompany);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, coinsuranceCompany.getName());
			createNewCompany();
			loadCompany();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateCoinsuranceCompany() {
		try {
			coinsuranceCompanyService.update(coinsuranceCompany);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, coinsuranceCompany.getName());
			createNewCompany();
			loadCompany();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteCoinsuranceCompany(CoinsuranceCompany coinsuranceCompany) {
		try {
			coinsuranceCompanyService.delete(coinsuranceCompany);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, coinsuranceCompany.getName());
			createNewCompany();
			coinsuranceCompanies.remove(coinsuranceCompany);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public List<CoinsuranceCompany> getCoinsuranceCompanies() {
		return coinsuranceCompanies;
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		coinsuranceCompany.getAddress().setTownship(township);
	}

}

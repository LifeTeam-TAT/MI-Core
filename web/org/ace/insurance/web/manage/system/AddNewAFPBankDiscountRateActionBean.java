package org.ace.insurance.web.manage.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.afpBankDiscountRate.AFPBankDiscountRate;
import org.ace.insurance.afpBankDiscountRate.AFPR001;
import org.ace.insurance.afpBankDiscountRate.service.interfaces.IAFPBankDiscountRateService;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AddNewAFPBankDiscountRateActionBean")
public class AddNewAFPBankDiscountRateActionBean extends BaseBean {

	@ManagedProperty(value = "#{AFPBankDiscountRateService}")
	private IAFPBankDiscountRateService afpBankDiscountRateService;

	public void setAfpBankDiscountRateService(IAFPBankDiscountRateService afpBankDiscountRateService) {
		this.afpBankDiscountRateService = afpBankDiscountRateService;
	}

	private AFPBankDiscountRate afpRate;
	private ProductGroup productGroup;
	private boolean createNew;
	private List<AFPR001> afpRateList;

	@PostConstruct
	public void init() {
		initialization();
		createNewAfpRate();
		loadAFPBankDiscountRate();
	}

	private void initialization() {
		productGroup = (ProductGroup) getParam(Constants.PRODUCTGROUP_ID);
	}

	@PreDestroy
	public void destory() {
		removeParam(Constants.PRODUCTGROUP_ID);
	}

	public void loadAFPBankDiscountRate() {
		afpRateList = afpBankDiscountRateService.findAFPBankDiscountRateDTOByProductGroupId(productGroup.getId());
	}

	public void createNewAfpRate() {
		createNew = true;
		afpRate = new AFPBankDiscountRate();
		afpRate.setProductGroup(productGroup);
	}

	public void prepareUpdateAFPBankDiscountRate(AFPR001 afpr001) {
		createNew = false;
		this.afpRate = afpBankDiscountRateService.findAFPBankDiscountRateById(afpr001.getId());
	}

	public void addNewAfpRate() {
		try {
			afpBankDiscountRateService.addNewAFPBankDiscountRate(afpRate);
			afpRateList.add(new AFPR001(afpRate));
			addInfoMessage(null, MessageId.INSERT_SUCCESS, afpRate.getId());
			createNewAfpRate();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateAfpRate() {
		try {
			afpBankDiscountRateService.updateAFPBankDiscountRate(afpRate);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, afpRate.getId());
			createNewAfpRate();
			loadAFPBankDiscountRate();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteAfpRate(AFPR001 afpr001) {
		try {
			afpBankDiscountRateService.findAFPBankDiscountRateById(afpr001.getId());
			afpRateList.remove(afpr001);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, afpr001.getId());
			createNewAfpRate();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		afpRate.setBank(bank);
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<AFPR001> getAfpRateList() {
		return afpRateList;
	}

	public AFPBankDiscountRate getAfpRate() {
		return afpRate;
	}
}

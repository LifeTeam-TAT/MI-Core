package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.filter.bankCustomer.BNK001;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "BankDialogActionBean")
@ViewScoped
public class BankDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{BankService}")
	private IBankService bankService;

	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	private boolean isAccountClearing;
	private List<BNK001> bankList;

	@PostConstruct
	public void init() {
		isAccountClearing = isExistParam("IS_ACC_CLEARING") ? (boolean) getParam("IS_ACC_CLEARING") : false;
		if (isAccountClearing)
			bankList = bankService.findACodeNotNull();
		else
			bankList = bankService.findAllBank();
	}

	public List<BNK001> getBankList() {
		return bankList;
	}

	public void selectBank(BNK001 bnk001) {
		Bank bank = bankService.findBankById(bnk001.getId());
		PrimeFaces.current().dialog().closeDynamic(bank);
	}

}

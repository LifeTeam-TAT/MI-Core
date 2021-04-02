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

import org.ace.insurance.filter.bankCustomer.BNK001;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageBankActionBean")
public class ManageBankActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<BNK001> bankList;
	private boolean createNew;
	private Bank bank;

	@ManagedProperty(value = "#{BankService}")
	private IBankService bankService;

	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	private void laodBank() {
		bankList = bankService.findAllBank();
	}

	@PostConstruct
	public void init() {
		laodBank();
		createNewBank();
	}

	public void createNewBank() {
		createNew = true;
		bank = new Bank();
	}

	public void prepareUpdateBank(BNK001 bank) {
		createNew = false;
		this.bank = bankService.findBankById(bank.getId());
	}

	public void addNewBank() {
		try {
			bankService.addNewBank(bank);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, bank.getName());
			bankList.add(new BNK001(bank));
			createNewBank();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		laodBank();
	}

	public void updateBank() {
		try {
			bankService.updateBank(bank);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, bank.getName());
			createNewBank();
			laodBank();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		laodBank();
	}

	public void deleteBank(BNK001 bnk001) {
		try {
			bank = bankService.findBankById(bnk001.getId());
			bankService.deleteBank(bank);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, bank.getName());
			createNewBank();
			bankList.remove(bnk001);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<BNK001> getBankList() {
		return bankList;
	}

	public Bank getBank() {
		return bank;
	}

	public void returnCoa(SelectEvent event) {
		String aCode = (String) event.getObject();
		bank.setAcode(aCode);
	}
}

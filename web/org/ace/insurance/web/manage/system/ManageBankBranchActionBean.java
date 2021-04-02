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

import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.bankBranch.service.interfaces.IBankBranchService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageBankBranchActionBean")
public class ManageBankBranchActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean createNew;
	private BankBranch bankBranch;
	private List<BankBranch> bankBranchList;
	@ManagedProperty(value = "#{BankBranchService}")
	private IBankBranchService bankBranchService;

	public void setBankBranchService(IBankBranchService bankBranchService) {
		this.bankBranchService = bankBranchService;
	}

	private void loadBankBranch() {
		bankBranchList = bankBranchService.findAllBankBranch();
	}

	@PostConstruct
	public void init() {
		loadBankBranch();
		createNewBankBranch();
	}

	public void createNewBankBranch() {
		createNew = true;
		bankBranch = new BankBranch();
	}

	public void prepareUpdateBankBranch(BankBranch bankBranch) {
		createNew = false;
		this.bankBranch = bankBranch;
	}

	public void addNewBankBranch() {
		try {
			if (bankBranch.getBank() == null) {
				addInfoMessage(null, MessageId.REQUIRED_BANK);
				return;
			}

			if (bankBranch.getTownship() == null) {
				addInfoMessage(null, MessageId.REQUIRED_BRANCH);
				return;
			}

			bankBranchService.addNewBankBranch(bankBranch);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, bankBranch.getName());
			createNewBankBranch();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadBankBranch();
	}

	public void updateBankBranch() {
		try {
			bankBranchService.updateBankBranch(bankBranch);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, bankBranch.getName());
			createNewBankBranch();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadBankBranch();
	}

	public String deleteBankBranch() {
		try {
			bankBranchService.deleteBankBranch(bankBranch);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, bankBranch.getName());
			createNewBankBranch();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadBankBranch();
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<BankBranch> getBankBranchList() {
		return bankBranchList;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		bankBranch.setBank(bank);
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		bankBranch.setTownship(township);
	}
}

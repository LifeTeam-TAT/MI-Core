/****************************************************************************************@author<<Your Name>>*@Date 2013-02-11*@Version 1.0*@Purpose<<You have to write the comment the main purpose of this class>>*****************************************************************************************/package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.system.common.cashier.Cashier;
import org.ace.insurance.system.common.cashier.service.interfaces.ICashierService;
import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageCashierActionBean")
public class ManageCashierActionBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CashierService}")
	private ICashierService cashierService;

	public void setCashierService(ICashierService cashierService) {
		this.cashierService = cashierService;
	}


	private boolean createNew;
	private Cashier cashier;
	private List<Cashier> cashierList;
	private boolean isNrcCashier = true;
	private boolean isStillApplyCashier = false;

	@PostConstruct
	public void init() {
		createNewCashier();
		refreshCashierList();
	}


	public void changeIdType(AjaxBehaviorEvent e) {
		IdType idType = (IdType) ((UIOutput) e.getSource()).getValue();
		if (idType.equals(IdType.NRCNO)) {
			isNrcCashier = true;
			isStillApplyCashier = false;
		} else if (idType.equals(IdType.STILL_APPLYING)) {
			isNrcCashier = false;
			isStillApplyCashier = true;
			cashier.setIdNo(null);
			cashier.setIdConditionType(null);
		} else {
			isNrcCashier = false;
			isStillApplyCashier = false;
			cashier.setIdNo(null);
			cashier.setIdConditionType(null);
		}

	}

	public boolean isNrcCashier() {
		return isNrcCashier;
	}

	public void setNrcCashier(boolean isNrcCashier) {
		this.isNrcCashier = isNrcCashier;
	}

	public boolean isStillApplyCashier() {
		return isStillApplyCashier;
	}

	public void returnWorkshop(SelectEvent event) {
		WorkShop workshop = (WorkShop) event.getObject();
		cashier.setWorkshop(workshop);
	}


	public IdType[] getIdTypeSelectItemList() {
		return IdType.values();
	}

	public IdConditionType[] getIdConditionTypeSelectItemList() {
		return IdConditionType.values();
	}

	public void createNewCashier() {
		createNew = true;
		cashier = new Cashier();
		cashier.setIdType(IdType.NRCNO);
	}

	public boolean validation() {
		boolean result = true;
		String formID = "cashierEntryForm";
		switch (cashier.getIdType()) {
			case NRCNO: {
				if (cashier.getIdNo() == null || cashier.getIdNo().isEmpty()) {
					addErrorMessage(formID + ":cashierIdNoPanelGrid", UIInput.REQUIRED_MESSAGE_ID);
					result = false;
				} else if (cashier.getIdNo().length() != 6) {
					addErrorMessage(formID + ":cashierIdNoPanelGrid", MessageId.NRC_FORMAT_INCORRECT);
					result = false;
				}

			}
				break;
			case PASSPORTNO:
			case FRCNO:
				if (cashier.getIdNo() == null || cashier.getIdNo().isEmpty()) {
					addErrorMessage(formID + ":cashierIdNoPanelGrid", UIInput.REQUIRED_MESSAGE_ID);
					result = false;
				}
				break;
			default:
				break;
		}
		return result;
	}

	public void addNewCashier() {
		boolean isExiting = true;
		try {
			isExiting = cashierService.checkExistingCashier(cashier);
			if (!isExiting) {
				if (cashier.getIdType().equals(IdType.STILL_APPLYING)) {
					cashier.setIdNo(null);
				}
				cashierService.addNewCashier(cashier);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, cashier.getFullName());
				refreshCashierList();
				createNewCashier();
			} else {
				addInfoMessage(null, MessageId.EXISTING_CUSTOMER, cashier.getFullName());
			}

		} catch (SystemException ex) {
			handelSysException(ex);
			ex.printStackTrace();
		}
	}

	public void updateCashier() {
		try {
			if (validation()) {
				cashierService.updateCashier(cashier);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, cashier.getFullName());
				createNewCashier();
				refreshCashierList();
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteCashier() {
		try {
			cashierService.deleteCashier(cashier);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, cashier.getFullName());
			refreshCashierList();
			createNewCashier();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void prepareUpdateCashier(Cashier cashier) {
		this.cashier = cashier;
		createNew = false;
		IdType idType = cashier.getIdType();
		if (idType.equals(IdType.NRCNO)) {
			isNrcCashier = true;
			isStillApplyCashier = false;
		} else if (idType.equals(IdType.STILL_APPLYING)) {
			isNrcCashier = false;
			isStillApplyCashier = true;
		} else {
			isNrcCashier = false;
			isStillApplyCashier = false;
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}

	public List<Cashier> getCashierList() {
		return cashierList;
	}

	private void refreshCashierList() {
		cashierList = cashierService.findAllCashier();
	}

}

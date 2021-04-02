package org.ace.insurance.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.cashreceipt.CashReceiptCriteria;
import org.ace.insurance.cashreceipt.CashReceiptDTO;
import org.ace.insurance.cashreceipt.service.interfaces.ICashReceiptService;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;

@ViewScoped
@ManagedBean(name = "CashReceiptActionBean")
public class CashReceiptActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;

	@ManagedProperty(value = "#{CashReceiptService}")
	private ICashReceiptService cashReceiptService;

	public void setCashReceiptService(ICashReceiptService cashReceiptService) {
		this.cashReceiptService = cashReceiptService;
	}

	private List<CashReceiptDTO> generalConfirmationList;

	private List<CashReceiptDTO> selectedConfirmationList;

	private CashReceiptCriteria criteria;

	private String printTitle;

	private String tableTitle;
	// private String referenceTypeValue;
	private CashReceiptSelectableDataModel selectableDataModel;

	@PostConstruct
	public void init() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		criteria = new CashReceiptCriteria();
		if (criteria.getStartDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			criteria.setStartDate(cal.getTime());
		}
		if (criteria.getEndDate() == null) {
			Date endDate = new Date();
			criteria.setEndDate(endDate);
		}
		selectedConfirmationList = null;
		selectableDataModel = new CashReceiptSelectableDataModel();
	}

	public void filter() {
		selectedConfirmationList = null;
		if (ReferenceType.GROUP_LIFE.equals(criteria.getReferenceType())) {
			tableTitle = "Group Life Proposals Confirmation";
		} else if (ReferenceType.ENDOWMENT_LIFE.equals(criteria.getReferenceType())) {
			tableTitle = "Endownment Life Proposals Confirmation";
		} else if (ReferenceType.SNAKE_BITE.equals(criteria.getReferenceType())) {
			tableTitle = "Snake Bite Proposals Confirmation";
		}

		generalConfirmationList = cashReceiptService.findConfirmationList(criteria, user);
		selectableDataModel = new CashReceiptSelectableDataModel(generalConfirmationList);
	}

	public String confirm() {
		putParam("ConfirmationList", selectedConfirmationList);
		putParam("WorkFlowType", criteria.getReferenceType());
		if (criteria.getReferenceType().equals(ReferenceType.GROUP_LIFE)) {
			printTitle = "Multiple Life Proposals Confirmation";
		}
		return "printMultipleCashReceipt";
	}

	public void resetCriteria() {
		generalConfirmationList = null;
		init();
	}

	public ReferenceType[] getReferenceTypeSelectedItemList() {
		return new ReferenceType[] { ReferenceType.GROUP_LIFE };
	}

	public CashReceiptCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(CashReceiptCriteria criteria) {
		this.criteria = criteria;
	}

	public CashReceiptDTO[] getSelectedValues() {
		return null;
	}

	public void setSelectedValues(CashReceiptDTO[] cashReceiptDTOs) {
		System.out.println("selected item size.." + cashReceiptDTOs.length);
		selectedConfirmationList = Arrays.asList(cashReceiptDTOs);
	}

	public List<CashReceiptDTO> getSelectedConfirmationList() {
		return selectedConfirmationList;
	}

	public void setSelectedConfirmationList(List<CashReceiptDTO> selectedConfirmationList) {
		this.selectedConfirmationList = selectedConfirmationList;
	}

	public List<CashReceiptDTO> getGeneralConfirmationList() {
		return generalConfirmationList;
	}

	public void setGeneralConfirmationList(List<CashReceiptDTO> generalConfirmationList) {
		this.generalConfirmationList = generalConfirmationList;
	}

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	// public String getReferenceTypeValue() {
	// return referenceTypeValue;
	// }

	// public void setReferenceTypeValue(String referenceTypeValue) {
	// if (referenceTypeValue != null && !referenceTypeValue.isEmpty()) {
	// if (referenceTypeValue.equals("FIRE PROPOSAL")) {
	// criteria.setReferenceType(ReferenceType.FIRE);
	// } else if (referenceTypeValue.equals("MOTOR PROPOSAL")) {
	// criteria.setReferenceType(ReferenceType.MOTOR);
	// } else if (referenceTypeValue.equals("LIFE PROPOSAL")) {
	// criteria.setReferenceType(ReferenceType.LIFE);
	// }
	// }
	// this.referenceTypeValue = referenceTypeValue;
	// }

	public CashReceiptSelectableDataModel getSelectableDataModel() {
		return selectableDataModel;
	}

	public String getPrintTitle() {
		return printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}
}

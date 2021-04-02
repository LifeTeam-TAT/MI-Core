package org.ace.insurance.web.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.cashier.Cashier;
import org.ace.insurance.system.common.cashier.service.interfaces.ICashierService;
import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.insurance.system.common.workshop.service.interfaces.IWorkShopService;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "CashierDialogActionBean")
@ViewScoped
public class CashierDialogActionBean {
	@ManagedProperty(value = "#{CashierService}")
	private ICashierService cashierService;

	public void setCashierService(ICashierService cashierService) {
		this.cashierService = cashierService;
	}

	@ManagedProperty(value = "#{WorkShopService}")
	private IWorkShopService workShopService;

	public void setWorkShopService(IWorkShopService workShopService) {
		this.workShopService = workShopService;
	}

	private WorkShop workShop;
	private List<WorkShop> workShopList;
	private List<Cashier> cashierList;
	private String criteria;
	private String criteriaValue;

	@PostConstruct
	public void init() {
		workShop = new WorkShop();
		resetCriteria();
		cashierList = new ArrayList<Cashier>();
	}

	public void searchWorkShop() {
		workShopList = workShopService.findByCriteria(criteria, criteriaValue);
	}

	public List<Cashier> searchCashier(WorkShop workShop) {
		return cashierService.findCasherByWorkShop(workShop);
	}

	public void resetCriteria() {
		criteria = "";
		criteriaValue = "";
		workShopList = workShopService.findAll();
	}

	public List<String> getWorkShopCriteriaList() {
		ArrayList<String> criteriaList = new ArrayList<String>();
		criteriaList.add("NAME");
		criteriaList.add("CODE_NO");
		return criteriaList;
	}

	public List<Cashier> getCashierList() {
		return cashierList;
	}

	public List<WorkShop> getWorkShopList() {
		return workShopList;
	}

	public void selectCashier(Cashier cashier) {
		RequestContext.getCurrentInstance().closeDialog(cashier);
	}

	public WorkShop getWorkShop() {
		return workShop;
	}

	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

}

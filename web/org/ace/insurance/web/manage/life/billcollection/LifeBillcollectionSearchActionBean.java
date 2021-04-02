package org.ace.insurance.web.manage.life.billcollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.Name;
import org.ace.insurance.life.premium.LifePolicyBilling;
import org.ace.insurance.life.premium.LifePolicyPremium;
import org.ace.insurance.life.premium.service.interfaces.ILifePolicyBillingService;
import org.ace.insurance.life.premium.service.interfaces.ILifePolicyPremiumService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

/**
 * Life Bill Collection SearchActionBean
 * 
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/17
 */
@ViewScoped
@ManagedBean(name = "LifeBillcollectionSearchActionBean")
public class LifeBillcollectionSearchActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Customer customer;
	private Agent agent;
	private String lifePolicyNo;
	private Date startDate;
	private Date endDate;
	private LifePolicyBilling lifePolicyBilling;
	private List<LifePolicyPremium> lifePolicyPremiumList;
	private List<LifePolicyBilling> lifePolicyBillingList;

	@ManagedProperty(value = "#{LifePolicyPremiumService}")
	private ILifePolicyPremiumService lifePolicyPremiumService;

	public void setLifePolicyPremiumService(ILifePolicyPremiumService lifePolicyPremiumService) {
		this.lifePolicyPremiumService = lifePolicyPremiumService;
	}

	@ManagedProperty(value = "#{LifePolicyBillingService}")
	private ILifePolicyBillingService lifePolicyBillingService;

	public void setLifePolicyBillingService(ILifePolicyBillingService lifePolicyBillingService) {
		this.lifePolicyBillingService = lifePolicyBillingService;
	}

	@PostConstruct
	public void init() {
		lifePolicyNo = "";
		startDate = new Date();
		endDate = new Date();
		agent = new Agent();
		customer = new Customer();
		Name name = new Name();
		customer.setName(name);
		lifePolicyBillingList = billCollectionSearch(lifePolicyNo, customer.getId(), agent.getId(), startDate, endDate);
	}

	public String conditionChange() {
		lifePolicyBillingList = billCollectionSearch(lifePolicyNo, customer.getId(), agent.getId(), this.startDate, this.endDate);
		return "lifeBillCollectionSearchForm";
	}

	public String searchReset() {
		lifePolicyNo = "";
		startDate = new Date();
		endDate = new Date();
		agent = new Agent();
		customer = new Customer();
		return conditionChange();
	}

	public String billCollection(LifePolicyBilling lifePolicyBilling) {
		setLifePolicyBilling(lifePolicyBilling);
		return "lifeBillCollectionForm";
	}

	private List<LifePolicyBilling> billCollectionSearch(String lifePolicyNo, String customerID, String agentID, Date startDate, Date endDate) {
		List<LifePolicyBilling> result = new ArrayList<LifePolicyBilling>();
		result = lifePolicyBillingService.findLifePolicyBilling(lifePolicyNo, customerID, agentID, startDate, endDate);
		return result;
	}

	public String getLifePolicyNo() {
		return lifePolicyNo;
	}

	public void setLifePolicyNo(String lifePolicyNo) {
		this.lifePolicyNo = lifePolicyNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<LifePolicyPremium> getLifePolicyPremiumList() {
		return lifePolicyPremiumList;
	}

	public Agent getAgent() {
		return agent;
	}

	public Customer getCustomer() {
		return customer;
	}

	public LifePolicyBilling getLifePolicyBilling() {
		return lifePolicyBilling;
	}

	public void setLifePolicyBilling(LifePolicyBilling lifePolicyBilling) {
		this.lifePolicyBilling = lifePolicyBilling;
	}

	public List<LifePolicyBilling> getLifePolicyBillingList() {
		return lifePolicyBillingList;
	}

	public void setLifePolicyBillingList(List<LifePolicyBilling> lifePolicyBillingList) {
		this.lifePolicyBillingList = lifePolicyBillingList;
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		this.customer = customer;
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		this.agent = agent;
	}
}

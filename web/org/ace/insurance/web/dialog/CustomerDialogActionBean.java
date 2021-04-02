package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.filter.cirteria.CRIA001;
import org.ace.insurance.filter.customer.CUST001;
import org.ace.insurance.filter.customer.interfaces.ICUST_Filter;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "CustomerDialogActionBean")
@ViewScoped
public class CustomerDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CustomerService}")
	private ICustomerService customerService;

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@ManagedProperty(value = "#{CUST_Filter}")
	protected ICUST_Filter filter;

	private CRIA001 customerCriteria;
	private String criteriaValue;
	private List<CUST001> customerList;

	@PostConstruct
	public void init() {
		criteriaValue = "";
		customerList = filter.find(30);
	}

	public List<CUST001> getCustomerList() {
		return customerList;
	}

	public void search() {
		customerList = filter.find(customerCriteria, criteriaValue);
	}

	public void selectCustomer(CUST001 cust001) {
		Customer customer = customerService.findCustomerById(cust001.getId());
		PrimeFaces.current().dialog().closeDynamic(customer);
	}

	public CRIA001[] getCriteriaItems() {
		return CRIA001.values();
	}

	public CRIA001 getCustomerCriteria() {
		return customerCriteria;
	}

	public void setCustomerCriteria(CRIA001 customerCriteria) {
		this.customerCriteria = customerCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public void setFilter(ICUST_Filter filter) {
		this.filter = filter;
	}

}

package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.filter.cirteria.CRIA002;
import org.ace.insurance.filter.life.LPLC001;
import org.ace.insurance.filter.life.interfaces.ILIFE_Filter;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "LifePolicyDialogActionBean")
@ViewScoped
public class LifePolicyDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{LIFE_Filter}")
	protected ILIFE_Filter filter;

	private List<LPLC001> lifePolicyList;
	private CRIA002 criteria;
	private List<Product> productList;
	private List<Branch> branchList;

	@PostConstruct
	public void init() {
		resetCriteria();
		productList = productService.findProductByInsuranceType(InsuranceType.LIFE);
		branchList = branchService.findAllBranch();
	}

	public void search() {
		lifePolicyList = filter.find(criteria);
	}

	public void resetCriteria() {
		criteria = new CRIA002();
		if (criteria.getStartDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			criteria.setStartDate(cal.getTime());
		}
		if (criteria.getEndDate() == null) {
			Date endDate = new Date();
			criteria.setEndDate(endDate);
		}
		lifePolicyList = filter.find(criteria);
	}

	public List<LPLC001> loadLifePolicyList() {
		return lifePolicyList;
	}

	public CRIA002 getCriteria() {
		return criteria;
	}

	public void setCriteria(CRIA002 criteria) {
		this.criteria = criteria;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void selectLifePolicy(LPLC001 lplcDTO001) {
		putParam("LplcDTO001", lplcDTO001);
	}

	public void setFilter(ILIFE_Filter filter) {
		this.filter = filter;
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		criteria.setOrganization(organization);
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		criteria.setCustomer(customer);
	}

}

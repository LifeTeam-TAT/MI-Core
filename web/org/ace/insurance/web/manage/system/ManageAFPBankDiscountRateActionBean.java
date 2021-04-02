package org.ace.insurance.web.manage.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.product.PROGRP002;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.service.interfaces.IProductGroupService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;

@ViewScoped
@ManagedBean(name = "ManageAFPBankDiscountRateActionBean")
public class ManageAFPBankDiscountRateActionBean extends BaseBean {

	@ManagedProperty(value = "#{ProductGroupService}")
	private IProductGroupService productGroupService;

	public void setProductGroupService(IProductGroupService productGroupService) {
		this.productGroupService = productGroupService;
	}

	private List<PROGRP002> productGroupList;

	@PostConstruct
	public void init() {
		productGroupList = productGroupService.findAllByGroupType(null);
	}

	public String configuredDiscount(PROGRP002 param) {
		ProductGroup productGroup = productGroupService.findProductGroupById(param.getId());
		putParam(Constants.PRODUCTGROUP_ID, productGroup);
		return "addNewAFPBankDiscountRate";
	}

	public List<PROGRP002> getProductGroupList() {
		return productGroupList;
	}

}

package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.product.PROGRP002;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.service.interfaces.IProductGroupService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "ProductGroupDialogActionBean")
@ViewScoped
public class ProductGroupDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductGroupService}")
	private IProductGroupService productGroupService;

	public void setProductGroupService(IProductGroupService productGroupService) {
		this.productGroupService = productGroupService;
	}

	private List<PROGRP002> productGroupList;

	@PostConstruct
	public void init() {
		ProductGroupType productGroupType = (ProductGroupType) getParam("PRODUCTGROUPTYPE");
		productGroupList = productGroupService.findAllByGroupType(productGroupType);
	}

	public List<PROGRP002> getProductGroupList() {
		return productGroupList;
	}

	public void selectProductGroup(PROGRP002 progrp002) {
		ProductGroup productGroup = productGroupService.findProductGroupById(progrp002.getId());
		PrimeFaces.current().dialog().closeDynamic(productGroup);
	}
}

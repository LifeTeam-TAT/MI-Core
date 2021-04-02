package org.ace.insurance.web.manage.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.product.NcbRate;
import org.ace.insurance.product.NcbYear;
import org.ace.insurance.product.PROGRP003;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.service.interfaces.IProductGroupService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageProductGroupActionBean")
public class ManageProductGroupActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean createNew;
	private boolean crateNewNcbRate;

	@ManagedProperty(value = "#{ProductGroupService}")
	private IProductGroupService productGroupService;

	public void setProductGroupService(IProductGroupService productGroupService) {
		this.productGroupService = productGroupService;
	}

	private List<PROGRP003> productGroupList;
	private ProductGroup productGroup;
	private NcbRate ncbRate;

	@PostConstruct
	public void init() {
		createNewProductGroup();
		loadProductGroup();
	}

	public void loadProductGroup() {
		productGroupList = productGroupService.findAll_PROGRP003();
	}

	public void createNewProductGroup() {
		createNew = true;
		crateNewNcbRate = true;
		productGroup = new ProductGroup();
		ncbRate = new NcbRate();
		productGroup.setNcbRates(new ArrayList<NcbRate>());
	}

	public void prepareUpdateProductGroup(PROGRP003 progrp003) {
		createNew = false;
		this.productGroup = productGroupService.findProductGroupById(progrp003.getId());
	}

	public void addNewNcbRate() {
		productGroup.getNcbRates().add(ncbRate);
		ncbRate = new NcbRate();
	}

	public void prepareUpdateNcbRate(NcbRate ncbRate) {
		crateNewNcbRate = false;
		this.ncbRate = ncbRate;
	}

	public void updateNcbRate() {
		List<NcbRate> ncbRateList = productGroup.getNcbRates();
		for (NcbRate ncb : ncbRateList) {
			if (ncb.equals(ncbRate)) {
				ncbRateList.remove(ncb);
				break;
			}
		}
		productGroup.getNcbRates().add(ncbRate);
		ncbRate = new NcbRate();
		crateNewNcbRate = true;
	}

	public void deleteNcbRate(NcbRate ncbRate) {
		productGroup.getNcbRates().remove(ncbRate);
	}

	public void addNewProductGroup() {
		try {
			productGroupService.addNewProductGroup(productGroup);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, productGroup.getName());
			productGroupList.add(new PROGRP003(productGroup));
			createNewProductGroup();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateProductGroup() {
		try {
			productGroupService.updateProductGroup(productGroup);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, productGroup.getName());
			createNewProductGroup();
		} catch (SystemException ex) {
			handelSysException(ex);
		}

		loadProductGroup();
	}

	public void deleteProductGroup(PROGRP003 progrp003) {
		try {
			this.productGroup = productGroupService.findProductGroupById(progrp003.getId());
			productGroupService.deleteProductGroup(productGroup);
			productGroupList.remove(progrp003);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, progrp003.getName());
			createNewProductGroup();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public boolean isCrateNewNcbRate() {
		return crateNewNcbRate;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public List<PROGRP003> getProductGroupList() {
		return productGroupList;
	}

	public ProductGroupType[] getProductGroupTypeSelectItemList() {
		return ProductGroupType.values();
	}

	public NcbYear[] getNcbYearSelectItemList() {
		return NcbYear.values();
	}

	public NcbRate getNcbRate() {
		return ncbRate;
	}
}

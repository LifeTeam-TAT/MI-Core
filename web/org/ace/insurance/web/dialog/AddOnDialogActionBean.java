package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.addon.service.interfaces.IAddOnService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "AddOnDialogActionBean")
@ViewScoped
public class AddOnDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<AddOn> addOnList;
	private List<AddOn> selectedAddOnList;

	@ManagedProperty(value = "#{AddOnService}")
	private IAddOnService addOnService;

	public void setAddOnService(IAddOnService addOnService) {
		this.addOnService = addOnService;
	}

	@PostConstruct
	public void init() {
		if (isExistParam("PRODUCT")) {
			Product product = (Product) getParam("PRODUCT");
			addOnList = product.getAddOnList();
			// addOnList =
			// addOnService.findPremiumRateByProductId(product.getId());
		} else {
			addOnList = addOnService.findAllAddOn();
			// addOnList = addOnService.findPremiumRate();
		}
		selectedAddOnList = new ArrayList<AddOn>();
	}

	public List<AddOn> getAddOnList() {
		return addOnList;
	}

	public void setAddOnList(List<AddOn> addOnList) {
		this.addOnList = addOnList;
	}

	public List<AddOn> getSelectedAddOnList() {
		return selectedAddOnList;
	}

	public void setSelectedAddOnList(List<AddOn> selectedAddOnList) {
		this.selectedAddOnList = selectedAddOnList;
	}

	public void selectAddOn() {
		RequestContext.getCurrentInstance().closeDialog(selectedAddOnList);
	}

}

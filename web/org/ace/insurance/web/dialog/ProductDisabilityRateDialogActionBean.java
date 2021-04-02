package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;
import org.ace.insurance.productDisabilityPartLink.service.interfaces.IProductDisabilityPartLinkService;
import org.ace.insurance.web.Param;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "ProductDisabilityRateDialogActionBean")
@ViewScoped
public class ProductDisabilityRateDialogActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductDisabilityPartLinkService}")
	private IProductDisabilityPartLinkService productDisabilityService;

	public void setProductDisabilityService(IProductDisabilityPartLinkService productDisabilityService) {
		this.productDisabilityService = productDisabilityService;
	}

	private List<ProductDisabilityRate> productDisabilityRateList;

	private List<ProductDisabilityRate> selectedProductDisabilityRateList;

	// @PreDestroy
	// public void removeParam() {
	// removeParam("produtId");
	// }

	@PostConstruct
	public void init() {
		selectedProductDisabilityRateList = (List<ProductDisabilityRate>) getParam(Param.PRODUCT_DISABILITY_RATE);
		String produtId = isExistParam("produtId") ? (String) getParam("produtId") : null;
		if (produtId != null) {
			productDisabilityRateList = productDisabilityService.findAllDisabilityRateByProduct(produtId);
		}
		if (selectedProductDisabilityRateList == null) {
			selectedProductDisabilityRateList = new ArrayList<ProductDisabilityRate>();
		}
	}

	public List<ProductDisabilityRate> getSelectedProductDisabilityRateList() {
		return selectedProductDisabilityRateList;
	}

	public void setSelectedProductDisabilityRateList(List<ProductDisabilityRate> selectedProductDisabilityRateList) {
		this.selectedProductDisabilityRateList = selectedProductDisabilityRateList;
	}

	public List<ProductDisabilityRate> getProductDisabilityRateList() {
		return productDisabilityRateList;
	}

	public void selectDisabiliytRate() {
		PrimeFaces.current().dialog().closeDynamic(selectedProductDisabilityRateList);
	}
}

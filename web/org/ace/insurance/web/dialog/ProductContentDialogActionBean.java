package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.productContent.service.interfaces.IProductContentService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ProductContentDialogActionBean")
@ViewScoped
public class ProductContentDialogActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ProductContent> productContentList;

	@ManagedProperty(value = "#{ProductContentService}")
	private IProductContentService productContentService;

	public void setProductContentService(IProductContentService productContentService) {
		this.productContentService = productContentService;
	}

	@PostConstruct
	public void init() {
		productContentList = productContentService.findAllProductContent();
	}

	public List<ProductContent> getProductContentList() {
		return productContentList;
	}

	public void setProductContentList(List<ProductContent> productContentList) {
		this.productContentList = productContentList;
	}

	public void selectProductContent(ProductContent selectedProductContent) {
		RequestContext.getCurrentInstance().closeDialog(selectedProductContent);
	}

}

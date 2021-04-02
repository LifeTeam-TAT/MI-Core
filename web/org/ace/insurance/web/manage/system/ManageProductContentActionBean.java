package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.productContent.service.interfaces.IProductContentService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageProductContentActionBean")
public class ManageProductContentActionBean extends BaseBean implements Serializable {

	@ManagedProperty(value = "#{ProductContentService}")
	private IProductContentService productContentService;

	public void setProductContentService(IProductContentService productContentService) {
		this.productContentService = productContentService;
	}

	private ProductContent productContent;
	private List<ProductContent> productContentList;
	private boolean isNew;

	@PostConstruct
	public void init() {
		loadProductContent();
	}

	public void loadProductContent() {
		createNewInstances();
		productContentList = productContentService.findAllProductContent();
	}

	public void addNewProductContent() {
		try {
			productContentService.addNewProductContent(productContent);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, productContent.getName());
			loadProductContent();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateProductContent() {
		try {
			productContentService.updateProductContent(productContent);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, productContent.getName());
			loadProductContent();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteProductContent(ProductContent productContent) {
		try {
			productContentService.deleteProductContent(productContent);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, productContent.getName());
			loadProductContent();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void prepareUpdateProductContent(ProductContent productContent) {
		this.productContent = productContent;
		isNew = false;
	}

	public void createNewInstances() {
		productContent = new ProductContent();
		isNew = true;
	}

	public ProductContent getProductContent() {
		return productContent;
	}

	public void setProductContent(ProductContent productContent) {
		this.productContent = productContent;
	}

	public List<ProductContent> getProductContentList() {
		return productContentList;
	}

	public boolean getIsNew() {
		return isNew;
	}

}

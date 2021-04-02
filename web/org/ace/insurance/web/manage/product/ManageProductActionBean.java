/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.product;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.product.PRO001;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.service.interfaces.IProductGroupService;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageProductActionBean")
public class ManageProductActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	@ManagedProperty(value = "#{ProductGroupService}")
	private IProductGroupService productGroupService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setProductGroupService(IProductGroupService productGroupService) {
		this.productGroupService = productGroupService;
	}

	private List<PRO001> productList;
	private ProductGroup productGroup;
	private PRO001 selectedProduct;

	private void removeSessionOfThisPage() {
		removeParam(Constants.PRODUCTGROUP_ID);
	}

	@PostConstruct
	public void init() {
		if (isExistParam(Constants.PRODUCTGROUP_ID)) {
			productGroup = productGroupService.findProductGroupById((String) getParam(Constants.PRODUCTGROUP_ID));
			loadProductsByProductGroup();
		}
		removeSessionOfThisPage();
	}

	public void returnProductGroup(SelectEvent event) {
		productGroup = (ProductGroup) event.getObject();
		loadProductsByProductGroup();
	}

	private void loadProductsByProductGroup() {
		String productGroupId;
		if (productGroup.getId() != null)
			productGroupId = productGroup.getId();
		else
			productGroupId = "";
		productList = productService.findProductDTOGroupById(productGroupId);
	}

	public String prepareForAddNewMainCover() {
		putParam(Constants.PRODUCTGROUP_ID, productGroup.getId());
		return "addNewProduct";
	}

	public String prepareEditProduct() {
		String result = null;
		if (selectedProduct != null) {
			putParam(Constants.PRODUCTGROUP_ID, productGroup.getId());
			putParam(Constants.PRODUCT_ID, selectedProduct.getId());
			result = "addNewProduct";
		} else {
			addErrorMessage("productTableForm:productTable", MessageId.NEED_TO_SELECT_ONE_RECORD);
		}
		return result;
	}

	public String prepareAddPremiumRate() {
		String result = null;
		if (selectedProduct != null) {
			putParam(Constants.PRODUCTGROUP_ID, productGroup.getId());
			putParam(Constants.RATE_PRODUCT_ID, selectedProduct.getId());
			result = "manageProductPrmRateConfig";
		} else {
			addErrorMessage("productTableForm:productTable", MessageId.NEED_TO_SELECT_ONE_RECORD);
		}
		return result;
	}

	public String prepareAddAddOn() {
		String result = null;
		if (selectedProduct != null) {
			putParam(Constants.PRODUCTGROUP_ID, productGroup.getId());
			putParam(Constants.PRODUCT_ID, selectedProduct.getId());
			result = "manageAddOn";
		} else {
			addErrorMessage("productTableForm:productTable", null);
		}
		return result;
	}

	public void removeProduct() {
		try {
			if (selectedProduct != null) {
				productService.deleteProduct(productService.findProductById(selectedProduct.getId()));
				productList.remove(selectedProduct);
				addInfoMessage(null, MessageId.DELETE_SUCCESS, selectedProduct.getMainCoverName());
			} else {
				addErrorMessage("productTableForm:productTable", null);
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public PRO001 getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(PRO001 selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productList = null;
		this.selectedProduct = null;
		this.productGroup = productGroup;
	}

	public List<PRO001> getProductList() {
		return productList;
	}

}

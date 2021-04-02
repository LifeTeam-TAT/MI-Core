/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.addon.service.interfaces.IAddOnService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.keyfactor.service.interfaces.IKeyFactorService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageAddOnActionBean")
public class ManageAddOnActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{AddOnService}")
	private IAddOnService addOnService;

	public void setAddOnService(IAddOnService addOnService) {
		this.addOnService = addOnService;
	}

	@ManagedProperty(value = "#{KeyFactorService}")
	private IKeyFactorService keyFactorService;

	public void setKeyFactorService(IKeyFactorService keyFactorService) {
		this.keyFactorService = keyFactorService;
	}

	private Product product;
	private AddOn addOn;
	private List<KeyFactor> keyFactorList;
	private List<KeyFactor> selectedKeyFactorList;
	private List<AddOn> selectedAddOnList;
	private boolean isCreateNew;

	private void clearSessionOnThisPage() {
		removeParam(Constants.PRODUCT_ID);
	}

	@PostConstruct
	public void init() {
		if (isExistParam(Constants.PRODUCT_ID)) {
			String productId = (String) getParam(Constants.PRODUCT_ID);
			product = productService.findProductById(productId);
		} else
			product = new Product();
		createNewInstance();
		loadDataFromDB();
		clearSessionOnThisPage();
	}

	public void createNewInstance() {
		addOn = new AddOn();
		selectedKeyFactorList = new ArrayList<KeyFactor>();
		selectedAddOnList = new ArrayList<AddOn>();
		createNewAddOn();
	}

	private void loadDataFromDB() {
		keyFactorList = keyFactorService.findAllKeyFactor();
	}

	public void createNewAddOn() {
		isCreateNew = true;
		addOn = new AddOn();
	}

	private boolean validate() {
		boolean valid = true;
		if (addOn.isBaseOnKeyFactor() && addOn.getKeyFactorList().isEmpty()) {
			addErrorMessage("addOnEntryForm:keyFactorTable", MessageId.REQUIRED_VALUES);
			valid = false;
		}
		return valid;
	}

	public void addNewAddOn() {
		if (validate()) {
			product.addAddOn(addOn);
			productService.updateProduct(product);
			product = productService.findProductById(product.getId());
			addInfoMessage(null, MessageId.INSERT_SUCCESS, addOn.getProductContent().getName());
			createNewInstance();
		}
	}

	public String prepareCreatePremiumRate() {
		String result = null;
		if (selectedAddOnList.size() > 0) {
			putParam(Constants.PRODUCT_ID, product.getId());
			putParam(Constants.ADDON_ID, selectedAddOnList.get(0).getId());
			result = "manageProductPrmRateConfig";
		} else {
			addErrorMessage("addOnEntryForm:buildingTable", MessageId.NEED_TO_SELECT_ONE_RECORD);
		}
		return result;

	}

	public void prepareEditAddOn() {
		addOn = new AddOn(selectedAddOnList.get(0));
		isCreateNew = false;
	}

	public void updateAddOn() {
		if (validate()) {
			addOnService.updateAddOn(addOn);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, addOn.getProductContent().getName());
			// solve(although after update addOn, product's addons still old.)
			product = productService.findProductById(product.getId());
			createNewInstance();
		}
	}

	public void removeAddOn() {
		try {
			product.getAddOnList().removeAll(selectedAddOnList);
			productService.updateProduct(product);
			String addOnNames = "";
			for (AddOn addOn : selectedAddOnList) {
				addOnNames += addOn.getProductContent().getName() + ", ";
			}
			product = productService.findProductById(product.getId());
			addInfoMessage(null, MessageId.DELETE_SUCCESS, addOnNames);
			createNewInstance();
		} catch (SystemException ex) {
			for (AddOn addOn : selectedAddOnList)
				product.addAddOn(addOn);
			handelSysException(ex);
		}
	}

	public String backManageProduct() {
		return "manageProduct";
	}

	public void preparedUpdateKeyFactorList() {
		selectedKeyFactorList = addOn.getKeyFactorList();
	}

	public void saveKeyFactorList() {
		addOn.setKeyFactorList(selectedKeyFactorList);
	}

	public void removeKeyFactorList(KeyFactor keyFactor) {
		addOn.getKeyFactorList().remove(keyFactor);
	}

	public void returnProductContent(SelectEvent event) {
		ProductContent productContent = (ProductContent) event.getObject();
		addOn.setProductContent(productContent);
	}

	public Product getProduct() {
		return product;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public List<KeyFactor> getKeyFactorList() {
		return keyFactorList;
	}

	public List<KeyFactor> getSelectedKeyFactorList() {
		return selectedKeyFactorList;
	}

	public void setSelectedKeyFactorList(List<KeyFactor> selectedKeyFactorList) {
		this.selectedKeyFactorList = selectedKeyFactorList;
	}

	public List<AddOn> getSelectedAddOnList() {
		return selectedAddOnList;
	}

	public void setSelectedAddOnList(List<AddOn> selectedAddOnList) {
		this.selectedAddOnList = selectedAddOnList;
	}

	public boolean isCreateNew() {
		return isCreateNew;
	}

}

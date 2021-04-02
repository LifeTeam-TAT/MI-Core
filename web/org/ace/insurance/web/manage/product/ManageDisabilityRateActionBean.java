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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityPart;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;
import org.ace.insurance.productDisabilityPartLink.service.interfaces.IProductDisabilityPartLinkService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageDisabilityRateActionBean")
public class ManageDisabilityRateActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{ProductDisabilityPartLinkService}")
	private IProductDisabilityPartLinkService productDisabilityPartService;

	public void setProductDisabilityPartService(IProductDisabilityPartLinkService productDisabilityPartService) {
		this.productDisabilityPartService = productDisabilityPartService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private ProductDisabilityPart productDisability;
	private ProductDisabilityRate productDisabilityRate;
	private List<ProductDisabilityPart> productDisabilityList;
	private List<ProductDisabilityRate> productDisabilityRateList;
	private boolean createNew;

	@PostConstruct
	public void init() {
		productDisability = new ProductDisabilityPart();
		productDisabilityRate = new ProductDisabilityRate();
		productDisabilityRateList = new ArrayList<ProductDisabilityRate>();
		createNew = true;
		loadProductDisability();
	}

	public void createNewProductDisability() {
		productDisability = new ProductDisabilityPart();
		productDisabilityRate = new ProductDisabilityRate();
		productDisabilityRateList = new ArrayList<ProductDisabilityRate>();
		createNew = true;
		PrimeFaces.current().resetInputs("productDisabilityPartEntryForm");
	}

	private void loadProductDisability() {
		productDisabilityList = productDisabilityPartService.findAll();
	}

	private boolean validate() {
		boolean result = true;
		if (productDisabilityRateList.size() < 1) {
			addErrorMessage("productDisabilityPartEntryForm:disabilityPercentageGroup", MessageId.VALUE_IS_REQUIRED);
			result = false;
		} else {
			for (ProductDisabilityRate rate : productDisabilityRateList) {
				if (rate.getPercentage() <= 0.0) {
					result = false;
					addErrorMessage("productDisabilityPartEntryForm:disabilityPercentageGroup", MessageId.ADD_PERCENTAGE, productDisability.getProduct().getName());
					break;
				}
			}
		}
		return result;

	}

	public void addNewProductDisability() {
		try {
			if (validate()) {
				productDisability.setDisabilityRateList(productDisabilityRateList);
				productDisabilityPartService.addNewProductDisabilityPart(productDisability);
				productDisabilityList.add(productDisability);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, productDisability.getProduct().getName());
				createNewProductDisability();
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void deleteDisabilityRate(ProductDisabilityRate rate) {
		productDisabilityRateList.remove(rate);
	}

	public void updateProductDisability() {
		try {
			if (validate()) {
				productDisability.setDisabilityRateList(productDisabilityRateList);
				productDisability = productDisabilityPartService.updateProductDisabilityPart(productDisability);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, productDisability.getProduct().getName());
				createNewProductDisability();
				loadProductDisability();
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void prepareEditProductDisability(ProductDisabilityPart disability) {
		productDisabilityRateList = new ArrayList<>();
		this.productDisability = productDisabilityPartService.findProductDisbilityById(disability.getId());
		if (null != productDisability) {
			for (ProductDisabilityRate disabilityrate : disability.getDisabilityRateList()) {
				this.productDisabilityRateList.add(disabilityrate);
			}
		}
		createNew = false;
	}

	public void deleteProductDisability(ProductDisabilityPart disability) {
		try {
			productDisabilityPartService.deleteProductDisabilityPart(disability);
			productDisabilityList.remove(disability);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, disability.getProduct().getName());
			createNewProductDisability();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void returnDisabilityPart(SelectEvent event) {
		productDisabilityRateList = new ArrayList<>();
		List<DisabilityPart> disabilityPartList = new ArrayList<DisabilityPart>();
		List<DisabilityPart> selectedDisabilityPartList = (List<DisabilityPart>) event.getObject();
		for (DisabilityPart part : selectedDisabilityPartList) {
			if (!disabilityPartList.contains(part)) {
				productDisabilityRate = new ProductDisabilityRate();
				productDisabilityRate.setDisabilityPart(part);
				productDisabilityRateList.add(productDisabilityRate);
			}
		}
		productDisability.setDisabilityRateList(productDisabilityRateList);
	}

	public List<ProductDisabilityPart> getProductDisabilityList() {
		return productDisabilityList;
	}

	public ProductDisabilityPart getProductDisability() {
		return productDisability;
	}

	public ProductDisabilityRate getProductDisabilityRate() {
		return productDisabilityRate;
	}

	public List<ProductDisabilityRate> getProductDisabilityRateList() {
		return productDisabilityRateList;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<Product> getProductList() {
		return productService.findAllProduct();
	}

	public void selectDisabilityPart() {
		List<DisabilityPart> disabilityPartList = new ArrayList<DisabilityPart>();
		for (ProductDisabilityRate rate : productDisabilityRateList) {
			disabilityPartList.add(rate.getDisabilityPart());
		}
		selectDisabilityPart(disabilityPartList);
	}
}

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
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.product.Product;
import org.ace.insurance.product.StampFeeRateType;
import org.ace.insurance.product.service.interfaces.IProductGroupService;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.keyfactor.service.interfaces.IKeyFactorService;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AddNewProductActionBean")
public class AddNewProductActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{ProductGroupService}")
	private IProductGroupService productGroupService;

	public void setProductGroupService(IProductGroupService productGroupService) {
		this.productGroupService = productGroupService;
	}

	@ManagedProperty(value = "#{KeyFactorService}")
	private IKeyFactorService keyFactorService;

	public void setKeyFactorService(IKeyFactorService keyFactorService) {
		this.keyFactorService = keyFactorService;
	}

	@ManagedProperty(value = "#{PaymentTypeService}")
	private IPaymentTypeService paymentTypeService;

	public void setPaymentTypeService(IPaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	private String productId;
	private boolean isNew;
	private Product product;
	private List<KeyFactor> keyFactorList;
	private List<PaymentType> paymentTypeList;
	private List<KeyFactor> selectedKeyFactorList;
	private List<PaymentType> selectedPaymentTypeList;
	private List<Currency> currencyList;
	private String groupId;

	private void initializeInjection() {
		this.productId = (String) getParam(Constants.PRODUCT_ID);
		if (productId == null) {
			isNew = true;
		} else {
			isNew = false;
		}
	}

	private void createProduct() {
		if (isNew) {
			product = new Product();
			groupId = (String) getParam(Constants.PRODUCTGROUP_ID);
			product.setProductGroup(productGroupService.findProductGroupById(groupId));
		} else {
			product = productService.findProductById(productId);
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		createProduct();
		paymentTypeList = paymentTypeService.findAllPaymentType();
		keyFactorList = keyFactorService.findAllKeyFactor();
		currencyList = currencyService.findAllCurrency();
		selectedPaymentTypeList = new ArrayList<>();
		selectedKeyFactorList = new ArrayList<>();
	}

	@PreDestroy
	public void destory() {
		removeParam(Constants.PRODUCT_ID);
	}

	public void savePaymentTypeList() {
		product.setPaymentTypeList(selectedPaymentTypeList);
	}

	public void preparedUpdatePaymentTypeList() {
		selectedPaymentTypeList = product.getPaymentTypeList();
	}

	public void removePaymentTypeList(PaymentType paymentType) {
		product.getPaymentTypeList().remove(paymentType);
	}

	public void preparedUpdateKeyFactorList() {
		selectedKeyFactorList = product.getKeyFactorList();
	}

	public void saveKeyFactorList() {
		product.setKeyFactorList(selectedKeyFactorList);
	}

	public void removeKeyFactorList(KeyFactor keyFactor) {
		product.getKeyFactorList().remove(keyFactor);
	}

	public boolean validate() {
		boolean valid = true;
		if (product.isBaseOnKeyFactor() && product.getKeyFactorList().isEmpty()) {
			addErrorMessage("productEntryForm:keyFactorTable", MessageId.REQUIRED_VALUES);
			valid = false;
		}
		if (product.getPaymentTypeList().isEmpty()) {
			addErrorMessage("productEntryForm:paymentTypeTable", MessageId.REQUIRED_VALUES);
			valid = false;
		}
		return valid;
	}

	public String addNewProduct() {
		String result = null;
		try {
			if (validate()) {
				ExternalContext extContext = getFacesContext().getExternalContext();
				if (isNew) {
					productService.addNewProduct(product);
					extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.INSERT_SUCCESS);
				} else {
					productService.updateProduct(product);
					extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UPDATE_SUCCESS);
				}
				extContext.getSessionMap().put(Constants.MESSAGE_PARAM, product.getProductContent().getName());
				result = "manageProduct";
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public StampFeeRateType[] getStampFeeRateType() {
		return StampFeeRateType.values();
	}

	public String getProductId() {
		return productId;
	}

	public Product getProduct() {
		return product;
	}

	public List<KeyFactor> getSelectedKeyFactorList() {
		return selectedKeyFactorList;
	}

	public void setSelectedKeyFactorList(List<KeyFactor> selectedKeyFactorList) {
		this.selectedKeyFactorList = selectedKeyFactorList;
	}

	public List<PaymentType> getSelectedPaymentTypeList() {
		return selectedPaymentTypeList;
	}

	public void setSelectedPaymentTypeList(List<PaymentType> selectedPaymentTypeList) {
		this.selectedPaymentTypeList = selectedPaymentTypeList;
	}

	public List<KeyFactor> getKeyFactorList() {
		return keyFactorList;
	}

	public List<PaymentType> getPaymentTypeList() {
		return paymentTypeList;
	}

	public void returnProductContent(SelectEvent event) {
		ProductContent productContent = (ProductContent) event.getObject();
		product.setProductContent(productContent);
	}

	public boolean isNew() {
		return isNew;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}
}

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
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.PremiumRateType;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.service.interfaces.IProductGroupService;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.addon.service.interfaces.IAddOnService;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.keyfactor.service.interfaces.IKeyFactorService;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.insurance.web.datamodel.AddOnDataModel;
import org.ace.insurance.web.datamodel.KeyFactorDataModel;
import org.ace.insurance.web.datamodel.PaymentTypeDataModel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "UpdateProductActionBean")
public class UpdateProductActionBean extends BaseBean implements Serializable {
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

	@ManagedProperty(value = "#{PaymentTypeService}")
	private IPaymentTypeService paymentTypeService;

	public void setPaymentTypeService(IPaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	private Product product;
	private String productId;
	private List<KeyFactor> keyFactorList;
	private List<ProductGroup> productGroups;
	private List<PaymentType> paymentTypeList;
	private List<AddOn> addOnList;

	private void initializeInjection() {
		if (isExistParam(Constants.PRODUCT_ID)) {
			productId = (String) getParam(Constants.PRODUCT_ID);
			product = productService.findProductById(productId);
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		productGroups = productGroupService.findAllProductGroup();
		paymentTypeList = paymentTypeService.findAllPaymentType();
		addOnList = addOnService.findAllAddOn();
		keyFactorList = keyFactorService.findAllKeyFactor();
	}

	public EnumSet<PremiumRateType> getPremiumRateTypes() {
		return EnumSet.allOf(PremiumRateType.class);
	}

	public InsuranceType[] getInsuranceTypes() {
		return InsuranceType.values();
	}

	public void changeFixValue(AjaxBehaviorEvent e) {
		if (product.isBaseOnKeyFactor()) {
			product.setKeyFactorList(new ArrayList<KeyFactor>());
		}
	}

	public List<PaymentType> getPaymentTypeList() {
		return paymentTypeList;
	}

	public void removePaymentTypeList(PaymentType paymentType) {
		product.getPaymentTypeList().remove(paymentType);
	}

	public void removeAddOnList(AddOn addOn) {
		product.getAddOnList().remove(addOn);
	}

	public void removeKeyFactorList(KeyFactor keyFactor) {
		product.getKeyFactorList().remove(keyFactor);
	}

	public String updateProduct() {
		String result = null;
		try {
			productService.updateProduct(product);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, product.getName());
			result = "manageProduct";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public PaymentTypeDataModel getPaymentTypeDataModel() {
		return new PaymentTypeDataModel(paymentTypeList);
	}

	public AddOnDataModel getAddOnDataModel() {
		return new AddOnDataModel(addOnList);
	}

	public KeyFactorDataModel getKeyFactorDataModel() {
		return new KeyFactorDataModel(keyFactorList);
	}

	public PaymentType[] getSelectedPaymentTypes() {
		return null;
	}

	public List<ProductGroup> getProductGroups() {
		return productGroups;
	}

	public void setSelectedPaymentTypes(PaymentType[] paymentTypes) {
		List<PaymentType> paymentTypeList = product.getPaymentTypeList();
		if (paymentTypeList == null) {
			paymentTypeList = new ArrayList<PaymentType>();
		}
		Set<PaymentType> paymentTypeSet = new HashSet<PaymentType>(paymentTypeList);
		for (PaymentType var : paymentTypes) {
			paymentTypeSet.add(var);
		}
		product.setPaymentTypeList(new ArrayList<PaymentType>(paymentTypeSet));
	}

	public AddOn[] getSelectedAddOns() {
		return null;
	}

	public void setSelectedAddOns(AddOn[] addons) {
		List<AddOn> addOnList = product.getAddOnList();
		if (addOnList == null) {
			addOnList = new ArrayList<AddOn>();
		}
		Set<AddOn> addOnSet = new HashSet<AddOn>(addOnList);
		for (AddOn var : addons) {
			addOnSet.add(var);
		}
		product.setAddOnList(new ArrayList<AddOn>(addOnSet));
	}

	public KeyFactor[] getSelectedKeyFactors() {
		return null;
	}

	public void setSelectedKeyFactors(KeyFactor[] keyFactors) {
		List<KeyFactor> keyFactorList = product.getKeyFactorList();
		if (keyFactorList == null) {
			keyFactorList = new ArrayList<KeyFactor>();
		}
		Set<KeyFactor> keyFactorSet = new HashSet<KeyFactor>(keyFactorList);
		for (KeyFactor var : keyFactors) {
			keyFactorSet.add(var);
		}
		product.setKeyFactorList(new ArrayList<KeyFactor>(keyFactorSet));
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void returnProductGroup(SelectEvent event) {
		ProductGroup productGroup = (ProductGroup) event.getObject();
		product.setProductGroup(productGroup);
	}

	public void returnCurrency(SelectEvent event) {
		Currency currency = (Currency) event.getObject();
		product.setCurrency(currency);
	}

	public void returnProductContent(SelectEvent event) {
		ProductContent productContent = (ProductContent) event.getObject();
		product.setProductContent(productContent);
	}
}

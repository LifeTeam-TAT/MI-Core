package org.ace.insurance.web.manage.claimProduct;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.service.interfaces.IClaimProductService;
import org.ace.insurance.common.InsuranceType;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;

@ViewScoped
@ManagedBean(name = "ManageClaimProductActionBean")
public class ManageClaimProductActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ClaimProductService}")
	private IClaimProductService claimProductService;

	public void setClaimProductService(IClaimProductService claimProductService) {
		this.claimProductService = claimProductService;
	}

	private ClaimProduct claimProduct;
	private List<ClaimProduct> productList;
	private InsuranceType insuranceType;

	@PostConstruct
	public void init() {
		loadProuctList();
	}

	public void changeInsuranceType(AjaxBehaviorEvent e) {
		loadProuctList();
	}

	public void loadProuctList() {
		productList = insuranceType != null ? claimProductService.findClaimProductByInsuranceType(insuranceType) : claimProductService.findAllClaimProduct();
	}

	public String prepareForAddNewProduct() {
		putParam("claimProductParam", null);
		return "addNewClaimProduct";
	}

	public String prepareForUpdateProduct(ClaimProduct claimProduct) {
		putParam("claimProductParam", claimProduct);
		return "updateClaimProduct";
	}

	public String prepareForClaimRateConfig(ClaimProduct claimProduct) {
		putParam("claimProductParam", claimProduct);
		return "manageClaimProductRate";
	}

	public void deleteProduct() {
		try {
			claimProductService.deleteClaimProduct(claimProduct);
			addInfoMessage(null, "DELETE_SUCCESS", claimProduct.getName());
			loadProuctList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public ClaimProduct getClaimProduct() {
		return claimProduct;
	}

	public void setClaimProduct(ClaimProduct claimProduct) {
		this.claimProduct = claimProduct;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public InsuranceType[] getInsuranceTypes() {
		return InsuranceType.values();
	}

	public List<ClaimProduct> getProductList() {
		return productList;
	}

	public void checkMessage(ComponentSystemEvent event) {
		ExternalContext extContext = getFacesContext().getExternalContext();
		String messageId = (String) extContext.getSessionMap().get(Constants.MESSAGE_ID);
		String productNo = (String) extContext.getSessionMap().get(Constants.PRODUCT_NO);
		if (messageId != null) {
			if (productNo != null) {
				addInfoMessage(null, messageId, productNo);
				extContext.getSessionMap().remove(Constants.MESSAGE_ID);
				extContext.getSessionMap().remove(Constants.PRODUCT_NO);
			}
		}
	}
}

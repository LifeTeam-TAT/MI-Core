package org.ace.insurance.web.manage.claimProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.ClaimRateType;
import org.ace.insurance.claimproduct.service.interfaces.IClaimProductService;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.keyfactor.service.interfaces.IKeyFactorService;
import org.ace.insurance.web.datamodel.KeyFactorDataModel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "AddAndUpdateClaimProductActionBean")
public class AddAndUpdateClaimProductActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ClaimProductService}")
	private IClaimProductService claimProductService;

	@ManagedProperty(value = "#{KeyFactorService}")
	private IKeyFactorService keyFactorService;

	public void setClaimProductService(IClaimProductService claimProductService) {
		this.claimProductService = claimProductService;
	}

	public void setKeyFactorService(IKeyFactorService keyFactorService) {
		this.keyFactorService = keyFactorService;
	}

	private List<KeyFactor> keyFactorList;
	private ClaimProduct claimProduct;
	private boolean isNewProduct;

	@PostConstruct
	public void init() {
		keyFactorList = keyFactorService.findAllKeyFactor();
		claimProduct = (ClaimProduct) getParam("claimProductParam");
		if (claimProduct == null) {
			claimProduct = new ClaimProduct();
			claimProduct.setAutoCalculate(true);
			claimProduct.setRateType(ClaimRateType.USER_DEFINED);
			isNewProduct = true;
		} else {
			isNewProduct = false;
		}
	}

	@PreDestroy
	public void destroy() {
		removeParam("claimProductParam");
	}

	public InsuranceType[] getInsuranceTypes() {
		return InsuranceType.values();
	}

	public void setKeyFactorList(List<KeyFactor> keyFactorList) {
		this.keyFactorList = keyFactorList;
	}

	public void changeFixValue(AjaxBehaviorEvent e) {
		if (!this.claimProduct.isAutoCalculate()) {
			this.claimProduct.setKeyFactors(new ArrayList<KeyFactor>());
		}
	}

	public void removeKeyFactorList(KeyFactor keyFactor) {
		this.claimProduct.getKeyFactors().remove((Object) keyFactor);
	}

	public String addNewProduct() {
		String result = null;
		if (validate()) {
			try {
				claimProductService.addNewClaimProduct(claimProduct);
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.CLIAIMPRODUCT_INSERT_SUCCESS);
				extContext.getSessionMap().put(Constants.PRODUCT_NO, claimProduct.getName());
				result = "manageClaimProduct";
			} catch (SystemException ex) {
				handelSysException(ex);
			}
		}
		return result;

	}

	public String updateProduct() {
		String result = null;
		if (validate()) {
			try {
				claimProductService.updateClaimProduct(claimProduct);
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.CLIAIMPRODUCT_UPDATE_SUCCESS);
				extContext.getSessionMap().put(Constants.PRODUCT_NO, claimProduct.getName());
				result = "manageClaimProduct";
			} catch (SystemException ex) {
				handelSysException(ex);
			}
		}
		return result;
	}

	private boolean validate() {
		boolean result = true;
		if (claimProduct.isAutoCalculate()) {
			if (claimProduct.getKeyFactors() == null || claimProduct.getKeyFactors().isEmpty()) {
				String formId = "claimProductEntryForm:keyFactorListPanelGroup";
				result = false;
				addErrorMessage(formId, MessageId.KEYFACTOR_REQUIRED);
			}
		}
		return result;
	}

	public KeyFactorDataModel getKeyFactorDataModel() {
		return new KeyFactorDataModel(this.keyFactorList);
	}

	public KeyFactor[] getSelectedKeyFactors() {
		return null;
	}

	public void setSelectedKeyFactors(KeyFactor[] keyFactors) {
		List<KeyFactor> keyFactorList = claimProduct.getKeyFactors();
		if (keyFactorList == null) {
			keyFactorList = new ArrayList<KeyFactor>();
		}
		Set<KeyFactor> keyFactorSet = new HashSet<KeyFactor>(keyFactorList);
		for (KeyFactor var : keyFactors) {
			keyFactorSet.add(var);
		}
		claimProduct.setKeyFactors(new ArrayList<KeyFactor>(keyFactorSet));
	}

	public List<KeyFactor> getKeyFactorList() {
		return this.keyFactorList;
	}

	public ClaimProduct getClaimProduct() {
		return claimProduct;
	}

	public void setProduct(ClaimProduct claimProduct) {
		this.claimProduct = claimProduct;
	}

	public boolean isNewProduct() {
		return isNewProduct;
	}

	public ClaimRateType[] getClaimRateTypes() {
		return ClaimRateType.values();
	}

}

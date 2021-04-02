/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.claimProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.ClaimProductRate;
import org.ace.insurance.claimproduct.ClaimProductRateKeyFactor;
import org.ace.insurance.claimproduct.service.interfaces.IClaimProductService;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "ManageClaimProductRateActionBean")
public class ManageClaimProductRateActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ClaimProductService}")
	private IClaimProductService claimProductService;

	public void setClaimProductService(IClaimProductService claimProductService) {
		this.claimProductService = claimProductService;
	}

	private ClaimProduct claimProduct;
	private ClaimProductRate claimProductRate;

	private List<ClaimProductRate> cprList;
	private boolean createNew;

	private String toDeleteId;

	@PostConstruct
	public void init() {
		this.claimProduct = (ClaimProduct) getParam("claimProductParam");
		if (claimProduct == null) {
			redirect();
		}
		refreshList();
		createNewClaimProductRate();
	}

	private void refreshList() {
		cprList = claimProductService.findClaimProductRateByClaimProduct(claimProduct.getId());
		Collections.sort(cprList);
	}

	private String redirect() {
		return "manageClaimProduct";
	}

	private void createNewClaimProductRate() {
		createNew = true;
		this.claimProductRate = new ClaimProductRate();
		this.claimProductRate.setClaimProduct(claimProduct);
		for (KeyFactor kf : claimProduct.getKeyFactors()) {
			ClaimProductRateKeyFactor cprkf = new ClaimProductRateKeyFactor();
			cprkf.setKeyFactor(kf);
			claimProductRate.addClaimProductRateKeyFactor(cprkf);
		}
	}

	public List<SelectItem> getKFRefValueList(String entityName) {
		List<SelectItem> itemList = new ArrayList<SelectItem>();
		List<KFRefValue> kfRefValueList = claimProductService.findReferenceValue(entityName, null);
		for (KFRefValue kfRefValue : kfRefValueList) {
			itemList.add(new SelectItem(kfRefValue.getId(), kfRefValue.getName()));
		}
		return itemList;
	}

	public void addNewClaimProductRate() {
		try {
			claimProductService.addNewClaimProductRate(claimProductRate);
			createNewClaimProductRate();
			refreshList();
		} catch (SystemException ex) {
			this.handelSysException(ex);
		}
	}

	public void prepareForUpdate(String cprid) {
		createNew = false;
		this.claimProductRate = claimProductService.findClaimProductRateById(cprid);
	}

	public void deleteClaimProductRate() {
		try {
			ClaimProductRate claimProductRate = claimProductService.findClaimProductRateById(toDeleteId);
			claimProductService.deleteClaimProductRate(claimProductRate);
			createNewClaimProductRate();
			refreshList();
		} catch (SystemException ex) {
			this.handelSysException(ex);
		}
	}

	public void updateClaimProductRate() {
		try {
			claimProductService.updateClaimProductRate(claimProductRate);
			createNewClaimProductRate();
			refreshList();
		} catch (SystemException ex) {
			this.handelSysException(ex);
		}
	}

	public boolean getCreateNew() {
		return createNew;
	}

	public List<ClaimProductRate> getCprList() {
		return cprList;
	}

	public ClaimProductRate getClaimProductRate() {
		return claimProductRate;
	}

	public void setClaimProductRate(ClaimProductRate claimProductRate) {
		this.claimProductRate = claimProductRate;
	}

	public void setToDeleteId(String toDeleteId) {
		this.toDeleteId = toDeleteId;
	}

	public String getToDeleteId() {
		return toDeleteId;
	}

	public ClaimProduct getClaimProduct() {
		return claimProduct;
	}
}

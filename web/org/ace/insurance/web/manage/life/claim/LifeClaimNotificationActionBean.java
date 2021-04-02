package org.ace.insurance.web.manage.life.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimNotificationService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeClaimNotificationActionBean")
public class LifeClaimNotificationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeClaimNotificationService}")
	private ILifeClaimNotificationService notificationService;

	public void setNotificationService(ILifeClaimNotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;

	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}

	private boolean createNew;
	private LifeClaimNotification notification;
	private List<Product> productsList;
	private List<PolicyInsuredPerson> insuredPersonList;
	private List<LifeClaimNotification> notificationList;
	private List<String> provinceCodeList = new ArrayList<String>();
	private List<String> townshipCodeList = new ArrayList<String>();

	@PostConstruct
	public void init() {
		initialization();
		// productsList =
		// productService.findProductByInsuranceType(InsuranceType.LIFE);
		insuredPersonList = new ArrayList<PolicyInsuredPerson>();
		loadLifeClaimNotification();
		// notificationList = notificationService.findLifeClaimNotification();
		provinceCodeList = provinceService.findAllProvinceNo();
	}

	@PreDestroy
	private void destroy() {
		removeParam("notification");
	}

	private void initialization() {
		notification = (LifeClaimNotification) getParam("notification");
	}

	public void loadLifeClaimNotification() {
		if (notification != null) {
			createNew = false;
			this.notification.setClaimStatus(ClaimStatus.INITIAL_INFORM);
			LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(notification.getLifePolicyNo());
			insuredPersonList = lifePolicy.getPolicyInsuredPersonList();
			notification.getClaimInitialReporter().loadFullIdNo();
			townshipCodeList = townshipService.findTspShortNameByProvinceNo(notification.getClaimInitialReporter().getProvinceCode());
		} else {
			createNewLifeClaimNotification();
		}
	}

	public void createNewLifeClaimNotification() {
		createNew = true;
		notification = new LifeClaimNotification();
	}

	public void changeProduct(AjaxBehaviorEvent event) {
		notification.setLifePolicyNo(null);
	}

	public String addNotification() {
		String result = null;
		try {
			notification.getClaimInitialReporter().setFullIdNo();
			notification.setClaimStatus(ClaimStatus.INITIAL_INFORM);
			notificationService.addNewLifeClaimNotification(notification);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.LIFE_ClAIM_NOTIFICATION_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, notification.getNotificationNo());
			result = "managelifeClaimNotification";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public String updateNotification() {
		String result = null;
		try {
			notification.getClaimInitialReporter().setFullIdNo();
			notification.setClaimStatus(ClaimStatus.INITIAL_INFORM);
			notificationService.updateLifeClaimNotification(notification);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.LIFE_ClAIM_NOTIFICATION_PROCESS_UPDATE);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, notification.getNotificationNo());
			result = "managelifeClaimNotification";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public String onFlowProcess(FlowEvent event) {
		PrimeFaces.current().resetInputs("lifeClaimNotificationForm");
		return event.getNewStep();
	}

	public void returnLifePolicyNo(SelectEvent event) {
		LifePolicySearch lifePolicySearch = (LifePolicySearch) event.getObject();
		notification.setLifePolicyNo(lifePolicySearch.getPolicyNo());
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(lifePolicySearch.getPolicyNo());
		insuredPersonList = lifePolicy.getPolicyInsuredPersonList();
		notification.setProduct(insuredPersonList.get(0).getProduct());
	}

	public void changeStateCode(AjaxBehaviorEvent e) {
		String provinceCode = (String) ((UIOutput) e.getSource()).getValue();
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(provinceCode);
	}

	public void changeIdType() {
		notification.getClaimInitialReporter().setIdConditionType(null);
		notification.getClaimInitialReporter().setProvinceCode(null);
		notification.getClaimInitialReporter().setTownshipCode(null);
		notification.getClaimInitialReporter().setIdNo(null);
		notification.getClaimInitialReporter().setFullIdNo(null);
	}

	public void selectPolicyInsuredPerson() {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(notification.getLifePolicyNo());
		selectPolicyInsuredPerson(lifePolicy);
	}

	public void setPolicyNoNull() {
		notification = new LifeClaimNotification();
	}

	public IdType[] getIdTypes() {
		return IdType.values();
	}

	public IdConditionType[] getIdConditionTypeSelectItemList() {
		return IdConditionType.values();
	}

	public void returnResidentTownship(SelectEvent event) {
		Township residentTownship = (Township) event.getObject();
		notification.getClaimInitialReporter().getResidentAddress().setTownship((residentTownship));
	}

	public void returnSalePoint(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		notification.setSalePoint(salesPoints);
	}

	public void returnPolicyInsuredPerson(SelectEvent event) {
		PolicyInsuredPerson policyInsuredPerson = (PolicyInsuredPerson) event.getObject();
		notification.setClaimPerson(policyInsuredPerson);
	}

	public void removeInsuredPerson() {
		notification.setClaimPerson(null);
	}

	public LifeClaimNotification getNotification() {
		return notification;
	}

	public void setNotification(LifeClaimNotification notification) {
		this.notification = notification;
	}

	public List<Product> getProductsList() {
		return productsList;
	}

	public List<PolicyInsuredPerson> getInsuredPersonList() {
		return insuredPersonList;
	}

	public List<LifeClaimNotification> getNotificationList() {
		return notificationList;
	}

	public List<String> getProvinceCodeList() {
		return provinceCodeList;
	}

	public List<String> getTownshipCodeList() {
		return townshipCodeList;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

}

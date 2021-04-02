/**
 * This class serves as the backing bean for submitting the sport man proposal (abroad).
 * 
 * @author YKKH
 * @version 1.0.0
 * @Date 2013/12/07
 */
package org.ace.insurance.web.manage.life.sportMan;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonAddon;
import org.ace.insurance.life.policy.PolicyInsuredPersonKeyFactorValue;
import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.product.PremiumCalData;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IPremiumCalculatorService;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Period;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "SportManAbroadProposalActionBean")
public class SportManAbroadProposalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{PremiumCalculatorService}")
	private IPremiumCalculatorService premiumCalculatorService;

	public void setPremiumCalculatorService(IPremiumCalculatorService premiumCalculatorService) {
		this.premiumCalculatorService = premiumCalculatorService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private String policyNo;
	private PolicyCriteria policyCriteria;
	private LifePolicy lifePolicy;
	private WorkFlowDTO workFlowDTO;
	private List<SportManTravelAbroad> sportManTravelList;
	private List<SportManTravelAbroad> selectedSportManList;
	private User responsiblePerson;
	private User user;
	private boolean isSubmit = true;
	List<Payment> paymentList;
	private boolean print;
	private List<Product> productsList;
	private Product product;
	private List<PolicyInsuredPersonAddon> personAddonList;
	private SportManTravelAbroad sportManAbroad;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		productsList = productService.findProductByInsuranceType(InsuranceType.SPORTMANABROAD);
		product = productsList.get(0);
		reset();
		selectedSportManList = new ArrayList<>();
	}

	public void reset() {
		policyCriteria = new PolicyCriteria();
		sportManTravelList = new ArrayList<>();
		lifePolicy = new LifePolicy();

		policyNo = "";
	}

	public void search() {
		String formId = "sportManAbroadForm";
		if (StringUtils.isBlank(policyNo)) {
			addErrorMessage(formId + ":lifePolicy", UIInput.REQUIRED_MESSAGE_ID);
		} else {
			sportManTravelList = new ArrayList<>();
			lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(policyNo);

			for (PolicyInsuredPerson insuredPerson : lifePolicy.getPolicyInsuredPersonList()) {
				SportManTravelAbroad sportManTravel = new SportManTravelAbroad();
				personAddonList = new ArrayList<PolicyInsuredPersonAddon>();
				PolicyInsuredPersonAddon abroadAddOn;
				for (AddOn addOn : product.getAddOnList()) {
					abroadAddOn = new PolicyInsuredPersonAddon(addOn);
					personAddonList.add(abroadAddOn);
				}
				sportManTravel.setPolicyInsuredPersonAddOnList(personAddonList);
				sportManTravel.setPolicyInsuredPerson(insuredPerson);
				sportManTravelList.add(sportManTravel);
			}
		}

	}

	private boolean validate(SportManTravelAbroad sportMan) {
		boolean valid = true;
		if (sportMan.getTravelStartDate() == null) {
			addErrorMessage("sportManAbroadForm:sportManAbroadTable", MessageId.START_DATE_REQUIRED);
			valid = false;
		}
		if (sportMan.getTravelEndDate() == null) {
			addErrorMessage("sportManAbroadForm:sportManAbroadTable", MessageId.END_DATE_REQUIRED);
			valid = false;
		}
		return valid;
	}

	public void calculateAdditionalPremium() {
		try {
			if (isNotEmptySportMan()) {
				double premium, addOnPremium = 0.00;
				List<PolicyInsuredPersonKeyFactorValue> keyfactorList = new ArrayList<>();

				for (SportManTravelAbroad sportMan : selectedSportManList) {
					if (validate(sportMan)) {
						sportMan.setProduct(product);
						int days = Utils.daysBetween(sportMan.getTravelStartDate(), sportMan.getTravelEndDate(), false, false);
						String value = Integer.toString(days);
						for (KeyFactor kf : sportMan.getProduct().getKeyFactorList()) {
							PolicyInsuredPersonKeyFactorValue ipkf = new PolicyInsuredPersonKeyFactorValue(kf);
							ipkf.setKeyFactor(kf);
							ipkf.setValue(value);
							keyfactorList.add(ipkf);
						}
						sportMan.setPolicyInsuredPersonkeyFactorValueList(keyfactorList);
						Map<KeyFactor, String> keyfatorValueMap1 = new HashMap<KeyFactor, String>();
						for (PolicyInsuredPersonKeyFactorValue ipkf : sportMan.getPolicyInsuredPersonkeyFactorValueList()) {
							keyfatorValueMap1.put(ipkf.getKeyFactor(), "1");
						}
						Map<KeyFactor, String> keyfatorValueMap2 = new HashMap<KeyFactor, String>();
						for (PolicyInsuredPersonKeyFactorValue ipkf : sportMan.getPolicyInsuredPersonkeyFactorValueList()) {
							keyfatorValueMap2.put(ipkf.getKeyFactor(), "8");
						}
						double premium1 = premiumCalculatorService.calculatePremium(keyfatorValueMap1, sportMan.getProduct(), new PremiumCalData(null, null, null, 1.0));
						double premium2 = premiumCalculatorService.calculatePremium(keyfatorValueMap2, sportMan.getProduct(), new PremiumCalData(null, null, null, 1.0));
						Period period = Utils.getPeriod(sportMan.getTravelStartDate(), sportMan.getTravelEndDate(), true, true);
						int day = period.getDays();
						int month = period.getMonths();
						if (month == 0) {
							if (day <= 7) {
								premium = premium1;
							} else {
								premium = premium2;
							}
						} else {
							premium = premium2 * month;
							if (day > 1 && day <= 7) {
								premium = premium + premium1;
							} else if (day > 7) {
								premium = premium + premium2;
							}
						}
						sportMan.setPremium(premium);
						for (PolicyInsuredPersonAddon insuredPersonAddOn : sportMan.getPolicyInsuredPersonAddOnList()) {
							Map<KeyFactor, String> addOnKeyfatorValueMap = new HashMap<KeyFactor, String>();
							if (insuredPersonAddOn.isInclude()) {
								addOnPremium = premiumCalculatorService.calculatePremium(addOnKeyfatorValueMap, insuredPersonAddOn.getAddOn(),
										new PremiumCalData(null, null, premium, null));
								insuredPersonAddOn.setPremium(addOnPremium);
							} else {
								insuredPersonAddOn.setPremium(0.0);
							}
						}
						isSubmit = false;
					}
				}
			}
		} catch (SystemException ex) {
			addDirectErrorMessage("sportManAbroadForm:sportManAbroadTable", generateMessage(ex));
		}

	}

	private boolean isNotEmptySportMan() {
		if (selectedSportManList.isEmpty()) {
			addErrorMessage("sportManAbroadForm:sportManAbroadTable", MessageId.EMPTY_SPORTMANLIST);
			return false;
		} else
			return true;

	}

	public void changePremiumCalculation() {
		isSubmit = true;
	}

	private void updateAddOnList() {
		for (SportManTravelAbroad sportMan : selectedSportManList) {
			List<PolicyInsuredPersonAddon> removeaddOnList = new ArrayList<PolicyInsuredPersonAddon>();
			for (PolicyInsuredPersonAddon addOn : sportMan.getPolicyInsuredPersonAddOnList()) {
				if (!addOn.isInclude()) {
					removeaddOnList.add(addOn);
				}
			}
			if (!removeaddOnList.isEmpty()) {
				sportMan.getPolicyInsuredPersonAddOnList().removeAll(removeaddOnList);
			}
		}
	}

	public void addNewSportManTravelAbroad() {
		try {
			if (isNotEmptySportMan()) {
				updateAddOnList();
				workFlowDTO = new WorkFlowDTO(null, lifePolicy.getBranch().getId(), null, WorkflowTask.PAYMENT, ReferenceType.SPORT_MAN_ABROAD, TransactionType.UNDERWRITING, user,
						responsiblePerson);
				paymentList = lifePolicyService.addNewSportManTravelAbroad(selectedSportManList, workFlowDTO);
				print = true;
				isSubmit = true;
				addInfoMessage(null, MessageId.SUCCESS_SPORTMANTRAVELABROAD_PROCESS, lifePolicy.getPolicyNo());
			}
			// reset();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void searchPolicyCriteria() {
		if (inputCheck(policyCriteria)) {
			policyCriteria.setProduct("SPORTMAN");
		}
	}

	private boolean inputCheck(PolicyCriteria policyCriteria) {
		boolean result = true;
		if ((policyCriteria.getPolicyCriteria() == null) || (policyCriteria.getPolicyCriteria().toString().equals("Select"))) {
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Please select policy criteria.", "Please select policy criteria"));
			result = false;
		} else if (StringUtils.isBlank(policyCriteria.getCriteriaValue())) {
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Criteria value is required.", "Criteria value is required."));
			result = false;
		}
		return result;
	}

	public void selectSportManLifePolicyNo() {
		selectSportManLifePolicyNo("SportMan");
	}

	/** getter/setter */

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public PolicyCriteria getPolicyCriteria() {
		return policyCriteria;
	}

	public void setPolicyCriteria(PolicyCriteria policyCriteria) {
		this.policyCriteria = policyCriteria;
	}

	public boolean getIsSubmit() {
		return isSubmit;
	}

	public List<SportManTravelAbroad> getSportManTravelList() {
		return sportManTravelList;
	}

	public void setSportManTravelList(List<SportManTravelAbroad> sportManTravelList) {
		this.sportManTravelList = sportManTravelList;
	}

	public List<SportManTravelAbroad> getSelectedSportManList() {
		return selectedSportManList;
	}

	public void setSelectedSportManList(List<SportManTravelAbroad> selectedSportManList) {
		this.selectedSportManList = selectedSportManList;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	/** Common methods end */

	public void selectUser() {
		selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE, TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}

	public void returnLifePolicy(SelectEvent event) {
		LifePolicySearch lifePolicySearch = (LifePolicySearch) event.getObject();
		lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(lifePolicySearch.getPolicyNo());
		this.policyNo = lifePolicy.getPolicyNo();
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		setResponsiblePerson(user);
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	/** Generate Report */
	String fileName = "SportManAbroadLetters.pdf";
	private final String reportName = "SportManAbroad";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateInvoiceAndCertificate() {
		DocumentBuilder.generateSportManAboradCertificatAndInvoice(selectedSportManList, paymentList.get(0), dirPath, fileName);
	}

	public SportManTravelAbroad getSportManAbroad() {
		return sportManAbroad;
	}

	public void setSportManAbroad(SportManTravelAbroad sportManAbroad) {
		this.sportManAbroad = sportManAbroad;
	}

}

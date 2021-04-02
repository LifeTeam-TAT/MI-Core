package org.ace.java.web.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.MonthNames;
import org.ace.insurance.common.PeriodType;
import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.common.ProposalStatus;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.SchoolLevelType;
import org.ace.insurance.common.SchoolType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.UploadFileConfig;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.common.interfaces.IProposal;
import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.product.PremiumRateType;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductBaseType;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.surveyTeam.SurveyTeam;
import org.ace.insurance.user.AuthorityPermission;
import org.ace.insurance.web.Param;
import org.ace.insurance.web.common.ErrorMessage;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.common.WebUtils;
import org.ace.java.component.SystemException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.PrimeFaces;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseBean {

	private static final String THEME = "theme";
	private static final String DASHBOARD_URL = "http://localhost:8080/efilplife/view/dashboard.xhtml";

	protected static <T> String getProposalUserType(T proposal) {
		IProposal iProposal = null;
		if (proposal instanceof LifeProposal) {
			iProposal = (LifeProposal) proposal;
		}
		return iProposal.getUserType();
	}

	protected static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ServletContext getServletContext() {
		return (ServletContext) getFacesContext().getExternalContext().getContext();
	}

	protected static Application getApplication() {
		return getFacesContext().getApplication();
	}

	protected Object getSpringBean(String beanName) {
		return WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean(beanName);
	}

	protected Map getApplicationMap() {
		return getFacesContext().getExternalContext().getApplicationMap();
	}

	protected Map<String, Object> getSessionMap() {
		return getFacesContext().getExternalContext().getSessionMap();
	}

	protected void putParam(String key, Object obj) {
		getSessionMap().put(key, obj);
	}

	protected Object getParam(String key) {
		return getSessionMap().get(key);
	}

	public EnumSet<SchoolType> getSchoolTypes() {
		return EnumSet.allOf(SchoolType.class);
	}

	public EnumSet<SchoolLevelType> getSchoolLevelTypes() {
		return EnumSet.allOf(SchoolLevelType.class);
	}

	protected boolean isExistParam(String key) {
		return getSessionMap().containsKey(key);
	}

	protected void removeParam(String key) {
		if (isExistParam(key)) {
			getSessionMap().remove(key);
		}
	}

	protected void addErrorMessage(ErrorMessage errorMessage) {
		String message = getMessage(errorMessage.getErrorcode(), errorMessage.getParams());
		getFacesContext().addMessage(errorMessage.getId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	protected static ResourceBundle getBundle() {
		return ResourceBundle.getBundle(getApplication().getMessageBundle(), getFacesContext().getViewRoot().getLocale());
	}

	protected String getWebRootPath() {
		Object context = getFacesContext().getExternalContext().getContext();
		String systemPath = ((ServletContext) context).getRealPath("/");
		return systemPath;
	}

	protected String getFullTempDirPath() {
		return getWebRootPath() + Constants.TEMP_DIR;
	}

	protected String getFullUploadDirPath() {
		return getWebRootPath() + Constants.UPLOAD_DIR;
	}

	protected String getFullReportDirPath() {
		return getWebRootPath() + Constants.REPORT_DIR;
	}

	public static String getMessage(String errorCode, Object... params) {
		String text = null;
		try {
			text = getBundle().getString(errorCode);
		} catch (MissingResourceException e) {
			text = "!! key " + errorCode + " not found !!";
		}
		if (params != null) {
			MessageFormat mf = new MessageFormat(text);
			text = mf.format(text, params).toString();
		}
		return text;
	}

	// This method is only for addOn in jsfPages
	public boolean isNextLine(int i) {
		return i % 2 == 0 ? true : false;
	}

	protected void addWranningMessage(String id, String errorCode, Object... params) {
		String message = getMessage(errorCode, params);
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_WARN, message, ""));
	}

	protected void addInfoMessage(String id, String errorCode, Object... params) {
		String message = getMessage(errorCode, params);
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
	}

	protected void addErrorMessage(String id, String errorCode, Object... params) {
		String message = getMessage(errorCode, params);
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	protected void addDirectErrorMessage(String id, String message) {
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	protected void addErrorMessage(String message) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
	}

	protected void addInfoMessage(String message) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
	}

	protected void addWranningMessage(String message) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, ""));
	}

	protected void handelSysException(SystemException systemException) {
		systemException.printStackTrace();
		FacesMessage facesMessage = resolveExcption(systemException);
		getFacesContext().addMessage(null, facesMessage);
	}

	protected void handelException(Exception exception) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, exception.getMessage(), "");
		getFacesContext().addMessage(null, facesMessage);
	}

	@SuppressWarnings("unchecked")
	protected FacesMessage resolveExcption(SystemException systemException) {
		String errorCode = systemException.getErrorCode();
		if (ErrorCode.NO_PREMIUM_RATE.equals(errorCode)) {
			String source = (String) systemException.getSource();
			String paramMessage = source != null ? source + " : " : "";
			Map<KeyFactor, String> keyfatorValueMap = (Map<KeyFactor, String>) systemException.getResponse();
			int count = keyfatorValueMap.size();
			for (KeyFactor kf : keyfatorValueMap.keySet()) {
				count--;
				if (count == 0) {
					paramMessage = paramMessage + kf.getValue() + " - " + keyfatorValueMap.get(kf);
				} else {
					paramMessage = paramMessage + kf.getValue() + " - " + keyfatorValueMap.get(kf) + ", ";
				}
			}
			return new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage(errorCode, paramMessage), "");
		}
		if (errorCode != null) {
			return new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage(errorCode), systemException.getMessage());
		} else {
			return new FacesMessage(FacesMessage.SEVERITY_INFO, systemException.getMessage(), "");
		}
	}

	protected String generateMessage(SystemException systemException) {
		String errorCode = systemException.getErrorCode();
		if (ErrorCode.NO_PREMIUM_RATE.equals(errorCode)) {
			String source = (String) systemException.getSource();
			String paramMessage = source != null ? source + " : " : "";
			Map<KeyFactor, String> keyfatorValueMap = (Map<KeyFactor, String>) systemException.getResponse();
			int count = keyfatorValueMap.size();
			for (KeyFactor kf : keyfatorValueMap.keySet()) {
				count--;
				if (count == 0) {
					paramMessage = paramMessage + kf.getValue() + " - " + keyfatorValueMap.get(kf);
				} else {
					paramMessage = paramMessage + kf.getValue() + " - " + keyfatorValueMap.get(kf) + ", ";
				}
			}
			return getMessage(errorCode, paramMessage);
		}
		if (errorCode != null) {
			return getMessage(errorCode) + systemException.getMessage();
		} else {
			return systemException.getMessage();
		}
	}

	/* Premium Recalculation config */
	protected final String PROPOSAL = "PROPOSAL";
	protected final String INFORM = "INFORM";
	protected final String CONFIRMATION = "CONFIRMATION";
	protected final String PAYMENT = "PAYMENT";
	private static Properties themeConfig;

	/* Dialog Selection */
	public void selectAgent() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 520);
		dialogOptions.put("contentWidth", 950);
		PrimeFaces.current().dialog().openDynamic("agentDialog", dialogOptions, null);
	}

	public void selectCargoType() {
		PrimeFaces.current().dialog().openDynamic("cargoTypeDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCargoName() {
		PrimeFaces.current().dialog().openDynamic("cargoNameDialog", WebUtils.getDialogOption(), null);
	}

	public void selectRoute() {
		PrimeFaces.current().dialog().openDynamic("routeDialog", WebUtils.getDialogOption(), null);
	}

	public void selectPort() {
		PrimeFaces.current().dialog().openDynamic("portDialog", WebUtils.getDialogOption(), null);
	}

	public void selectRole() {
		PrimeFaces.current().dialog().openDynamic("roleDialog", WebUtils.getDialogOption(), null);
	}

	protected void selectUser(WorkflowTask workflowTask, WorkFlowType workFlowType, TransactionType transactionType, String branchId, String accessBranchId) {
		putParam("workflowTask", workflowTask);
		putParam("workFlowType", workFlowType);
		putParam("transactionType", transactionType);
		putParam("branchId", branchId);
		putParam("accessBranchId", accessBranchId);
		PrimeFaces.current().dialog().openDynamic("userDialog", WebUtils.getDialogOption(), null);
	}

	protected void selectUser(WorkflowTask workflowTask, WorkFlowType workFlowType) {
		putParam("WORKFLOWTASK", workflowTask);
		putParam("WORKFLOWTYPE", workFlowType);
		PrimeFaces.current().dialog().openDynamic("userDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCashInTransitPolicyNo() {
		PrimeFaces.current().dialog().openDynamic("cashInTransitPolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void selectOrganization() {
		PrimeFaces.current().dialog().openDynamic("organizationDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCustomer() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 900);
		PrimeFaces.current().dialog().openDynamic("customerDialog", dialogOptions, null);
	}

	public void selectExpress() {
		PrimeFaces.current().dialog().openDynamic("expressDialog", WebUtils.getDialogOption(), null);
	}

	public void selectTownship() {
		PrimeFaces.current().dialog().openDynamic("townshipDialog", WebUtils.getDialogOption(), null);
	}

	public void selectBranch() {
		PrimeFaces.current().dialog().openDynamic("branchDialog", WebUtils.getDialogOption(), null);
	}

	public void selectSalesPoints() {
		PrimeFaces.current().dialog().openDynamic("salesPointsDialog", WebUtils.getDialogOption(), null);
	}

	public void selectClassOfInsurance() {
		PrimeFaces.current().dialog().openDynamic("classOfInsuranceDialog", WebUtils.getDialogOption(), null);
	}

	public void selectICD10() {
		PrimeFaces.current().dialog().openDynamic("ICD10Dialog", WebUtils.getDialogOption(), null);
	}

	public void selectCurrency() {
		PrimeFaces.current().dialog().openDynamic("currencyDialog", WebUtils.getDialogOption(), null);
	}

	public void selectProductContent() {
		PrimeFaces.current().dialog().openDynamic("productContentDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCoa() {
		PrimeFaces.current().dialog().openDynamic("coaDialog", WebUtils.getDialogOption(), null);
	}

	public void selectQualification() {
		PrimeFaces.current().dialog().openDynamic("qualificationDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCompany() {
		PrimeFaces.current().dialog().openDynamic("companyDialog", WebUtils.getDialogOption(), null);
	}

	public void selectTypesOfSport() {
		PrimeFaces.current().dialog().openDynamic("typesOfSportDialog", WebUtils.getDialogOption(), null);
	}

	public void selectIndustry() {
		PrimeFaces.current().dialog().openDynamic("industryDialog", WebUtils.getDialogOption(), null);
	}

	public void selectProduct(InsuranceType insuranceType) {
		putParam("INSURANCETYPE", insuranceType);
		PrimeFaces.current().dialog().openDynamic("productDialog", WebUtils.getDialogOption(), null);
	}

	public void selectProcess() {
		PrimeFaces.current().dialog().openDynamic("processDialog", WebUtils.getDialogOption(), null);
	}

	public void selectProduct() {
		PrimeFaces.current().dialog().openDynamic("productDialog", WebUtils.getDialogOption(), null);
	}

	public void selectProductGroup() {
		PrimeFaces.current().dialog().openDynamic("productGroupDialog", WebUtils.getDialogOption(), null);
	}

	public void selectProductGroup(ProductGroupType productGroupType) {
		putParam("PRODUCTGROUPTYPE", productGroupType);
		PrimeFaces.current().dialog().openDynamic("productGroupDialog", WebUtils.getDialogOption(), null);
	}

	public void selectProvince() {
		PrimeFaces.current().dialog().openDynamic("provinceDialog", WebUtils.getDialogOption(), null);
	}

	public void selectDistrict() {
		PrimeFaces.current().dialog().openDynamic("districtDialog", WebUtils.getDialogOption(), null);
	}

	public void selectRelationShip() {
		PrimeFaces.current().dialog().openDynamic("relationShipDialog", WebUtils.getDialogOption(), null);
	}

	public void selectSchool() {
		PrimeFaces.current().dialog().openDynamic("schoolDialog", WebUtils.getDialogOption(), null);
	}

	public void selectReligion() {
		PrimeFaces.current().dialog().openDynamic("selectReligion", WebUtils.getDialogOption(), null);
	}

	public void selectWorkShop() {
		PrimeFaces.current().dialog().openDynamic("workShopDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCashier() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 1000);
		PrimeFaces.current().dialog().openDynamic("cashierDialog", dialogOptions, null);
	}

	public void selectPolicyInsuredPerson(LifePolicy lifePolicy) {
		putParam("LIFEPOLICY", lifePolicy);
		PrimeFaces.current().dialog().openDynamic("policyInsuredPersonDialog", WebUtils.getDialogOption(), null);
	}

	// To FIXME by THK
	public void selectFirePolicy() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 1350);
		PrimeFaces.current().dialog().openDynamic("firePolicyDialog", dialogOptions, null);
	}

	public void selectPolicy() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 800);
		PrimeFaces.current().dialog().openDynamic("selectPolicyDialog", dialogOptions, null);
	}

	public void selectTypeOfBody() {
		PrimeFaces.current().dialog().openDynamic("typeOfBodyDialog", WebUtils.getDialogOption(), null);
	}

	public void selectManufacture() {
		PrimeFaces.current().dialog().openDynamic("manufactureDialog", WebUtils.getDialogOption(), null);
	}

	public void selectFloor() {
		PrimeFaces.current().dialog().openDynamic("floorDialog", WebUtils.getDialogOption(), null);
	}

	public void selectWall() {
		PrimeFaces.current().dialog().openDynamic("wallDialog", WebUtils.getDialogOption(), null);
	}

	public void selectRoof() {
		PrimeFaces.current().dialog().openDynamic("roofDialog", WebUtils.getDialogOption(), null);
	}

	public void selectNatureOfBusiness() {
		PrimeFaces.current().dialog().openDynamic("natureOfBusinessDialog", WebUtils.getDialogOption(), null);
	}

	public void selectSurveyTeam(List<SurveyTeam> surveyTeamList) {
		putParam("SURVEYTEAMLIST", surveyTeamList);
		PrimeFaces.current().dialog().openDynamic("surveyTeamDialog", WebUtils.getDialogOption(), null);
	}

	public void selectAllSurveyTeam() {
		PrimeFaces.current().dialog().openDynamic("surveyTeamDialog", WebUtils.getDialogOption(), null);
	}

	public void selectAccountBank() {
		putParam("IS_ACC_CLEARING", true);
		PrimeFaces.current().dialog().openDynamic("bankDialog", WebUtils.getDialogOption(), null);
	}

	public void selectBank() {
		putParam("IS_ACC_CLEARING", false);
		PrimeFaces.current().dialog().openDynamic("bankDialog", WebUtils.getDialogOption(), null);
	}

	public void selectBankBranch() {
		PrimeFaces.current().dialog().openDynamic("bankBranchDialog", WebUtils.getDialogOption(), null);
	}

	public void selectAfpBankBranch(String productGroupId) {
		putParam("productGroupId", productGroupId);
		PrimeFaces.current().dialog().openDynamic("afpBankBranchDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCity() {
		PrimeFaces.current().dialog().openDynamic("cityDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCountry() {
		PrimeFaces.current().dialog().openDynamic("countryDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCoInsuranceCompany() {
		PrimeFaces.current().dialog().openDynamic("coInsuranceCompanyDialog", WebUtils.getDialogOption(), null);
	}

	public void selectMultiCoinsuranceCompany() {
		PrimeFaces.current().dialog().openDynamic("multiCoInsuranceCompanyDialog", WebUtils.getDialogOption(), null);
	}

	public void selectPaymentType() {
		PrimeFaces.current().dialog().openDynamic("paymentTypeDialog", WebUtils.getDialogOption(), null);
	}

	public void selectPaymentType(Product product) {
		putParam("PRODUCT", product);
		PrimeFaces.current().dialog().openDynamic("paymentTypeDialog", WebUtils.getDialogOption(), null);
	}

	public void selectClaimType() {
		PrimeFaces.current().dialog().openDynamic("lifeDisabilityClaimTypeDialog", WebUtils.getDialogOption(), null);
	}

	public void selectAddOn(Product product) {
		putParam("PRODUCT", product);
		PrimeFaces.current().dialog().openDynamic("addOnDialog", WebUtils.getDialogOption(), null);
	}

	public void selectAddOn() {
		PrimeFaces.current().dialog().openDynamic("addOnDialog", WebUtils.getDialogOption(), null);
	}

	public void selectOccurrence() {
		PrimeFaces.current().dialog().openDynamic("occurrenceDialog", WebUtils.getDialogOption(), null);
	}

	public void selectBuildingOccupation() {
		PrimeFaces.current().dialog().openDynamic("natureOfBusinessDialog", WebUtils.getDialogOption(), null);
	}

	public void selectBuildingClass() {
		PrimeFaces.current().dialog().openDynamic("buildingClassDialog", WebUtils.getDialogOption(), null);
	}

	public void selectOccupation() {
		PrimeFaces.current().dialog().openDynamic("occupationDialog", WebUtils.getDialogOption(), null);
	}

	public void selectRiskyOccupation() {
		PrimeFaces.current().dialog().openDynamic("riskyOccupationDialog", WebUtils.getDialogOption(), null);
	}

	public void selectHospital() {
		PrimeFaces.current().dialog().openDynamic("hospitalDialog", WebUtils.getDialogOption(), null);
	}

	public void selectVehiclePart() {
		PrimeFaces.current().dialog().openDynamic("vehiclePartDialog", WebUtils.getDialogOption(), null);
	}

	public void selectLifePolicyNo() {
		PrimeFaces.current().dialog().openDynamic("lifePolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void selectLifePolicyNo(String status) {
		putParam("Actived", status);
		PrimeFaces.current().dialog().openDynamic("lifePolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void selectSportManLifePolicyNo(String status) {
		putParam("SportMan", status);
		PrimeFaces.current().dialog().openDynamic("lifePolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void selectNonFullPaidDisabilityLifePolicyNo(String claim) {
		putParam("Claim", claim);
		PrimeFaces.current().dialog().openDynamic("lifePolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	// TO FIXME by thk
	public void selectFirePolicyNo() {
		PrimeFaces.current().dialog().openDynamic("firePolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void openLifeClaimInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("lifeClaimInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	public void selectDeclarationPolicyNo() {
		PrimeFaces.current().dialog().openDynamic("declarationPolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void selectSnakeBitePolicyNo() {
		PrimeFaces.current().dialog().openDynamic("snakeBitePolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void selectCashInSafePolicyNo() {
		PrimeFaces.current().dialog().openDynamic("cashInSafePolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	// select disability part
	public void selectDisabilityPart() {
		PrimeFaces.current().dialog().openDynamic("disabilityPartDialog", WebUtils.getDialogOption(), null);
	}

	public void selectMotorPolicyNo() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 800);
		PrimeFaces.current().dialog().openDynamic("motorPolicyNoDialog", dialogOptions, null);
	}

	public void selectClaimNo() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 800);
		PrimeFaces.current().dialog().openDynamic("claimNoDialog", dialogOptions, null);
	}

	public void selectFireClaimNo() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 800);
		PrimeFaces.current().dialog().openDynamic("fireClaimNoDialog", dialogOptions, null);
	}

	public void selectCargoPolicyNo() {
		Map<String, Object> dialogOptions = new HashMap<String, Object>();
		dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 800);
		PrimeFaces.current().dialog().openDynamic("cargoPolicyNoDialog", dialogOptions, null);
	}

	protected boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if (value.toString().isEmpty()) {
			return true;
		}
		return false;
	}

	protected static String getPrimeTheme() {
		if (themeConfig == null) {
			themeConfig = new Properties();
			try {
				themeConfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("theme-config.properties"));
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load \"theme-config\"", e);
			}
		}
		return themeConfig.getProperty(THEME).toString();
	}

	private static final String ALGORITHM = "Blowfish";
	private static String keyString = "secretTest";

	public static void encrypt(File inputFile, File outputFile, OutputStream op) throws Exception {
		doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile, op);
		System.out.println("File encrypted successfully!");
	}

	public static void decrypt(File inputFile, File outputFile) throws Exception {
		OutputStream op = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		};
		doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile, op);
		System.out.println("File decrypted successfully!");
	}

	private static void doCrypto(int cipherMode, File inputFile, File outputFile, OutputStream op) throws Exception {

		Key secretKey = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(cipherMode, secretKey);

		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] inputBytes = new byte[(int) inputFile.length()];
		inputStream.read(inputBytes);

		byte[] outputBytes = cipher.doFinal(inputBytes);

		FileOutputStream outputStream = new FileOutputStream(outputFile);
		op.write(outputBytes);
		outputStream.write(outputBytes);
		inputStream.close();
	}

	public void selectStateCode() {
		PrimeFaces.current().dialog().openDynamic("stateCodeDialog", WebUtils.getDialogOption(), null);
	}

	public void selectMedicalPolicyNo() {
		PrimeFaces.current().dialog().openDynamic("medicalPolicyNoDialog", WebUtils.getDialogOption(), null);
	}

	public void selectMedicalPlace() {
		PrimeFaces.current().dialog().openDynamic("medicalPlaceDialog", WebUtils.getDialogOption(), null);
	}

	public void selectOperation() {
		PrimeFaces.current().dialog().openDynamic("operationDialog", WebUtils.getDialogOption(), null);
	}

	public void createFile(File file, byte[] content) {
		try {
			/* At First : Create directory of target file */
			String filePath = file.getPath();
			int lastIndex = filePath.lastIndexOf("\\") + 1;
			FileUtils.forceMkdir(new File(filePath.substring(0, lastIndex)));

			/* Create target file */
			FileOutputStream outputStream = new FileOutputStream(file);
			IOUtils.write(content, outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSystemPath() {
		Object context = getFacesContext().getExternalContext().getContext();
		String systemPath = ((ServletContext) context).getRealPath("/");
		return systemPath;
	}

	public String getUploadPath() {
		return UploadFileConfig.getUploadFilePathHome();
	}

	public String getFileName(String filePath) {
		int lastIndex = filePath.lastIndexOf("/") + 1;
		return filePath.substring(lastIndex, filePath.length());
	}

	public void openMedicalProposalInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("medicalProposalInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	public void openLifeProposalInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("lifeProposalInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	public void openLifePolicyInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("lifePolicyInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	public void SelectNCBDialog() {
		PrimeFaces.current().dialog().openDynamic("NCBDialog", WebUtils.getTemplateDialogOption(), null);
	}

	public void openSportManAbroadInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("sportManAbroadInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	///// Student life
	public void openStudentLifeProposalInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("studentLifeProposalInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	public void openStudentLifeInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("studentLifeInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	public void openStudentLifePolicyInfoTemplate() {
		PrimeFaces.current().dialog().openDynamic("studentLifePolicyInfoTemplate", WebUtils.getTemplateDialogOption(), null);
	}

	public EnumSet<PaymentChannel> getPaymentChannels() {
		return EnumSet.allOf(PaymentChannel.class);
	}

	public EnumSet<MonthNames> getMonthSet() {
		return EnumSet.allOf(MonthNames.class);
	}

	public EnumSet<PremiumRateType> getAllPremiumRateTypes() {
		return EnumSet.allOf(PremiumRateType.class);
	}

	public EnumSet<ProductBaseType> getProductBaseTypes() {
		return EnumSet.allOf(ProductBaseType.class);
	}

	public EnumSet<PremiumRateType> getPremiumRateTypes() {
		return EnumSet.allOf(PremiumRateType.class);
	}

	public EnumSet<InsuranceType> getAllInsuranceTypes() {
		return EnumSet.allOf(InsuranceType.class);
	}

	public EnumSet<PeriodType> getPeriodTypes() {
		return EnumSet.allOf(PeriodType.class);
	}

	public EnumSet<SaleChannelType> getSaleChannelTypes() {
		return EnumSet.allOf(SaleChannelType.class);
	}

	public List<Integer> getYearList() {
		List<Integer> years = new ArrayList<Integer>();
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1999; startYear <= endYear; startYear++) {
			years.add(startYear);
		}
		Collections.reverse(years);
		return years;
	}

	public void redirectDashboardPage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		try {
			externalContext.redirect(DASHBOARD_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ReferenceType> getLifeReferenceTypes() {
		return Arrays.asList(new ReferenceType[] { ReferenceType.ENDOWMENT_LIFE, ReferenceType.SHORT_ENDOWMENT_LIFE, ReferenceType.GROUP_LIFE, ReferenceType.FARMER,
				ReferenceType.PA, ReferenceType.SNAKE_BITE, ReferenceType.SPORT_MAN, ReferenceType.STUDENT_LIFE, ReferenceType.PUBLIC_TERM_LIFE });
	}

	public List<ReferenceType> getHealthReferenceTypes() {
		return Arrays.asList(new ReferenceType[] { ReferenceType.HEALTH, ReferenceType.CRITICAL_ILLNESS, ReferenceType.MICRO_HEALTH });
	}

	public List<String> getProductByReferenceType(ReferenceType referenceType) {
		List<String> productList = new ArrayList<>();
		if (ReferenceType.ENDOWMENT_LIFE.equals(referenceType)) {
			productList.add(KeyFactorChecker.getPublicLifeId());
		} else if (ReferenceType.SHORT_ENDOWMENT_LIFE.equals(referenceType)) {
			productList.add(KeyFactorChecker.getShortTermEndowmentId());
		} else if (ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE.equals(referenceType)) {
			productList.add(KeyFactorChecker.getGovernmentShortTermEndowmentId());
		} else if (ReferenceType.STUDENT_LIFE.equals(referenceType)) {
			productList.add(KeyFactorChecker.getStudentLifeID());
		} else if (ReferenceType.PUBLIC_TERM_LIFE.equals(referenceType)) {
			productList.add(KeyFactorChecker.getPublicTermLifeId());
		} else if (ReferenceType.HEALTH.equals(referenceType)) {
			productList.add(KeyFactorChecker.getIndividualHealththId());
			productList.add(KeyFactorChecker.getGroupHealththId());
		} else if (ReferenceType.CRITICAL_ILLNESS.equals(referenceType)) {
			productList.add(KeyFactorChecker.getIndividualCriticalIllnessId());
			productList.add(KeyFactorChecker.getGroupCriticalIllnessId());
		} else if (ReferenceType.MICRO_HEALTH.equals(referenceType)) {
			productList.add(KeyFactorChecker.getMicroHealthId());
		}
		return productList;
	}

	public EnumSet<ProposalStatus> getProposalStatus() {
		return EnumSet.allOf(ProposalStatus.class);
	}

	public double getAuthorityAmount(List<AuthorityPermission> authorityPermissionList, String productId, TransactionType trsType) {
		double userAuthorityAmount = authorityPermissionList.stream().filter(a -> a.getProductCode().trim().equals(productId.trim()) && a.getTransactionType() == trsType)
				.mapToDouble(AuthorityPermission::getAmount).sum();
		return userAuthorityAmount;
	}

	public void selectDisabilityPart(List<DisabilityPart> partList) {
		putParam(Param.DISABILITY_PART_LIST, partList);
		PrimeFaces.current().dialog().openDynamic("disabilityPartDialog", WebUtils.getDialogOption(), null);
	}

	public void selectDisabilityPartRate(String productId, List<ProductDisabilityRate> rateList) {
		putParam("produtId", productId);
		putParam(Param.PRODUCT_DISABILITY_RATE, rateList);
		PrimeFaces.current().dialog().openDynamic("productDisabiltyPartRate", WebUtils.getDialogOption(), null);
	}

}

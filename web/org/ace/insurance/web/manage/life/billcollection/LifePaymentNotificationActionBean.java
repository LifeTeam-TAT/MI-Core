package org.ace.insurance.web.manage.life.billcollection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.MonthType;
import org.ace.insurance.common.NotificationCriteria;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

/**
 * Life Payment Notification ActionBean
 * 
 * @author
 * @since 1.0.0
 * @date 2013/09/19
 */
@ViewScoped
@ManagedBean(name = "LifePaymentNotificationActionBean")
public class LifePaymentNotificationActionBean<T extends IDataModel> extends BaseBean {
	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	private User user;
	private List<LifePolicyNotificationDTO> lifePolicieList;
	private NotificationCriteria criteria;
	private boolean showDayBetween;
	private boolean showMonthly;
	private boolean showLifePolicy;
	private LifePolicyNotificationDTO[] selectedNotis;
	private List<String> productList;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		showLifePolicy = false;
		reset();
	}

	public NotificationCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(NotificationCriteria criteria) {
		this.criteria = criteria;
	}

	public void search() {
		productList = null;
		productList = getProductByReferenceType(criteria.getProduct());
		lifePolicieList = lifePolicyService.findNotificationLifePolicy(criteria, productList);
	}

	public void reset() {
		showDayBetween = false;
		showMonthly = true;
		criteria = new NotificationCriteria();
		criteria.setYear(Calendar.getInstance().get(Calendar.YEAR));
		criteria.setProduct(ReferenceType.ENDOWMENT_LIFE);
		criteria.setReportType("Monthly");
		criteria.setMonth(Utils.getMonthType(Calendar.getInstance().get(Calendar.MONTH) + 1));
		productList = getProductByReferenceType(criteria.getProduct());
	}

	public List<Integer> getYears() {
		List<Integer> years = new ArrayList<Integer>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 0; i < 100; i++) {
			years.add(year - i);
		}
		return years;
	}

	public MonthType[] getMonthSelectItemList() {
		return MonthType.values();
	}

	public List<String> getReportTypeList() {
		List<String> result = new ArrayList<String>();
		result.add("Days Between");
		result.add("Monthly");
		return result;
	}

	public ReferenceType[] getLifeBillCollProductList() {
		ReferenceType[] types = { ReferenceType.ENDOWMENT_LIFE, ReferenceType.SHORT_ENDOWMENT_LIFE, ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE,
				ReferenceType.STUDENT_LIFE, ReferenceType.HEALTH, ReferenceType.CRITICAL_ILLNESS };
		return types;
	}

	public void criteriaChangeEvent(AjaxBehaviorEvent event) {
		showDayBetween = false;
		showMonthly = false;
		if (criteria.getReportType().equalsIgnoreCase("Days Between")) {
			showDayBetween = true;
		}
		if (criteria.getReportType().equalsIgnoreCase("Monthly")) {
			showMonthly = true;
		}
	}

	public boolean isShowDayBetween() {
		return showDayBetween;
	}

	public void setShowDayBetween(boolean showDayBetween) {
		this.showDayBetween = showDayBetween;
	}

	public boolean isShowMonthly() {
		return showMonthly;
	}

	public void setShowMonthly(boolean showMonthly) {
		this.showMonthly = showMonthly;
	}

	public boolean isShowLifePolicy() {
		return showLifePolicy;
	}

	public void setShowLifePolicy(boolean showLifePolicy) {
		this.showLifePolicy = showLifePolicy;
	}

	private String reportName = null;
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private String fileName = null;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		if (selectedNotis.length == 0) {
			addErrorMessage("paymentNotificationForm:lifePolicyInfoTable", MessageId.ATLEAST_ONE_CHECK_REQUIRED);
			return;
		} else {
			if (ReferenceType.ENDOWMENT_LIFE.equals(criteria.getProduct()) || ReferenceType.SHORT_ENDOWMENT_LIFE.equals(criteria.getProduct())
					|| ReferenceType.STUDENT_LIFE.equals(criteria.getProduct()) || ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE.equals(criteria.getProduct())) {
				reportName = "LifePolicyNotificationLetter";
				fileName = reportName + ".pdf";
				if (selectedNotis.length > 0) {
					List<LifePolicy> policies = new ArrayList<LifePolicy>();
					for (LifePolicyNotificationDTO dto : selectedNotis) {
						LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(dto.getPolicyNo());
						policies.add(lifePolicy);
					}
					DocumentBuilder.generateLifePolicyNotificationLetters(policies, dirPath, fileName);
				}
			} else {
				if (selectedNotis.length > 0) {
					reportName = "MedicalNotificationLetter";
					fileName = reportName + ".pdf";
					List<MedicalPolicy> policies = new ArrayList<MedicalPolicy>();
					for (LifePolicyNotificationDTO dto : selectedNotis) {
						MedicalPolicy medicalPolicy = medicalPolicyService.findMedicalPolicyByPolicyNo(dto.getPolicyNo());
						policies.add(medicalPolicy);
					}
					DocumentBuilder.generateMedicalPolicyNotificationLetters(policies, dirPath, fileName, criteria.getProduct());
				}
			}
		}
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		criteria.setSalePoint(salesPoints);
	}

	public LifePolicyNotificationDTO[] getSelectedNotis() {
		return selectedNotis;
	}

	public void setSelectedNotis(LifePolicyNotificationDTO[] selectedNotis) {
		this.selectedNotis = selectedNotis;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<LifePolicyNotificationDTO> getLifePolicieList() {
		return lifePolicieList;
	}

}

package org.ace.insurance.web.manage.system;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.system.common.coinsuranceRatio.CoinsuranceDetailRatio;
import org.ace.insurance.system.common.coinsuranceRatio.CoinsuranceRatio;
import org.ace.insurance.system.common.coinsuranceRatio.service.interfaces.ICoinsuranceRatioService;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageCoinsuranceRatioActionBean")
public class ManageCoinsuranceRatioActionBean extends BaseBean {

	@ManagedProperty(value = "#{CoinsuranceRatioService}")
	private ICoinsuranceRatioService coinsuranceRatioService;

	public void setCoinsuranceRatioService(ICoinsuranceRatioService coinsuranceRatioService) {
		this.coinsuranceRatioService = coinsuranceRatioService;
	}

	private CoinsuranceRatio coinsuranceRatio;
	private List<CoinsuranceRatio> coinsuranceRatioList;
	private ProductGroup productGroup;
	private boolean isNew;
	private Date minDate;
	private Date maxDate;
	private Date oldDate;
	private Calendar cal;

	@PostConstruct
	public void init() {
		initialization();
		if (productGroup != null) {
			createNewCoinsuranceRatio();
			findAllCoinsuranceRatio();
			isNew = true;
		}
	}

	private void initialization() {
		productGroup = (ProductGroup) getParam(Constants.PRODUCTGROUP_ID);
	}

	@PreDestroy
	public void destroy() {
		removeParam(Constants.PRODUCTGROUP_ID);
	}

	public void findAllCoinsuranceRatio() {
		minDate = null;
		maxDate = null;
		coinsuranceRatioList = coinsuranceRatioService.findCoinsuranceRatioListByProductGroupId(productGroup.getId());
		if (coinsuranceRatioList.size() >= 1) {
			minDate = coinsuranceRatioList.get(coinsuranceRatioList.size() - 1).getStartDate();
			cal.setTime(minDate);
			cal.add(Calendar.DATE, 1);
			minDate = cal.getTime();
		}
		createNewCoinsuranceRatio();
	}

	public void addNewCoinsuranceRatio() {
		try {
			if (validateDetailRatio()) {
				if (coinsuranceRatioList.size() > 0) {
					cal.setTime(coinsuranceRatio.getStartDate());
					cal.add(Calendar.DATE, -1);
					coinsuranceRatioList.get(coinsuranceRatioList.size() - 1).setEndDate(cal.getTime());
					coinsuranceRatioService.updateEndateByCoInRatioId(coinsuranceRatioList.get(coinsuranceRatioList.size() - 1));
				}
				coinsuranceRatio.setSetNo(coinsuranceRatioList.size() + 1);
				coinsuranceRatio.setProductGroup(productGroup);
				coinsuranceRatioService.addNewCoinsuranceRatio(coinsuranceRatio);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, coinsuranceRatio.getProductGroup().getName());
				findAllCoinsuranceRatio();
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateCoinsuranceRatio() {
		try {
			if (validateDetailRatio()) {
				if (coinsuranceRatio.getSetNo() != 1) {
					// added current startDate and then changed old endDate
					int days = (int) ((coinsuranceRatio.getStartDate().getTime() - oldDate.getTime()) / (1000 * 60 * 60 * 24));
					if (coinsuranceRatio.getStartDate().getTime() > oldDate.getTime()) {
						for (CoinsuranceRatio coRatio : coinsuranceRatioList) {
							if (coinsuranceRatio.getSetNo() - 1 == coRatio.getSetNo()) {
								cal.setTime(coRatio.getEndDate());
								cal.add(Calendar.DATE, days);
								coRatio.setEndDate(cal.getTime());
								coinsuranceRatioService.updateEndateByCoInRatioId(coRatio);
								break;
							}
						}
					} else {
						// reduced current startDate and then changed old
						// endDate
						coinsuranceRatioList.stream().filter(c -> c.getSetNo() == coinsuranceRatio.getSetNo() - 1).forEach(coRatio -> {
							cal.setTime(coinsuranceRatio.getStartDate());
							cal.add(Calendar.DATE, -1);
							coRatio.setEndDate(cal.getTime());
							coinsuranceRatioService.updateEndateByCoInRatioId(coRatio);
						});
					}
				}
				coinsuranceRatioService.updateCoinsuranceRatio(coinsuranceRatio);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, coinsuranceRatio.getProductGroup().getName());
				findAllCoinsuranceRatio();
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void editCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio) {
		// add Min Date
		this.coinsuranceRatio = coinsuranceRatio;
		isNew = false;
		minDate = null;
		maxDate = null;
		oldDate = coinsuranceRatio.getStartDate();
		if (coinsuranceRatio.getSetNo() != 1) {
			for (CoinsuranceRatio coRatio : coinsuranceRatioList) {
				if (coinsuranceRatio.getSetNo() - 1 == coRatio.getSetNo()) {
					cal.setTime(coRatio.getStartDate());
					cal.add(Calendar.DATE, 1);
					minDate = cal.getTime();
					break;
				}
			}
		} else {
			minDate = null;
		}
		// add Max Date
		for (CoinsuranceRatio coRatio : coinsuranceRatioList) {
			if (coinsuranceRatio.getSetNo() + 1 == coRatio.getSetNo()) {
				cal.setTime(coRatio.getStartDate());
				cal.add(Calendar.DATE, -1);
				maxDate = cal.getTime();
				break;
			}
		}
	}

	public boolean validateDetailRatio() {
		boolean result = true;
		if (coinsuranceRatio.getCoinsuranceDetailRatioList() == null || coinsuranceRatio.getCoinsuranceDetailRatioList().isEmpty()) {
			addErrorMessage("coInsuranceRatioEntryForm:coDetailRatioTablePanel", MessageId.REQUIRED_COINSURANCE_DETAIL_RATIO);
			result = false;
		}
		return result;
	}

	public void returnMultiConsuranceCompany(SelectEvent event) {
		List<CoinsuranceCompany> coinsuranceCompanyList = (List<CoinsuranceCompany>) event.getObject();
		List<CoinsuranceDetailRatio> coInDetailRatioList = new ArrayList<>();
		for (CoinsuranceCompany coinsuranceCompany : coinsuranceCompanyList) {
			CoinsuranceDetailRatio coinDetailRatio = new CoinsuranceDetailRatio();
			if (coinsuranceRatio.getCoinsuranceDetailRatioList() != null && !coinsuranceRatio.getCoinsuranceDetailRatioList().isEmpty()) {
				loop: for (CoinsuranceDetailRatio coinsuranceDetailRatio : coinsuranceRatio.getCoinsuranceDetailRatioList()) {
					if (coinsuranceCompany.getName().equals(coinsuranceDetailRatio.getCoinsuranceCompany().getName())) {
						// forCoInDetailRatioList
						coinDetailRatio = coinsuranceDetailRatio;
						break loop;
					} else {
						// forCoinsuranceCompanyList
						coinDetailRatio.setCoinsuranceCompany(coinsuranceCompany);
					}
				}
			} else {
				// forCoinsuranceCompanyList
				coinDetailRatio.setCoinsuranceCompany(coinsuranceCompany);
			}
			coInDetailRatioList.add(coinDetailRatio);
		}
		coinsuranceRatio.setCoinsuranceDetailRatioList(coInDetailRatioList);
	}

	public void editMultiCoinsuranceCompany() {
		if (coinsuranceRatio.getCoinsuranceDetailRatioList() != null && !coinsuranceRatio.getCoinsuranceDetailRatioList().isEmpty()) {
			List<CoinsuranceCompany> coinsuranceCompanyList = new ArrayList<>();
			CoinsuranceCompany coinsuranceCompany = new CoinsuranceCompany();
			for (CoinsuranceDetailRatio coInDetailRatio : coinsuranceRatio.getCoinsuranceDetailRatioList()) {
				coinsuranceCompany = coInDetailRatio.getCoinsuranceCompany();
				coinsuranceCompanyList.add(coinsuranceCompany);
			}
			putParam("selectedCoinsuranceCompany", coinsuranceCompanyList);
		} else {
			removeParam("selectedCoinsuranceCompany");
		}
		selectMultiCoinsuranceCompany();
	}

	public void createNewCoinsuranceRatio() {
		cal = Calendar.getInstance();
		coinsuranceRatio = new CoinsuranceRatio();
		isNew = true;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public CoinsuranceRatio getCoinsuranceRatio() {
		return coinsuranceRatio;
	}

	public void setCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio) {
		this.coinsuranceRatio = coinsuranceRatio;
	}

	public List<CoinsuranceRatio> getCoinsuranceRatioList() {
		return coinsuranceRatioList;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

}

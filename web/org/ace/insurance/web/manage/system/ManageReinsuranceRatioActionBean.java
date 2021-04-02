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
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceDetailRatio;
import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceRatio;
import org.ace.insurance.system.common.reinsuranceRatio.service.interfaces.IReinsuranceRatioService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageReinsuranceRatioActionBean")
public class ManageReinsuranceRatioActionBean extends BaseBean {

	@ManagedProperty(value = "#{ReinsuranceRatioService}")
	private IReinsuranceRatioService reinsuranceRatioService;

	public void setReinsuranceRatioService(IReinsuranceRatioService reinsuranceRatioService) {
		this.reinsuranceRatioService = reinsuranceRatioService;
	}

	private ReinsuranceRatio reinsuranceRatio;
	private List<ReinsuranceRatio> reinsuranceRatioList;
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
			createNewReinsuranceRatio();
			findAllReinsuranceRatio();
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

	public void findAllReinsuranceRatio() {
		minDate = null;
		maxDate = null;
		reinsuranceRatioList = reinsuranceRatioService.findReinsuranceRatioListByProductGroupId(productGroup.getId());
		if (reinsuranceRatioList.size() >= 1) {
			minDate = reinsuranceRatioList.get(reinsuranceRatioList.size() - 1).getStartDate();
			cal.setTime(minDate);
			cal.add(Calendar.DATE, 1);
			minDate = cal.getTime();
		}
		createNewReinsuranceRatio();
	}

	public void addNewReinsuranceRatio() {
		try {
			if (validateDetailRatio()) {
				if (reinsuranceRatioList.size() > 0) {
					cal.setTime(reinsuranceRatio.getStartDate());
					cal.add(Calendar.DATE, -1);
					reinsuranceRatioList.get(reinsuranceRatioList.size() - 1).setEndDate(cal.getTime());
					reinsuranceRatioService.updateEndateByReInRatioId(reinsuranceRatioList.get(reinsuranceRatioList.size() - 1));
				}
				reinsuranceRatio.setSetNo(reinsuranceRatioList.size() + 1);
				reinsuranceRatio.setProductGroup(productGroup);
				reinsuranceRatioService.addNewReinsuranceRatio(reinsuranceRatio);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, reinsuranceRatio.getProductGroup().getName());
				findAllReinsuranceRatio();
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateReinsuranceRatio() {
		try {
			if (validateDetailRatio()) {
				if (reinsuranceRatio.getSetNo() != 1) {
					// reduced current startDate and then changed old endDate
					int days = (int) ((reinsuranceRatio.getStartDate().getTime() - oldDate.getTime()) / (1000 * 60 * 60 * 24));
					if (reinsuranceRatio.getStartDate().getTime() > oldDate.getTime()) {
						for (ReinsuranceRatio reRatio : reinsuranceRatioList) {
							if (reinsuranceRatio.getSetNo() - 1 == reRatio.getSetNo()) {
								cal.setTime(reRatio.getEndDate());
								cal.add(Calendar.DATE, days);
								reRatio.setEndDate(cal.getTime());
								reinsuranceRatioService.updateEndateByReInRatioId(reRatio);
								break;
							}
						}
					} else {
						// added current startDate and then changed old endDate
						for (ReinsuranceRatio reRatio : reinsuranceRatioList) {
							if (reinsuranceRatio.getSetNo() - 1 == reRatio.getSetNo()) {
								cal.setTime(reinsuranceRatio.getStartDate());
								cal.add(Calendar.DATE, -1);
								reRatio.setEndDate(cal.getTime());
								reinsuranceRatioService.updateEndateByReInRatioId(reRatio);
								break;
							}
						}
					}
				}
				reinsuranceRatioService.updateReinsuranceRatio(reinsuranceRatio);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, reinsuranceRatio.getProductGroup().getName());
				findAllReinsuranceRatio();
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void editReinsuranceRatio(ReinsuranceRatio reinsuranceRatio) {
		this.reinsuranceRatio = reinsuranceRatio;
		isNew = false;
		minDate = null;
		maxDate = null;
		oldDate = reinsuranceRatio.getStartDate();
		if (reinsuranceRatio.getSetNo() != 1) {
			for (ReinsuranceRatio reRatio : reinsuranceRatioList) {
				if (reinsuranceRatio.getSetNo() - 1 == reRatio.getSetNo()) {
					cal.setTime(reRatio.getStartDate());
					cal.add(Calendar.DATE, 1);
					minDate = cal.getTime();
					break;
				}
			}
		} else {
			minDate = null;
		}
		// add Max Date
		for (ReinsuranceRatio reRatio : reinsuranceRatioList) {
			if (reinsuranceRatio.getSetNo() + 1 == reRatio.getSetNo()) {
				cal.setTime(reRatio.getStartDate());
				cal.add(Calendar.DATE, -1);
				maxDate = cal.getTime();
				break;
			}
		}
	}

	public void returnMultiConsuranceCompany(SelectEvent event) {
		List<CoinsuranceCompany> coinsuranceCompanyList = (List<CoinsuranceCompany>) event.getObject();
		List<ReinsuranceDetailRatio> reInDetailRatioList = new ArrayList<>();
		for (CoinsuranceCompany coinsuranceCompany : coinsuranceCompanyList) {
			ReinsuranceDetailRatio reinDetailRatio = new ReinsuranceDetailRatio();
			if (reinsuranceRatio.getReinsuranceDetailRatioList() != null && !reinsuranceRatio.getReinsuranceDetailRatioList().isEmpty()) {
				loop: for (ReinsuranceDetailRatio reinsuranceDetailRatio : reinsuranceRatio.getReinsuranceDetailRatioList()) {
					if (coinsuranceCompany.getName().equals(reinsuranceDetailRatio.getCoinsuranceCompany().getName())) {
						// forCoInDetailRatioList
						reinDetailRatio = reinsuranceDetailRatio;
						break loop;
					} else {
						// forCoinsuranceCompanyList
						reinDetailRatio.setCoinsuranceCompany(coinsuranceCompany);
					}
				}
			} else {
				// forCoinsuranceCompanyList
				reinDetailRatio.setCoinsuranceCompany(coinsuranceCompany);
			}
			reInDetailRatioList.add(reinDetailRatio);
		}
		reinsuranceRatio.setReinsuranceDetailRatioList(reInDetailRatioList);
	}

	public void editMultiCoinsuranceCompany() {
		if (reinsuranceRatio.getReinsuranceDetailRatioList() != null && !reinsuranceRatio.getReinsuranceDetailRatioList().isEmpty()) {
			List<CoinsuranceCompany> coinsuranceCompanyList = new ArrayList<>();
			CoinsuranceCompany coinsuranceCompany = new CoinsuranceCompany();
			for (ReinsuranceDetailRatio reInDetailRatio : reinsuranceRatio.getReinsuranceDetailRatioList()) {
				coinsuranceCompany = reInDetailRatio.getCoinsuranceCompany();
				coinsuranceCompanyList.add(coinsuranceCompany);
			}
			putParam("selectedCoinsuranceCompany", coinsuranceCompanyList);
		} else {
			removeParam("selectedCoinsuranceCompany");
		}
		selectMultiCoinsuranceCompany();
	}

	public boolean validateDetailRatio() {
		if (reinsuranceRatio.getReinsuranceDetailRatioList() == null || reinsuranceRatio.getReinsuranceDetailRatioList().isEmpty()) {
			addErrorMessage("reInsuranceRatioEntryForm:coDetailRatioTablePanel", MessageId.REQUIRED_COINSURANCE_DETAIL_RATIO);
			return false;
		}
		return true;
	}

	public void createNewReinsuranceRatio() {
		cal = Calendar.getInstance();
		reinsuranceRatio = new ReinsuranceRatio();
		isNew = true;
	}

	public ReinsuranceRatio getReinsuranceRatio() {
		return reinsuranceRatio;
	}

	public void setReinsuranceRatio(ReinsuranceRatio reinsuranceRatio) {
		this.reinsuranceRatio = reinsuranceRatio;
	}

	public List<ReinsuranceRatio> getReinsuranceRatioList() {
		return reinsuranceRatioList;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

}

package org.ace.insurance.web.manage.report.life;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.life.UPRReportCriteria;
import org.ace.insurance.report.life.view.UPRReportView;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "UPRReportActionBean")
public class UPRReportActionBean extends BaseBean {

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService policyService;

	public void setPolicyService(ILifePolicyService policyService) {
		this.policyService = policyService;
	}

	private User user;
	private UPRReportCriteria criteria;
	private List<UPRReportView> viewList;

	@PostConstruct
	private void init() {
		user = (User) getParam("LoginUser");
		resetCriteria();
	}

	public void filter() {
		viewList = policyService.findUPRReport(criteria);
	}

	public void resetCriteria() {
		criteria = new UPRReportCriteria();
		viewList = new ArrayList<>();
		criteria.setStartDate(DateUtils.getBudgetYearStartDate());
		criteria.setEndDate(DateUtils.getBudgetYearEndDate());
		criteria.setSalePointName(null);
		criteria.setProductName(null);
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		criteria.setSalePointId(salePoint.getId());
		criteria.setSalePointName(salePoint.getName());
	}

	public List<Branch> getBranchList() {
		return user.getAccessBranchList();
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "UPRReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			UPRReportExcel monthlyIncomeExcel = new UPRReportExcel();
			monthlyIncomeExcel.generate(op, viewList, criteria);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export UPRReport.xlsx", e);
		}
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		criteria.setProductId(product.getId());
		criteria.setProductName(product.getProductContent().getName());
	}

	public User getUser() {
		return user;
	}

	public UPRReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(UPRReportCriteria criteria) {
		this.criteria = criteria;
	}

	public List<UPRReportView> getViewList() {
		return viewList;
	}

}

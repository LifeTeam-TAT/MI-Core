package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.payment.service.interfaces.ITLFService;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.MonthlyIncomeReportDTO;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "MonthlyIncomeReportActionBean")
public class MonthlyIncomeReportActionBean extends BaseBean {

	@ManagedProperty(value = "#{TLFService}")
	private ITLFService tlfService;

	public void setTlfService(ITLFService tlfService) {
		this.tlfService = tlfService;
	}

	private boolean isAgent;
	private User user;
	private MonthlyIncomeReportCriteria criteria;
	private List<MonthlyIncomeReportDTO> monthlyIncomeReportDTOList;
	private List<MonthlyIncomeReportDTO> incomeDetailsReportList;

	@PostConstruct
	private void init() {
		monthlyIncomeReportDTOList = new ArrayList<>();
		incomeDetailsReportList = new ArrayList<>();
		user = (User) getParam("LoginUser");
		resetCriteria();
	}

	public void filter() {
		monthlyIncomeReportDTOList = tlfService.findMonthlyIncomeReport(criteria);
		incomeDetailsReportList = tlfService.findQuantityAndTotalSIDetails(criteria);
	}

	public void resetCriteria() {
		criteria = new MonthlyIncomeReportCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		//criteria.setSaleChannelType("");
		criteria.setIncludeAgent(false);
		criteria.setSalePointName(null);
		this.isAgent = false;
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "MonthlyIncomeReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			MonthlyIncomeReportExcel monthlyIncomeExcel = new MonthlyIncomeReportExcel();
			monthlyIncomeExcel.generate(op, monthlyIncomeReportDTOList, criteria.isIncludeAgent(), incomeDetailsReportList);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export MonthlyIncomeReport.xlsx", e);
		}
	}

	public List<MonthlyIncomeReportDTO> getMonthlyIncomeReportDTOList() {
		return monthlyIncomeReportDTOList;
	}

	public MonthlyIncomeReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(MonthlyIncomeReportCriteria criteria) {
		this.criteria = criteria;
	}

	public EnumSet<SaleChannelType> getSaleChannelType() {
		EnumSet<SaleChannelType> set = EnumSet.allOf(SaleChannelType.class);
		set.remove(SaleChannelType.AFP);
		set.remove(SaleChannelType.BANK);
		set.remove(SaleChannelType.COINSURANCE_INWARD);
		set.remove(SaleChannelType.REINSURANCE);
		return set;
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		criteria.setSalePointId(salePoint.getId());
		criteria.setSalePointName(salePoint.getName());
	}

	public List<Branch> getBranchList() {
		return user.getAccessBranchList();
	}

	public void agentSaleChannel() {
		if (criteria.getSaleChannelType().equalsIgnoreCase(SaleChannelType.DIRECTMARKETING.toString())) {
			this.isAgent = true;
		} else {
			this.isAgent = false;
		}
	}

	public boolean isAgent() {
		return this.isAgent;
	}
}

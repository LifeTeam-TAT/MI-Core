package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.MonthNames;
import org.ace.insurance.payment.service.interfaces.ITLFService;
import org.ace.insurance.report.TLF.CeoShortTermLifeDTO;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "CeoShortTermLifeMonthlyReportActionBean")
public class CeoShortTermLifeMonthlyReportActionBean extends BaseBean {

	@ManagedProperty(value = "#{TLFService}")
	private ITLFService tlfService;

	public void setTlfService(ITLFService tlfService) {
		this.tlfService = tlfService;
	}

	private User user;
	private MonthlyIncomeReportCriteria criteria;
	private List<CeoShortTermLifeDTO> ceoshortEndownLifeMonthlyReportList;

	@PostConstruct
	private void init() {
		ceoshortEndownLifeMonthlyReportList = new ArrayList<>();
		user = (User) getParam("LoginUser");
		resetCriteria();
	}

	public void filter() {
		ceoshortEndownLifeMonthlyReportList = tlfService.findCeoShortEndowLifePolicyMonthlyReport(criteria);
	}

	public void resetCriteria() {
		// Date today = new Date();
		criteria = new MonthlyIncomeReportCriteria();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		criteria.setRequiredMonth(new Date().getMonth());
		criteria.setRequiredYear(cal.get(Calendar.YEAR));
		criteria.setPeriodOfYears(criteria.getPeriodOfYears());
		ceoshortEndownLifeMonthlyReportList = new ArrayList<CeoShortTermLifeDTO>();
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
		ceoshortEndownLifeMonthlyReportList = tlfService.findCeoShortEndowLifePolicyMonthlyReport(criteria);
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "CEOShortTermLifeMonthlyReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			CEOShortTemLifeReportExcel monthlyIncomeExcel = new CEOShortTemLifeReportExcel();
			monthlyIncomeExcel.generate(op, ceoshortEndownLifeMonthlyReportList);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export CEOShortTermLifeMonthlyReport.xlsx", e);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public List<CeoShortTermLifeDTO> getCeoshortEndownLifeMonthlyReportList() {
		return ceoshortEndownLifeMonthlyReportList;
	}

	public void setCeoshortEndownLifeMonthlyReportList(List<CeoShortTermLifeDTO> ceoshortEndownLifeMonthlyReportList) {
		this.ceoshortEndownLifeMonthlyReportList = ceoshortEndownLifeMonthlyReportList;
	}

	/* Short Term Endowment Life */
	public List<Integer> getSePeriodYears() {
		return Arrays.asList(5, 7, 10);
	}

	public EnumSet<MonthNames> getMonthSet() {
		return EnumSet.allOf(MonthNames.class);
	}

	public MonthlyIncomeReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(MonthlyIncomeReportCriteria criteria) {
		this.criteria = criteria;
	}

	public Map<Integer, Integer> getYears() {
		SortedMap<Integer, Integer> years = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1900; startYear <= endYear; startYear++) {
			years.put(startYear, startYear);
		}
		return years;
	}

}

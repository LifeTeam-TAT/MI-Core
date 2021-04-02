package org.ace.insurance.web.manage.report.shortEndowLife;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.life.service.interfaces.ILifePolicyReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ShortEndownmentMonthlyReportActionBean")
public class ShortEndownmentMonthlyReportActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifePolicyReportService}")
	private ILifePolicyReportService lifePolicyReportService;

	public void setLifePolicyReportService(ILifePolicyReportService lifePolicyReportService) {
		this.lifePolicyReportService = lifePolicyReportService;
	}

	private MonthlyIncomeReportCriteria criteria;
	private List<ShortEndownLifeMonthlyReportDTO> shortEndownLifeMonthlyReportList;
	private User user;

	@PostConstruct
	private void init() {
		user = (User) getParam("LoginUser");
		resetCriteria();
	}

	public void resetCriteria() {
		Date today = new Date();
		criteria = new MonthlyIncomeReportCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		int month = DateUtils.getMonthFromDate(today) - 1;
		int year = DateUtils.getYearFromDate(today);
		criteria.setStartDate(Utils.getStartDate(year, month));
		criteria.setEndDate(Utils.getEndDate(year, month));
		criteria.setSalePointName(null);
		shortEndownLifeMonthlyReportList = new ArrayList<ShortEndownLifeMonthlyReportDTO>();
	}

	public void filter() {
		shortEndownLifeMonthlyReportList = lifePolicyReportService.findShortEndowLifePolicyMonthlyReport(criteria);
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "Short Term Life Monthly Report (Sent to Finance & Account).xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			ShortTermMonthlyReportExcel shortTermMonthlyExcel = new ShortTermMonthlyReportExcel();
			shortTermMonthlyExcel.generate(op, shortEndownLifeMonthlyReportList);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export ShortTermMonthlyReportExcel.xlsx", e);
		}
	}

	public MonthlyIncomeReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(MonthlyIncomeReportCriteria criteria) {
		this.criteria = criteria;
	}

	public List<ShortEndownLifeMonthlyReportDTO> getShortEndownLifeMonthlyReportList() {
		return shortEndownLifeMonthlyReportList;
	}

	public List<Branch> getBranchList() {
		return user.getAccessBranchList();
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		criteria.setSalePointId(salePoint.getId());
		criteria.setSalePointName(salePoint.getName());
	}

	public SaleChannelType[] getSaleChannel() {
		SaleChannelType[] types = { SaleChannelType.AGENT, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING };
		return types;
	}

	public void changeSaleEvent(AjaxBehaviorEvent event) {
		criteria.setSaleBank(null);
		criteria.setAgent(null);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}

	public void returnSaleBank(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		criteria.setSaleBank(bankBranch);
	}

}

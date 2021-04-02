package org.ace.insurance.web.manage.report.student;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.MonthNames;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.payment.service.interfaces.ITLFService;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.MonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.StudentMontlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.StudentMontlyIncomeReportDTO;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.manage.report.account.MonthlyIncomeReportExcel;
import org.ace.insurance.web.manage.report.shortEndowLife.ShortEndownLifeMonthlyReportDTO;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "StudentMontlyIncomeReportActionBean")
public class StudentMontlyIncomeReportActionBean extends BaseBean{

	@ManagedProperty(value = "#{TLFService}")
	private ITLFService tlfService;

	public void setTlfService(ITLFService tlfService) {
		this.tlfService = tlfService;
	}

	private User user;
	private StudentMontlyIncomeReportCriteria criteria;
	private List<StudentMontlyIncomeReportDTO> studentmonthlyIncomeReportDTOList;


	@PostConstruct
	private void init() {
		studentmonthlyIncomeReportDTOList = new ArrayList<>();
		user = (User) getParam("LoginUser");
		resetCriteria();
	}

	public void filter() {
		studentmonthlyIncomeReportDTOList = tlfService.findStudentMontlyIncomeReport(criteria);
	}

	public void resetCriteria() {
//		Date today = new Date();
		criteria = new StudentMontlyIncomeReportCriteria();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -7);
//		int month = DateUtils.getMonthFromDate(today) - 1;
//		int year = DateUtils.getYearFromDate(today);
		criteria.setMonth(new Date().getMonth());
		criteria.setYear(cal.get(Calendar.YEAR));
		criteria.setSalePointName(null);
		studentmonthlyIncomeReportDTOList = new ArrayList<StudentMontlyIncomeReportDTO>();
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "StudentLifeMonthlyReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			StudentMontlyIncomeReportExcel studentmonthlyIncomeExcel = new StudentMontlyIncomeReportExcel();
			studentmonthlyIncomeExcel.generate(op, studentmonthlyIncomeReportDTOList);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export StudentLifeMonthlyReport.xlsx", e);
		}
	}


	public Map<Integer, Integer> getYears() {
		SortedMap<Integer, Integer> years = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1900; startYear <= endYear; startYear++) {
			years.put(startYear, startYear);
		}
		return years;
	}
	
	public EnumSet<MonthNames> getMonthSet() {
		return EnumSet.allOf(MonthNames.class);
	}
	
	public List<Branch> getBranchList() {
		return user.getAccessBranchList();
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		criteria.setSalePointId(salePoint.getId());
		criteria.setSalePointName(salePoint.getName());
	}


	public StudentMontlyIncomeReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(StudentMontlyIncomeReportCriteria criteria) {
		this.criteria = criteria;
	}

	public List<StudentMontlyIncomeReportDTO> getStudentmonthlyIncomeReportDTOList() {
		return studentmonthlyIncomeReportDTOList;
	}

	public void setStudentmonthlyIncomeReportDTOList(List<StudentMontlyIncomeReportDTO> studentmonthlyIncomeReportDTOList) {
		this.studentmonthlyIncomeReportDTOList = studentmonthlyIncomeReportDTOList;
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

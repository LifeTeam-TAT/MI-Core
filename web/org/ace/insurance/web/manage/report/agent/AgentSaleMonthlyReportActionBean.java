package org.ace.insurance.web.manage.report.agent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.AgentSaleInsuranceType;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.MonthNames;
import org.ace.insurance.report.agent.AgentSaleMonthlyDto;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.insurance.report.agent.service.interfaces.IAgentSaleMonthlyReportService;
import org.ace.insurance.report.excelreport.AgentSaleMonthlyReportGeneral;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AgentSaleMonthlyReportActionBean")
public class AgentSaleMonthlyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentSaleMonthlyReportService}")
	private IAgentSaleMonthlyReportService agentMonthlySaleService;

	public void setAgentMonthlySaleService(IAgentSaleMonthlyReportService agentMonthlySaleService) {
		this.agentMonthlySaleService = agentMonthlySaleService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	private AgentSalesReportCriteria criteria;
	List<AgentSaleMonthlyDto> agentMonthlySaleList;
	private boolean isAccessBranches;
	private User user;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		reset();
	}

	public void reset() {
		// if (user.isAccessAllBranch()) {
		isAccessBranches = true;
		// } else {
		criteria.setBranch(user.getBranch());
		// }
		criteria = new AgentSalesReportCriteria();
		criteria.setMonth(new Date().getMonth());
		criteria.setInsuranceType(AgentSaleInsuranceType.GENERAL);
		Calendar cal = Calendar.getInstance();
		criteria.setYear(cal.get(Calendar.YEAR));
		agentMonthlySaleList = new ArrayList<AgentSaleMonthlyDto>();
	}

	public void search() {
		agentMonthlySaleList = new ArrayList<AgentSaleMonthlyDto>();
		agentMonthlySaleList = agentMonthlySaleService.findMonthlySaleReport(criteria);
		Collections.sort(agentMonthlySaleList, AgentSaleMonthlyDto.codeNoComparator);
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "AgentSale_MONTHLY_NEW_RENEWAL_GENERAL_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			AgentSaleMonthlyReportGeneral generalReport = new AgentSaleMonthlyReportGeneral(agentMonthlySaleList, criteria);
			generalReport.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export AgentSale_MONTHLY_NEW_RENEWAL_GENERAL_Report.xlsx", e);
		}

	}

	public AgentSalesReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AgentSalesReportCriteria criteria) {
		this.criteria = criteria;
	}

	public List<Currency> getCurrencyList() {
		return currencyService.findAllCurrency();
	}

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	// Getter.
	public EnumSet<MonthNames> getMonthSet() {
		return EnumSet.allOf(MonthNames.class);
	}

	public List<AgentSaleMonthlyDto> getAgentMonthlySaleList() {
		return agentMonthlySaleList;
	}

	public boolean isAccessBranches() {
		return isAccessBranches;
	}

	/**
	 * Get year list.
	 * 
	 * @return List<Integer>[Year List from 1999 to current year].
	 */
	public List<Integer> getYears() {
		List<Integer> years = new ArrayList<Integer>();
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1999; startYear <= endYear; startYear++) {
			years.add(startYear);
		}
		Collections.reverse(years);
		return years;
	}

	/**
	 * Set branch to criteria.
	 * 
	 * @param SelectEvent
	 *            .
	 * 
	 * @return void [nothing].
	 */
	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	/**
	 * Set agent to criteria.
	 * 
	 * @param SelectEvent
	 *            .
	 * 
	 * @return void [nothing].
	 */
	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}
}

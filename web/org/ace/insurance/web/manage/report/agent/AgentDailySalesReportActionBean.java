package org.ace.insurance.web.manage.report.agent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.AgentSaleInsuranceType;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.report.agent.AgentSaleComparisonReport;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.insurance.report.agent.service.interfaces.IAgentDailySalesReportService;
import org.ace.insurance.report.excelreport.AgentSaleReportGeneral;
import org.ace.insurance.report.excelreport.AgentSaleReportLife;
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
@ManagedBean(name = "AgentDailySalesReportActionBean")
public class AgentDailySalesReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentDailySalesReportService}")
	private IAgentDailySalesReportService agentDailySalesReportService;

	public void setAgentDailySalesReportService(IAgentDailySalesReportService agentDailySalesReportService) {
		this.agentDailySalesReportService = agentDailySalesReportService;
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

	private List<AgentSaleComparisonReport> agentDailySalesList;
	private boolean isLifeInsurance;
	private boolean isAccessBranches;
	private User user;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		reset();
	}

	public void filter() {

		if (criteria.getInsuranceType().equals(AgentSaleInsuranceType.LIFE)) {
			agentDailySalesList = agentDailySalesReportService.findForLife(criteria);
			isLifeInsurance = true;
		} else {
			if (criteria.getProposalType().equals("NEW_RENEWAL")) {
				agentDailySalesList = agentDailySalesReportService.findForNonLife_NEW_RENEWAL(criteria);
			} else {
				agentDailySalesList = agentDailySalesReportService.findForNonLife(criteria);
			}
			isLifeInsurance = false;
		}
		Collections.sort(agentDailySalesList, AgentSaleComparisonReport.codeNoComparator);
	}

	public boolean isLifeInsurance() {
		return isLifeInsurance;
	}

	public void reset() {
		// if (user.isAccessAllBranch()) {
		isAccessBranches = true;
		// } else {
		criteria.setBranch(user.getBranch());
		// }
		criteria = new AgentSalesReportCriteria();
		criteria.setInsuranceType(AgentSaleInsuranceType.GENERAL);
		criteria.setProposalType("NEW");

		// filter();
	}

	public void changeInsuranceTypes(AjaxBehaviorEvent evt) {
		if (criteria.getInsuranceType().equals(AgentSaleInsuranceType.LIFE)) {
			isLifeInsurance = true;
			criteria.setProposalType("NEW");
		} else {
			isLifeInsurance = false;
		}
	}

	public AgentSalesReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AgentSalesReportCriteria criteria) {
		this.criteria = criteria;
	}

	public void setAgentDailySalesList(List<AgentSaleComparisonReport> agentDailySalesList) {
		this.agentDailySalesList = agentDailySalesList;
	}

	public List<AgentSaleComparisonReport> getAgentDailySalesList() {
		return agentDailySalesList;
	}

	public AgentSaleInsuranceType[] getInsuranceTypes() {
		return AgentSaleInsuranceType.values();
	}

	public ProposalType[] getProposalTypeSelectItemList() {
		return ProposalType.values();
	}

	public boolean isAccessBranches() {
		return isAccessBranches;
	}

	public List<Currency> getCurrencyList() {
		return currencyService.findAllCurrency();
	}

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = criteria.getProposalType().equals("RENEWAL") ? "AgentSale_Daily_RENEWAL_GENERAL_Report.xlsx" : "AgentSale_Daily_NEW_RENEWAL_GENERAL_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			AgentSaleReportGeneral generalReport = new AgentSaleReportGeneral(agentDailySalesList, criteria.getProposalType());
			generalReport.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export Fire_Daily_Report.xlsx", e);
		}
	}

	public void exportExcel1() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName2 = "AgentSale_Daily_Life_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName2 + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			AgentSaleReportLife lifeReport = new AgentSaleReportLife(agentDailySalesList);
			lifeReport.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export AgentSale_Monthly_Report1.xlsx", e);
		}
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}
}

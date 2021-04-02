package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.payment.service.interfaces.ITLFService;
import org.ace.insurance.report.TLF.FarmerMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
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
@ManagedBean(name = "FarmerMonthlyIncomeReportActionBean")
public class FarmerMonthlyIncomeReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{TLFService}")
	private ITLFService tlfService;

	public void setTlfService(ITLFService tlfService) {
		this.tlfService = tlfService;
	}

	private MonthlyIncomeReportCriteria criteria;
	private List<FarmerMonthlyIncomeReportDTO> farmerMonthlyIncomeReportDTOList;
	private User user;

	@PostConstruct
	private void init() {
		user = (User) getParam("LoginUser");
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new MonthlyIncomeReportCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int min = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, min);
		criteria.setStartDate(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, max);
		criteria.setEndDate(cal.getTime());
		criteria.setSalePointName(null);
		farmerMonthlyIncomeReportDTOList = new ArrayList<FarmerMonthlyIncomeReportDTO>();
	}

	public void filter() {
		farmerMonthlyIncomeReportDTOList = tlfService.findFarmerMonthlyIncomeReport(criteria);
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "FarmerMonthlyIncomeReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			FarmerMonthlyIncomeReportExcel farmerMonthlyExcel = new FarmerMonthlyIncomeReportExcel();
			farmerMonthlyExcel.generate(op, farmerMonthlyIncomeReportDTOList);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export FarmerMonthlyReportExcel.xlsx", e);
		}
	}

	public MonthlyIncomeReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(MonthlyIncomeReportCriteria criteria) {
		this.criteria = criteria;
	}

	public List<FarmerMonthlyIncomeReportDTO> getFarmerMonthlyIncomeReportDTOList() {
		return farmerMonthlyIncomeReportDTOList;
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

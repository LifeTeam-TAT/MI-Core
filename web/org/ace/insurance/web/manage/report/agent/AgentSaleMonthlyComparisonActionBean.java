package org.ace.insurance.web.manage.report.agent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.MonthNames;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.service.interfaces.IAgentSaleMonthlyComparisonService;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.insurance.report.common.AgentSaleData;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2015-12-10
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>AgentSaleComparisonReport</code> Report process.
 * 
 ***************************************************************************************/

@ViewScoped
@ManagedBean(name = "AgentSaleMonthlyComparisonActionBean")
public class AgentSaleMonthlyComparisonActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentSaleMonthlyComparisonService}")
	private IAgentSaleMonthlyComparisonService agentSaleMonthlyComparisonService;

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setAgentSaleMonthlyComparisonService(IAgentSaleMonthlyComparisonService agentSaleMonthlyComparisonService) {
		this.agentSaleMonthlyComparisonService = agentSaleMonthlyComparisonService;
	}

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	private AgentSaleComparisonCriteria criteria;
	private List<AgentSaleData> reportList;
	private User user;
	private boolean accessBranches;

	public AgentSaleComparisonCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AgentSaleComparisonCriteria criteria) {
		this.criteria = criteria;
	}

	public List<AgentSaleData> getReportList() {
		return reportList;
	}

	public void setReportList(List<AgentSaleData> reportList) {
		this.reportList = reportList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public void setAccessBranches(boolean accessBranches) {
		this.accessBranches = accessBranches;
	}

	@PostConstruct
	public void init() {
		resetCriteria();
	}

	public void resetCriteria() {
		DateTime dateTime = new DateTime();
		criteria = new AgentSaleComparisonCriteria();
		criteria.setMonth(new Date().getMonth());
		criteria.setYear(dateTime.getYear());
		criteria.setProposalType("NEW");
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// } else {
		criteria.setBranch(user.getBranch());
		// }
		reportList = new ArrayList<AgentSaleData>();
	}

	public void filter() {
		try {
			reportList = agentSaleMonthlyComparisonService.findAgentSaleMonthlyReport(criteria);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public EnumSet<MonthNames> getMonthSet() {
		return EnumSet.allOf(MonthNames.class);
	}

	public List<Integer> getYears() {
		List<Integer> years = new ArrayList<Integer>();
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1999; startYear <= endYear; startYear++) {
			years.add(startYear);
		}
		Collections.reverse(years);
		return years;
	}

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	public List<Currency> getCurrencyList() {
		return currencyService.findAllCurrency();
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "AgentSaleComparison_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(criteria.getYear(), Utils.getMonthString(criteria.getMonth()), reportList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export AgentSaleComparison_Report.xlsx", e);
		}
	}

	private class ExportExcel {
		private int year;
		private String month;
		private List<AgentSaleData> reportList;
		private XSSFWorkbook wb;

		public ExportExcel(int year, String month, List<AgentSaleData> reportList) {
			this.year = year;
			this.month = month;
			this.reportList = reportList;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/AgentSaleComparisonReport.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load  AgentSaleComparisonReport.xlsx template", e);
			}
		}

		public int getNoOfTotalPolicy() {
			int noOfTotalPolicy = 0;
			for (AgentSaleData data : reportList) {
				noOfTotalPolicy += data.getNoOfpolicy();
			}
			return noOfTotalPolicy;
		}

		public double getTotalPremium() {
			double totalPremium = 0.0;
			for (AgentSaleData data : reportList) {
				totalPremium += data.getPremium();
			}
			return totalPremium;
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("AgentSaleComparisonReport");

				ExcelUtils.fillCompanyLogo(wb, sheet, 4);
				Row titleRow = sheet.getRow(0);
				Cell title = titleRow.getCell(0);
				if (criteria.getBranch() == null) {
					title.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n  Agent Sale Comparison Report ( All )");
				} else {
					title.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n  Agent Sale Comparison Report ( " + criteria.getBranch().getName() + " )");
				}

				CellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				CellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				CellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell noCell;
				Cell insuranceTypeCell;
				Cell noOfPolicyCell;
				Cell premiumCell;
				Cell remarkCell;

				int i = 1;
				int index = 0;

				for (AgentSaleData saleReport : reportList) {
					i = i + 1;
					index = index + 1;

					row = sheet.createRow(i);
					noCell = row.createCell(0);
					noCell.setCellValue(index);
					noCell.setCellStyle(defaultCellStyle);

					insuranceTypeCell = row.createCell(1);
					insuranceTypeCell.setCellValue(saleReport.getInsuranceType());
					insuranceTypeCell.setCellStyle(textCellStyle);

					noOfPolicyCell = row.createCell(2);
					noOfPolicyCell.setCellValue(saleReport.getNoOfpolicy());
					noOfPolicyCell.setCellStyle(defaultCellStyle);

					premiumCell = row.createCell(3);
					premiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getPremium()));
					premiumCell.setCellStyle(currencyCellStyle);

					remarkCell = row.createCell(4);
					remarkCell.setCellStyle(textCellStyle);
				}
				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 1));
				row = sheet.createRow(i);

				Cell totalLabelCell = row.createCell(0);
				totalLabelCell.setCellValue("Grand Total ");
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 1), sheet, wb);
				totalLabelCell.setCellStyle(defaultCellStyle);

				noOfPolicyCell = row.createCell(2);
				noOfPolicyCell.setCellValue(getNoOfTotalPolicy());
				noOfPolicyCell.setCellStyle(defaultCellStyle);

				premiumCell = row.createCell(3);
				premiumCell.setCellValue(Utils.getCurrencyFormatString(getTotalPremium()));
				premiumCell.setCellStyle(currencyCellStyle);

				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 4, 4), sheet, wb);

				wb.setPrintArea(0, 0, 4, 0, i);
				wb.write(op);
				op.flush();
				op.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

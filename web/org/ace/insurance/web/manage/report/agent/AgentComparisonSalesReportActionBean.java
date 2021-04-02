package org.ace.insurance.web.manage.report.agent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.MonthNames;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.service.interfaces.IAgentComparisonSalesReportService;
import org.ace.insurance.report.common.AgentComparisonSalesReport;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**************************************************************************
 * @Date 11/05/2016.
 * @author Zarni Phyo.
 * @Rev v1.0.
 * @CopyRight ACEPLUS SOLUTIONS CO., Ltd.
 *************************************************************************/

@ViewScoped
@ManagedBean(name = "AgentComparisonSalesReportActionBean")
public class AgentComparisonSalesReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentComparisonSalesReportService}")
	private IAgentComparisonSalesReportService agentComparisonSalesReportService;

	public void setAgentComparisonSalesReportService(IAgentComparisonSalesReportService agentComparisonSalesReportService) {
		this.agentComparisonSalesReportService = agentComparisonSalesReportService;
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

	private AgentSaleComparisonCriteria criteria;
	private List<AgentComparisonSalesReport> reportList;
	private User user;
	private boolean isAccessBranch;

	private final String reportName = "agentComparisonSalesReport";
	// pdf path.
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	// pdf name.
	private final String fileName = reportName + ".pdf";

	/**
	 * new criteria
	 */
	@PostConstruct
	public void init() {
		resetCriteria();
	}

	/**
	 * Create criteria.Set year, month and proposalType of criteria. Get current
	 * user from session.Branch is according to the current user and create new
	 * AgentComparisonSalesReport Lists.
	 */
	public void resetCriteria() {
		criteria = new AgentSaleComparisonCriteria();
		Calendar cal = Calendar.getInstance();
		criteria.setYear(cal.get(Calendar.YEAR));
		criteria.setMonth(cal.get(Calendar.MONTH));
		user = (User) getParam("LoginUser");

		criteria.setBranch(user.getBranch());
		reportList = new ArrayList<AgentComparisonSalesReport>();
	}

	/**
	 * report list by choosing criteria.
	 */
	public void report() {
		reportList = agentComparisonSalesReportService.findAgentComparisonSalesReport(criteria);
	}

	/**
	 * generate Jasper report .
	 * 
	 * @param reportList
	 */
	public void previewReport() {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			String reportDate = new ApplicationSetting().getMonthInString(criteria.getMonth()) + " " + criteria.getYear();
			String branch;
			if (criteria.getBranch() == null) {
				branch = "All Branch Office";
			} else {
				branch = criteria.getBranch().getName();
				if ("YANGON".equalsIgnoreCase(branch)) {
					branch = branch + " Head Office";
				} else {
					branch = branch + " Branch Office";
				}
			}

			Map parameters = new HashMap();
			parameters.put("reportDate", reportDate);
			parameters.put("reportList", reportList);
			parameters.put("branch", branch);
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/report-template/agent/agentComparisonSalesReport.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			jasperPrintList.add(jasperPrint);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			FileHandler.forceMakeDirectory(dirPath);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
			exporter.exportReport();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("agentComparisonSalesDialog.show()");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * get PDF directory.
	 * 
	 * @return String[pdfDirPath and fileName]
	 */
	public String getStream() {
		return pdfDirPath + fileName;

	}

	
	//To FIXME by thk
	/**
	 * total number of fire policy.
	 * 
	 * @return long[firePolicy]
	 */
	public long getFirePolicy() {
		long firePolicy = 0;
		for (AgentComparisonSalesReport data : reportList) {
			firePolicy += data.getFirePolicy();
		}
		return firePolicy;

	}

	/**
	 * total number of motor policy.
	 * 
	 * @return long[motorPolicy]
	 */
	public long getMotorPolicy() {
		long motorPolicy = 0;
		for (AgentComparisonSalesReport data : reportList) {
			motorPolicy += data.getMotorPolicy();
		}
		return motorPolicy;

	}

	/**
	 * total number of cargo policy.
	 * 
	 * @return long[cargoPolicy]
	 */
	public long getCargoPolicy() {
		long cargoPolicy = 0;
		for (AgentComparisonSalesReport data : reportList) {
			cargoPolicy += data.getCargoPolicy();
		}
		return cargoPolicy;

	}

	/**
	 * number of total policy.
	 * 
	 * @return long[noOfTotalPolicy]
	 */
	public long getNoOfTotalPolicy() {
		long noOfTotalPolicy = 0;
		for (AgentComparisonSalesReport data : reportList) {
			noOfTotalPolicy += data.getNoOfTotalpolicy();
		}
		return noOfTotalPolicy;
	}

	/**
	 * total premium of fire policy.
	 * 
	 * @return double[firePremium]
	 */
	public double getFirePremium() {
		double firePremium = 0.0;
		for (AgentComparisonSalesReport data : reportList) {
			firePremium += data.getTotalPremium();
		}
		return firePremium;
	}

	/**
	 * total premium of motor policy.
	 * 
	 * @return double[motorPremium]
	 */
	public double getMotorPremium() {
		double motorPremium = 0.0;
		for (AgentComparisonSalesReport data : reportList) {
			motorPremium += data.getMotorPremium();
		}
		return motorPremium;
	}

	/**
	 * total premium of cargo policy.
	 * 
	 * @return double[cargoPremium]
	 */
	public double getCargoPremium() {
		double cargoPremium = 0.0;
		for (AgentComparisonSalesReport data : reportList) {
			cargoPremium += data.getCargoPremium();
		}
		return cargoPremium;
	}

	/**
	 * total premium.
	 * 
	 * @return double[totalPremium]
	 */
	public double getTotalPremium() {
		double totalPremium = 0.0;
		for (AgentComparisonSalesReport data : reportList) {
			totalPremium += data.getTotalPremium();
		}
		return totalPremium;
	}

	/**
	 * get year.
	 * 
	 * @return List[years]
	 */

	public EnumSet<MonthNames> getMonthSet() {
		criteria.setMonth(new Date().getMonth());
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

	/**
	 * generate excel file
	 */
	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "agentComparisonSalesReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream oStream = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(criteria.getYear(), Utils.getMonthString(criteria.getMonth()), reportList);
			exportExcel.generate(oStream);
			getFacesContext().getResponseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export agentComparisonSalesReport.xlsx", e);
		}
	}

	private class ExportExcel {
		private int year;
		private String month;
		private List<AgentComparisonSalesReport> reportList;
		private XSSFWorkbook wb;

		public ExportExcel(int year, String month, List<AgentComparisonSalesReport> reportList) {
			this.year = year;
			this.month = month;
			this.reportList = reportList;
			load();
		}

		private void load() {
			try {
				InputStream iStream = this.getClass().getResourceAsStream("/report-template/agent/agentComparisonSalesReport.xlsx");
				wb = new XSSFWorkbook(iStream);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load agentComparisonSalesReport.xlsx template", e);
			}
		}

		public void generate(OutputStream oStream) {
			try {
				Sheet sheet = wb.getSheet("agentComparisonSalesReport");
				Row titleRow = sheet.getRow(0);
				Cell companyName = titleRow.getCell(0);
				companyName.setCellValue(ApplicationSetting.getCompanyLabel());

				Row branchRow = sheet.getRow(1);
				Cell branchCell = branchRow.getCell(0);
				String branch;
				if (criteria.getBranch() == null) {
					branch = "Agency Department( All Branch Office )";
				} else {
					branch = criteria.getBranch().getName();
					if ("YANGON".equalsIgnoreCase(branch)) {
						branch = "Agency Department(" + branch + " Head Office)";
					} else {
						branch = "Agency Department(" + branch + " Branch Office)";
					}
				}
				branchCell.setCellValue(branch);

				Row reportNameRow = sheet.getRow(4);
				Cell reportNameCell = reportNameRow.getCell(0);
				String title = month + " " + year;
				reportNameCell.setCellValue(title);
				reportNameCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);

				CellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				CellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				CellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell proposalTypeCell;
				Cell firePolicyCell;
				Cell firePremiumCell;
				Cell motorPolicyCell;
				Cell motorPremiumCell;
				Cell cargoPolicyCell;
				Cell cargoPremiumCell;
				Cell noOfTotalPolicyCell;
				Cell totalPremiumCell;
				Cell remarkCell;

				int i = 7;
				int index = 0;
				for (AgentComparisonSalesReport saleReport : reportList) {
					i = i + 1;
					index = index + 1;

					row = sheet.createRow(i);
					proposalTypeCell = row.createCell(0);
					proposalTypeCell.setCellValue(saleReport.getProposalType() + " BUSINESS");
					proposalTypeCell.setCellStyle(textCellStyle);

					firePolicyCell = row.createCell(1);
					firePolicyCell.setCellValue(saleReport.getFirePolicy());
					firePolicyCell.setCellStyle(defaultCellStyle);

					firePremiumCell = row.createCell(2);
					firePremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getFirePremium()));
					firePremiumCell.setCellStyle(currencyCellStyle);

					motorPolicyCell = row.createCell(3);
					motorPolicyCell.setCellValue(saleReport.getMotorPolicy());
					motorPolicyCell.setCellStyle(defaultCellStyle);

					motorPremiumCell = row.createCell(4);
					motorPremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getMotorPremium()));
					motorPremiumCell.setCellStyle(currencyCellStyle);

					cargoPolicyCell = row.createCell(5);
					cargoPolicyCell.setCellValue(saleReport.getCargoPolicy());
					cargoPolicyCell.setCellStyle(defaultCellStyle);

					cargoPremiumCell = row.createCell(6);
					cargoPremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getCargoPremium()));
					cargoPremiumCell.setCellStyle(currencyCellStyle);

					noOfTotalPolicyCell = row.createCell(7);
					noOfTotalPolicyCell.setCellValue(saleReport.getNoOfTotalpolicy());
					noOfTotalPolicyCell.setCellStyle(defaultCellStyle);

					totalPremiumCell = row.createCell(8);
					totalPremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getTotalPremium()));
					totalPremiumCell.setCellStyle(currencyCellStyle);

					remarkCell = row.createCell(9);
					remarkCell.setCellStyle(textCellStyle);
				}
				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 0));
				row = sheet.createRow(i);

				proposalTypeCell = row.createCell(0);
				proposalTypeCell.setCellValue("Total");
				proposalTypeCell.setCellStyle(defaultCellStyle);

				firePolicyCell = row.createCell(1);
				firePolicyCell.setCellValue(getFirePolicy());
				firePolicyCell.setCellStyle(defaultCellStyle);

				firePremiumCell = row.createCell(2);
				firePremiumCell.setCellValue(Utils.getCurrencyFormatString(getFirePremium()));
				firePremiumCell.setCellStyle(currencyCellStyle);

				motorPolicyCell = row.createCell(3);
				motorPolicyCell.setCellValue(getMotorPolicy());
				motorPolicyCell.setCellStyle(defaultCellStyle);

				motorPremiumCell = row.createCell(4);
				motorPremiumCell.setCellValue(Utils.getCurrencyFormatString(getMotorPremium()));
				motorPremiumCell.setCellStyle(currencyCellStyle);

				cargoPolicyCell = row.createCell(5);
				cargoPolicyCell.setCellValue(getCargoPolicy());
				cargoPolicyCell.setCellStyle(defaultCellStyle);

				cargoPremiumCell = row.createCell(6);
				cargoPremiumCell.setCellValue(Utils.getCurrencyFormatString(getCargoPremium()));
				cargoPremiumCell.setCellStyle(currencyCellStyle);

				noOfTotalPolicyCell = row.createCell(7);
				noOfTotalPolicyCell.setCellValue(getNoOfTotalPolicy());
				noOfTotalPolicyCell.setCellStyle(defaultCellStyle);

				totalPremiumCell = row.createCell(8);
				totalPremiumCell.setCellValue(Utils.getCurrencyFormatString(getTotalPremium()));
				totalPremiumCell.setCellStyle(currencyCellStyle);
				wb.setPrintArea(0, 0, 9, 0, i);
				wb.write(oStream);
				oStream.flush();
				oStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get all branch lists.
	 * 
	 * @return List[branch]
	 */
	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	/**
	 * get all currency lists.
	 * 
	 * @return List[currency]
	 */
	public List<Currency> getCurrencyList() {
		return currencyService.findAllCurrency();
	}

	/**
	 * set branch.
	 * 
	 * @param event
	 */
	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	// getter
	public AgentSaleComparisonCriteria getCriteria() {
		return criteria;
	}

	// setter
	public void setCriteria(AgentSaleComparisonCriteria criteria) {
		this.criteria = criteria;
	}

	// getter
	public List<AgentComparisonSalesReport> getReportList() {
		return reportList;
	}

	// getter
	public User getUser() {
		return user;
	}

	// getter
	public boolean isAccessBranch() {
		return isAccessBranch;
	}

}

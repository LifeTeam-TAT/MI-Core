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
import org.ace.insurance.report.agent.AgentMonthlyLifeSaleReport;
import org.ace.insurance.report.agent.service.interfaces.IAgentMonthlyLifeSaleReportService;
import org.ace.insurance.report.common.AgentMonthlyLifeSaleCriteria;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
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
 * @Date 12/05/2016.
 * @author Pyae Phyo Aung.
 * @Rev v1.0.
 * @CopyRight ACEPLUS SOLUTIONS CO., Ltd.
 *************************************************************************/

@ViewScoped
@ManagedBean(name = "AgentMonthlyLifeSaleReportActionBean")
public class AgentMonthlyLifeSaleReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentMonthlyLifeSaleReportService}")
	private IAgentMonthlyLifeSaleReportService agentMonthlyLifeSaleReportService;

	public void setAgentMonthlyLifeSaleReportService(IAgentMonthlyLifeSaleReportService agentMonthlyLifeSaleReportService) {
		this.agentMonthlyLifeSaleReportService = agentMonthlyLifeSaleReportService;
	}

	// To get System Current user.
	private User user;
	// To get Month Names List
	private EnumSet<MonthNames> monthSet;
	// To check current user is accessible for all branches
	private boolean isAccessBranches;
	// To filter required fields
	private AgentMonthlyLifeSaleCriteria criteria;
	// To get AgentMonthlyLifeSaleReport List
	private List<AgentMonthlyLifeSaleReport> reportList;

	// Define Report Directory Path and file Name
	private final String reportName = "agentSaleMonthlyReport_Life";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	/**
	 * When page load, get current user from session. Load Month Names. Create
	 * new criteria object.
	 * 
	 * @return void [Nothing].
	 */
	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		monthSet = EnumSet.allOf(MonthNames.class);
		resetCriteria();

	}

	/**
	 * Create new criteria object. Set criteria's month and year to system
	 * calendar month and year. Check current current is accessible for all
	 * branch or not. If not accessible for all, only show accessible branch's
	 * data.Create new AgentMonthlyLifeSaleReport List.
	 * 
	 * @return void [Nothing].
	 */
	public void resetCriteria() {
		criteria = new AgentMonthlyLifeSaleCriteria();
		Calendar cal = Calendar.getInstance();
		criteria.setMonth(new Date().getMonth());
		criteria.setYear(cal.get(Calendar.YEAR));
		criteria.setMonth(cal.get(Calendar.MONTH));
		// if (user.isAccessAllBranch()) {
		isAccessBranches = true;
		// } else {
		criteria.setBranch(user.getBranch());
		// }
		reportList = new ArrayList<AgentMonthlyLifeSaleReport>();
	}

	/**
	 * Search report list according to chosen criteria fields.
	 * 
	 * @return void [Nothing].
	 */
	public void search() {
		try {
			reportList = agentMonthlyLifeSaleReportService.findAgentMonthlyLifeSaleReport(criteria);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	/**
	 * Generate report to excel file.
	 * 
	 * @return void [Nothing].
	 */
	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "agentSaleMonthlyReport_Life.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(criteria.getYear(), Utils.getMonthString(criteria.getMonth()), reportList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export agentSaleMonthlyReport_Life.xlsx", e);
		}
	}

	private class ExportExcel {

		private int year;
		private String month;
		private List<AgentMonthlyLifeSaleReport> reportList;
		private XSSFWorkbook wb;

		// Constructor
		public ExportExcel(int year, String month, List<AgentMonthlyLifeSaleReport> reportList) {
			this.year = year;
			this.month = month;
			this.reportList = reportList;
			load();
		}

		/**
		 * Load predefine excel file.
		 * 
		 * @return void [nothing].
		 */
		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/agentSaleMonthlyReport_Life.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load  agentSaleMonthlyReport_Life.xlsx template", e);
			}
		}

		/**
		 * Generate report data to Microsoft excel.
		 * 
		 * @return void [nothing].
		 */
		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("agentSaleMonthlyReport");
				Row titleRow = sheet.getRow(0);
				Cell companyName = titleRow.getCell(0);
				companyName.setCellValue(ApplicationSetting.getCompanyLabel());

				Row reportNameRow = sheet.getRow(2);
				Cell reportNameCell = reportNameRow.getCell(0);
				String title = "Agent Monthly Sales Report for " + month + " " + year;
				reportNameCell.setCellValue(title);
				reportNameCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);

				Row branchRow = sheet.getRow(5);
				Cell branchCell = branchRow.getCell(0);
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
				branchCell.setCellValue(branch);

				CellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				CellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				CellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell noCell;
				Cell nameCell;
				Cell codeNoCell;
				Cell endowPolicyCell;
				Cell endowPremiumCell;
				Cell groupPolicyCell;
				Cell groupPremiumCell;
				Cell healthPolicyCell;
				Cell healthPremiumCell;
				Cell totalPolicyCell;
				Cell totalPremiumCell;

				int i = 7;
				int index = 0;
				for (AgentMonthlyLifeSaleReport saleReport : reportList) {
					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);

					noCell = row.createCell(0);
					noCell.setCellValue(index);
					noCell.setCellStyle(defaultCellStyle);

					nameCell = row.createCell(1);
					nameCell.setCellValue(saleReport.getAgentName());
					nameCell.setCellStyle(textCellStyle);

					codeNoCell = row.createCell(2);
					codeNoCell.setCellValue(saleReport.getAgentCodeNo());
					codeNoCell.setCellStyle(textCellStyle);

					endowPolicyCell = row.createCell(3);
					endowPolicyCell.setCellValue(saleReport.getEndowmentPolicy());
					endowPolicyCell.setCellStyle(defaultCellStyle);

					endowPremiumCell = row.createCell(4);
					endowPremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getEndowmentPremium()));
					endowPremiumCell.setCellStyle(currencyCellStyle);

					groupPolicyCell = row.createCell(5);
					groupPolicyCell.setCellValue(saleReport.getGroupPolicy());
					groupPolicyCell.setCellStyle(defaultCellStyle);

					groupPremiumCell = row.createCell(6);
					groupPremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getGroupPremium()));
					groupPremiumCell.setCellStyle(currencyCellStyle);

					healthPolicyCell = row.createCell(7);
					healthPolicyCell.setCellValue(saleReport.getHealthPolicy());
					healthPolicyCell.setCellStyle(defaultCellStyle);

					healthPremiumCell = row.createCell(8);
					healthPremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getHealthPremium()));
					healthPremiumCell.setCellStyle(currencyCellStyle);

					totalPolicyCell = row.createCell(9);
					totalPolicyCell.setCellValue(saleReport.getTotalPolicy());
					totalPolicyCell.setCellStyle(defaultCellStyle);

					totalPremiumCell = row.createCell(10);
					totalPremiumCell.setCellValue(Utils.getCurrencyFormatString(saleReport.getTotalPremium()));
					totalPremiumCell.setCellStyle(currencyCellStyle);
				}
				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
				row = sheet.createRow(i);

				nameCell = row.createCell(0);
				nameCell.setCellValue(" Total ");
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 2), sheet, wb);
				nameCell.setCellStyle(defaultCellStyle);

				endowPolicyCell = row.createCell(3);
				endowPolicyCell.setCellValue(getTotalEndowPolicy());
				endowPolicyCell.setCellStyle(defaultCellStyle);

				endowPremiumCell = row.createCell(4);
				endowPremiumCell.setCellValue(Utils.getCurrencyFormatString(getTotalEndowPremium()));
				endowPremiumCell.setCellStyle(currencyCellStyle);

				groupPolicyCell = row.createCell(5);
				groupPolicyCell.setCellValue(getTotalGroupPolicy());
				groupPolicyCell.setCellStyle(defaultCellStyle);

				groupPremiumCell = row.createCell(6);
				groupPremiumCell.setCellValue(Utils.getCurrencyFormatString(getTotalGroupPremium()));
				groupPremiumCell.setCellStyle(currencyCellStyle);

				healthPolicyCell = row.createCell(7);
				healthPolicyCell.setCellValue(getTotalHealthPolicy());
				healthPolicyCell.setCellStyle(defaultCellStyle);

				healthPremiumCell = row.createCell(8);
				healthPremiumCell.setCellValue(Utils.getCurrencyFormatString(getTotalHealthPremium()));
				healthPremiumCell.setCellStyle(currencyCellStyle);

				totalPolicyCell = row.createCell(9);
				totalPolicyCell.setCellValue(getTotalPolicy());
				totalPolicyCell.setCellStyle(defaultCellStyle);

				totalPremiumCell = row.createCell(10);
				totalPremiumCell.setCellValue(Utils.getCurrencyFormatString(getTotalPremium()));
				totalPremiumCell.setCellStyle(currencyCellStyle);

				wb.setPrintArea(0, 0, 10, 0, i);
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

		/**
		 * Get no of total endowment policy from report list.
		 * 
		 * @return int [No of total Endowment policy].
		 */
		public int getTotalEndowPolicy() {
			int totalEndowPolicy = 0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalEndowPolicy += report.getEndowmentPolicy();
			}
			return totalEndowPolicy;
		}

		/**
		 * Get total endowment Premium from report List.
		 * 
		 * @return double [Total Endowment Premium Amount].
		 */
		public double getTotalEndowPremium() {
			double totalEndowPremium = 0.0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalEndowPremium += report.getEndowmentPremium();
			}
			return totalEndowPremium;
		}

		/**
		 * Get no of total group policy from report list.
		 * 
		 * @return int [No of total Group policy].
		 */
		public int getTotalGroupPolicy() {
			int totalGroupPolicy = 0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalGroupPolicy += report.getGroupPolicy();
			}
			return totalGroupPolicy;
		}

		/**
		 * Get total group Premium from report List.
		 * 
		 * @return double [Total Group Premium Amount].
		 */
		public double getTotalGroupPremium() {
			double totalGroupPremium = 0.0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalGroupPremium += report.getGroupPremium();
			}
			return totalGroupPremium;
		}

		/**
		 * Get no of total health policy from report list.
		 * 
		 * @return int [No of total health policy].
		 */
		public int getTotalHealthPolicy() {
			int totalHealthPolicy = 0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalHealthPolicy += report.getHealthPolicy();
			}
			return totalHealthPolicy;
		}

		/**
		 * Get total health Premium from report List.
		 * 
		 * @return double [Total health Premium Amount].
		 */
		public double getTotalHealthPremium() {
			double totalHealthPremium = 0.0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalHealthPremium += report.getHealthPremium();
			}
			return totalHealthPremium;
		}

		/**
		 * Get no of total policy from report list.
		 * 
		 * @return int [Total no of policy].
		 */
		public int getTotalPolicy() {
			int totalPolicy = 0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalPolicy += report.getTotalPolicy();
			}
			return totalPolicy;
		}

		/**
		 * Get total Premium from report List.
		 * 
		 * @return double [Total Premium Amount].
		 */
		public double getTotalPremium() {
			double totalPremium = 0.0;
			for (AgentMonthlyLifeSaleReport report : reportList) {
				totalPremium += report.getTotalPremium();
			}
			return totalPremium;
		}
	}

	/**
	 * Generate report data to Pdf with jasper.
	 * 
	 * @return void [nothing].
	 */
	public void generatePdf() {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> params = new HashMap<String, Object>();
			String title;
			String branch;

			title = "Agent Monthly Sales Report for " + new ApplicationSetting().getMonthInString(criteria.getMonth()) + " " + criteria.getYear();
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

			params.put("title", title);
			params.put("branch", branch);
			params.put("dataSource", reportList);
			InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/agent/agentSaleMonthlyReport_Life.jrxml");
			JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
			JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
			jasperPrintList.add(policyJP);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			FileHandler.forceMakeDirectory(dirPath);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
			exporter.exportReport();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get file link.
	 * 
	 * @return String[file Directory and fileName].
	 */
	public String getStream() {
		String fileFullName = pdfDirPath + fileName;
		return fileFullName;
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

	// Getter.
	public EnumSet<MonthNames> getMonthSet() {
		return monthSet;
	}

	// Getter.
	public AgentMonthlyLifeSaleCriteria getCriteria() {
		return criteria;
	}

	// Setter.
	public void setCriteria(AgentMonthlyLifeSaleCriteria criteria) {
		this.criteria = criteria;
	}

	// Getter.
	public boolean isAccessBranches() {
		return isAccessBranches;
	}

	// Getter.
	public List<AgentMonthlyLifeSaleReport> getReportList() {
		return reportList;
	}

}

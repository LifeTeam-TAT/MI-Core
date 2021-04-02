package org.ace.insurance.web.manage.report.medical;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthMonthlyReport;
import org.ace.insurance.report.medical.service.interfaces.IHealthMonthlyReportService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "HealthMonthlyReportActionBean")
public class HealthMonthlyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{HealthMonthlyReportService}")
	private IHealthMonthlyReportService healthMonthlyReportService;

	public void setHealthMonthlyReportService(IHealthMonthlyReportService healthMonthlyReportService) {
		this.healthMonthlyReportService = healthMonthlyReportService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private boolean accessBranches;
	private MonthlyReportCriteria criteria;
	private List<HealthMonthlyReport> healthMonthlyReporList;
	private User user;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// }
		resetCriteria();
	}

	private void resetCriteria() {
		criteria = new MonthlyReportCriteria();
		if (!accessBranches) {
			criteria.setBranch(user.getBranch());
		}
		DateTime dateTime = new DateTime();
		criteria.setMonth(dateTime.getMonthOfYear() - 1);
		criteria.setYear(dateTime.getYear());
		try {
			healthMonthlyReporList = healthMonthlyReportService.findHealthMonthlyReport(criteria);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public MonthlyReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(MonthlyReportCriteria criteria) {
		this.criteria = criteria;
	}

	public void filter() {
		try {
			healthMonthlyReporList = healthMonthlyReportService.findHealthMonthlyReport(criteria);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
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

	public List<HealthMonthlyReport> getHealthMonthlyReporList() {
		return healthMonthlyReporList;
	}

	private double getTotalPremium() {
		double premium = 0.0;
		for (HealthMonthlyReport m : healthMonthlyReporList) {
			premium += m.getPremium();
		}
		return premium;
	}

	private double getTotalCommission() {
		double commission = 0.0;
		for (HealthMonthlyReport m : healthMonthlyReporList) {
			commission += m.getCommission();
		}
		return commission;
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "Medical_Monthly_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(criteria.getYear(), Utils.getMonthString(criteria.getMonth()), healthMonthlyReporList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export Health_Monthly_Report.xlsx", e);
		}
	}

	private class ExportExcel {
		private int year;
		private String month;
		private List<HealthMonthlyReport> healthMonthlyReportList;
		private XSSFWorkbook wb;
		private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		public ExportExcel(int year, String month, List<HealthMonthlyReport> healthMonthlyReportList) {
			this.year = year;
			this.month = month;
			this.healthMonthlyReportList = healthMonthlyReportList;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/Medical_Monthly_Report.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load Medical_Monthly_Report.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("Medical");

				Row titleRow = sheet.getRow(2);
				Cell titleCell = titleRow.getCell(0);
				titleCell.setCellValue(getMessage("HEALTH_MONTHLY_REPORT_TITLE", String.valueOf(year), month));

				Row dateRow = sheet.getRow(4);
				Cell dateCell = dateRow.getCell(23);
				dateCell.setCellValue(getMessage("HEALTH_MONTHLY_REPORT_DATE", dateFormat.format(new Date())));
				dateCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
				XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);

				Row row = null;
				Cell cell;

				int i = 6;
				int index = 0;
				for (HealthMonthlyReport report : healthMonthlyReportList) {
					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);
					cell = row.createCell(0);
					cell.setCellValue(index);
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(1);
					cell.setCellValue(report.getActivedPolicyStartDate());
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(2);
					cell.setCellValue(report.getPolicyNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(3);
					cell.setCellValue(report.getInsuredPersonName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(4);
					cell.setCellValue(report.getGender().getLabel());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(5);
					cell.setCellValue(report.getDateOfBirth());
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(6);
					cell.setCellValue(report.getAge());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(7);
					cell.setCellValue(report.getFullIdNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(8);
					cell.setCellValue(report.getOccupation());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(9);
					cell.setCellValue(report.getAddress());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(10);
					cell.setCellValue(report.getPaymentType());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(11);
					cell.setCellValue(report.getPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(12);
					cell.setCellValue(report.getBeneficiaryName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(13);
					cell.setCellValue(report.getRelationship());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(14);
					cell.setCellValue("");
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(15);
					cell.setCellValue("");
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(16);
					cell.setCellValue("");
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(17);
					cell.setCellValue(report.getUnit());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(18);
					cell.setCellValue(report.getBasicPlusUnit());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(19);
					cell.setCellValue(report.getAddOn1());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(20);
					cell.setCellValue(report.getAddOn2());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(21);
					cell.setCellValue(report.getSalePersonName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(22);
					// fix later
					// cell.setCellValue(report.getCustomerType().getLabel());
					cell.setCellValue("");
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(23);
					cell.setCellValue(report.getSalePersonType());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(24);
					cell.setCellValue(report.getCommission());
					cell.setCellStyle(currencyCellStyle);
				}
				String strFormula;
				Font font = wb.createFont();
				font.setFontName("Myanmar3");

				i = i + 1;
				row = sheet.createRow(i);

				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 10));

				cell = row.createCell(0);
				cell.setCellValue("Grand Total Premium");
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 10), sheet, wb);
				cell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
				cell.getCellStyle().setFont(font);

				cell = row.createCell(11);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(L8:L" + i + ")";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				sheet.addMergedRegion(new CellRangeAddress(i, i, 12, 23));

				cell = row.createCell(12);
				cell.setCellValue("Grand Total Commission");
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 12, 23), sheet, wb);
				cell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
				cell.getCellStyle().setFont(font);

				cell = row.createCell(24);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(Y8:Y" + i + ")";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				wb.setPrintArea(0, 0, 24, 0, i);
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

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public void setAccessBranches(boolean accessBranches) {
		this.accessBranches = accessBranches;
	}

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

}

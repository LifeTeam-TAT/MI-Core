package org.ace.insurance.web.manage.report.medical;

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
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalInusuredPersonMonthlyReportDTO;
import org.ace.insurance.report.medical.service.interfaces.IMedicalInsuredPersonMonthlyReportService;
import org.ace.insurance.system.common.branch.Branch;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "MedicalInsuredPersonMonthlyReportActionBean")
public class MedicalInsuredPersonMonthlyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{MedicalInsuredPersonMonthlyReportService}")
	private IMedicalInsuredPersonMonthlyReportService healthMonthlyReportService;

	public void setHealthMonthlyReportService(IMedicalInsuredPersonMonthlyReportService healthMonthlyReportService) {
		this.healthMonthlyReportService = healthMonthlyReportService;
	}

	private boolean accessBranches;
	private MonthlyReportCriteria criteria;
	private List<MedicalInusuredPersonMonthlyReportDTO> healthMonthlyReporList;
	private User user;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void init() {
		DateTime dateTime = new DateTime();
		criteria = new MonthlyReportCriteria();
		criteria.setMonth(new Date().getMonth());
		criteria.setYear(dateTime.getYear());
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// } else {
		criteria.setBranch(user.getBranch());
		// }
		healthMonthlyReporList = new ArrayList<MedicalInusuredPersonMonthlyReportDTO>();

	}

	public MonthlyReportCriteria getCriteria() {
		return criteria;
	}

	public void filter() {
		try {
			healthMonthlyReporList = healthMonthlyReportService.find(criteria);
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

	public List<MedicalInusuredPersonMonthlyReportDTO> getHealthMonthlyReporList() {
		return healthMonthlyReporList;
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "MedicalMonthly_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(criteria.getYear(), Utils.getMonthString(criteria.getMonth()), healthMonthlyReporList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export Medical_Monthly_Report_Insu.xlsx", e);
		}
	}

	private class ExportExcel {
		private int year;
		private String month;
		private List<MedicalInusuredPersonMonthlyReportDTO> healthMonthlyReportList;
		private XSSFWorkbook wb;
		String date = Utils.getDateFormatString(new Date());

		public ExportExcel(int year, String month, List<MedicalInusuredPersonMonthlyReportDTO> healthMonthlyReportList) {
			this.year = year;
			this.month = month;
			this.healthMonthlyReportList = healthMonthlyReportList;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/MedicalMonthly_Report.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load MedicalMonthly_Report.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("MedInsuMonthlyReport");

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell cell;

				row = sheet.getRow(0);
				cell = row.createCell(0);
				cell.setCellValue(ApplicationSetting.getCompanyLabel());
				cell.setCellStyle(defaultCellStyle);

				row = sheet.getRow(2);
				cell = row.getCell(0);
				cell.setCellValue(year + " \u1001\u102F\u1014\u103E\u1005\u103A " + month
						+ " \u101C \u1021\u1010\u103D\u1000\u103A \u1021\u102C\u1019\u1001\u1036\u101C\u1001\u103B\u102F\u1015\u103A\u1005\u102C\u101B\u1004\u103A\u1038 ");

				row = sheet.getRow(4);
				cell = row.createCell(0);
				cell.setCellValue("အစီရင်ခံတင်ပြသည့်နေ့ " + date);
				cell.setCellStyle(defaultCellStyle);

				int i = 6;
				int index = 0;
				for (MedicalInusuredPersonMonthlyReportDTO report : healthMonthlyReportList) {
					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);

					cell = row.createCell(0);
					cell.setCellValue(index);
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(1);
					cell.setCellValue(report.getPolicyStartDate());
					cell.setCellStyle(dateCellStyle);

					sheet.addMergedRegion(new CellRangeAddress(i, i, 2, 3));
					ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 2, 3), sheet, wb);
					cell = row.createCell(2);
					cell.setCellValue(report.getPolicyNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(4);
					cell.setCellValue(report.getInsuredName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(5);
					cell.setCellValue(report.getNrc());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(6);
					cell.setCellValue(report.getGender());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(7);
					cell.setCellValue(report.getDateofBirth());
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(8);
					cell.setCellValue(report.getAge());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(9);
					cell.setCellValue(report.getAddress());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(10);
					cell.setCellValue(report.getOccupation());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(11);
					cell.setCellValue("");
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(12);
					cell.setCellValue(report.getDisease());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(13);
					cell.setCellValue(report.getUnit());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(14);
					cell.setCellValue(report.getBasicPlusUnit());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(15);
					cell.setCellValue(report.getAddOnUnit());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(16);
					cell.setCellValue(report.getTotalUnit());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(17);
					cell.setCellValue(report.getPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(18);
					cell.setCellValue(report.getBasicPlusPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(19);
					cell.setCellValue(report.getAddOnPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(20);
					cell.setCellValue(report.getTotalPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(21);
					cell.setCellValue(report.getBranch());
					cell.setCellStyle(textCellStyle);

				}
				wb.setPrintArea(0, 0, 21, 0, i);
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

}

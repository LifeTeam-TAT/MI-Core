package org.ace.insurance.web.manage.report.stampFees;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
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
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.report.stampfee.StampFeeCriteria;
import org.ace.insurance.report.stampfee.StampFeeReport;
import org.ace.insurance.report.stampfee.service.interfaces.IStampFeeService;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ViewScoped
@ManagedBean(name = "StampFeeReportActionBean")
public class StampFeeReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{StampFeeReportService}")
	private IStampFeeService stampFeeService;

	public void setStampFeeService(IStampFeeService stampFeeService) {
		this.stampFeeService = stampFeeService;
	}

	private StampFeeCriteria stampFeeCriteria;
	private List<StampFeeReport> stampFeeReportList;

	@PostConstruct
	public void init() {
		loadCriteria();
	}

	public void loadCriteria() {
		stampFeeCriteria = new StampFeeCriteria();
		if (stampFeeCriteria.getStartDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			stampFeeCriteria.setStartDate(cal.getTime());
		}
		if (stampFeeCriteria.getEndDate() == null) {
			Date endDate = new Date();
			stampFeeCriteria.setEndDate(endDate);
		}
		stampFeeReportList = stampFeeService.find(stampFeeCriteria);
	}

	public double totalReportSumInsured() {
		double total = 0.0;
		for (StampFeeReport stampFeeReport : stampFeeReportList) {
			total += stampFeeReport.getSumInsured();
		}
		return total;
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

	public void filter() {
		stampFeeReportList = stampFeeService.find(stampFeeCriteria);
	}

	public PolicyReferenceType[] getPolicyReferenceType() {
		return PolicyReferenceType.values();
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "Stamp_Fee_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(stampFeeReportList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export Fire_Monthly_Report.xlsx", e);
		}
	}

	private class ExportExcel {
		private List<StampFeeReport> stampFeeReportList;
		private XSSFWorkbook wb;

		public ExportExcel(List<StampFeeReport> stampFeeReportList) {
			this.stampFeeReportList = stampFeeReportList;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/stampFee/Stamp_Fee_Report.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load Stamp_Fee_Report.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("StampFee");
				ExcelUtils.fillCompanyLogo(wb, sheet, 11);
				Row titleRow = sheet.getRow(0);
				Cell title = titleRow.getCell(0);
				title.setCellValue(ApplicationSetting.getCompanyLabel() + " \n Policy Stamp Register");
				int i = 2;
				int index = 0;

				Row row = null;
				Cell noCell = null;
				Cell cusNameCell = null;
				String customer = null;
				Cell policyNoCell = null;
				Cell engineCell = null;
				Cell premiumCell = null;
				Cell detail5 = null;
				Cell detail10 = null;
				Cell detail50 = null;
				Cell detail100 = null;
				Cell detail500 = null;
				Cell detail1000 = null;
				Cell total = null;
				Cell netPrmCell = null;
				Cell signature = null;
				XSSFCellStyle textStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle currencyStyle = ExcelUtils.getCurrencyCellStyle(wb);

				for (StampFeeReport sfr : stampFeeReportList) {
					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);
					// index
					noCell = row.createCell(0);
					noCell.setCellValue(index);
					noCell.setCellStyle(textStyle);
					// insured name
					cusNameCell = row.createCell(1);
					customer = "";
					if (sfr.getCustomerId() != null) {
						customer = sfr.getCustomerName();
					} else if (sfr.getOrganizationId() != null) {
						customer = sfr.getOrganizationName();
					} else if (sfr.getBankCustomerId() != null) {
						customer = sfr.getBankCustomerName();
					}
					cusNameCell.setCellValue(customer);
					cusNameCell.setCellStyle(textStyle);
					// policy no
					policyNoCell = row.createCell(2);
					policyNoCell.setCellValue(sfr.getPolicyNo());
					policyNoCell.setCellStyle(textStyle);

					// sumInsured
					engineCell = row.createCell(3);
					engineCell.setCellValue(sfr.getSumInsured());
					engineCell.setCellStyle(currencyStyle);

					// stamp Value
					premiumCell = row.createCell(4);
					premiumCell.setCellValue(sfr.getStampFees());
					premiumCell.setCellStyle(currencyStyle);

					// 5
					detail5 = row.createCell(5);
					detail5.setCellValue("");
					detail5.setCellStyle(textStyle);
					// 10
					detail10 = row.createCell(6);
					detail10.setCellValue("");
					detail10.setCellStyle(textStyle);
					// 50
					detail50 = row.createCell(7);
					detail50.setCellValue("");
					detail50.setCellStyle(textStyle);
					// 100
					detail100 = row.createCell(8);
					detail100.setCellValue("");
					detail100.setCellStyle(textStyle);
					// 500
					detail500 = row.createCell(9);
					detail500.setCellValue("");
					detail500.setCellStyle(textStyle);

					// 1000
					detail1000 = row.createCell(10);
					detail1000.setCellValue("");
					detail1000.setCellStyle(textStyle);

					// Total
					total = row.createCell(11);
					total.setCellValue("");
					total.setCellStyle(textStyle);

					// remark
					netPrmCell = row.createCell(12);
					netPrmCell.setCellValue(sfr.getRemark());
					netPrmCell.setCellStyle(currencyStyle);

					// signature
					signature = row.createCell(13);
					signature.setCellValue("");
					signature.setCellStyle(textStyle);

					// Cell policyCell = row.createCell(6);
					// policyCell.setCellValue(" ");
					// policyCell.setCellStyle(getDefaultCell());
				}
				wb.setPrintArea(0, 0, 13, 0, i);
				wb.write(op);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public StampFeeCriteria getStampFeeCriteria() {
		return stampFeeCriteria;
	}

	public void setStampFeeCriteria(StampFeeCriteria stampFeeCriteria) {
		this.stampFeeCriteria = stampFeeCriteria;
	}

	public List<StampFeeReport> getStampFeeReportList() {
		return stampFeeReportList;
	}
}

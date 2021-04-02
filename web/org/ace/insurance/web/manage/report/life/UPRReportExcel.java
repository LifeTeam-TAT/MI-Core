package org.ace.insurance.web.manage.report.life;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.report.life.UPRReportCriteria;
import org.ace.insurance.report.life.view.UPRReportView;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.insurance.web.common.myanmarLanguae.MyanmarLanguae;
import org.ace.java.component.SystemException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UPRReportExcel {

	private XSSFWorkbook wb;

	public UPRReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/upr/UPR_Report.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load UPRReport.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<UPRReportView> uprReportView, UPRReportCriteria criteria) {
		try {
			Sheet sheet = wb.getSheet("UPR");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
			XSSFCellStyle getFontBoldAlignCenterStyle = ExcelUtils.getFontBoldAlignCenterStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);

			Row row;
			Cell cell;

			row = sheet.getRow(2);
			cell = row.createCell(0);

			Date startDate = criteria.getStartDate();
			Date endDate = criteria.getEndDate();
			cell.setCellValue(Utils.getYearFormat(startDate) + " " + MyanmarLanguae.getMyanmarLanguaeString("YEAR_MYANMAR_017") + " "
					+ Utils.getMonthStringWithLowerCase(startDate.getMonth()) + " " + MyanmarLanguae.getMyanmarLanguaeString("UPR_MONTH_MYANMAR_017") + " " + startDate.getDate()
					+ " " + MyanmarLanguae.getMyanmarLanguaeString("UPR_FROM_DAY_MYANMAR_017") + " " + Utils.getYearFormat(endDate) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("YEAR_MYANMAR_017") + " " + Utils.getMonthStringWithLowerCase(endDate.getMonth()) + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("UPR_MONTH_MYANMAR_017") + " " + +endDate.getDate() + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("UPR_TO_DAY_MYANMAR_017"));
			cell.setCellStyle(getFontBoldAlignCenterStyle);

			int i = 4;
			int index = 0;
			String premiumFormula = "";

			for (UPRReportView upr : uprReportView) {
				i = i + 1;
				index = index + 1;
				row = sheet.createRow(i);
				// index
				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				// Product Name
				cell = row.createCell(1);
				cell.setCellValue(upr.getProductName());
				cell.setCellStyle(textCellStyle);

				// Policy No
				cell = row.createCell(2);
				cell.setCellValue(upr.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				// Policy Start Date
				cell = row.createCell(3);
				cell.setCellValue(DateUtils.getDateFormatStringStartWithMonth(upr.getActivedPolicyStartDate()));
				cell.setCellStyle(dateCellStyle);

				// Policy End Date
				cell = row.createCell(4);
				cell.setCellValue(DateUtils.getDateFormatStringStartWithMonth(upr.getActivedPolicyEndDate()));
				cell.setCellStyle(dateCellStyle);

				// TermPremium
				cell = row.createCell(5);
				cell.setCellValue(upr.getTermPremium());
				cell.setCellStyle(currencyCellStyle);

				// Payment Type
				cell = row.createCell(6);
				cell.setCellValue(upr.getPaymentType());
				cell.setCellStyle(textCellStyle);

				// coveratge Date
				cell = row.createCell(7);
				cell.setCellValue(DateUtils.getDateFormatStringStartWithMonth(upr.getCoverageDate()));
				cell.setCellStyle(dateCellStyle);

			}

			i = i + 1;
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 4));
			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue("Total : ");
			cell.setCellStyle(textCellStyle);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 4), sheet, wb);

			cell = row.createCell(5);
			cell.setCellStyle(currencyCellStyle);
			premiumFormula = "SUM(F6:F" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(premiumFormula);

			wb.setPrintArea(0, 0, 7, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

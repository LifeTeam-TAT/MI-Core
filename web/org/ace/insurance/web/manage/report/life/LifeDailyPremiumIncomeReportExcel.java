package org.ace.insurance.web.manage.report.life;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.life.LifeDailyPremiumIncomeReportDTO;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LifeDailyPremiumIncomeReportExcel {

	private XSSFWorkbook wb;
	// private FormulaEvaluator evaluator ;

	public LifeDailyPremiumIncomeReportExcel() {
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/life/Life_Daily_Premium_Income_Report.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load Life_Daily_Premium_Income_Report.xlsx tempalte", e);
		}
	}

	public void generate(OutputStream op, List<LifeDailyPremiumIncomeReportDTO> lifeDailyIncomeList, Date startDate, Date endDate) {
		try {
			Sheet sheet = wb.getSheet("LifeDailyPremiumIncome");
			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
			XSSFCellStyle textAlignRightStyle = ExcelUtils.getTextAlignRightStyle(wb);

			Row row;
			Cell cell;
			row = sheet.getRow(0);
			cell = row.getCell(0);

			int i = 2;
			int index = 0;
			String premiumFormula = "";

			/* Start (For Date Row) */
			row = sheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue("From Date :");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(1);
			cell.setCellValue(startDate);
			cell.setCellStyle(dateCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("To Date :");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(3);
			cell.setCellValue(endDate);
			cell.setCellStyle(dateCellStyle);
			/* End (For Date Row) */

			for (LifeDailyPremiumIncomeReportDTO report : lifeDailyIncomeList) {
				i = i + 1;
				index = index + 1;
				row = sheet.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(1);
				cell.setCellValue(report.getPaymentDate());
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(2);
				if (report.getPersonsCount() > 1) {
					cell.setCellValue(report.getInsuredPersonName() + "+" + (report.getPersonsCount() - 1));
				} else
					cell.setCellValue(report.getInsuredPersonName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(3);
				cell.setCellValue(report.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(4);
				cell.setCellValue(report.getReceiptNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(5);
				cell.setCellValue(" Due- " + report.getToTerm());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(6);
				cell.setCellValue(report.getPaymentType());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(7);
				cell.setCellValue(report.getNetPremium());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(8);
				cell.setCellValue(report.getRemark());
				cell.setCellStyle(currencyCellStyle);
			}

			i = i + 1;
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
			row = sheet.createRow(i);
			cell = row.createCell(0);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);

			cell.setCellValue("Total");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			premiumFormula = "SUM(H4:H" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(premiumFormula);

			cell = row.createCell(8);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

			wb.setPrintArea(0, 0, 8, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
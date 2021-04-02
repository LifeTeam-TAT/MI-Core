package org.ace.insurance.web.manage.report.sportMan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.sportMan.SportManMonthlyReportDTO;
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

public class SportManMonthlyReportExcel {

	private XSSFWorkbook wb;

	public SportManMonthlyReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/sportMan/SportMan_MonthlyReport.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load SportMan_MonthlyReport.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<SportManMonthlyReportDTO> sportManMonthlyReportList, MonthlyIncomeReportCriteria criteria) {
		try {
			Sheet sheet1 = wb.getSheet("SMR");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
			XSSFCellStyle centerCellStyle = ExcelUtils.getAlignCenterStyle(wb);

			Row row;
			Cell cell;

			row = sheet1.getRow(2);
			cell = row.createCell(0);

			cell.setCellValue(Utils.getDateFormatString(criteria.getStartDate()) + " " + MyanmarLanguae.getMyanmarLanguaeString("FROM_MYANMAR_002") + " "
					+ Utils.getDateFormatString(criteria.getEndDate()) + " " + MyanmarLanguae.getMyanmarLanguaeString("TO_MYANMAR_003")
					+ MyanmarLanguae.getMyanmarLanguaeString("SPORTMAN_MONTHLY_REPORTH_HEADER_001"));
			cell.setCellStyle(centerCellStyle);

			int i = 4;
			int index = 0;
			String siFormula = "";
			String oneYearpremiumFormula = "";
			String termPremiumFormula = "";
			String commissionFormula = "";

			for (SportManMonthlyReportDTO monthlyDto : sportManMonthlyReportList) {
				i = i + 1;
				index = index + 1;
				row = sheet1.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(1);
				cell.setCellValue(monthlyDto.getInsuredPersonName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(2);
				cell.setCellValue(monthlyDto.getResidentAddress());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(3);
				cell.setCellValue(monthlyDto.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(4);
				cell.setCellValue(monthlyDto.getTypeOfSport() == null ? "-" : monthlyDto.getTypeOfSport());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(5);
				cell.setCellValue(monthlyDto.getSumInsured());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(6);
				cell.setCellValue(monthlyDto.getAmount());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(7);
				cell.setCellValue(0.0);
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(8);
				cell.setCellValue(monthlyDto.getReceiptNo() + " \n [" + Utils.getDateFormatString(monthlyDto.getPaymentDate()) + "]");
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(9);
				cell.setCellValue(monthlyDto.getCommission());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(10);
				cell.setCellValue(monthlyDto.getAgentName() == null ? "NA[-]" : monthlyDto.getAgentName() + " \n [" + monthlyDto.getLiscenseNo() + "]");
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(11);
				cell.setCellValue(monthlyDto.getFromDateToDate());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(12);
				cell.setCellValue(monthlyDto.getFromTermToTerm());
				cell.setCellStyle(textCellStyle);
				
				cell = row.createCell(13);
				cell.setCellValue(monthlyDto.getSaleChannelType().toString());
				cell.setCellStyle(textCellStyle);
			}

			i = i + 1;
			sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 4));
			row = sheet1.createRow(i);
			cell = row.createCell(0);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 4), sheet1, wb);

			cell.setCellValue("Total");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(5);
			cell.setCellStyle(currencyCellStyle);
			siFormula = "SUM(F4:F" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(siFormula);

			cell = row.createCell(6);
			cell.setCellStyle(currencyCellStyle);
			oneYearpremiumFormula = "SUM(G4:G" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(oneYearpremiumFormula);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			termPremiumFormula = "SUM(H4:H" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(termPremiumFormula);

			cell = row.createCell(8);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

			cell = row.createCell(9);
			cell.setCellStyle(currencyCellStyle);
			commissionFormula = "SUM(J4:J" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(commissionFormula);

			cell = row.createCell(10);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

			cell = row.createCell(11);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

			cell = row.createCell(12);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

			cell = row.createCell(13);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");
			
		

			wb.setPrintArea(0, 0, 13, 0, i);

			wb.write(op);
			op.flush();
			op.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

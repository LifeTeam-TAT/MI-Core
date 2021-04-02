package org.ace.insurance.web.manage.report.life;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.insurance.web.common.myanmarLanguae.MyanmarLanguae;
import org.ace.insurance.web.manage.report.shortEndowLife.ShortEndownLifeMonthlyReportDTO;
import org.ace.java.component.SystemException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PublicLifeMonthlyReportExcel {

	private XSSFWorkbook wb;

	public PublicLifeMonthlyReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/shortTermEndowLife/ShortEndownment_MonthlyReport.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load ShortEndownment_MonthlyReport.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<ShortEndownLifeMonthlyReportDTO> publicLifeMonthlyReportList, MonthlyIncomeReportCriteria criteria) {
		try {
			Sheet sheet1 = wb.getSheet("MonthlySummaryReport");

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
					+ MyanmarLanguae.getMyanmarLanguaeString("PUBLICLIFE_MONTHLY_REPORTH_HEADER_001"));
			cell.setCellStyle(centerCellStyle);

			int i = 3;
			int index = 0;
			String siFormula = "";
			String premiumFormula = "";
			String commissionFormula = "";

			for (ShortEndownLifeMonthlyReportDTO monthlyDto : publicLifeMonthlyReportList) {
				i = i + 1;
				index = index + 1;
				row = sheet1.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(1);
				cell.setCellValue(monthlyDto.getInsuredPerson());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(2);
				cell.setCellValue(monthlyDto.getAge());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(3);
				cell.setCellValue(monthlyDto.getPolicyTerm());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(4);
				cell.setCellValue(monthlyDto.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(5);
				cell.setCellValue(monthlyDto.getAddress());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(6);
				cell.setCellValue(monthlyDto.getSuminsured());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(7);
				cell.setCellValue(monthlyDto.getPremium());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(8);
				cell.setCellValue(monthlyDto.getPaymentType());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(9);
				cell.setCellValue(monthlyDto.getCommission());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(10);
				cell.setCellValue(monthlyDto.getReceiptNo() + " \n [" + Utils.getDateFormatString(monthlyDto.getPaymentDate()) + "]");
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(11);
				cell.setCellValue(monthlyDto.getAgentName() == null ? "NA[-]" : monthlyDto.getAgentName() + " \n [" + monthlyDto.getLiscenseNo() + "]");
				cell.setCellStyle(textCellStyle);

				/*
				 * cell = row.createCell(12); cell.setCellValue(monthlyDto.getSalePointsName());
				 * cell.setCellStyle(textCellStyle);
				 */
				cell = row.createCell(12);
				cell.setCellValue(Utils.formattedDate(monthlyDto.getActivedPolicyStartDate()) + "-  " + Utils.formattedDate(monthlyDto.getActivedPolicyEndDate()));
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(13);
				cell.setCellValue(monthlyDto.getFromTermToTerm());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(14);
				cell.setCellValue(monthlyDto.getSaleChannelType().toString());
				cell.setCellStyle(textCellStyle);
			}

			i = i + 1;
			sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 5));
			row = sheet1.createRow(i);
			cell = row.createCell(0);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 5), sheet1, wb);

			cell.setCellValue("Total");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(6);
			cell.setCellStyle(currencyCellStyle);
			siFormula = "SUM(G4:G" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(siFormula);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			premiumFormula = "SUM(H4:H" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(premiumFormula);

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

			cell = row.createCell(14);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

		
			wb.setPrintArea(0, 0, 14, 0, i);

			wb.write(op);
			op.flush();
			op.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

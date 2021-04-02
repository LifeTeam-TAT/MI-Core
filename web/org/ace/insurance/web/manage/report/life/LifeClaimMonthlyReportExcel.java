package org.ace.insurance.web.manage.report.life;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.claim.LCL001;
import org.ace.insurance.report.claim.LifeClaimMonthlyReportDTO;
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

public class LifeClaimMonthlyReportExcel {

	private XSSFWorkbook wb;

	public LifeClaimMonthlyReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/claim/LifeClaim_MonthlyReport.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to loadLifeClaim_MonthlyReport.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<LifeClaimMonthlyReportDTO> lifeClaimMonthlyReportList, LCL001 criteria) {
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
					+ Utils.getDateFormatString(criteria.getEndDate()) + " " + MyanmarLanguae.getMyanmarLanguaeString("TO_MYANMAR_003") + "("
					+ criteria.getLifeProdutType().getLabel() + ")");
			cell.setCellStyle(centerCellStyle);

			int i = 3;
			int index = 0;
			String siFormula = "";
			String premiumFormula = "";
			String commissionFormula = "";

			for (LifeClaimMonthlyReportDTO monthlyDto : lifeClaimMonthlyReportList) {
				i = i + 1;
				index = index + 1;
				row = sheet1.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				// Date
				cell = row.createCell(1);
				cell.setCellValue(new Date());
				cell.setCellStyle(dateCellStyle);

				// Name
				cell = row.createCell(2);
				cell.setCellValue(monthlyDto.getInsuredPersonName());
				cell.setCellStyle(defaultCellStyle);

				// Claim Date
				cell = row.createCell(3);
				cell.setCellValue(monthlyDto.getClaimDate());
				cell.setCellStyle(dateCellStyle);

				// CN
				cell = row.createCell(4);
				cell.setCellValue(monthlyDto.getClaimNo());
				cell.setCellStyle(textCellStyle);

				// Policy Name
				cell = row.createCell(5);
				cell.setCellValue(monthlyDto.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				// Policy Period
				cell = row.createCell(6);
				cell.setCellValue(Utils.getDateFormatString(monthlyDto.getPolicyStartDate()) + " to " + Utils.getDateFormatString(monthlyDto.getPolicyEndDate()));
				cell.setCellStyle(textCellStyle);

				// Claimed
				cell = row.createCell(7);
				cell.setCellValue(monthlyDto.getClaimAmount());
				cell.setCellStyle(currencyCellStyle);

				// MedicalFess
				cell = row.createCell(8);
				cell.setCellValue("-");
				cell.setCellStyle(textCellStyle);

				// Total(Ks)
				cell = row.createCell(9);
				cell.setCellValue(monthlyDto.getClaimAmount());
				cell.setCellStyle(currencyCellStyle);

				// Paid Date
				cell = row.createCell(10);
				cell.setCellValue(monthlyDto.getPaidDate());
				cell.setCellStyle(dateCellStyle);

				// Injury/Death
				cell = row.createCell(11);
				cell.setCellValue(monthlyDto.getCauseOfReason());
				cell.setCellStyle(textCellStyle);

				// Ratio
				cell = row.createCell(12);
				cell.setCellValue(monthlyDto.getPercentage());
				cell.setCellStyle(defaultCellStyle);

				// Remark
				cell = row.createCell(13);
				cell.setCellValue(monthlyDto.getBranchCode());
				cell.setCellStyle(textCellStyle);
				// suminsured
				cell = row.createCell(14);
				cell.setCellValue(monthlyDto.getSuminsured());
				cell.setCellStyle(currencyCellStyle);

				// Payment Type
				cell = row.createCell(15);
				cell.setCellValue(monthlyDto.getPaymentType());
				cell.setCellStyle(textCellStyle);

				// Premium
				cell = row.createCell(16);
				cell.setCellValue(monthlyDto.getPremium());
				cell.setCellStyle(currencyCellStyle);
			}

			i = i + 1;
			sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 5));
			row = sheet1.createRow(i);
			cell = row.createCell(0);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 5), sheet1, wb);

			cell.setCellValue("Total");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			siFormula = "SUM(H4:H" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(siFormula);

			cell = row.createCell(9);
			cell.setCellStyle(currencyCellStyle);
			siFormula = "SUM(J4:J" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(siFormula);

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

			cell = row.createCell(15);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

			cell = row.createCell(16);
			cell.setCellStyle(defaultCellStyle);
			cell.setCellValue("-");

			wb.setPrintArea(0, 0, 16, 0, i);

			wb.write(op);
			op.flush();
			op.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

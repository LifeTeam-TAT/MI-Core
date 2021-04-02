package org.ace.insurance.web.manage.report.student;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.report.TLF.StudentMontlyIncomeReportDTO;
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

public class StudentMontlyIncomeReportExcel {

	private XSSFWorkbook wb;

	public StudentMontlyIncomeReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/studentLife/StudentMonthlyReport.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load StudentMonthlyReport.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<StudentMontlyIncomeReportDTO> studentmonthlyIncomeReportDTOList) {
		try {
			Sheet sheet1 = wb.getSheet("MonthlySummaryReport");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
			XSSFCellStyle centerCellStyle = ExcelUtils.getAlignCenterStyle(wb);

			Row row;
			Cell cell;
			String year = "";
			String month = "";

			row = sheet1.getRow(2);
			cell = row.createCell(0);

			Date date = studentmonthlyIncomeReportDTOList.get(0).getPaymentDate();
			if (date != null) {
				year = Utils.getYearFormat(date);
				month = Utils.getMonthStringWithLowerCase(date.getMonth());
			}
			cell.setCellValue(year + " " + MyanmarLanguae.getMyanmarLanguaeString("YEAR_MYANMAR_002") + " " + month + " "
					+ MyanmarLanguae.getMyanmarLanguaeString("MONTH_MYANMAR_003") + MyanmarLanguae.getMyanmarLanguaeString("STUDENTLIFE_MONTHLY_HEADER_001"));
			cell.setCellStyle(centerCellStyle);

			int i = 3;
			int index = 0;
			String siFormula = "";
			String premiumFormula = "";
			String commissionFormula = "";
			String premiumendDate = "";
			String premiumstartDate = "";

			for (StudentMontlyIncomeReportDTO monthlyDto : studentmonthlyIncomeReportDTOList) {
				Date endDate = DateUtils.proposalEndtDate(monthlyDto.getActivePolicyendDate(), -3);
				Date startDate = monthlyDto.getActivePolicystartDate();
				premiumendDate = Utils.formattedDate(endDate);
				premiumstartDate = Utils.formattedDate(startDate);

				i = i + 1;
				index = index + 1;
				row = sheet1.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(1);
				cell.setCellValue(monthlyDto.getCustomerName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(2);
				cell.setCellValue(monthlyDto.getInsuredPerson());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(3);
				cell.setCellValue(monthlyDto.getAge());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(4);
				cell.setCellValue(monthlyDto.getPolicyTerm());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(5);
				cell.setCellValue(monthlyDto.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(6);
				cell.setCellValue(monthlyDto.getAddress());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(7);
				cell.setCellValue(monthlyDto.getSuminsured());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(8);
				cell.setCellValue(monthlyDto.getPremium());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(9);
				cell.setCellValue(monthlyDto.getPaymentType());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(10);
				cell.setCellValue(monthlyDto.getCommission());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(11);
				String receiptDate = " [".concat(monthlyDto.getPaymentDate() != null ? Utils.formattedDate(monthlyDto.getPaymentDate()) : "").concat("]");
				String receiptNo = monthlyDto.getReceiptNo() == null ? "" : monthlyDto.getReceiptNo();
				String receiptNoAndDate = receiptNo.concat(" \n").concat(receiptDate);
				cell.setCellValue(receiptNoAndDate);
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(12);
				cell.setCellValue(monthlyDto.getAgentName() == null ? "NA[-]" : monthlyDto.getAgentName());
				cell.setCellStyle(textCellStyle);

				/*
				 * cell = row.createCell(13); cell.setCellValue(monthlyDto.getSalePointsName());
				 * cell.setCellStyle(textCellStyle);
				 */

				cell = row.createCell(13);
				cell.setCellValue(Utils.formattedDate(monthlyDto.getActivePolicystartDate()).concat("-").concat(Utils.formattedDate(monthlyDto.getActivePolicyendDate())));
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(14);
				cell.setCellValue(monthlyDto.getFromTermToTerm());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(15);
				cell.setCellValue(monthlyDto.getSaleChannelType().toString());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(16);
				cell.setCellValue("(" + monthlyDto.getPremiumPeriod() + " )" + premiumstartDate + "-" + premiumendDate);
				cell.setCellStyle(textCellStyle);

			}

			i = i + 1;
			sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
			row = sheet1.createRow(i);
			cell = row.createCell(0);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet1, wb);

			cell.setCellValue("Total");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			siFormula = "SUM(H4:H" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(siFormula);

			cell = row.createCell(8);
			cell.setCellStyle(currencyCellStyle);
			premiumFormula = "SUM(I4:I" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(premiumFormula);

			cell = row.createCell(10);
			cell.setCellStyle(currencyCellStyle);
			commissionFormula = "SUM(K4:K" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(commissionFormula);

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

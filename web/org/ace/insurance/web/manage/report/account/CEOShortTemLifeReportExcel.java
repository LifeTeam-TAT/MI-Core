package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.report.TLF.CeoShortTermLifeDTO;
import org.ace.insurance.report.TLF.PAMonthlyIncomeReportDTO;
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

public class CEOShortTemLifeReportExcel {

	private XSSFWorkbook wb;

	public CEOShortTemLifeReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/life/CEOShortTermLifeMonthlyReportExcel.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load CEOShortTemLifeReportExcel.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<CeoShortTermLifeDTO> ceoshortEndownLifeMonthlyReportList) {
			try {
				Sheet sheet1 = wb.getSheet("CEOShortTemLife");

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
				XSSFCellStyle centerCellStyle = ExcelUtils.getAlignCenterStyle(wb);
				XSSFCellStyle centerBoldCellStyle = ExcelUtils.getFontBoldAlignCenterStyle(wb);

				Row row;
				Cell cell;

				row = sheet1.getRow(2);
				cell = row.createCell(0);

				Date date = ceoshortEndownLifeMonthlyReportList.get(0).getPaymentDate();
				String year = Utils.getYearFormat(date);
				String month = Utils.getMonthStringWithLowerCase(date.getMonth());

				cell.setCellValue(year + " " + MyanmarLanguae.getMyanmarLanguaeString("YEAR_MYANMAR_002") + " " + month + " "
						+ MyanmarLanguae.getMyanmarLanguaeString("MONTH_MYANMAR_003") + MyanmarLanguae.getMyanmarLanguaeString("PA_MONTHLY_HEADER_001"));
				cell.setCellStyle(centerBoldCellStyle);

				int i = 5;
				int index = 0;
				
				for (CeoShortTermLifeDTO monthlyDto : ceoshortEndownLifeMonthlyReportList) {
					i = i + 1;
					index = index + 1;
					row = sheet1.createRow(i);

					
					cell = row.createCell(0);
					cell.setCellValue(index);
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(1);
					cell.setCellValue(monthlyDto.getPolicyNo());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(2);
					cell.setCellValue(monthlyDto.getPolicyTerm());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(3);
					cell.setCellValue(monthlyDto.getPaymentType());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(4);
					cell.setCellValue(
							DateUtils.getDateFormatString(monthlyDto.getActivedPolicyStartDate()));
					cell.setCellStyle(dateCellStyle);
					
					cell = row.createCell(5);
					cell.setCellValue(
							DateUtils.getDateFormatString(monthlyDto.getActivedPolicyEndDate()));
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(6);
					cell.setCellValue(monthlyDto.getSuminsured());
					cell.setCellStyle(defaultCellStyle);


				}

				i = i + 1;
				sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
				row = sheet1.createRow(i);
				cell = row.createCell(0);
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet1, wb);

				

				cell = row.createCell(1);
				cell.setCellStyle(defaultCellStyle);
				cell.setCellValue("-");

				cell = row.createCell(2);
				cell.setCellStyle(defaultCellStyle);
				cell.setCellValue("-");

				cell = row.createCell(3);
				cell.setCellStyle(defaultCellStyle);
				cell.setCellValue("-");

				cell = row.createCell(4);
				cell.setCellStyle(defaultCellStyle);
				cell.setCellValue("-");

				cell = row.createCell(5);
				cell.setCellStyle(defaultCellStyle);
				cell.setCellValue("-");
				
				cell = row.createCell(6);
				cell.setCellStyle(defaultCellStyle);
				cell.setCellValue("-");

				wb.setPrintArea(0, 0, 6, 0, i);

				wb.write(op);
				op.flush();
				op.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}

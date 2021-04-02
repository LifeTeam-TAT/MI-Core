package org.ace.insurance.web.manage.report.medical;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.medical.HealthPolicyReportDTO;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HealthPolicyReportExcel {
	private XSSFWorkbook wb;

	public HealthPolicyReportExcel() {
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/HealthPolicyReport.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load HealthPolicyReportExcel.xlsx tempalte", e);
		}
	}

	public void generate(OutputStream op, List<HealthPolicyReportDTO> hdList) {
		try {
			Sheet sheet = wb.getSheet("HealthPolicyReport");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

			Row row;
			Cell cell;

			row = sheet.getRow(0);
			cell = row.getCell(0);

			for (HealthPolicyReportDTO report : hdList) {
				if (report.getBranch() == null) {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Medical Policy Report ( All )");
					break;
				} else {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Medical Policy Report ( " + report.getBranch() + " )");
					break;
				}
			}

			int i = 1;
			int index = 0;
			for (HealthPolicyReportDTO report : hdList) {
				i = i + 1;
				index = index + 1;
				row = sheet.createRow(i);
				
				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(1);
				cell.setCellValue(report.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(2);
				cell.setCellValue(report.getProposalNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(3);
				cell.setCellValue(report.getReceiptNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(4);
				cell.setCellValue(report.getCommencementDate());
				cell.setCellStyle(dateCellStyle);
				
				cell = row.createCell(5);
				cell.setCellValue(report.getCustomerName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(6);
				cell.setCellValue(report.getAddress());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(7);
				cell.setCellValue(report.getPhoneNoAndEmailAddress());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(8);
				cell.setCellValue(report.getBasicUnit());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(9);
				cell.setCellValue(report.getAdditionalUnit());
				cell.setCellStyle(defaultCellStyle);
				
				cell = row.createCell(10);
				cell.setCellValue(report.getOption1Unit());
				cell.setCellStyle(defaultCellStyle);
				
				cell = row.createCell(11);
				cell.setCellValue(report.getOption2Unit());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(12);
				cell.setCellValue(report.getPremium());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(13);
				cell.setCellValue(report.getPremiumMode());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(14);
				cell.setCellValue(report.getAgentNameAndCodeNo());
				cell.setCellStyle(textCellStyle);
				
				cell = row.createCell(15);
				cell.setCellValue(report.getRemarks());
				cell.setCellStyle(defaultCellStyle);
			}
			
			String strFormula;
			Font font = wb.createFont();
			font.setFontName("Myanmar3");
			
			i = i + 1;
			row = sheet.createRow(i);
			
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 7));
			
			cell = row.createCell(0);
			cell.setCellValue("Total");
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 7), sheet, wb);
			cell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			cell.getCellStyle().setFont(font);
			
			cell = row.createCell(8);
			cell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(I3:I" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(strFormula);
			
			cell = row.createCell(9);
			cell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(J3:J" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(strFormula);
			
			cell = row.createCell(10);
			cell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(K3:K" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(strFormula);
			
			cell = row.createCell(11);
			cell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(L3:L" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(strFormula);
			
			cell = row.createCell(12);
			cell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(M3:M" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(strFormula);
			
			wb.setPrintArea(0, 0, 15, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

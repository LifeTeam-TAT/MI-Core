package org.ace.insurance.web.manage.report.medical;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.medical.HealthProposalReportDTO;
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

public class HealthProposalReportExcel {
	private XSSFWorkbook wb;

	public HealthProposalReportExcel() {
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/HealthProposalReport.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load HealthProposalReportExcel.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<HealthProposalReportDTO> hdList) {
		try {
			Sheet sheet = wb.getSheet("HealthProposalReport");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

			Row row = null;
			Cell cell;

			row = sheet.getRow(0);
			cell = row.getCell(0);

			for (HealthProposalReportDTO report : hdList) {
				if (report.getBranch() == null) {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Medical Proposal Report ( All )");
					break;
				} else {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Medical Proposal Report ( " + report.getBranch() + " )");
					break;
				}
			}

			int i = 1;
			int index = 0;
			for (HealthProposalReportDTO report : hdList) {
				i = i + 1;
				index = index + 1;
				row = sheet.createRow(i);
				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(1);
				cell.setCellValue(report.getProposalNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(2);
				cell.setCellValue(report.getDateOfProposed());
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(3);
				cell.setCellValue(report.getCustomerName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(4);
				cell.setCellValue(report.getNrcNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(5);
				cell.setCellValue(report.getFatherName());
				cell.setCellStyle(textCellStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(report.getAddressAndPhoneNo());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(7);
				cell.setCellValue(report.getBasicUnit());
				cell.setCellStyle(defaultCellStyle);
				
				cell = row.createCell(8);
				cell.setCellValue(report.getAdditionalUnit());
				cell.setCellStyle(defaultCellStyle);
				
				cell = row.createCell(9);
				cell.setCellValue(report.getOption1Unit());
				cell.setCellStyle(defaultCellStyle);
				
				cell = row.createCell(10);
				cell.setCellValue(report.getOption2Unit());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(11);
				cell.setCellValue(report.getPremium());
				cell.setCellStyle(currencyCellStyle);
				
				cell = row.createCell(12);
				cell.setCellValue(report.getAgentNameAndCodeNo());
				cell.setCellStyle(textCellStyle);
				
				cell = row.createCell(13);
				cell.setCellValue(report.getWorkFlowTask() != null ? report.getWorkFlowTask().getLabel() : null);
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(14);
				cell.setCellValue(report.getResponsiblePerson());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(15);
				cell.setCellValue(report.getRemark());
				cell.setCellStyle(defaultCellStyle);
			}
			
			String strFormula;
			Font font = wb.createFont();
			font.setFontName("Myanmar3");
			
			i = i + 1;
			row = sheet.createRow(i);
			
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));

			cell = row.createCell(0);
			cell.setCellValue("Total");
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);
			cell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			cell.getCellStyle().setFont(font);
			
			
			cell = row.createCell(7);
			cell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(H3:H" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(strFormula);
			
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
			cell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(L3:L" + i + ")";
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

package org.ace.insurance.web.manage.report.medical;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.medical.HealthDailyReportDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HealthDailyReportExcel {
	private XSSFWorkbook wb;
	private FormulaEvaluator evaluator;

	public HealthDailyReportExcel() {
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/HealthDailyIncomeReport.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load HealthDailyIncomeReport.xlsx template", e);
		}
	}

	public Map<String, List<HealthDailyReportDTO>> seperatePaymentChannel(List<HealthDailyReportDTO> healthDailyReportList) {
		Map<String, List<HealthDailyReportDTO>> map = new LinkedHashMap<String, List<HealthDailyReportDTO>>();
		if (healthDailyReportList != null) {
			for (HealthDailyReportDTO report : healthDailyReportList) {
				if (map.containsKey(report.getPaymentChannel())) {
					map.get(report.getPaymentChannel()).add(report);
				} else {
					List<HealthDailyReportDTO> list = new ArrayList<HealthDailyReportDTO>();
					list.add(report);
					map.put(report.getPaymentChannel(), list);
				}

			}
		}

		return map;
	}

	public void generate(OutputStream op, List<HealthDailyReportDTO> healthDailyReportList) {
		try {
			Sheet sheet = wb.getSheet("HealthDailyReceiptReport");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
			XSSFCellStyle textAlignRightStyle = ExcelUtils.getTextAlignRightStyle(wb);

			Row row = null;
			Cell cell;

			row = sheet.getRow(1);
			Cell cell1 = row.getCell(1);
			cell1.setCellValue(healthDailyReportList.get(0).getPaymentDate());
			cell1.setCellStyle(dateCellStyle);

			Cell cell3 = row.getCell(3);
			cell3.setCellValue(healthDailyReportList.get(0).getPaymentDate());
			cell3.setCellStyle(dateCellStyle);

			int i = 2;
			int startIndex;
			String strFormula;
			String grandSIFormula = "";
			String grandStampFeeFormula = "";
			String grandTotalAmtFormula = "";
			String grandNetPrmFormula = "";
			String paymentChannel = "";
			Map<String, List<HealthDailyReportDTO>> map = seperatePaymentChannel(healthDailyReportList);
			for (List<HealthDailyReportDTO> paymentChList : map.values()) {
				startIndex = i + 1 + 1;
				for (HealthDailyReportDTO report : paymentChList) {
					i = i + 1;
					row = sheet.createRow(i);

					cell = row.createCell(0);
					cell.setCellValue(report.getPaymentDate());
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(1);
					if (report.getPaymentChannel().equals(PaymentChannel.CASHED.toString())) {
						cell.setCellValue(PaymentChannel.CASHED.getLabel());
					} else {
						cell.setCellValue(report.getBankBranchName());
					}
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(2);
					cell.setCellValue(report.getProposerName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(3);
					cell.setCellValue(report.getAgentName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(4);
					cell.setCellValue(report.getProposalNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(5);
					cell.setCellValue(report.getReceiptNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(6);
					cell.setCellValue(report.getPolicyNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(7);
					cell.setCellValue(report.getSuminsured());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(8);
					cell.setCellValue(report.getNetPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(9);
					cell.setCellValue(report.getStampfees());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(10);
					cell.setCellValue(report.getTotalAmount());
					cell.setCellStyle(currencyCellStyle);

					paymentChannel = report.getPaymentChannel().toString();

				}

				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
				row = sheet.createRow(i);

				cell = row.createCell(0);

				String buffer = "Receipt Type Total";
				if (paymentChannel.equalsIgnoreCase(PaymentChannel.CASHED.name()))
					buffer = buffer + " (Cashed)";
				else if (paymentChannel.equalsIgnoreCase(PaymentChannel.TRANSFER.name()))
					buffer = buffer + " (Transfer)";
				else
					buffer = buffer + " (Cheque)";

				cell.setCellValue(buffer);
				cell.setCellStyle(textAlignRightStyle);
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);

				cell = row.createCell(7);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(H" + startIndex + ":H" + i + ")";
				grandSIFormula += "H" + (i + 1) + "+";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				cell = row.createCell(8);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(I" + startIndex + ":I" + i + ")";
				grandNetPrmFormula += "I" + (i + 1) + "+";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				cell = row.createCell(9);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(J" + startIndex + ":J" + i + ")";
				grandStampFeeFormula += "J" + (i + 1) + "+";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				cell = row.createCell(10);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(K" + startIndex + ":K" + i + ")";
				grandTotalAmtFormula += "K" + (i + 1) + "+";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

			}
			i = i + 1;
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
			row = sheet.createRow(i);

			cell = row.createCell(0);
			cell.setCellValue("Daily Total");
			cell.setCellStyle(textAlignRightStyle);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			grandSIFormula = grandSIFormula.substring(0, grandSIFormula.length() - 1);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandSIFormula);

			cell = row.createCell(8);
			cell.setCellStyle(currencyCellStyle);
			grandNetPrmFormula = grandNetPrmFormula.substring(0, grandNetPrmFormula.length() - 1);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandNetPrmFormula);

			cell = row.createCell(9);
			cell.setCellStyle(currencyCellStyle);
			grandStampFeeFormula = grandStampFeeFormula.substring(0, grandStampFeeFormula.length() - 1);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandStampFeeFormula);

			cell = row.createCell(10);
			cell.setCellStyle(currencyCellStyle);
			grandTotalAmtFormula = grandTotalAmtFormula.substring(0, grandTotalAmtFormula.length() - 1);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandTotalAmtFormula);

			i = i + 1;
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
			row = sheet.createRow(i);

			cell = row.createCell(0);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(textAlignRightStyle);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandSIFormula);

			cell = row.createCell(8);
			cell.setCellStyle(currencyCellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandNetPrmFormula);

			cell = row.createCell(9);
			cell.setCellStyle(currencyCellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandStampFeeFormula);

			cell = row.createCell(10);
			cell.setCellStyle(currencyCellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandTotalAmtFormula);

			wb.setPrintArea(0, 0, 10, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

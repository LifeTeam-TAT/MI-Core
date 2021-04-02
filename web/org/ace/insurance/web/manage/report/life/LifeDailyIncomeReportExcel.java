package org.ace.insurance.web.manage.report.life;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.life.LifeDailyIncomeReportDTO;
import org.ace.insurance.system.common.PaymentChannel;
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

public class LifeDailyIncomeReportExcel {

	private XSSFWorkbook wb;
	// private FormulaEvaluator evaluator ;

	public LifeDailyIncomeReportExcel() {
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/life/Life_Daily_Income_Report.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load Life_Daily_Income_Report.xlsx tempalte", e);
		}
	}

	public Map<String, List<LifeDailyIncomeReportDTO>> separateByPaymentChannel(List<LifeDailyIncomeReportDTO> lifeDailyIncomeList) {
		Map<String, List<LifeDailyIncomeReportDTO>> map = new LinkedHashMap<String, List<LifeDailyIncomeReportDTO>>();
		if (lifeDailyIncomeList != null) {
			for (LifeDailyIncomeReportDTO report : lifeDailyIncomeList) {
				if (map.containsKey(report.getPaymentChannel())) {
					map.get(report.getPaymentChannel()).add(report);
				} else {
					List<LifeDailyIncomeReportDTO> list = new ArrayList<LifeDailyIncomeReportDTO>();
					list.add(report);
					map.put(report.getPaymentChannel(), list);
				}
			}
		}
		return map;

	}

	public void generate(OutputStream op, List<LifeDailyIncomeReportDTO> lifeDailyIncomeList, Date startDate, Date endDate) {
		try {
			Sheet sheet = wb.getSheet("LifeDailyIncome");
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
			int startIndex;
			String strFormula;
			String grandSIFormula = "";
			String grandNetPrmFormula = "";
			String grandStampFeeFormula = "";
			String grandTotalAmountFormula = "";
			Map<String, List<LifeDailyIncomeReportDTO>> map = separateByPaymentChannel(lifeDailyIncomeList);

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

			for (List<LifeDailyIncomeReportDTO> paymentChannelList : map.values()) {
				startIndex = i + 1 + 1;
				String paymentChannel = "";
				for (LifeDailyIncomeReportDTO report : paymentChannelList) {
					i = i + 1;
					index = index + 1;

					row = sheet.createRow(i);
					cell = row.createCell(0);
					cell.setCellValue(report.getPaymentDate());
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(1);
					if (report.getPaymentChannel().equalsIgnoreCase(PaymentChannel.CASHED.name()))
						cell.setCellValue(report.getPaymentChannel());
					else
						cell.setCellValue(report.getReceivedBankBranchName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(2);
					cell.setCellValue(report.getCustomerName());
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
					cell.setCellValue(report.getSumInsured());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(8);
					cell.setCellValue(report.getNetPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(9);
					cell.setCellValue(report.getStampFees());
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
				grandTotalAmountFormula += "K" + (i + 1) + "+";
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
			grandTotalAmountFormula = grandTotalAmountFormula.substring(0, grandTotalAmountFormula.length() - 1);
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(grandTotalAmountFormula);

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
			cell.setCellFormula(grandTotalAmountFormula);

			// sheet.addMergedRegion(new CellRangeAddress(i, i, 9, 10));
			// ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new
			// CellRangeAddress(i, i, 9, 10), sheet, wb);

			wb.setPrintArea(0, 0, 10, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
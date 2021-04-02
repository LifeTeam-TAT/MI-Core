package org.ace.insurance.report.TLF;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.ErrorCode;
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

public class SalePointIncomeExcel {

	private XSSFWorkbook wb;

	public SalePointIncomeExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/salePoint/SalePointReport.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load LifeProposalReport.xlsx template", e);
		}
	}

	public Map<String, List<SalePointIncomeReportDTO>> separateSalePoint(List<SalePointIncomeReportDTO> sPIncomeReportDTOList) {
		Map<String, List<SalePointIncomeReportDTO>> map = new LinkedHashMap<>();
		for (SalePointIncomeReportDTO dto : sPIncomeReportDTOList) {
			if (map.containsKey(dto.getSalePointId())) {
				map.get(dto.getSalePointId()).add(dto);
			} else {
				List<SalePointIncomeReportDTO> list = new ArrayList<SalePointIncomeReportDTO>();
				list.add(dto);
				map.put(dto.getSalePointId(), list);
			}
		}
		return map;
	}

	public void generate(OutputStream op, List<SalePointIncomeReportDTO> sPIncomeReportDTOList) {
		try {
			Sheet sheet = wb.getSheet("SalePointReport");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

			Row row;
			Cell cell;

			row = sheet.getRow(0);
			cell = row.getCell(0);

			int i = 1;
			int index = 0;
			int startIndex;
			String cashDebitFormula;
			String cashCreditFormula;
			String transferDebitFormula;
			String transferCreditFormula;
			String GrandSIFormula = "";
			String GrandPremiumFormula = "";
			Map<String, List<SalePointIncomeReportDTO>> map = separateSalePoint(sPIncomeReportDTOList);
			for (List<SalePointIncomeReportDTO> salePointList : map.values()) {
				startIndex = i + 1 + 1;
				for (SalePointIncomeReportDTO salePointDTO : sPIncomeReportDTOList) {
					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);
					// index
					cell = row.createCell(0);
					cell.setCellValue(index);
					cell.setCellStyle(defaultCellStyle);
					// pocliy no
					cell = row.createCell(1);
					cell.setCellValue(salePointDTO.getPolicyNo());
					cell.setCellStyle(textCellStyle);

					// TLF No
					cell = row.createCell(2);
					cell.setCellValue(salePointDTO.getTlfNo());
					cell.setCellStyle(textCellStyle);

					// coaid
					cell = row.createCell(3);
					cell.setCellValue(salePointDTO.getCoaId());
					cell.setCellStyle(textCellStyle);

					// narration
					cell = row.createCell(4);
					cell.setCellValue(salePointDTO.getNarration());
					cell.setCellStyle(textCellStyle);
					/*
					 * // sale point name cell = row.createCell(5);
					 * cell.setCellValue(salePointDTO.getSalePointName());
					 * cell.setCellStyle(textCellStyle);
					 */

					// Payment Channel
					cell = row.createCell(5);
					cell.setCellValue(salePointDTO.getPaymentChannel());
					cell.setCellStyle(textCellStyle);

					// Start Date
					cell = row.createCell(6);
					cell.setCellValue(salePointDTO.getCreatedDate());
					cell.setCellStyle(dateCellStyle);

					// CASHDEBIT
					cell = row.createCell(7);
					cell.setCellValue(salePointDTO.getCashDebit());
					cell.setCellStyle(currencyCellStyle);

					// CASHCREDIT
					cell = row.createCell(8);
					cell.setCellValue(salePointDTO.getCashCredit());
					cell.setCellStyle(currencyCellStyle);

					// CASHCREDIT
					cell = row.createCell(9);
					cell.setCellValue(salePointDTO.getTransferDebit());
					cell.setCellStyle(currencyCellStyle);

					// CASHCREDIT
					cell = row.createCell(10);
					cell.setCellValue(salePointDTO.getTransferCredit());
					cell.setCellStyle(currencyCellStyle);

					// Agent Transaction
					cell = row.createCell(11);
					cell.setCellValue(salePointDTO.isAgentTransaction());
					cell.setCellStyle(textCellStyle);

					
					 }
				
			}
			i = i + 1;
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 7));
			row = sheet.createRow(i);

			cell = row.createCell(0);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 7), sheet, wb);
			cell.setCellValue("Total");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			cashDebitFormula = "SUMIF(M3:M" + i + "," + "\"FALSE\"" + "," + "I3:I" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(cashDebitFormula);

			cell = row.createCell(8);
			cell.setCellStyle(currencyCellStyle);
			cashCreditFormula = "SUMIF(M3:M" + i + "," + "\"FALSE\"" + "," + "J3:J" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(cashCreditFormula);

			cell = row.createCell(9);
			cell.setCellStyle(currencyCellStyle);
			transferDebitFormula = "SUMIF(M3:M" + i + "," + "\"FALSE\"" + "," + "K3:K" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(transferDebitFormula);

			cell = row.createCell(10);
			cell.setCellStyle(currencyCellStyle);
			transferCreditFormula = "SUMIF(M3:M" + i + "," + "\"FALSE\"" + "," + "L3:L" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(transferCreditFormula);
			

			wb.setPrintArea(0, 0, 12, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.TLF.GroupLifeMonthlyIncomeReportDTO;
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

public class GroupLifeMonthlyIncomeReportExcel {

	private XSSFWorkbook wb;

	public GroupLifeMonthlyIncomeReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/life/GroupLifeMonthlyIncomeReportExcel.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load GroupLifeMonthlyIncomeReport.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<GroupLifeMonthlyIncomeReportDTO> glMonthlyIncomeReportDTOList) {
		try {
			Sheet sheet = wb.getSheet("GroupLifeMonthlyReport");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
			XSSFCellStyle alignCenterCellStyle = ExcelUtils.getAlignCenterStyle(wb);

			Row row;
			Cell cell;

			row = sheet.getRow(2);
			cell = row.createCell(0);

			Date date = glMonthlyIncomeReportDTOList.get(0).getPaymentDate();
			cell.setCellValue(Utils.getYearFormat(date) + " " + MyanmarLanguae.getMyanmarLanguaeString("YEAR_MYANMAR_012") + " "
					+ Utils.getMonthStringWithLowerCase(date.getMonth()) + " " + MyanmarLanguae.getMyanmarLanguaeString("GROUP_LIFE_MONTH_MYANMAR_012"));
			cell.setCellStyle(alignCenterCellStyle);

			int i = 4;
			int index = 0;
			String siFormula = "";
			String premiumFormula = "";
			String insuPersonFormula = "";
			String commissionFormula = "";

			for (GroupLifeMonthlyIncomeReportDTO monthlyIncomeDTO : glMonthlyIncomeReportDTOList) {
				i = i + 1;
				index = index + 1;
				row = sheet.createRow(i);
				// index
				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				// customerName
				cell = row.createCell(1);
				cell.setCellValue(monthlyIncomeDTO.getCustomerName());
				cell.setCellStyle(textCellStyle);

				// Address
				cell = row.createCell(2);
				cell.setCellValue(monthlyIncomeDTO.getAddress());
				cell.setCellStyle(textCellStyle);

				// policyNo
				cell = row.createCell(3);
				cell.setCellValue(monthlyIncomeDTO.getPolicyNo());
				cell.setCellStyle(textCellStyle);

				// SI
				cell = row.createCell(4);
				cell.setCellValue(monthlyIncomeDTO.getSumInsured());
				cell.setCellStyle(currencyCellStyle);

				// NoOfInsuredPerson
				cell = row.createCell(5);
				cell.setCellValue(monthlyIncomeDTO.getInsuPersonCount());
				cell.setCellStyle(currencyCellStyle);

				// premium
				cell = row.createCell(6);
				cell.setCellValue(monthlyIncomeDTO.getPremium());
				cell.setCellStyle(currencyCellStyle);

				// commission
				cell = row.createCell(7);
				cell.setCellValue(monthlyIncomeDTO.getCommission());
				cell.setCellStyle(currencyCellStyle);

				// receiptNoAndDate
				cell = row.createCell(8);
				cell.setCellValue(monthlyIncomeDTO.getReceiptNoAndDate());
				cell.setCellStyle(textCellStyle);

				// agentName
				cell = row.createCell(9);
				cell.setCellValue(monthlyIncomeDTO.getAgentName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(10);
				cell.setCellValue(monthlyIncomeDTO.getSalePointsName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(11);
				cell.setCellValue(monthlyIncomeDTO.getFromDateToDate());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(12);
				cell.setCellValue(monthlyIncomeDTO.getFromTermToTerm());
				cell.setCellStyle(textCellStyle);
				
				cell = row.createCell(13);
				cell.setCellValue(monthlyIncomeDTO.getSaleChannelType().toString());
				cell.setCellStyle(textCellStyle);

			}

			i = i + 1;
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 3));
			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue("Total : ");
			cell.setCellStyle(textCellStyle);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 3), sheet, wb);

			cell = row.createCell(4);
			cell.setCellStyle(currencyCellStyle);
			siFormula = "SUM(E2:E" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(siFormula);

			cell = row.createCell(5);
			cell.setCellStyle(currencyCellStyle);
			insuPersonFormula = "SUM(F2:F" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(insuPersonFormula);

			cell = row.createCell(6);
			cell.setCellStyle(currencyCellStyle);
			premiumFormula = "SUM(G2:G" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(premiumFormula);

			cell = row.createCell(7);
			cell.setCellStyle(currencyCellStyle);
			commissionFormula = "SUM(H2:H" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(commissionFormula);

			cell = row.createCell(8);
			cell.setCellValue("-");
			cell.setCellStyle(textCellStyle);

			cell = row.createCell(9);
			cell.setCellValue("-");
			cell.setCellStyle(textCellStyle);

			cell = row.createCell(10);
			cell.setCellValue("-");
			cell.setCellStyle(textCellStyle);

			cell = row.createCell(11);
			cell.setCellValue("-");
			cell.setCellStyle(textCellStyle);

			cell = row.createCell(12);
			cell.setCellValue("-");
			cell.setCellStyle(textCellStyle);
			
			cell = row.createCell(13);
			cell.setCellValue("-");
			cell.setCellStyle(textCellStyle);

			wb.setPrintArea(0, 0, 13, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

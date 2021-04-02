package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.TLF.MonthlyIncomeReportDTO;
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

public class MonthlyIncomeReportExcel {

	private XSSFWorkbook wb;

	public MonthlyIncomeReportExcel() {
		load();
	}

	private void load() {
		try (InputStream inp = this.getClass().getResourceAsStream("/report-template/salePoint/LifeMonthlyIncomeReport.xlsx");) {
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load LifeMonthlyIncomeReport.xlsx template", e);
		}
	}

	public void generate(OutputStream op, List<MonthlyIncomeReportDTO> monthlyIncomeReportDTOList, boolean isAgent, List<MonthlyIncomeReportDTO> incomeDetailsReportList) {
		try {
			Sheet sheet1 = wb.getSheet("MonthlyReport");
			Sheet sheet2 = wb.getSheet("Detail");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
			XSSFCellStyle wrapCellStyle = ExcelUtils.getWrapCellStyle(wb);
			XSSFCellStyle textAlignRightStyle = ExcelUtils.getTextAlignRightStyle(wb);
			XSSFCellStyle textAlignCenterStyle = ExcelUtils.getAlignCenterStyle(wb);

			Row row;
			Cell cell;

			row = sheet1.getRow(0);
			cell = row.createCell(0);

			Date date = monthlyIncomeReportDTOList.get(0).getPaymentDate();
			String branchName = monthlyIncomeReportDTOList.get(0).getBranchName();

			cell.setCellValue("Monthly Income (" + Utils.getMonthAndYearFormat(date) + ")," + branchName + " Direct Marketing income");
			cell.setCellStyle(textAlignCenterStyle);

			row = sheet1.createRow(2);
			cell = row.createCell(0);
			if (monthlyIncomeReportDTOList.get(0).getSaleChannelType().equals("DIRECTMARKETING") && isAgent) {
				cell.setCellValue("Agents income (Direct Marketing)");
				cell.setCellStyle(defaultCellStyle);
			} else if (monthlyIncomeReportDTOList.get(0).getSaleChannelType().equals("AGENT")) {
				cell.setCellValue("Agents income");
				cell.setCellStyle(defaultCellStyle);
			} else if (monthlyIncomeReportDTOList.get(0).getSaleChannelType().equals("WALKIN")) {
				cell.setCellValue("Income");
				cell.setCellStyle(defaultCellStyle);
			} else {
				cell.setCellValue("Direct MKT income");
				cell.setCellStyle(defaultCellStyle);
			}

			int i = 2;
			int index = 0;
			String siFormula = "";
			String premiumFormula = "";

			for (MonthlyIncomeReportDTO monthlyIncomeDTO : monthlyIncomeReportDTOList) {
				i = i + 1;
				index = index + 1;
				row = sheet1.createRow(i);
				// index
				cell = row.createCell(0);
				cell.setCellValue(index);
				cell.setCellStyle(defaultCellStyle);

				// paymentDate
				cell = row.createCell(1);
				cell.setCellValue(monthlyIncomeDTO.getPaymentDate());
				cell.setCellStyle(dateCellStyle);

				// customerName
				cell = row.createCell(2);
				cell.setCellValue(monthlyIncomeDTO.getCustomerName());
				cell.setCellStyle(textCellStyle);

				// SI
				cell = row.createCell(3);
				cell.setCellValue(monthlyIncomeDTO.getSumInsured());
				cell.setCellStyle(currencyCellStyle);

				// premium
				cell = row.createCell(4);
				cell.setCellValue(monthlyIncomeDTO.getPremium());
				cell.setCellStyle(currencyCellStyle);

				// policyStartDate
				cell = row.createCell(5);
				cell.setCellValue(monthlyIncomeDTO.getActivedPolicyStartDate());
				cell.setCellStyle(dateCellStyle);

				// policyEndDate
				cell = row.createCell(6);
				cell.setCellValue(monthlyIncomeDTO.getActivedPolicyEndDate());
				cell.setCellStyle(dateCellStyle);

				// agentName
				cell = row.createCell(7);
				cell.setCellValue(monthlyIncomeDTO.getAgentName());
				cell.setCellStyle(textCellStyle);

				// productType
				cell = row.createCell(8);
				cell.setCellValue(monthlyIncomeDTO.getProductName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(9);
				cell.setCellValue(" ");
				cell.setCellStyle(defaultCellStyle);
			}

			i = i + 1;
			sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
			row = sheet1.createRow(i);
			cell = row.createCell(0);
			ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 2), sheet1, wb);

			if ((monthlyIncomeReportDTOList.get(0).getSaleChannelType().equals("DIRECTMARKETING") && isAgent)
					|| monthlyIncomeReportDTOList.get(0).getSaleChannelType().equals("AGENT")) {
				cell.setCellValue("Agent Totals");
				cell.setCellStyle(defaultCellStyle);
			} else if (monthlyIncomeReportDTOList.get(0).getSaleChannelType().equals("WALKIN")) {
				cell.setCellValue("Total");
				cell.setCellStyle(defaultCellStyle);
			} else {
				cell.setCellValue("Direct MKT Total");
				cell.setCellStyle(defaultCellStyle);
			}

			cell = row.createCell(3);
			cell.setCellStyle(currencyCellStyle);
			siFormula = "SUM(D3:D" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(siFormula);

			cell = row.createCell(4);
			cell.setCellStyle(currencyCellStyle);
			premiumFormula = "SUM(E3:E" + i + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(premiumFormula);

			sheet1.addMergedRegion(new CellRangeAddress(i, i, 5, 9));

			i = i + 1;
			sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
			row = sheet1.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue("Prepared By");
			cell.setCellStyle(textAlignCenterStyle);

			sheet1.addMergedRegion(new CellRangeAddress(i, i, 3, 5));
			cell = row.createCell(3);
			cell.setCellValue("Checked By");
			cell.setCellStyle(textAlignCenterStyle);

			sheet1.addMergedRegion(new CellRangeAddress(i, i, 6, 8));
			cell = row.createCell(6);
			cell.setCellValue("Approved By");
			cell.setCellStyle(textAlignCenterStyle);

			i = i + 1 + 1 + 1;
			sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
			row = sheet1.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue("(Daw Hsu Hsu Hlaing) \n Sr.Marketing Assistant");
			cell.setCellStyle(wrapCellStyle);
			row.setHeightInPoints((2 * sheet1.getDefaultRowHeightInPoints()));

			sheet1.addMergedRegion(new CellRangeAddress(i, i, 3, 5));
			cell = row.createCell(3);
			cell.setCellValue("(Daw Swe Thaw Tar Myint) \n Dy - Marketing Manager");
			cell.setCellStyle(wrapCellStyle);
			row.setHeightInPoints((2 * sheet1.getDefaultRowHeightInPoints()));

			sheet1.addMergedRegion(new CellRangeAddress(i, i, 6, 8));
			cell = row.createCell(6);
			cell.setCellValue("(Daw Tint Tint Aye) \n Assit:General Manager");
			cell.setCellStyle(wrapCellStyle);
			row.setHeightInPoints((2 * sheet1.getDefaultRowHeightInPoints()));

			// Sheet 2
			row = sheet2.getRow(0);
			cell = row.getCell(0);

			cell.setCellValue("Detail (Direct Marketing  Agent) ( " + Utils.getMonthAndYearFormat(date) + " )");
			cell.setCellStyle(textCellStyle);

			row = sheet2.createRow(1);

			cell = row.createCell(0);
			cell.setCellValue(Utils.getMonthAndYearFormat(date));
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Quantity");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("SI");
			cell.setCellStyle(defaultCellStyle);

			int rowNum = 1;
			int healthQty = 0;
			int healthSI = 0;
			int criticalQty = 0;
			int criticalSI = 0;
			String totalQtyFormula = "";
			String totalSiFormula = "";

			for (MonthlyIncomeReportDTO details : incomeDetailsReportList) {
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getPublicLifeId())) {
					cell = sheet2.getRow(2).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(2).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getShortEndowLifeId())) {
					cell = sheet2.getRow(3).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(3).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getGroupLifeId())) {
					cell = sheet2.getRow(4).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(4).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getPersonalAccidentMMKId())) {
					cell = sheet2.getRow(5).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(5).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getIndividualHealthInsuranceId())
						|| details.getProductId().trim().equals(KeyFactorIDConfig.getGroupHealthInsuranceId())) {
					healthQty += details.getQuantity();
					healthSI += details.getSumInsured();

					cell = sheet2.getRow(6).createCell(1);
					cell.setCellValue(healthQty);
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(6).createCell(2);
					cell.setCellValue(healthSI);
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getIndividualCriticalIllness_Id())
						|| details.getProductId().trim().equals(KeyFactorIDConfig.getGroupCriticalIllness_Id())) {
					criticalQty += details.getQuantity();
					criticalSI += details.getSumInsured();

					cell = sheet2.getRow(7).createCell(1);
					cell.setCellValue(criticalQty);
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(7).createCell(2);
					cell.setCellValue(criticalSI);
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getMicroHealthInsurance())) {
					cell = sheet2.getRow(8).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(8).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getSportManId())) {
					cell = sheet2.getRow(9).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(9).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getFarmerId())) {
					cell = sheet2.getRow(10).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(10).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getEducationLifeId())) {
					cell = sheet2.getRow(11).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(11).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}
				if (details.getProductId().trim().equals(KeyFactorIDConfig.getSnakeBikeId())) {
					cell = sheet2.getRow(12).createCell(1);
					cell.setCellValue(details.getQuantity());
					cell.setCellStyle(textAlignRightStyle);

					cell = sheet2.getRow(12).createCell(2);
					cell.setCellValue(details.getSumInsured());
					cell.setCellStyle(currencyCellStyle);
				}

			}

			rowNum = 13;
			row = sheet2.createRow(rowNum);

			cell = row.createCell(0);
			cell.setCellValue("Total");
			cell.setCellStyle(defaultCellStyle);

			cell = row.createCell(1);
			cell.setCellStyle(defaultCellStyle);
			totalQtyFormula = "SUM(B2:B" + rowNum + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(totalQtyFormula);
			cell.setCellStyle(textAlignRightStyle);

			cell = row.createCell(2);
			cell.setCellStyle(defaultCellStyle);
			totalSiFormula = "SUM(C2:C" + rowNum + ")";
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(totalSiFormula);
			cell.setCellStyle(currencyCellStyle);

			wb.setPrintArea(0, 0, 9, 0, i);
			wb.setPrintArea(1, 0, 5, 0, rowNum);

			wb.write(op);
			op.flush();
			op.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

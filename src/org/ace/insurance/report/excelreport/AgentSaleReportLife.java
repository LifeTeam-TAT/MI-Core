package org.ace.insurance.report.excelreport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.agent.AgentSaleComparisonReport;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AgentSaleReportLife extends ExcelReportUtility {

	public AgentSaleReportLife(List<AgentSaleComparisonReport> agentDailySalesList) {
		super(agentDailySalesList);
		load();
	}

	public void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/AgentSale_Daily_Life_Report.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load AgentSale_Daily_Life_Report.xlsx tempalte", e);
		}
	}

	public void generate(OutputStream op) {
		try {
			List<AgentSaleComparisonReport> agentDailySalesList = (List<AgentSaleComparisonReport>) list;
			XSSFDataFormat cellFormat = wb.createDataFormat();
			Sheet sheet = wb.getSheet("AgentSaleReport");
			ExcelUtils.fillCompanyLogo(wb, sheet, 8);
			Row companyRow = sheet.getRow(1);
			Cell companyCell = companyRow.getCell(0);
			companyCell.setCellValue(ApplicationSetting.getCompanyLabel());
			Row titleRow = sheet.getRow(2);
			Cell titleCell = titleRow.getCell(0);
			titleCell.setCellValue("Agent Daily Sales Report");

			Row proposalTypeRow = sheet.getRow(3);
			Cell proposalTypeCell = proposalTypeRow.getCell(3);

			int i = 4;
			int index = 0;
			for (AgentSaleComparisonReport asr : agentDailySalesList) {

				i = i + 1;
				index = index + 1;
				Row row = sheet.createRow(i);

				Cell noCell = row.createCell(0);
				noCell.setCellValue(index);
				noCell.setCellStyle(getDefaultCell());
				noCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);

				Cell agentNameCell = row.createCell(1);
				agentNameCell.setCellValue(asr.getAgentName());
				agentNameCell.setCellStyle(getDefaultCell());
				agentNameCell.getCellStyle().setWrapText(true);
				agentNameCell.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

				Cell agentCodeCell = row.createCell(2);
				agentCodeCell.setCellValue(asr.getCodeNo());
				agentCodeCell.setCellStyle(getDefaultCell());
				agentCodeCell.getCellStyle().setWrapText(true);
				agentCodeCell.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

				Cell groupLifeCountCell = row.createCell(3);
				groupLifeCountCell.setCellValue(asr.getEndowmentLife());
				groupLifeCountCell.setCellStyle(getDefaultCell());
				groupLifeCountCell.getCellStyle().setWrapText(true);
				groupLifeCountCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);

				Cell groupLifePremiumCell = row.createCell(4);
				groupLifePremiumCell.setCellValue(asr.getEndowmentPremium());
				groupLifePremiumCell.setCellStyle(getDefaultCell());
				groupLifePremiumCell.getCellStyle().setWrapText(true);
				groupLifePremiumCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
				groupLifePremiumCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);

				Cell publicLifeCountCell = row.createCell(5);
				publicLifeCountCell.setCellValue(asr.getGroupLife());
				publicLifeCountCell.setCellStyle(getDefaultCell());
				publicLifeCountCell.getCellStyle().setWrapText(true);
				publicLifeCountCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);

				Cell publicLifePremiumCell = row.createCell(6);
				publicLifePremiumCell.setCellValue(asr.getGroupLifePremium());
				publicLifePremiumCell.setCellStyle(getDefaultCell());
				publicLifePremiumCell.getCellStyle().setWrapText(true);
				publicLifePremiumCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
				publicLifePremiumCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);

				Cell totalCountCell = row.createCell(7);
				totalCountCell.setCellValue(asr.getLifePolicy());
				totalCountCell.setCellStyle(getDefaultCell());
				totalCountCell.getCellStyle().setWrapText(true);
				totalCountCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);

				Cell totalPremiumCell = row.createCell(8);
				totalPremiumCell.setCellValue(asr.getTotallifePremium());
				totalPremiumCell.setCellStyle(getDefaultCell());
				totalPremiumCell.getCellStyle().setWrapText(true);
				totalPremiumCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
				totalPremiumCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);

				Cell remarkCell = row.createCell(9);
				remarkCell.setCellStyle(getDefaultCell());

			}

			i = i + 1;
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));

			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 13);
			font.setBoldweight((short) 500);
			font.setFontName("Myanmar3");

			Row totalRow = sheet.createRow(i);
			Cell totalCell = totalRow.createCell(0);
			totalCell.setCellValue("Total");
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 9), sheet, wb);
			totalCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			totalCell.getCellStyle().setFont(font);

			// for total Group Life Policy count
			Cell groupLifePolicyCountCell = totalRow.createCell(3);
			groupLifePolicyCountCell.setCellStyle(getDefaultCell());
			groupLifePolicyCountCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
			groupLifePolicyCountCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			groupLifePolicyCountCell.getCellStyle().setFont(font);
			String strFormula = "SUM(D6:D" + i + ")";
			groupLifePolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			groupLifePolicyCountCell.setCellFormula(strFormula);

			// for total Group Life Policy Premium
			Cell groupLifePolicyPremiumCell = totalRow.createCell(4);
			groupLifePolicyPremiumCell.setCellStyle(getDefaultCell());
			groupLifePolicyPremiumCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
			groupLifePolicyPremiumCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
			groupLifePolicyPremiumCell.getCellStyle().setFont(font);
			strFormula = "SUM(E6:E" + i + ")";
			groupLifePolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			groupLifePolicyPremiumCell.setCellFormula(strFormula);

			// for total Public Life Policy count
			Cell publicLifePolicyCountCell = totalRow.createCell(5);
			publicLifePolicyCountCell.setCellStyle(getDefaultCell());
			publicLifePolicyCountCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
			publicLifePolicyCountCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			publicLifePolicyCountCell.getCellStyle().setFont(font);
			strFormula = "SUM(F6:F" + i + ")";
			publicLifePolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			publicLifePolicyCountCell.setCellFormula(strFormula);

			// for total Public Life Policy Premium
			Cell publicLifePolicyPremiumCell = totalRow.createCell(6);
			publicLifePolicyPremiumCell.setCellStyle(getDefaultCell());
			publicLifePolicyPremiumCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
			publicLifePolicyPremiumCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
			publicLifePolicyPremiumCell.getCellStyle().setFont(font);
			strFormula = "SUM(G6:G" + i + ")";
			publicLifePolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			publicLifePolicyPremiumCell.setCellFormula(strFormula);

			// for total Policy Count
			Cell totalPolicyCountCell = totalRow.createCell(7);
			totalPolicyCountCell.setCellStyle(getDefaultCell());
			totalPolicyCountCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
			totalPolicyCountCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			totalPolicyCountCell.getCellStyle().setFont(font);
			strFormula = "SUM(H6:H" + i + ")";
			totalPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			totalPolicyCountCell.setCellFormula(strFormula);

			// for total Policy Premium
			Cell totalPolicyPremiumCell = totalRow.createCell(8);
			totalPolicyPremiumCell.setCellStyle(getDefaultCell());
			totalPolicyPremiumCell.getCellStyle().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
			totalPolicyPremiumCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
			totalPolicyPremiumCell.getCellStyle().setFont(font);
			strFormula = "SUM(I6:I" + i + ")";
			totalPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			totalPolicyPremiumCell.setCellFormula(strFormula);

			/*
			 * i = i + 1; sheet.addMergedRegion(new CellRangeAddress(i, i, 0,
			 * 6)); Row totalRow = sheet.createRow(i); Cell totalCell =
			 * totalRow.createCell(0); totalCell.setCellValue("Sub Total");
			 * setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i,
			 * 0, 8), sheet, wb);
			 * totalCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
			 * 
			 * Cell totalCountCell = totalRow.createCell(7);
			 * totalCountCell.setCellValue(getTotalPolicyCount());
			 * totalCountCell.setCellStyle(getDefaultCell());
			 * totalCountCell.getCellStyle
			 * ().setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
			 * totalCountCell
			 * .getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			 * 
			 * Cell totalPremiumCell = totalRow.createCell(8);
			 * totalPremiumCell.setCellValue(getTotalPremium());
			 * totalPremiumCell.setCellStyle(getDefaultCell());
			 * totalPremiumCell.
			 * getCellStyle().setDataFormat(cellFormat.getFormat
			 * (CURRENCY_FORMAT));
			 */

			wb.setPrintArea(0, 0, 9, 0, i);
			wb.write(op);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	double getTotalPremium() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int getTotalPolicyCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}

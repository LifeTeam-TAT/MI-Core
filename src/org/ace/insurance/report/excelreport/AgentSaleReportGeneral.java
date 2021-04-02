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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AgentSaleReportGeneral extends ExcelReportUtility {

	
	//to FIXME this page By THK
	private String proposalType;

	@SuppressWarnings("unchecked")
	public AgentSaleReportGeneral(List<AgentSaleComparisonReport> agentDailySalesList, String proposalType) {
		super(agentDailySalesList);
		this.proposalType = proposalType;
		load();
	}

	@SuppressWarnings("unchecked")
	public void load() {
		try {
			if (!this.proposalType.equals("RENEWAL")) {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/AgentSale_Daily_NEW_RENEWAL_GENERAL_Report.xlsx");
				wb = new XSSFWorkbook(inp);
			} else {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/AgentSale_Daily_RENEWAL_GENERAL_Report.xlsx");
				wb = new XSSFWorkbook(inp);
			}
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load AgentSale_Daily_Report.xlsx tempalte", e);
		}
	}

	public void generate(OutputStream op) {
		try {
			@SuppressWarnings("unchecked")
			List<AgentSaleComparisonReport> agentDailySalesList = (List<AgentSaleComparisonReport>) list;
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
			proposalTypeCell.setCellValue(this.proposalType);

			Font font = wb.createFont();
			String strFormula;
			XSSFCellStyle defaultCell = getDefaultCell();
			XSSFCellStyle defaultTextCell = getDefaultTextCell();
			XSSFCellStyle defaultCurrencyCell = getDefaultCurrencyCell();

			Row row = null;
			Cell noCell = null;
			Cell agentNameCell = null;
			Cell agentCodeCell = null;
			Cell firePolicyCountCell = null;
			Cell firePolicyPremiumCell = null;
			Cell motorPolicyCountCell = null;
			Cell motorPolicyPremiumCell = null;
			Cell cargoPolicyCountCell = null;
			Cell cargoPolicyPremiumCell = null;
			Cell totalCountCell = null;
			Cell totalPremiumCell = null;
			Cell remarkCell = null;
			Cell totalCell = null;
			Cell totalPolicyCountCell = null;
			Cell totalPolicyPremiumCell = null;

			int i = 5;
			int index = 0;
			for (AgentSaleComparisonReport asr : agentDailySalesList) {

				i = i + 1;
				index = index + 1;
				row = sheet.createRow(i); // 5

				noCell = row.createCell(0);
				noCell.setCellValue(index);
				noCell.setCellStyle(defaultCell);

				agentNameCell = row.createCell(1);
				agentNameCell.setCellValue(asr.getAgentName());
				agentNameCell.setCellStyle(defaultTextCell);

				agentCodeCell = row.createCell(2);
				agentCodeCell.setCellValue(asr.getCodeNo());
				agentCodeCell.setCellStyle(defaultTextCell);

				firePolicyCountCell = row.createCell(3);
				firePolicyCountCell.setCellValue(asr.getFirePolicy());
				firePolicyCountCell.setCellStyle(defaultCell);

				firePolicyPremiumCell = row.createCell(4);
				firePolicyPremiumCell.setCellValue(asr.getTotalfirePremium());
				firePolicyPremiumCell.setCellStyle(defaultCurrencyCell);

				motorPolicyCountCell = row.createCell(5);
				motorPolicyCountCell.setCellValue(asr.getMotorPolicy());
				motorPolicyCountCell.setCellStyle(defaultCell);

				motorPolicyPremiumCell = row.createCell(6);
				motorPolicyPremiumCell.setCellValue(asr.getTotalmotorPremium());
				motorPolicyPremiumCell.setCellStyle(defaultCurrencyCell);

				if (!this.proposalType.equals("RENEWAL")) {
					cargoPolicyCountCell = row.createCell(7);
					cargoPolicyCountCell.setCellValue(asr.getCargoPolicy());
					cargoPolicyCountCell.setCellStyle(defaultCell);

					cargoPolicyPremiumCell = row.createCell(8);
					cargoPolicyPremiumCell.setCellValue(asr.getTotalCargoPremium());
					cargoPolicyPremiumCell.setCellStyle(defaultCurrencyCell);

					totalCountCell = row.createCell(9);
					totalCountCell.setCellValue(asr.getPolicyCount());
					totalCountCell.setCellStyle(defaultCell);

					totalPremiumCell = row.createCell(10);
					totalPremiumCell.setCellValue(asr.getTotalAmount());
					totalPremiumCell.setCellStyle(defaultCurrencyCell);

					remarkCell = row.createCell(11);
					remarkCell.setCellStyle(defaultCell);
				} else {
					totalCountCell = row.createCell(7);
					totalCountCell.setCellValue(asr.getPolicyCount());
					totalCountCell.setCellStyle(defaultCell);

					totalPremiumCell = row.createCell(8);
					totalPremiumCell.setCellValue(asr.getTotalAmount());
					totalPremiumCell.setCellStyle(defaultCurrencyCell);

					remarkCell = row.createCell(9);
					remarkCell.setCellStyle(defaultCell);
				}

			}
			i = i + 1;
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));

			font.setFontHeightInPoints((short) 13);
			font.setBoldweight((short) 500);
			font.setFontName("Myanmar3");

			row = sheet.createRow(i);
			totalCell = row.createCell(0);
			totalCell.setCellValue("Total");
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 9), sheet, wb);
			totalCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
			totalCell.getCellStyle().setFont(font);

			// for total Fire Policy count
			firePolicyCountCell = row.createCell(3);
			firePolicyCountCell.setCellStyle(defaultCell);
			firePolicyCountCell.getCellStyle().setFont(font);
			strFormula = "SUM(D7:D" + i + ")";
			firePolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			firePolicyCountCell.setCellFormula(strFormula);

			// for total Fire Policy Premium
			firePolicyPremiumCell = row.createCell(4);
			firePolicyPremiumCell.setCellStyle(defaultCurrencyCell);
			firePolicyPremiumCell.getCellStyle().setFont(font);
			strFormula = "SUM(E7:E" + i + ")";
			firePolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			firePolicyPremiumCell.setCellFormula(strFormula);

			// for total Motor Policy count
			motorPolicyCountCell = row.createCell(5);
			motorPolicyCountCell.setCellStyle(defaultCell);
			motorPolicyCountCell.getCellStyle().setFont(font);
			strFormula = "SUM(F7:F" + i + ")";
			motorPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			motorPolicyCountCell.setCellFormula(strFormula);

			// for total Motor Policy Premium
			motorPolicyPremiumCell = row.createCell(6);
			motorPolicyPremiumCell.setCellStyle(defaultCurrencyCell);
			motorPolicyPremiumCell.getCellStyle().setFont(font);
			strFormula = "SUM(G7:G" + i + ")";
			motorPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			motorPolicyPremiumCell.setCellFormula(strFormula);

			if (!proposalType.equals("RENEWAL")) {
				// for total Cargo Policy Count
				cargoPolicyCountCell = row.createCell(7);
				cargoPolicyCountCell.setCellStyle(defaultCell);
				cargoPolicyCountCell.getCellStyle().setFont(font);
				strFormula = "SUM(H7:H" + i + ")";
				cargoPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				cargoPolicyCountCell.setCellFormula(strFormula);

				// for total Cargo Policy Premium
				cargoPolicyPremiumCell = row.createCell(8);
				cargoPolicyPremiumCell.setCellStyle(defaultCurrencyCell);
				cargoPolicyPremiumCell.getCellStyle().setFont(font);
				strFormula = "SUM(I7:I" + i + ")";
				cargoPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				cargoPolicyPremiumCell.setCellFormula(strFormula);

				// for total Policy Count
				totalPolicyCountCell = row.createCell(9);
				totalPolicyCountCell.setCellStyle(defaultCell);
				totalPolicyCountCell.getCellStyle().setFont(font);
				strFormula = "SUM(J7:J" + i + ")";
				totalPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				totalPolicyCountCell.setCellFormula(strFormula);

				// for total Policy Premium
				totalPolicyPremiumCell = row.createCell(10);
				totalPolicyPremiumCell.setCellStyle(defaultCurrencyCell);
				totalPolicyPremiumCell.getCellStyle().setFont(font);
				strFormula = "SUM(K7:K" + i + ")";
				totalPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				totalPolicyPremiumCell.setCellFormula(strFormula);
			} else {
				// for total Policy Count
				totalPolicyCountCell = row.createCell(7);
				totalPolicyCountCell.setCellStyle(defaultCell);
				totalPolicyCountCell.getCellStyle().setFont(font);
				strFormula = "SUM(H7:H" + i + ")";
				totalPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				totalPolicyCountCell.setCellFormula(strFormula);

				// for total Policy Premium
				totalPolicyPremiumCell = row.createCell(8);
				totalPolicyPremiumCell.setCellStyle(defaultCurrencyCell);
				totalPolicyPremiumCell.getCellStyle().setFont(font);
				strFormula = "SUM(I7:I" + i + ")";
				totalPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				totalPolicyPremiumCell.setCellFormula(strFormula);
			}
			wb.setPrintArea(0, 0, 11, 0, i);
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

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
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

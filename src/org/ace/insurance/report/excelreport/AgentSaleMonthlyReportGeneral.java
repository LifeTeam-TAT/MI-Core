package org.ace.insurance.report.excelreport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.agent.AgentSaleMonthlyDto;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AgentSaleMonthlyReportGeneral extends ExcelReportUtility {

	private AgentSalesReportCriteria criteria;

	//To FIXME this page By thk
	
	@SuppressWarnings("unchecked")
	public AgentSaleMonthlyReportGeneral(List<AgentSaleMonthlyDto> agentMonthlySalesList, AgentSalesReportCriteria criteria) {
		super(agentMonthlySalesList);
		this.criteria = criteria;
		load();
	}

	@SuppressWarnings("unchecked")
	public void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/AgentSale_MONTHLY_NEW_RENEWAL_GENERAL_Report.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load AgentSale_MONTHLY_NEW_RENEWAL_GENERAL_Report.xlsx tempalte", e);
		}
	}

	public void generate(OutputStream op) {
		try {
			@SuppressWarnings("unchecked")
			List<AgentSaleMonthlyDto> agentMonthlySalesList = (List<AgentSaleMonthlyDto>) list;
			Sheet sheet = wb.getSheet("AgentSaleReport");
			ExcelUtils.fillCompanyLogo(wb, sheet, 8);
			Row companyRow = sheet.getRow(1);
			Cell companyCell = companyRow.getCell(0);
			companyCell.setCellValue(ApplicationSetting.getCompanyLabel());
			Row titleRow = sheet.getRow(2);
			Cell titleCell = titleRow.getCell(0);
			titleCell.setCellValue("Agent Monthly Sales Report For " + criteria.getMonthString() + "  " + criteria.getYear());

			Row branchRow = sheet.getRow(3);
			Cell branchCell = branchRow.getCell(0);
			branchCell.setCellValue(criteria.getBranch() == null ? "All Branch" : criteria.getBranch().getDescription());

			CellStyle defaultCellStyle = getDefaultCell();
			CellStyle textCellStyle = getDefaultTextCell();
			CellStyle currencyCellStyle = getDefaultCurrencyCell();
			Row row;
			Cell noCell;
			Cell agentNameCell;
			Cell agentCodeCell;
			Cell newFirePolicyCountCell;
			Cell newFirePolicyPremiumCell;
			Cell newMotorPolicyCountCell;
			Cell newMotorPolicyPremiumCell;
			Cell newCargoPolicyCountCell;
			Cell newCargoPolicyPremiumCell;
			Cell renewalFirePolicyCountCell;
			Cell renewalFirePolicyPremiumCell;
			Cell renewalMotorPolicyCountCell;
			Cell renewalMotorPolicyPremiumCell;
			Cell renewalCargoPolicyCountCell;
			Cell renewalCargoPolicyPremiumCell;
			Cell newRenewalFirePolicyCountCell;
			Cell newRenewalFirePremiumCell;
			Cell newRenewalMotorPolicyCountCell;
			Cell newRenewalMotorPremiumCell;
			Cell newRenewalCargoPolicyCountCell;
			Cell newRenewalCargoPremiumCell;
			Cell totalPolicyCountCell;
			Cell totalPremiumCell;
			Cell remarkCell;

			int i = 6;
			int index = 0;
			String strFormula = "";
			for (AgentSaleMonthlyDto asr : agentMonthlySalesList) {

				i = i + 1;
				index = index + 1;
				row = sheet.createRow(i); // 6

				// for index at cell 0
				noCell = row.createCell(0);
				noCell.setCellValue(index);
				noCell.setCellStyle(defaultCellStyle);

				// for agentName cell 1
				agentNameCell = row.createCell(1);
				agentNameCell.setCellValue(asr.getAgentName());
				agentNameCell.setCellStyle(textCellStyle);

				// for code no
				agentCodeCell = row.createCell(2);
				agentCodeCell.setCellValue(asr.getCodeNo());
				agentCodeCell.setCellStyle(textCellStyle);

				// New Business Fire Policy
				newFirePolicyCountCell = row.createCell(3);
				newFirePolicyCountCell.setCellValue(asr.getNewFirePolicy());
				newFirePolicyCountCell.setCellStyle(defaultCellStyle);

				// New Business Fire Premium
				newFirePolicyPremiumCell = row.createCell(4);
				newFirePolicyPremiumCell.setCellValue(asr.getNewFirePremium());
				newFirePolicyPremiumCell.setCellStyle(currencyCellStyle);

				// New Business Motor Policy
				newMotorPolicyCountCell = row.createCell(5);
				newMotorPolicyCountCell.setCellValue(asr.getNewMotorPolicy());
				newMotorPolicyCountCell.setCellStyle(defaultCellStyle);

				// New Business Motor Premium
				newMotorPolicyPremiumCell = row.createCell(6);
				newMotorPolicyPremiumCell.setCellValue(asr.getNewMotorPremium());
				newMotorPolicyPremiumCell.setCellStyle(currencyCellStyle);

				// New Business Cargo Policy
				newCargoPolicyCountCell = row.createCell(7);
				newCargoPolicyCountCell.setCellValue(asr.getNewCargoPolicy());
				newCargoPolicyCountCell.setCellStyle(defaultCellStyle);

				// New Business Cargo Premium
				newCargoPolicyPremiumCell = row.createCell(8);
				newCargoPolicyPremiumCell.setCellValue(asr.getNewCargoPremium());
				newCargoPolicyPremiumCell.setCellStyle(currencyCellStyle);

				// Renewal Business Fire Policy
				renewalFirePolicyCountCell = row.createCell(9);
				renewalFirePolicyCountCell.setCellValue(asr.getRenewalFirePolicy());
				renewalFirePolicyCountCell.setCellStyle(defaultCellStyle);

				// Renewal Business Fire Premium
				renewalFirePolicyPremiumCell = row.createCell(10);
				renewalFirePolicyPremiumCell.setCellValue(asr.getRenewalFirePremium());
				renewalFirePolicyPremiumCell.setCellStyle(currencyCellStyle);

				// Renewal Business Motor Policy
				renewalMotorPolicyCountCell = row.createCell(11);
				renewalMotorPolicyCountCell.setCellValue(asr.getRenewalMotorPolicy());
				renewalMotorPolicyCountCell.setCellStyle(defaultCellStyle);

				// Renewal Business Motor Premium
				renewalMotorPolicyPremiumCell = row.createCell(12);
				renewalMotorPolicyPremiumCell.setCellValue(asr.getRenewalMotorPremium());
				renewalMotorPolicyPremiumCell.setCellStyle(currencyCellStyle);

				// Renewal Business Cargo Policy
				renewalCargoPolicyCountCell = row.createCell(13);
				renewalCargoPolicyCountCell.setCellValue(asr.getRenewalCargoPolicy());
				renewalCargoPolicyCountCell.setCellStyle(defaultCellStyle);

				// Renewal Business Cargo Premium
				renewalCargoPolicyPremiumCell = row.createCell(14);
				renewalCargoPolicyPremiumCell.setCellValue(asr.getRenewalCargoPremium());
				renewalCargoPolicyPremiumCell.setCellStyle(currencyCellStyle);

				// New + Renewal Business Fire Policy
				newRenewalFirePolicyCountCell = row.createCell(15);
				newRenewalFirePolicyCountCell.setCellStyle(defaultCellStyle);
				strFormula = "D" + (i + 1) + "+" + "J" + (i + 1);
				newRenewalFirePolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				newRenewalFirePolicyCountCell.setCellFormula(strFormula);

				// New + Renewal Business Fire Premium
				newRenewalFirePremiumCell = row.createCell(16);
				newRenewalFirePremiumCell.setCellStyle(currencyCellStyle);
				strFormula = "E" + (i + 1) + "+" + "K" + (i + 1);
				newRenewalFirePremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				newRenewalFirePremiumCell.setCellFormula(strFormula);

				// New + Renewal Business Motor Policy
				newRenewalMotorPolicyCountCell = row.createCell(17);
				newRenewalMotorPolicyCountCell.setCellStyle(defaultCellStyle);
				strFormula = "F" + (i + 1) + "+" + "L" + (i + 1);
				newRenewalMotorPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				newRenewalMotorPolicyCountCell.setCellFormula(strFormula);

				// New + Renewal Business Motor Premium
				newRenewalMotorPremiumCell = row.createCell(18);
				newRenewalMotorPremiumCell.setCellStyle(currencyCellStyle);
				strFormula = "G" + (i + 1) + "+" + "M" + (i + 1);
				newRenewalMotorPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				newRenewalMotorPremiumCell.setCellFormula(strFormula);

				// New + Renewal Business Cargo Policy
				newRenewalCargoPolicyCountCell = row.createCell(19);
				newRenewalCargoPolicyCountCell.setCellStyle(defaultCellStyle);
				strFormula = "H" + (i + 1) + "+" + "N" + (i + 1);
				newRenewalCargoPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				newRenewalCargoPolicyCountCell.setCellFormula(strFormula);

				// New + Renewal Business Cargo Premium
				newRenewalCargoPremiumCell = row.createCell(20);
				newRenewalCargoPremiumCell.setCellStyle(currencyCellStyle);
				strFormula = "I" + (i + 1) + "+" + "O" + (i + 1);
				newRenewalCargoPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				newRenewalCargoPremiumCell.setCellFormula(strFormula);

				// Total Policy
				totalPolicyCountCell = row.createCell(21);
				totalPolicyCountCell.setCellStyle(defaultCellStyle);
				strFormula = "P" + (i + 1) + "+" + "R" + (i + 1) + "+" + "T" + (i + 1);
				totalPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				totalPolicyCountCell.setCellFormula(strFormula);

				// Total Premium
				totalPremiumCell = row.createCell(22);
				totalPremiumCell.setCellStyle(currencyCellStyle);
				strFormula = "Q" + (i + 1) + "+" + "S" + (i + 1) + "+" + "U" + (i + 1);
				totalPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				totalPremiumCell.setCellFormula(strFormula);

				remarkCell = row.createCell(23);
				remarkCell.setCellStyle(textCellStyle);

			}
			i = i + 1;
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));

			row = sheet.createRow(i);
			Cell totalCell = row.createCell(0);
			totalCell.setCellValue("Total");
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 9), sheet, wb);
			totalCell.setCellStyle(defaultCellStyle);

			// for New Business total Fire Policy count
			newFirePolicyCountCell = row.createCell(3);
			newFirePolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(D8:D" + i + ")";
			newFirePolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newFirePolicyCountCell.setCellFormula(strFormula);

			// for New Business total Fire Policy Premium
			newFirePolicyPremiumCell = row.createCell(4);
			newFirePolicyPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(E8:E" + i + ")";
			newFirePolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newFirePolicyPremiumCell.setCellFormula(strFormula);

			// for New Business total Motor Policy count
			newMotorPolicyCountCell = row.createCell(5);
			newMotorPolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(F8:F" + i + ")";
			newMotorPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newMotorPolicyCountCell.setCellFormula(strFormula);

			// for New Business total Motor Policy Premium
			newMotorPolicyPremiumCell = row.createCell(6);
			newMotorPolicyPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(G8:G" + i + ")";
			newMotorPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newMotorPolicyPremiumCell.setCellFormula(strFormula);

			// for New total Cargo Policy Count
			newCargoPolicyCountCell = row.createCell(7);
			newCargoPolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(H8:H" + i + ")";
			newCargoPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newCargoPolicyCountCell.setCellFormula(strFormula);

			// for New total Cargo Policy Premium
			newCargoPolicyPremiumCell = row.createCell(8);
			newCargoPolicyPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(I8:I" + i + ")";
			newCargoPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newCargoPolicyPremiumCell.setCellFormula(strFormula);

			// for Renewal Business total Fire Policy count
			renewalFirePolicyCountCell = row.createCell(9);
			renewalFirePolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(J8:J" + i + ")";
			renewalFirePolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			renewalFirePolicyCountCell.setCellFormula(strFormula);

			// for renewal Business total Fire Policy Premium
			renewalFirePolicyPremiumCell = row.createCell(10);
			renewalFirePolicyPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(K8:K" + i + ")";
			renewalFirePolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			renewalFirePolicyPremiumCell.setCellFormula(strFormula);

			// for renewal Business total Motor Policy count
			renewalMotorPolicyCountCell = row.createCell(11);
			renewalMotorPolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(L8:L" + i + ")";
			renewalMotorPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			renewalMotorPolicyCountCell.setCellFormula(strFormula);

			// for renewal Business total Motor Policy Premium
			renewalMotorPolicyPremiumCell = row.createCell(12);
			renewalMotorPolicyPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(M8:M" + i + ")";
			renewalMotorPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			renewalMotorPolicyPremiumCell.setCellFormula(strFormula);

			// for renewal total Cargo Policy Count
			renewalCargoPolicyCountCell = row.createCell(13);
			renewalCargoPolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(N8:N" + i + ")";
			renewalCargoPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			renewalCargoPolicyCountCell.setCellFormula(strFormula);

			// for renewal total Cargo Policy Premium
			renewalCargoPolicyPremiumCell = row.createCell(14);
			renewalCargoPolicyPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(O8:O" + i + ")";
			renewalCargoPolicyPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			renewalCargoPolicyPremiumCell.setCellFormula(strFormula);

			// for newRenewal Business total Fire Policy count
			newRenewalFirePolicyCountCell = row.createCell(15);
			newRenewalFirePolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(P8:P" + i + ")";
			newRenewalFirePolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newRenewalFirePolicyCountCell.setCellFormula(strFormula);

			// for newRenewal Business total Fire Policy Premium
			newRenewalFirePremiumCell = row.createCell(16);
			newRenewalFirePremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(Q8:Q" + i + ")";
			newRenewalFirePremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newRenewalFirePremiumCell.setCellFormula(strFormula);

			// for newRenewal Business total Motor Policy count
			newRenewalMotorPolicyCountCell = row.createCell(17);
			newRenewalMotorPolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(R8:R" + i + ")";
			newRenewalMotorPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newRenewalMotorPolicyCountCell.setCellFormula(strFormula);

			// for newRenewal Business total Motor Policy Premium
			newRenewalMotorPremiumCell = row.createCell(18);
			newRenewalMotorPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(S8:S" + i + ")";
			newRenewalMotorPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newRenewalMotorPremiumCell.setCellFormula(strFormula);

			// for newRenewal total Cargo Policy Count
			newRenewalCargoPolicyCountCell = row.createCell(19);
			newRenewalCargoPolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(T8:T" + i + ")";
			newRenewalCargoPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newRenewalCargoPolicyCountCell.setCellFormula(strFormula);

			// for newRenewal total Cargo Policy Premium
			newRenewalCargoPremiumCell = row.createCell(20);
			newRenewalCargoPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(U8:U" + i + ")";
			newRenewalCargoPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			newRenewalCargoPremiumCell.setCellFormula(strFormula);

			// for total Policy Count
			totalPolicyCountCell = row.createCell(21);
			totalPolicyCountCell.setCellStyle(defaultCellStyle);
			strFormula = "SUM(V8:V" + i + ")";
			totalPolicyCountCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			totalPolicyCountCell.setCellFormula(strFormula);

			// for total Policy Premium
			totalPremiumCell = row.createCell(22);
			totalPremiumCell.setCellStyle(currencyCellStyle);
			strFormula = "SUM(W8:W" + i + ")";
			totalPremiumCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			totalPremiumCell.setCellFormula(strFormula);

			wb.setPrintArea(0, 0, 23, 0, i);
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

package org.ace.insurance.web.manage.report.medical;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.medical.HealthDailyIncomeReportDTO;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HealthClaimRequestReportExcel {
	private XSSFWorkbook wb;

	public HealthClaimRequestReportExcel() {
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/Health_Claim_Request_Report.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load HealthDailyIncomeReportExcel.xlsx tempalte", e);
		}
	}

	public void generate(OutputStream op, List<HealthDailyIncomeReportDTO> hdList) {
		try {
			Sheet sheet = wb.getSheet("LifeDailyIncome");

			XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
			XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

			Row row = null;
			Cell cell;

			row = sheet.getRow(0);
			cell = row.getCell(0);

			for (HealthDailyIncomeReportDTO report : hdList) {
				if (report.getBranch() == null) {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Medical Claim Request  Report ( All )");
					break;
				} else {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Medical Claim Request  Report ( " + report.getBranch() + " )");
					break;
				}
			}

			int i = 1;
			int index = 0;
			for (HealthDailyIncomeReportDTO report : hdList) {
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
				cell.setCellValue(report.getClaimRequestId());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(3);
				cell.setCellValue(report.getCustomerName());
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(4);
				cell.setCellValue(report.getNrc());
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(5);
				cell.setCellValue(report.getFatherName());
				cell.setCellStyle(textCellStyle);

				cell = row.createCell(6);
				cell.setCellValue(report.getPolicyInsuredPersonName());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(7);
				cell.setCellValue(report.getSubmittedDate());
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(8);
				cell.setCellValue(report.getHospitalizedAmount());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(9);
				cell.setCellValue(report.getOperationAmount());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(10);
				cell.setCellValue(report.getDisabilityAmount());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(11);
				cell.setCellValue(report.getDeathAmount());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(12);
				cell.setCellValue(report.getTotalClaimAmount());
				cell.setCellStyle(currencyCellStyle);

				cell = row.createCell(13);
				cell.setCellValue(report.getBranch());
				cell.setCellStyle(textCellStyle);
			}
			wb.setPrintArea(0, 0, 13, 0, i);
			wb.write(op);
			op.flush();
			op.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

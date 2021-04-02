package org.ace.insurance.web.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonBeneficiaries;
import org.ace.insurance.web.common.ExportExcel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PolicyIssueExportExcel extends BaseBean implements ExportExcel {

	private XSSFWorkbook wb;
	private MedicalPolicy medicalPolicy;
	private PaymentDTO paymentDTO;

	public PolicyIssueExportExcel() {
		load();
	}

	public PolicyIssueExportExcel(MedicalPolicy medicalPolicy, PaymentDTO paymentDTO) {
		this.medicalPolicy = medicalPolicy;
		this.paymentDTO = paymentDTO;
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/MedicalPolicyIssue.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load PolicyIssueTemplate.xlsx Formatted Excel", e);
		}
	}

	public void generate(OutputStream op) throws IOException {
		Sheet sheet = wb.getSheet("PolicyIssue");

		MedicalPolicyInsuredPerson mpPerson = null;

		Cell cellForCashReceipt = sheet.getRow(2).getCell(4);

		cellForCashReceipt.setCellValue(paymentDTO.getReceiptNo());
		cellForCashReceipt.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForPaymentDate = sheet.getRow(3).getCell(4);
		SimpleDateFormat dateFormatPaymentDate = new SimpleDateFormat("dd-MM-yyyy");
		String paymentDate = dateFormatPaymentDate.format(paymentDTO.getPaymentDate());
		cellForPaymentDate.setCellValue(paymentDate);
		cellForPaymentDate.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForPolicyNo = sheet.getRow(2).getCell(12);
		cellForPolicyNo.setCellValue(medicalPolicy.getPolicyNo());
		cellForPolicyNo.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForAgent = sheet.getRow(3).getCell(12);
		cellForAgent.setCellValue(medicalPolicy.getAgent().getFullName() + "/" + medicalPolicy.getAgent().getLiscenseNo());
		cellForAgent.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForPeriodOfInsurance = sheet.getRow(4).getCell(8);
		cellForPeriodOfInsurance.setCellValue(medicalPolicy.getPeriodYears());
		cellForPeriodOfInsurance.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForUnit = sheet.getRow(5).getCell(8);
		cellForUnit.setCellValue(mpPerson.getUnit());
		cellForUnit.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForPremium = sheet.getRow(6).getCell(8);
		cellForPremium.setCellValue(paymentDTO.getTotalPremium());
		cellForPremium.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForCustomerName = sheet.getRow(7).getCell(8);
		cellForCustomerName.setCellValue("");
		cellForCustomerName.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForFatherName = sheet.getRow(8).getCell(8);
		cellForFatherName.setCellValue(mpPerson.getFatherName());
		cellForFatherName.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForNRCNo = sheet.getRow(9).getCell(8);
		cellForNRCNo.setCellValue(mpPerson.getFullIdNo());
		cellForNRCNo.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForAge = sheet.getRow(10).getCell(8);
		if (mpPerson.getDateOfBirth() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String ipBirthdayDate = sdf.format(mpPerson.getDateOfBirth());
			ipBirthdayDate = "( " + ipBirthdayDate + " )";
			cellForAge.setCellValue(mpPerson.getAge() + ipBirthdayDate);
			cellForCashReceipt.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
		} else {
			cellForAge.setCellValue("-");
			cellForCashReceipt.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
		}

		Cell cellForOccupation = sheet.getRow(11).getCell(8);
		if (mpPerson.getOccupation() == null) {
			cellForOccupation.setCellValue("-");
			cellForCashReceipt.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
		} else {
			cellForOccupation.setCellValue(mpPerson.getOccupation().getName());
			cellForCashReceipt.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
		}

		Cell cellForAddress = sheet.getRow(12).getCell(8);
		cellForAddress.setCellValue(mpPerson.getFullAddress());
		cellForAddress.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
		String guardianName = "";
		String guardianNRC = "";
		String guardianRelation = "";
		if (mpPerson.getGuardian() != null) {
			guardianName = mpPerson.getGuardian().getCustomer().getFullName();
			guardianNRC = mpPerson.getGuardian().getCustomer().getFullIdNo();
			guardianRelation = mpPerson.getGuardian().getRelationship().getName();
		}

		Cell cellForGuardianName = sheet.getRow(13).getCell(8);
		cellForGuardianName.setCellValue(guardianName);
		cellForGuardianName.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForGuardianNrc = sheet.getRow(14).getCell(8);
		cellForGuardianNrc.setCellValue(guardianNRC);
		cellForGuardianNrc.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		Cell cellForGurdianRelation = sheet.getRow(15).getCell(8);
		cellForGurdianRelation.setCellValue(guardianRelation);
		cellForGurdianRelation.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
		int i = 18;
		int index = 0;
		for (MedicalPolicyInsuredPersonBeneficiaries mBeneficiaries : mpPerson.getPolicyInsuredPersonBeneficiariesList()) {
			i = i + 1;
			index = index + 1;
			Row subTotalRow = sheet.createRow(i);
			Cell noCell = subTotalRow.createCell(1);
			noCell.setCellValue(index);
			noCell.setCellStyle(getDefaultCell());
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 1, 2), sheet, wb);
			noCell.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

			sheet.addMergedRegion(new CellRangeAddress(i, i, 2, 5));
			Cell name = subTotalRow.createCell(2);
			name.setCellValue(mBeneficiaries.getFullName());
			name.setCellStyle(getDefaultCell());
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 2, 5), sheet, wb);
			name.getCellStyle().setWrapText(true);

			sheet.addMergedRegion(new CellRangeAddress(i, i, 6, 8));
			Cell NRC = subTotalRow.createCell(6);
			NRC.setCellValue(mBeneficiaries.getFullIdNo());
			NRC.setCellStyle(getDefaultCell());
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 6, 8), sheet, wb);
			NRC.getCellStyle().setWrapText(true);

			sheet.addMergedRegion(new CellRangeAddress(i, i, 9, 10));
			Cell relationShip = subTotalRow.createCell(9);
			relationShip.setCellValue(mBeneficiaries.getRelationship().getName());
			relationShip.setCellStyle(getDefaultCell());
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 9, 10), sheet, wb);
			relationShip.getCellStyle().setWrapText(true);

			sheet.addMergedRegion(new CellRangeAddress(i, i, 11, 12));
			Cell age = subTotalRow.createCell(11);
			age.setCellValue(mBeneficiaries.getAgeForNextYear());
			age.setCellStyle(getDefaultCell());
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 11, 12), sheet, wb);
			age.getCellStyle().setWrapText(true);

			sheet.addMergedRegion(new CellRangeAddress(i, i, 13, 14));
			Cell percentage = subTotalRow.createCell(13);
			percentage.setCellValue(mBeneficiaries.getPercentage());
			percentage.setCellStyle(getDefaultCell());
			setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 13, 14), sheet, wb);
			percentage.getCellStyle().setWrapText(true);

		}

		wb.setPrintArea(0, 0, 15, 0, 33);
		sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		wb.write(op);
		op.flush();
		op.close();
	}

	private XSSFCellStyle getDefaultCell() {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font font = wb.createFont();
		font.setFontName("Myanmar3");
		font.setFontHeightInPoints((short) 10);
		cellStyle.setFont(font);
		return cellStyle;
	}

	private void setRegionBorder(int borderWidth, CellRangeAddress crAddress, Sheet sheet, Workbook workBook) {
		RegionUtil.setBorderTop(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderBottom(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderRight(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderLeft(borderWidth, crAddress, sheet, workBook);
	}

	public void setMedicalPolicy(MedicalPolicy medicalPolicy) {
		this.medicalPolicy = medicalPolicy;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

}

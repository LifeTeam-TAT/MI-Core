package org.ace.insurance.web.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.web.common.ExportExcel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ChallanLetterExportExcel extends BaseBean implements ExportExcel {

	private XSSFWorkbook wb;
	private MedicalPolicy medicalPolicy;
	private List<Payment> paymentList;

	public ChallanLetterExportExcel() {
		load();
	}

	public ChallanLetterExportExcel(MedicalPolicy medicalPolicy, List<Payment> paymentList) {
		this.medicalPolicy = medicalPolicy;
		this.paymentList = paymentList;
		load();
	}

	private void load() {
		try {
			InputStream inp = getFacesContext().getExternalContext().getResourceAsStream("/report-template/medical/ChallanLetterTamplate.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load ChallanLetterTemplate.xlsx Formatted Excel", e);
		}
	}

	public void generate(OutputStream op) throws IOException {
		Payment payment = paymentList.get(0);
		Sheet sheet = wb.getSheet("Challan");
		Cell cellForDate = sheet.getRow(8).getCell(17);
		cellForDate.setCellValue(new Date());
		Cell cellForProposalNo = sheet.getRow(11).getCell(6);
		cellForProposalNo.setCellValue(medicalPolicy.getMedicalProposal().getProposalNo());
		Cell cellForChallanNo = sheet.getRow(12).getCell(6);
		// cellForChallanNo.setCellValue(payment.getChalanNo());
		Cell cellForPaymentType = sheet.getRow(13).getCell(6);
		cellForPaymentType.setCellValue(medicalPolicy.getPaymentType().getName());
		Cell cellForAgentName = sheet.getRow(14).getCell(6);
		if (medicalPolicy.getAgent() != null)
			cellForAgentName.setCellValue(medicalPolicy.getAgent().getFullName());
		Cell cellForInsuredPersonName = sheet.getRow(15).getCell(6);
		cellForInsuredPersonName.setCellValue("");
		Cell cellForAddress = sheet.getRow(16).getCell(6);
		cellForAddress.setCellValue("");
		Cell cellForNoOfUnit = sheet.getRow(19).getCell(9);
		cellForNoOfUnit.setCellValue("");
		Cell cellForPremium = sheet.getRow(20).getCell(9);
		cellForPremium.setCellValue(payment.getBasicPremium());
		Cell cellForDiscountAmount = sheet.getRow(21).getCell(9);
		cellForDiscountAmount.setCellValue(payment.getSpecialDiscount());
		Cell cellForServiceCharges = sheet.getRow(22).getCell(9);
		cellForServiceCharges.setCellValue(payment.getServicesCharges());
		Cell cellForTotal = sheet.getRow(23).getCell(9);
		cellForTotal.setCellValue(payment.getTotalAmount());
		wb.setPrintArea(0, 0, 20, 0, 39);
		wb.write(op);
		op.flush();
		op.close();
	}

}

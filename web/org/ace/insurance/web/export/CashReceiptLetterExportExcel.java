package org.ace.insurance.web.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.web.common.ExportExcel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CashReceiptLetterExportExcel extends BaseBean implements ExportExcel {

	private XSSFWorkbook wb;
	private MedicalPolicy medicalPolicy;
	private List<Payment> paymentList;

	public CashReceiptLetterExportExcel() {
		load();
	}

	public CashReceiptLetterExportExcel(MedicalPolicy medicalPolicy, List<Payment> paymentList) {
		this.medicalPolicy = medicalPolicy;
		this.paymentList = paymentList;
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/CashReceiptLetterTemplate.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load CashReceiptLetterTamplate.xlsx Formatted Excel", e);
		}
	}

	public void generate(OutputStream op) throws IOException {
		Payment payment = paymentList.get(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String period = "From " + dateFormat.format(new Date()) + " To " + dateFormat.format(Utils.addMonth(new Date(), 12));
		Sheet sheet = wb.getSheet("CashReceipt");
		Cell cellForDate = sheet.getRow(10).getCell(17);
		cellForDate.setCellValue(new Date());
		Cell cellForProposalNo = sheet.getRow(10).getCell(5);
		cellForProposalNo.setCellValue(medicalPolicy.getMedicalProposal().getProposalNo());
		Cell cellForReceiptNo = sheet.getRow(11).getCell(5);
		cellForReceiptNo.setCellValue(payment.getReceiptNo());
		Cell cellForNoOfUnit = sheet.getRow(13).getCell(6);
		cellForNoOfUnit.setCellValue("");
		Cell cellForPremium = sheet.getRow(14).getCell(6);
		cellForPremium.setCellValue(payment.getBasicPremium());
		Cell cellForDiscountAmount = sheet.getRow(15).getCell(6);
		cellForDiscountAmount.setCellValue(payment.getSpecialDiscount());
		Cell cellForAdditionalPremium = sheet.getRow(16).getCell(6);
		cellForAdditionalPremium.setCellValue(payment.getAddOnPremium());
		Cell cellForNetPremium = sheet.getRow(17).getCell(6);
		cellForNetPremium.setCellValue(payment.getNetPremium());
		Cell cellForServiceCharges = sheet.getRow(18).getCell(6);
		cellForServiceCharges.setCellValue(payment.getServicesCharges());
		Cell cellForPolicyStampFees = sheet.getRow(19).getCell(6);
		cellForPolicyStampFees.setCellValue(payment.getStampFees());
		Cell cellForTotal = sheet.getRow(20).getCell(6);
		cellForTotal.setCellValue(payment.getTotalAmount());
		Cell cellForPolicyType = sheet.getRow(12).getCell(15);
		cellForPolicyType.setCellValue("");
		Cell cellForPaymentType = sheet.getRow(13).getCell(15);
		cellForPaymentType.setCellValue(medicalPolicy.getPaymentType().getName());
		Cell cellForPeriod = sheet.getRow(14).getCell(15);
		cellForPeriod.setCellValue(period);
		Cell cellForAgentName = sheet.getRow(15).getCell(15);
		if (medicalPolicy.getAgent() != null)
			cellForAgentName.setCellValue(medicalPolicy.getAgent().getFullName());
		Cell cellForInsuredPersonName = sheet.getRow(16).getCell(15);
		cellForInsuredPersonName.setCellValue("");
		Cell cellForAddress = sheet.getRow(18).getCell(11);
		cellForAddress.setCellValue("");

		Cell cellForAccDate = sheet.getRow(26).getCell(17);
		cellForAccDate.setCellValue(new Date());
		Cell cellForAccProposalNo = sheet.getRow(26).getCell(5);
		cellForAccProposalNo.setCellValue(medicalPolicy.getMedicalProposal().getProposalNo());
		Cell cellForAccReceiptNo = sheet.getRow(27).getCell(5);
		cellForAccReceiptNo.setCellValue(payment.getReceiptNo());
		Cell cellForAccNoOfUnit = sheet.getRow(29).getCell(6);
		cellForAccNoOfUnit.setCellValue("");
		Cell cellForAccPremium = sheet.getRow(30).getCell(6);
		cellForAccPremium.setCellValue(payment.getBasicPremium());
		Cell cellForAccDiscountAmount = sheet.getRow(31).getCell(6);
		cellForAccDiscountAmount.setCellValue(payment.getSpecialDiscount());
		Cell cellForAccAdditionalPremium = sheet.getRow(32).getCell(6);
		cellForAccAdditionalPremium.setCellValue(payment.getAddOnPremium());
		Cell cellForAccNetPremium = sheet.getRow(33).getCell(6);
		cellForAccNetPremium.setCellValue(payment.getNetPremium());
		Cell cellForAccServiceCharges = sheet.getRow(34).getCell(6);
		cellForAccServiceCharges.setCellValue(payment.getServicesCharges());
		Cell cellForAccPolicyStampFees = sheet.getRow(35).getCell(6);
		cellForAccPolicyStampFees.setCellValue(payment.getStampFees());
		Cell cellForAccTotal = sheet.getRow(36).getCell(6);
		cellForAccTotal.setCellValue(payment.getTotalAmount());
		Cell cellForAccPolicyType = sheet.getRow(28).getCell(15);
		cellForAccPolicyType.setCellValue("");
		Cell cellForAccPaymentType = sheet.getRow(29).getCell(15);
		cellForAccPaymentType.setCellValue(medicalPolicy.getPaymentType().getName());
		Cell cellForAccPeriod = sheet.getRow(30).getCell(15);
		cellForAccPeriod.setCellValue(period);
		Cell cellForAccAgentName = sheet.getRow(31).getCell(15);
		if (medicalPolicy.getAgent() != null)
			cellForAccAgentName.setCellValue(medicalPolicy.getAgent().getFullName());
		Cell cellForAccInsuredPersonName = sheet.getRow(32).getCell(15);
		cellForAccInsuredPersonName.setCellValue("");
		Cell cellForAccAddress = sheet.getRow(34).getCell(11);
		cellForAccAddress.setCellValue("");

		Cell cellForMedDate = sheet.getRow(42).getCell(17);
		cellForMedDate.setCellValue(new Date());
		Cell cellForMedProposalNo = sheet.getRow(42).getCell(5);
		cellForMedProposalNo.setCellValue(medicalPolicy.getMedicalProposal().getProposalNo());
		Cell cellForMedReceiptNo = sheet.getRow(43).getCell(5);
		cellForMedReceiptNo.setCellValue(payment.getReceiptNo());
		Cell cellForMedNoOfUnit = sheet.getRow(45).getCell(6);
		cellForMedNoOfUnit.setCellValue("");
		Cell cellForMedPremium = sheet.getRow(46).getCell(6);
		cellForMedPremium.setCellValue(payment.getBasicPremium());
		Cell cellForMedDiscountAmount = sheet.getRow(47).getCell(6);
		cellForMedDiscountAmount.setCellValue(payment.getSpecialDiscount());
		Cell cellForMedAdditionalPremium = sheet.getRow(48).getCell(6);
		cellForMedAdditionalPremium.setCellValue(payment.getAddOnPremium());
		Cell cellForMedNetPremium = sheet.getRow(49).getCell(6);
		cellForMedNetPremium.setCellValue(payment.getNetPremium());
		Cell cellForMedServiceCharges = sheet.getRow(50).getCell(6);
		cellForMedServiceCharges.setCellValue(payment.getServicesCharges());
		Cell cellForMedPolicyStampFees = sheet.getRow(51).getCell(6);
		cellForMedPolicyStampFees.setCellValue(payment.getStampFees());
		Cell cellForMedTotal = sheet.getRow(52).getCell(6);
		cellForMedTotal.setCellValue(payment.getTotalAmount());
		Cell cellForMedPolicyType = sheet.getRow(44).getCell(15);
		cellForMedPolicyType.setCellValue("");
		Cell cellForMedPaymentType = sheet.getRow(45).getCell(15);
		cellForMedPaymentType.setCellValue(medicalPolicy.getPaymentType().getName());
		Cell cellForMedPeriod = sheet.getRow(46).getCell(15);
		cellForMedPeriod.setCellValue(period);
		Cell cellForMedAgentName = sheet.getRow(47).getCell(15);
		if (medicalPolicy.getAgent() != null)
			cellForMedAgentName.setCellValue(medicalPolicy.getAgent().getFullName());
		Cell cellForMedInsuredPersonName = sheet.getRow(48).getCell(15);
		cellForMedInsuredPersonName.setCellValue("");
		Cell cellForMedAddress = sheet.getRow(50).getCell(11);
		cellForMedAddress.setCellValue("");
		wb.setPrintArea(0, 0, 20, 0, 55);
		wb.write(op);
		op.flush();
		op.close();
	}

}

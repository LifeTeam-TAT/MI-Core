package org.ace.insurance.web.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ace.insurance.common.AcceptedLetterConfig;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.web.common.ExportExcel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AcceptedLetterExportExcel extends BaseBean implements ExportExcel {
	private XSSFWorkbook wb;
	private MedicalProposal medicalProposal;

	public AcceptedLetterExportExcel() {
		load();
	}

	public AcceptedLetterExportExcel(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/MedicalPolicyAcceptedLetter.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load MedicalPolicyAcceptedLetter.xlsx Formatted Excel", e);
		}
	}

	public void generate(OutputStream op) throws IOException {

		Sheet sheet = wb.getSheet("PolicyAcceptedLetter");

		Cell cellForProposalNo = sheet.getRow(10).getCell(13);
		cellForProposalNo.setCellValue(medicalProposal.getProposalNo());

		Cell cellForDate = sheet.getRow(11).getCell(13);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String todayDate = dateFormat.format(new Date());
		cellForDate.setCellValue(todayDate);

		Cell cellForCustomer = sheet.getRow(13).getCell(1);
		cellForCustomer.setCellValue(medicalProposal.getCustomer().getFullName());

		Cell cellForAddress = sheet.getRow(14).getCell(5);
		cellForAddress.setCellValue(medicalProposal.getCustomer().getFullAddress());

		String acceptedCase = AcceptedLetterConfig.getACCEPTED_CASE1() + " " + medicalProposal.getCustomer().getFullName() + " " + AcceptedLetterConfig.getACCEPTED_CASE2();
		Cell cellForCase = sheet.getRow(18).getCell(1);
		cellForCase.setCellValue(acceptedCase);

		Cell cellForProposal = sheet.getRow(23).getCell(12);
		cellForProposal.setCellValue(medicalProposal.getProposalNo());

		Cell cellForPeriodYear = sheet.getRow(24).getCell(12);
		cellForPeriodYear.setCellValue("");

		Cell cellForUnit = sheet.getRow(26).getCell(12);
		cellForUnit.setCellValue("");

		Cell cellForPremium = sheet.getRow(27).getCell(12);
		cellForPremium.setCellValue(medicalProposal.getTotalPremium());

		Cell cellForStampFee = sheet.getRow(29).getCell(12);
		cellForStampFee.setCellValue("-");

		Cell cellForTotalPremium = sheet.getRow(30).getCell(12);
		cellForTotalPremium.setCellValue(medicalProposal.getTotalPremium());

		String agentInfo;
		if (medicalProposal.getAgent() != null) {
			agentInfo = AcceptedLetterConfig.getAGENT_INFO1() + " " + medicalProposal.getAgent().getFullName() + "( " + medicalProposal.getAgent().getLiscenseNo() + " )" + " "
					+ AcceptedLetterConfig.getAGENT_INFO2();
		} else {
			agentInfo = AcceptedLetterConfig.getAGENT_INFO1() + " " + "( - )" + " " + AcceptedLetterConfig.getAGENT_INFO2();
		}
		Cell cellForAgent = sheet.getRow(39).getCell(2);
		cellForAgent.setCellValue(agentInfo);

		wb.setPrintArea(0, 0, 18, 0, 40);
		wb.write(op);
		op.flush();
		op.close();
	}

	public void setWb(XSSFWorkbook wb) {
		this.wb = wb;
	}

	public void setMedicalProposal(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
	}

}

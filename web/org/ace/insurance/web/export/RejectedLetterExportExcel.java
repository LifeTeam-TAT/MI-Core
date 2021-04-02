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

public class RejectedLetterExportExcel extends BaseBean implements ExportExcel {
	private XSSFWorkbook wb;
	private MedicalProposal medicalProposal;

	public RejectedLetterExportExcel() {
		load();
	}

	public RejectedLetterExportExcel(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
		load();
	}

	private void load() {
		try {
			InputStream inp = getFacesContext().getExternalContext().getResourceAsStream("/report-template/medical/MedicalPolicyRejectedLetter.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load MedicalPolicyRejectedLetter.xlsx Formatted Excel", e);
		}
	}

	public void generate(OutputStream op) throws IOException {

		Sheet sheet = wb.getSheet("PolicyRejectedLetter");

		Cell cellForProposalNo = sheet.getRow(10).getCell(13);
		cellForProposalNo.setCellValue(medicalProposal.getProposalNo());

		Cell cellForDate = sheet.getRow(11).getCell(13);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String todayDate = dateFormat.format(new Date());
		cellForDate.setCellValue(todayDate);

		String rejectedCase = "                                    " + AcceptedLetterConfig.getREJECTED_CASE1() + " " + medicalProposal.getCustomer().getFullName() + " "
				+ AcceptedLetterConfig.getREJECTED_CASE2() + " " + medicalProposal.getCustomer().getIdNo() + " " + AcceptedLetterConfig.getREJECTED_CASE3();
		Cell cellForCase = sheet.getRow(18).getCell(1);
		cellForCase.setCellValue(rejectedCase);

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

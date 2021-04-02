package org.ace.insurance.report.medical.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalPolicyMonthlyReportDTO;

public interface IMedicalPolicyMonthlyReportService {
	public List<MedicalPolicyMonthlyReportDTO> findMedicalPolicyMonthlyReport(MonthlyReportCriteria criteria);

	public void generateMedicalPolicyMonthlyReport(List<MedicalPolicyMonthlyReportDTO> medicalPolicyList, String dirPath, String fileName, String branchName) throws Exception;
}

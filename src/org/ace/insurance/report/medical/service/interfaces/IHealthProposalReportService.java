package org.ace.insurance.report.medical.service.interfaces;

import java.util.List;

import org.ace.insurance.report.medical.HealthProposalReportDTO;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;

public interface IHealthProposalReportService {
	public List<HealthProposalReportDTO> findHealthProposalReportDTO(HealthProposalReportCriteria criteria);
	
	public void generateHealthProposalReport(List<HealthProposalReportDTO> healthProposalReportList, String dirPath, String fileName, String branchName) throws Exception;
}

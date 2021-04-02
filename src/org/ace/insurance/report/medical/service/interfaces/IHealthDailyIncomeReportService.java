package org.ace.insurance.report.medical.service.interfaces;

import java.util.List;

import org.ace.insurance.report.medical.HealthDailyIncomeReportDTO;
import org.ace.insurance.report.medical.HealthDailyPremiumReportDTO;
import org.ace.insurance.report.medical.HealthDailyReportDTO;
import org.ace.insurance.web.manage.report.medical.HealthDailyIncomeReportCriteria;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;

public interface IHealthDailyIncomeReportService {
	public List<HealthDailyReportDTO> findHealthDailyReportDTO(HealthDailyIncomeReportCriteria criteria);

	public List<HealthDailyPremiumReportDTO> findHealthDailyPremiumReportDTO(HealthDailyIncomeReportCriteria criteria);

	public List<HealthDailyIncomeReportDTO> findMedicalClaimRequest(HealthProposalReportCriteria criteria);

	public List<HealthDailyIncomeReportDTO> findMedicalClaimPaymentReport(HealthProposalReportCriteria criteria);
}

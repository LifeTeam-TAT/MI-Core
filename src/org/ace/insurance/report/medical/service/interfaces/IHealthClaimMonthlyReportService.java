package org.ace.insurance.report.medical.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthClaimMonthlyReport;

public interface IHealthClaimMonthlyReportService {
	public List<HealthClaimMonthlyReport> findHealthClaimMonthlyReport(MonthlyReportCriteria criteria);
}

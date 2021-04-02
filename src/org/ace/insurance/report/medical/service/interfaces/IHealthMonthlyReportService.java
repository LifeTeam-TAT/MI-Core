package org.ace.insurance.report.medical.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthMonthlyReport;
import org.ace.insurance.report.medical.HealthMonthlyReportDTO;

public interface IHealthMonthlyReportService {
	public List<HealthMonthlyReport> findHealthMonthlyReport(MonthlyReportCriteria criteria);
}

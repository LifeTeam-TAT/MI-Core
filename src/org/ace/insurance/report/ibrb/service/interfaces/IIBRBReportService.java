package org.ace.insurance.report.ibrb.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.ReportCriteria;
import org.ace.insurance.report.ibrb.CriticalIBRBMonthlyReport;
import org.ace.insurance.report.ibrb.HealthIBRBMonthlyReport;
import org.ace.insurance.report.ibrb.MicroHealthIBRBMonthlyReport;

public interface IIBRBReportService {
	public List<HealthIBRBMonthlyReport> findHealthIBRBMonthlyReports(ReportCriteria criteria);

	public List<CriticalIBRBMonthlyReport> findCriticalIBRBMonthlyReports(ReportCriteria criteria);

	public List<MicroHealthIBRBMonthlyReport> findMicroHealthIBRBMonthlyReports(ReportCriteria criteria);
}

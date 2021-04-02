package org.ace.insurance.report.common.service.interfaces;

import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.FinancialReportCriteria;

public interface IFinancialReportService {
	public FinancialReport findFinancialReport(FinancialReportCriteria criteria);
	public FinancialReport findAllWorkFlowStatus();
}

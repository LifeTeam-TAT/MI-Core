package org.ace.insurance.report.common.persistence.interfaces;

import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.FinancialReportCriteria;

public interface IFinancialReportDAO {
	public FinancialReport findFinancialReport(FinancialReportCriteria criteria);
}

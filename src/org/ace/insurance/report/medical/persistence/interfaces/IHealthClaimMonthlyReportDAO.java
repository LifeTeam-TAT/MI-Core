package org.ace.insurance.report.medical.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthClaimMonthlyReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IHealthClaimMonthlyReportDAO {
	public List<HealthClaimMonthlyReport> findHealthClaimMonthlyReport(MonthlyReportCriteria criteria) throws DAOException;
}

package org.ace.insurance.report.medical.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthMonthlyReport;
import org.ace.insurance.report.medical.HealthMonthlyReportDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface IHealthMonthlyReportDAO {
	public List<HealthMonthlyReport> findHealthMonthlyReport(MonthlyReportCriteria criteria) throws DAOException;
}

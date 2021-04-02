package org.ace.insurance.report.ibrb.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.ReportCriteria;
import org.ace.insurance.report.ibrb.CriticalIBRBMonthlyReport;
import org.ace.insurance.report.ibrb.HealthIBRBMonthlyReport;
import org.ace.insurance.report.ibrb.MicroHealthIBRBMonthlyReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IIBRBReportDAO {

	public List<HealthIBRBMonthlyReport> findHealthIBRBMonthlyReports(ReportCriteria criteria) throws DAOException;

	public List<CriticalIBRBMonthlyReport> findCriticalIBRBMonthlyReports(ReportCriteria criteria) throws DAOException;

	public List<MicroHealthIBRBMonthlyReport> findMicroHealthIBRBMonthlyReports(ReportCriteria criteria) throws DAOException;

}

package org.ace.insurance.report.life.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifeClaimStatusReport;
import org.ace.insurance.report.life.LifeClaimStatusReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeClaimStatusReportDAO {
	public List<LifeClaimStatusReport> findLifeClaimStatusReport(LifeClaimStatusReportCriteria criteria) throws DAOException;
}

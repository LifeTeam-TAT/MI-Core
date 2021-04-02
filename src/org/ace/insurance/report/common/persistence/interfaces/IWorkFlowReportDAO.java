package org.ace.insurance.report.common.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.WorkFlowStatusCriteria;
import org.ace.insurance.report.common.WorkFlowStatusReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IWorkFlowReportDAO {
	public List<WorkFlowStatusReport> findStatusReport(WorkFlowStatusCriteria criteria) throws DAOException;
	public FinancialReport findAllWorkFlowStatus() throws DAOException;
}

package org.ace.insurance.report.common.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.WorkFlowStatusCriteria;
import org.ace.insurance.report.common.WorkFlowStatusReport;

public interface IWorkFlowReportService {
	public List<WorkFlowStatusReport> findStatusReport(WorkFlowStatusCriteria criteria);
}

package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.AgentComparisonSalesReport;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;

public interface IAgentComparisonSalesReportService {
	public List<AgentComparisonSalesReport> findAgentComparisonSalesReport(AgentSaleComparisonCriteria criteria);
}

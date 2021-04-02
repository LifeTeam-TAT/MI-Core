package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.AgentComparisonSalesReport;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentComparisonSalesReportDAO {
	public List<AgentComparisonSalesReport> findAgentComparisonSalesReport(AgentSaleComparisonCriteria criteria) throws DAOException;
}

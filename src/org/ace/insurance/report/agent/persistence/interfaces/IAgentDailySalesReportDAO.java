package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentSaleComparisonReport;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentDailySalesReportDAO {
	public List<AgentSaleComparisonReport> findForLife(AgentSalesReportCriteria criteria, String type) throws DAOException;

	public List<AgentSaleComparisonReport> findForNonLifeByReferenceType(AgentSalesReportCriteria agentDailySalesCriteria, String referenceType) throws DAOException;
}

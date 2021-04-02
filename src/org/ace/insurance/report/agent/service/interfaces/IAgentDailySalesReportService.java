package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;
import java.util.Map;

import org.ace.insurance.report.agent.AgentSaleComparisonReport;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;

public interface IAgentDailySalesReportService {

	public List<AgentSaleComparisonReport> findForLife(AgentSalesReportCriteria criteria);

	public List<AgentSaleComparisonReport> findForNonLife(AgentSalesReportCriteria criteria);

	public void calculatePolicyForNonLife(List<AgentSaleComparisonReport> agentSaleList, Map<String, AgentSaleComparisonReport> agentSaleReportMap, String proposalType,
			String referenceType);

	public List<AgentSaleComparisonReport> findForNonLife_NEW_RENEWAL(AgentSalesReportCriteria criteria);

}

package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;
import java.util.Map;

import org.ace.insurance.report.agent.AgentSaleMonthlyDto;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;

public interface IAgentSaleMonthlyReportService {
	public List<AgentSaleMonthlyDto> findMonthlySaleReport(AgentSalesReportCriteria criteria);

	public void combineNew_Renewal_AgentMonthlySale(List<AgentSaleMonthlyDto> agentSaleList, Map<String, AgentSaleMonthlyDto> agentSaleReportMap);

	public List<AgentSaleMonthlyDto> findForNonLife(AgentSalesReportCriteria criteria);

	public void calculatePolicyForNonLife(List<AgentSaleMonthlyDto> agentSaleList, Map<String, AgentSaleMonthlyDto> agentSaleReportMap, String proposalType, String referenceType);
}

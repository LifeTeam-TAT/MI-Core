package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.insurance.report.common.AgentSaleData;

public interface IAgentSaleMonthlyComparisonService {

	public List<AgentSaleData> findAgentSaleMonthlyReport(AgentSaleComparisonCriteria criteria);

}

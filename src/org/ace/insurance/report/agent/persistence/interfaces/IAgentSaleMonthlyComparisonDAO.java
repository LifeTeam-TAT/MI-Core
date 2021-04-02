package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.insurance.report.common.AgentSaleData;

public interface IAgentSaleMonthlyComparisonDAO {

	public List<AgentSaleData> find(AgentSaleComparisonCriteria criteria);

}

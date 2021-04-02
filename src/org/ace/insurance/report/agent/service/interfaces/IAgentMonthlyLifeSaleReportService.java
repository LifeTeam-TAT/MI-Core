package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentMonthlyLifeSaleReport;
import org.ace.insurance.report.common.AgentMonthlyLifeSaleCriteria;

public interface IAgentMonthlyLifeSaleReportService {

	public List<AgentMonthlyLifeSaleReport> findAgentMonthlyLifeSaleReport(AgentMonthlyLifeSaleCriteria criteria);
}

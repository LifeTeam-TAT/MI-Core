package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.Map;

import org.ace.insurance.report.agent.AgentMonthlyLifeSaleReport;
import org.ace.insurance.report.common.AgentMonthlyLifeSaleCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentMonthlyLifeSaleReportDAO {

	public Map<String, AgentMonthlyLifeSaleReport> findMonthlySale(AgentMonthlyLifeSaleCriteria criteria) throws DAOException;
}

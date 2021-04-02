package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentSaleCriteria;
import org.ace.insurance.report.agent.AgentSaleReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentSaleReportDAO {

	public List<AgentSaleReport> findGeneral(AgentSaleCriteria criteria) throws DAOException;

	public List<AgentSaleReport> findLife(AgentSaleCriteria criteria) throws DAOException;
}

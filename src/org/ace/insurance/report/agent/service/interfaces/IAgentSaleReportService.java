package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentSaleCriteria;
import org.ace.insurance.report.agent.AgentSaleReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentSaleReportService {
	public List<AgentSaleReport> findForLife(AgentSaleCriteria criteria) throws DAOException;

	public List<AgentSaleReport> findForGrneral(AgentSaleCriteria criteria) throws DAOException;
}

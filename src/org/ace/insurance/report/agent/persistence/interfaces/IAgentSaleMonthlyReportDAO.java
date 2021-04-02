package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentSaleMonthlyDto;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentSaleMonthlyReportDAO {
	public List<AgentSaleMonthlyDto> findForNonLifeByReferenceType(AgentSalesReportCriteria criteria, String referenceType) throws DAOException;

}

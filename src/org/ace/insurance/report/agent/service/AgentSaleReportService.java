package org.ace.insurance.report.agent.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.agent.AgentSaleCriteria;
import org.ace.insurance.report.agent.AgentSaleReport;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSaleReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentSaleReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentSaleReportService")
public class AgentSaleReportService implements IAgentSaleReportService {

	@Resource(name = "AgentSaleReportDAO")
	private IAgentSaleReportDAO agentSaleReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleReport> findForGrneral(AgentSaleCriteria criteria) throws DAOException {
		List<AgentSaleReport> result = null;
		try {
			result = agentSaleReportDAO.findGeneral(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SalesReportReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleReport> findForLife(AgentSaleCriteria criteria) throws DAOException {
		List<AgentSaleReport> result = null;
		try {
			result = agentSaleReportDAO.findLife(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SalesReportReport by criteria.", e);
		}
		return result;
	}

}

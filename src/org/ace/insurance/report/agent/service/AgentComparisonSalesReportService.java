package org.ace.insurance.report.agent.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.agent.persistence.interfaces.IAgentComparisonSalesReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentComparisonSalesReportService;
import org.ace.insurance.report.common.AgentComparisonSalesReport;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentComparisonSalesReportService")
public class AgentComparisonSalesReportService extends BaseService implements IAgentComparisonSalesReportService {

	@Resource(name = "AgentComparisonSalesReportDAO")
	private IAgentComparisonSalesReportDAO agentComparisonSalesReportDAO;

	/**
	 * Find AgentComparisonSalesReport by given criteria
	 * 
	 * @param AgentSaleComparisonCriteria
	 * 
	 * @return List[AgentComparisonSalesReport]
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentComparisonSalesReport> findAgentComparisonSalesReport(AgentSaleComparisonCriteria criteria) {
		List<AgentComparisonSalesReport> result = new ArrayList<AgentComparisonSalesReport>();
		try {
			result = agentComparisonSalesReportDAO.findAgentComparisonSalesReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentComparisonSalesReport by criteria.", e);
		}
		return result;

	}
}

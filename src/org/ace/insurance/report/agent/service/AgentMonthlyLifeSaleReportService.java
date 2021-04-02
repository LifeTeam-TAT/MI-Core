package org.ace.insurance.report.agent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.agent.AgentMonthlyLifeSaleReport;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentMonthlyLifeSaleReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentMonthlyLifeSaleReportService;
import org.ace.insurance.report.common.AgentMonthlyLifeSaleCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**************************************************************************
 * @Date 12/05/2016.
 * @author Pyae Phyo Aung.
 * @Rev v1.0.
 * @CopyRight ACEPLUS SOLUTIONS CO., Ltd.
 *************************************************************************/

@Service(value = "AgentMonthlyLifeSaleReportService")
public class AgentMonthlyLifeSaleReportService extends BaseService implements IAgentMonthlyLifeSaleReportService {

	@Resource(name = "AgentMonthlyLifeSaleReportDAO")
	private IAgentMonthlyLifeSaleReportDAO agentMonthlyLifeSaleReportDAO;

	/**
	 * Find AgentMonthlyLifeSaleReport given required criteria fields. Call
	 * findMonthlySale method from agentMonthlyLifeSaleReportDAO.It will return
	 * Map and convert to list.
	 * 
	 * @param AgentMonthlyLifeSaleCriteria
	 *            [Required fields to filter for Report]
	 * 
	 * @return List<AgentMonthlyLifeSaleReport>[AgentMonthlyLifeSaleReport List]
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentMonthlyLifeSaleReport> findAgentMonthlyLifeSaleReport(AgentMonthlyLifeSaleCriteria criteria) {
		List<AgentMonthlyLifeSaleReport> result;
		Map<String, AgentMonthlyLifeSaleReport> resultMap;
		try {
			resultMap = agentMonthlyLifeSaleReportDAO.findMonthlySale(criteria);
			result = new ArrayList<AgentMonthlyLifeSaleReport>(resultMap.values());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find Agent Monthly Sale Report for Life Assessment by criteria Service.", e);
		}
		return result;
	}
}

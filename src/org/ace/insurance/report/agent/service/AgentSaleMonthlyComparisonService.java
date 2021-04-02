package org.ace.insurance.report.agent.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.agent.persistence.interfaces.IAgentSaleMonthlyComparisonDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentSaleMonthlyComparisonService;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.insurance.report.common.AgentSaleData;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2015-12-10
 * @Version 1.0
 * @Purpose This class serves as Service Layer to show the
 *          <code>AgentSaleComparisonReport</code> Report process.
 * 
 ***************************************************************************************/

@Service(value = "AgentSaleMonthlyComparisonService")
public class AgentSaleMonthlyComparisonService extends BaseService implements IAgentSaleMonthlyComparisonService {

	@Resource(name = "AgentSaleMonthlyComparisonDAO")
	private IAgentSaleMonthlyComparisonDAO agentSaleMonthlyComparisonDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleData> findAgentSaleMonthlyReport(AgentSaleComparisonCriteria criteria) {
		List<AgentSaleData> result = new ArrayList<AgentSaleData>();
		try {
			result = agentSaleMonthlyComparisonDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentSaleMonthlyReport by criteria.", e);
		}
		return result;
	}
}

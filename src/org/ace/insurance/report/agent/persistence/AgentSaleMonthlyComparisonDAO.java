package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSaleMonthlyComparisonDAO;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.insurance.report.common.AgentSaleData;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2015-12-10
 * @Version 1.0
 * @Purpose This class serves as the Data Manipulation to retrieve the
 *          <code>AgentSaleComparisonReport</code> Report data.
 * 
 ***************************************************************************************/

@Repository("AgentSaleMonthlyComparisonDAO")
public class AgentSaleMonthlyComparisonDAO extends BasicDAO implements IAgentSaleMonthlyComparisonDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleData> find(AgentSaleComparisonCriteria criteria) throws DAOException {
		List<AgentSaleData> result = new ArrayList<AgentSaleData>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW org.ace.insurance.report.common.AgentSaleData(a.referenceType,");
			if (criteria.getProposalType().equalsIgnoreCase("NEW")) {
				query.append("SUM(a.newPolicy),");
				// query.append("SUM(a.newPolicy+a.endowmentLife+a.groupLife),");
			} else {
				query.append("SUM(a.renewalPolicy),");
				// query.append("SUM(a.renewalPolicy+a.endowmentLife+a.groupLife),");
			}
			if (criteria.getCurrencyType() != null) {
				query.append("SUM(a.totalPremium))");
			} else {
				query.append("SUM(a.homeTotalPremium))");
			}
			query.append(" FROM AgentSaleComparisonReportView a WHERE a.agentId IS NOT NULL ");
			query.append(" AND  a.activedPolicyStartDate >= :startDate");
			query.append(" AND a.activedPolicyStartDate <= :endDate");
			query.append(" AND a.proposalType = :proposalType");
			if (criteria.getCurrencyType() != null) {
				query.append(" AND a.currencyId = :currency");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND a.branchId = :branchId");
			}
			query.append(" GROUP BY a.referenceType");

			Query q = em.createQuery(query.toString());

			q.setParameter("startDate", Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("endDate", Utils.getEndDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("proposalType", criteria.getProposalType().toString());
			if (criteria.getCurrencyType() != null) {
				q.setParameter("currency", criteria.getCurrencyType().getCurrencyCode());
			}
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Agent Sale Monthly Report by criteria.", pe);
		}
		return result;
	}
}

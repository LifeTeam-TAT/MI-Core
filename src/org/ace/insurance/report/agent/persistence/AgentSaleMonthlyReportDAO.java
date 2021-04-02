package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.AgentSaleMonthlyDto;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSaleMonthlyReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AgentSaleMonthlyReportDAO")
public class AgentSaleMonthlyReportDAO extends BasicDAO implements IAgentSaleMonthlyReportDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleMonthlyDto> findForNonLifeByReferenceType(AgentSalesReportCriteria criteria, String referenceType) throws DAOException {
		List<AgentSaleMonthlyDto> reportList = new ArrayList<AgentSaleMonthlyDto>();
		try {

			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW org.ace.insurance.report.agent.AgentSaleMonthlyDto(m.codeNo,m.agentName,Sum(m.newPolicy),Sum(m.renewalPolicy),Sum(m.totalPremium),m.referenceType,m.proposalType)");
			query.append(" FROM AgentSaleComparisonReportView m WHERE m.referenceType=:referenceType");
			query.append(" AND m.activedPolicyStartDate >= :startDate");
			query.append(" AND m.activedPolicyStartDate <= :endDate");

			if (criteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}
			if (criteria.getCurrency() != null) {
				query.append(" AND m.currencyId = :currencyId");
			}
			if (criteria.getProposalType() != null) {
				query.append(" AND m.proposalType = :proposalType");
			}
			if (criteria.getAgent() != null) {
				query.append(" AND m.agentId= :agentId");
			}

			query.append(" group by m.codeNo, m.agentName, m.referenceType, m.proposalType ORDER BY m.codeNo");
			Query q = em.createQuery(query.toString());

			criteria.setStartDate(Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("startDate", criteria.getStartDate());

			criteria.setEndDate(Utils.getEndDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("endDate", criteria.getEndDate());

			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getCurrency() != null) {
				q.setParameter("currencyId", criteria.getCurrency().getId());
			}
			if (criteria.getProposalType() != null) {
				q.setParameter("proposalType", criteria.getProposalType());
			}
			if (criteria.getAgent() != null) {
				q.setParameter("agentId", criteria.getAgent().getId());
			}

			q.setParameter("referenceType", referenceType);
			reportList = q.getResultList();

			em.flush();
			return reportList;
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AgentDailySalesReport by criteria.", pe);
		}
	}
}

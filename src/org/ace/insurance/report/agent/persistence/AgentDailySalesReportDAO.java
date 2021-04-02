package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.AgentSaleComparisonReport;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentDailySalesReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AgentDailySalesReportDAO")
public class AgentDailySalesReportDAO extends BasicDAO implements IAgentDailySalesReportDAO {
	List<Object[]> rawList;
	List<AgentSaleComparisonReport> reportList;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleComparisonReport> findForLife(AgentSalesReportCriteria agentDailySalesCriteria, String type) throws DAOException {
		List<AgentSaleComparisonReport> reportList = new ArrayList<>();
		try {

			StringBuffer query = new StringBuffer();
			query.append(
					"SELECT NEW org.ace.insurance.report.agent.AgentSaleComparisonReport(m.codeNo,m.agentName,Sum(m.endowmentLife),Sum(m.groupLife),Sum(m.totalPremium),m.referenceType,m.proposalType)");
			query.append(" FROM AgentSaleComparisonReportView m WHERE m.referenceType=:referenceType");
			if (agentDailySalesCriteria.getStartDate() != null) {
				agentDailySalesCriteria.setStartDate(Utils.resetStartDate(agentDailySalesCriteria.getStartDate()));
				query.append(" AND m.activedPolicyStartDate >= :startDate");
			}
			if (agentDailySalesCriteria.getEndDate() != null) {
				agentDailySalesCriteria.setEndDate(Utils.resetEndDate(agentDailySalesCriteria.getEndDate()));
				query.append(" AND m.activedPolicyStartDate <= :endDate");
			}
			if (agentDailySalesCriteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}
			if (agentDailySalesCriteria.getCurrency() != null) {
				query.append(" AND m.currencyId = :currencyId");
			}
			if (agentDailySalesCriteria.getProposalType() != null) {
				query.append(" AND m.proposalType = :proposalType");
			}

			query.append(" AND " + type + "!=0");

			query.append(" group by m.agentName, m.codeNo, m.referenceType, m.proposalType");
			Query q = em.createQuery(query.toString());
			if (agentDailySalesCriteria.getStartDate() != null) {
				agentDailySalesCriteria.setStartDate(Utils.resetStartDate(agentDailySalesCriteria.getStartDate()));
				q.setParameter("startDate", agentDailySalesCriteria.getStartDate());
			}
			if (agentDailySalesCriteria.getEndDate() != null) {
				agentDailySalesCriteria.setEndDate(Utils.resetStartDate(agentDailySalesCriteria.getEndDate()));
				q.setParameter("endDate", agentDailySalesCriteria.getEndDate());
			}
			if (agentDailySalesCriteria.getBranch() != null) {
				q.setParameter("branchId", agentDailySalesCriteria.getBranch().getId());
			}
			if (agentDailySalesCriteria.getCurrency() != null) {
				q.setParameter("currencyId", agentDailySalesCriteria.getCurrency().getId());
			}
			if (agentDailySalesCriteria.getProposalType() != null) {
				q.setParameter("proposalType", agentDailySalesCriteria.getProposalType());
			}

			q.setParameter("referenceType", "LIFE_POLICY");
			reportList = q.getResultList();
			em.flush();
			return reportList;
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AgentDailySalesReport by criteria.", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleComparisonReport> findForNonLifeByReferenceType(AgentSalesReportCriteria agentDailySalesCriteria, String referenceType) throws DAOException {
		List<AgentSaleComparisonReport> reportList = new ArrayList<>();
		try {

			StringBuffer query = new StringBuffer();
			query.append(
					"SELECT NEW org.ace.insurance.report.agent.AgentSaleComparisonReport(m.codeNo,m.agentName,Sum(m.newPolicy),Sum(m.renewalPolicy),Sum(m.totalPremium),m.referenceType,m.proposalType)");
			query.append(" FROM AgentSaleComparisonReportView m WHERE m.referenceType=:referenceType");
			if (agentDailySalesCriteria.getStartDate() != null) {
				agentDailySalesCriteria.setStartDate(Utils.resetStartDate(agentDailySalesCriteria.getStartDate()));
				query.append(" AND m.activedPolicyStartDate >= :startDate");
			}
			if (agentDailySalesCriteria.getEndDate() != null) {
				agentDailySalesCriteria.setEndDate(Utils.resetEndDate(agentDailySalesCriteria.getEndDate()));
				query.append(" AND m.activedPolicyStartDate <= :endDate");
			}
			if (agentDailySalesCriteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}
			if (agentDailySalesCriteria.getCurrency() != null) {
				query.append(" AND m.currencyId = :currencyId");
			}
			if (agentDailySalesCriteria.getProposalType() != null) {
				query.append(" AND m.proposalType = :proposalType");
			}

			query.append(" group by m.codeNo, m.agentName, m.referenceType, m.proposalType ORDER BY m.codeNo");
			Query q = em.createQuery(query.toString());
			if (agentDailySalesCriteria.getStartDate() != null) {
				agentDailySalesCriteria.setStartDate(Utils.resetStartDate(agentDailySalesCriteria.getStartDate()));
				q.setParameter("startDate", agentDailySalesCriteria.getStartDate());
			}
			if (agentDailySalesCriteria.getEndDate() != null) {
				agentDailySalesCriteria.setEndDate(Utils.resetStartDate(agentDailySalesCriteria.getEndDate()));
				q.setParameter("endDate", agentDailySalesCriteria.getEndDate());
			}
			if (agentDailySalesCriteria.getBranch() != null) {
				q.setParameter("branchId", agentDailySalesCriteria.getBranch().getId());
			}
			if (agentDailySalesCriteria.getCurrency() != null) {
				q.setParameter("currencyId", agentDailySalesCriteria.getCurrency().getId());
			}
			if (agentDailySalesCriteria.getProposalType() != null) {
				q.setParameter("proposalType", agentDailySalesCriteria.getProposalType());
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

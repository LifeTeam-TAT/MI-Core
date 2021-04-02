package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.AgentStatus;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.AgentCommissionDetailCriteria;
import org.ace.insurance.report.agent.AgentCommissionDetailReport;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentCommissionDetailDAO;
import org.ace.insurance.report.agent.view.AgentCommissionDetailReportView;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("AgentCommissionDetailReportDAO")
public class AgentCommissionDetailReportDAO extends BasicDAO implements IAgentCommissionDetailDAO {

	public List<AgentCommissionDetailReport> find(AgentCommissionDetailCriteria criteria) throws DAOException {
		List<AgentCommissionDetailReport> resultList = new ArrayList<AgentCommissionDetailReport>();

		List<AgentCommissionDetailReport> lifeList = null;

		if (criteria.insuranceType == null) {

			lifeList = findForLife(criteria);

			resultList.addAll(lifeList);

		} else if (criteria.insuranceType.equals(InsuranceType.LIFE)) {
			resultList = findForLife(criteria);
		}
		return resultList;
	}

	private List<AgentCommissionDetailReport> findForLife(AgentCommissionDetailCriteria criteria) throws DAOException {
		List<AgentCommissionDetailReport> reportList = new ArrayList<AgentCommissionDetailReport>();
		List<AgentCommissionDetailReportView> viewList = null;
		AgentCommissionDetailReport report = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT a FROM AgentCommissionDetailReportView a WHERE a.policyId IS NOT NULL    AND a.insuranceType = :referenceType");
			if (criteria.getAgent() != null) {
				query.append(" AND a.agentId = :agentId");
			}
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.append(" AND a.commissionDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetStartDate(criteria.getEndDate()));
				query.append(" AND a.commissionDate <= :endDate");
			}
			if (criteria.getAgentStatus() != null) {
				if (criteria.getAgentStatus().equals(AgentStatus.OUTSTANDING)) {
					query.append(" AND a.status = FALSE AND a.isPaid = FALSE");
				}
				if (criteria.getAgentStatus().equals(AgentStatus.SANCTION)) {
					query.append(" AND a.status = TRUE AND a.isPaid = FALSE");
				}
				if (criteria.getAgentStatus().equals(AgentStatus.PAID)) {
					query.append(" AND a.status = TRUE AND a.isPaid = TRUE");
				}
			} else {
				query.append(" AND a.status = FALSE AND a.isPaid = FALSE");
			}

			Query q = em.createQuery(query.toString());

			// FIXME CHECK REFTYPE
			q.setParameter("referenceType", PolicyReferenceType.GROUP_LIFE_POLICY);
			if (criteria.getAgent() != null) {
				q.setParameter("agentId", criteria.getAgent().getId());
			}
			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}

			viewList = q.getResultList();
			for (AgentCommissionDetailReportView view : viewList) {
				report = new AgentCommissionDetailReport(view);
				reportList.add(report);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AgentCommission by criteria.", pe);
		}
		return reportList;
	}

}
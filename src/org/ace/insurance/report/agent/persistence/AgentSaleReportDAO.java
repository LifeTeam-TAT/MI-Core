package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.report.agent.AgentSaleCriteria;
import org.ace.insurance.report.agent.AgentSaleReport;
import org.ace.insurance.report.agent.GeneralAgentSaleReportView;
import org.ace.insurance.report.agent.LifeAgentSaleReportView;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSaleReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AgentSaleReportDAO")
public class AgentSaleReportDAO extends BasicDAO implements IAgentSaleReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleReport> findGeneral(AgentSaleCriteria criteria) throws DAOException {
		List<AgentSaleReport> result = new ArrayList<AgentSaleReport>();
		List<GeneralAgentSaleReportView> genearalAgentSaleReportList = new ArrayList<GeneralAgentSaleReportView>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT g FROM GeneralAgentSaleReportView g WHERE g.agentId is NOT NULL");

			if (criteria.getAgent() != null) {
				query.append(" AND g.agentId = :agentId ");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND g.branchId = :branchId ");
			}
			if (criteria.getStartDate() != null) {
				query.append(" AND g.commissionStartDate >=  :startDate ");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND g.commissionStartDate <= :endDate  ");
			}

			Query q = em.createQuery(query.toString());

			if (criteria.getAgent() != null) {
				q.setParameter("agentId", criteria.getAgent().getId());
			}
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}
			genearalAgentSaleReportList = q.getResultList();
			for (GeneralAgentSaleReportView view : genearalAgentSaleReportList) {
				result.add(new AgentSaleReport(view));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of General Agent Sale by criteria.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleReport> findLife(AgentSaleCriteria criteria) throws DAOException {
		List<AgentSaleReport> result = new ArrayList<AgentSaleReport>();
		List<LifeAgentSaleReportView> lifeAgentSaleReportList = new ArrayList<LifeAgentSaleReportView>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT l FROM LifeAgentSaleReportView l WHERE l.agentId is NOT NULL");

			if (criteria.getAgent() != null) {
				query.append(" AND l.agentId = :agentId");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND l.branchId = :branchId");
			}
			if (criteria.getStartDate() != null) {
				query.append(" AND l.commissionStartDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND l.commissionStartDate <= :endDate");
			}

			Query q = em.createQuery(query.toString());

			if (criteria.getAgent() != null) {
				q.setParameter("agentId", criteria.getAgent().getId());
			}
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}
			lifeAgentSaleReportList = q.getResultList();
			for (LifeAgentSaleReportView view : lifeAgentSaleReportList) {
				result.add(new AgentSaleReport(view));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Life Agent Sale by criteria.", pe);
		}
		return result;
	}
}

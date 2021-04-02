package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.AgentInformationCriteria;
import org.ace.insurance.report.agent.AgentInformationReport;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentInformationReportDAO;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("AgentInformationReportDAO")
public class AgentInformationReportDAO extends BasicDAO implements IAgentInformationReportDAO {

	@Override
	public List<AgentInformationReport> find(AgentInformationCriteria criteria) throws DAOException {
		List<AgentInformationReport> result = new ArrayList<AgentInformationReport>();
		List<Agent> agentList = new ArrayList<Agent>();

		try {
			StringBuffer query = new StringBuffer();

			query.append("SELECT DISTINCT f FROM Agent f");
			query.append(" WHERE f.id IS NOT NULL");

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.append(" AND f.appointedDate >= :startDate");
			}

			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetStartDate(criteria.getEndDate()));
				query.append(" AND f.appointedDate <= :endDate");
			}

			if (criteria.getGroupType() != null) {
				query.append(" AND f.groupType = :groupType");
			}

			if (criteria.getOrganization() != null) {
				query.append(" AND f.organization.id = :organization");
			}
			if (criteria.getSearchType() != null) {
				switch (criteria.getSearchType()) {
					case FIRSTNAME: {
						query.append(" AND f.name.firstName like :name");
						break;
					}
					case MIDDLENAME: {
						query.append(" AND f.name.middleName like :name");
						break;
					}
					case LASTNAME: {
						query.append(" AND f.name.lastName like :name");
						break;
					}
					case LISCENSENO: {
						query.append(" AND f.liscenseNo like :liscenseNo");
						break;
					}
					case NRCNO:
					case FRCNO:
					case PASSPORTNO: {
						query.append(" AND f.idNo = :idNo AND f.idType = :idType");
						break;
					}
					default: {
						query.append(
								" AND CONCAT(f.name.firstName, ' ', f.name.middleName, ' ', f.name.lastName) like :name OR CONCAT(f.name.firstName, ' ', f.name.middleName, f.name.lastName) like :name");
						break;
					}
				}
			}
			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}

			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}

			if (criteria.getGroupType() != null) {
				q.setParameter("groupType", criteria.getGroupType());
			}

			if (criteria.getOrganization() != null) {
				q.setParameter("organization", criteria.getOrganization());
			}

			if (criteria.getSearchType() != null) {
				switch (criteria.getSearchType()) {
					case FIRSTNAME:
					case MIDDLENAME:
					case LASTNAME:
					default: {
						q.setParameter("name", "%" + criteria.getAgent() + "%");
						break;
					}
					case LISCENSENO: {
						q.setParameter("liscenseNo", "%" + criteria.getAgent() + "%");
						break;
					}
					case NRCNO: {
						q.setParameter("idNo", criteria.getAgent());
						q.setParameter("idType", IdType.NRCNO);
						break;
					}
					case FRCNO: {
						q.setParameter("idNo", criteria.getAgent());
						q.setParameter("idType", IdType.FRCNO);
						break;
					}
					case PASSPORTNO: {
						q.setParameter("idNo", criteria.getAgent());
						q.setParameter("idType", IdType.PASSPORTNO);
						break;
					}
				}
			}

			agentList = q.getResultList();

			for (Agent a : agentList) {
				result.add(new AgentInformationReport(a));
			}

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AgentInformation by criteria.", pe);
		}
		return result;

	}

}

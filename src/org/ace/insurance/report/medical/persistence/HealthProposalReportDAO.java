package org.ace.insurance.report.medical.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.medical.HealthProposalReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthProposalReportDAO;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("HealthProposalReportDAO")
public class HealthProposalReportDAO extends BasicDAO implements IHealthProposalReportDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthProposalReportDTO> findHealthProposalReportDTO(HealthProposalReportCriteria criteria) throws DAOException {
		List<HealthProposalReportDTO> resultList = new ArrayList<HealthProposalReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT new org.ace.insurance.report.medical.HealthProposalReportDTO(h) from HealthProposalReportView h WHERE h.id IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND h.dateOfProposed >= :startDate");
			}
			if (criteria.getEndDate() != null) {

				query.append(" AND h.dateOfProposed <= :endDate");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND h.branchId = :branchId");
			}
			if (criteria.getAgent() != null) {
				query.append(" AND h.agentId = :agentId");
			}
			if (criteria.getCustomer() != null) {
				query.append(" AND h.customerId = :customerId");
			}

			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", Utils.resetStartDate(criteria.getStartDate()));
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", Utils.resetEndDate(criteria.getEndDate()));
			}
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getAgent() != null) {
				q.setParameter("agentId", criteria.getAgent().getId());
			}
			if (criteria.getCustomer() != null) {
				q.setParameter("customerId", criteria.getCustomer().getId());
			}
			resultList = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Health Proposal Report", pe);
		}
		return resultList;
	}
}
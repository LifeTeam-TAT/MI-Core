package org.ace.insurance.report.medical.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.medical.HealthPolicyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthPolicyReportDAO;
import org.ace.insurance.web.manage.report.medical.HealthPolicyReportCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("HealthPolicyReportDAO")
public class HealthPolicyReportDAO extends BasicDAO implements IHealthPolicyReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthPolicyReportDTO> findHealthPolicyReportDTO(HealthPolicyReportCriteria criteria) throws DAOException {
		List<HealthPolicyReportDTO> resultList = null;
		try {

			StringBuffer query = new StringBuffer();
			query.append("SELECT new org.ace.insurance.report.medical.HealthPolicyReportDTO(h) from HealthPolicyReportView h WHERE h.id IS NOT NULL");

			if (criteria.getPaymentStartDate() != null) {
				criteria.setPaymentStartDate(Utils.resetStartDate(criteria.getPaymentStartDate()));
				query.append(" AND h.paymentDate >= :paymentStartDate");
			}
			if (criteria.getPaymentEndDate() != null) {
				criteria.setPaymentEndDate(Utils.resetEndDate(criteria.getPaymentEndDate()));
				query.append(" AND h.paymentDate <= :paymentEndDate");
			}
			if (criteria.getCommenceStartDate() != null) {
				criteria.setCommenceStartDate(Utils.resetStartDate(criteria.getCommenceStartDate()));
				query.append(" AND h.commencementDate >= :commenceStartDate");
			}
			if (criteria.getCommenceEndDate() != null) {
				criteria.setCommenceEndDate(Utils.resetEndDate(criteria.getCommenceEndDate()));
				query.append(" AND h.commencementDate <= :commenceEndDate");
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
			if (criteria.getPaymentStartDate() != null) {
				criteria.setPaymentStartDate(Utils.resetStartDate(criteria.getPaymentStartDate()));
				q.setParameter("paymentStartDate", criteria.getPaymentStartDate());
			}
			if (criteria.getPaymentEndDate() != null) {
				criteria.setPaymentEndDate(Utils.resetEndDate(criteria.getPaymentEndDate()));
				q.setParameter("paymentEndDate", criteria.getPaymentEndDate());
			}
			if (criteria.getCommenceStartDate() != null) {
				criteria.setCommenceStartDate(Utils.resetStartDate(criteria.getCommenceStartDate()));
				q.setParameter("commenceStartDate", criteria.getCommenceStartDate());
			}
			if (criteria.getCommenceEndDate() != null) {
				criteria.setCommenceEndDate(Utils.resetEndDate(criteria.getCommenceEndDate()));
				q.setParameter("commenceEndDate", criteria.getCommenceEndDate());
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
			throw translate("Failed to find HealthPolicyReportDTO", pe);
		}
		return resultList;
	}
}

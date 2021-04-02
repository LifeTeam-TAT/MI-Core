package org.ace.insurance.report.medical.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.medical.HealthDailyIncomeReportDTO;
import org.ace.insurance.report.medical.HealthDailyPremiumReportDTO;
import org.ace.insurance.report.medical.HealthDailyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthDailyIncomeReportDAO;
import org.ace.insurance.web.manage.report.medical.HealthDailyIncomeReportCriteria;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("HealthDailyIncomeReportDAO")
public class HealthDailyIncomeReportDAO extends BasicDAO implements IHealthDailyIncomeReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyReportDTO> findHealthDailyReportDTO(HealthDailyIncomeReportCriteria criteria) throws DAOException {
		List<HealthDailyReportDTO> resultList = new ArrayList<HealthDailyReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT new org.ace.insurance.report.medical.HealthDailyReportDTO(h) from HealthDailyReportView h WHERE h.policyNo IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND h.paymentDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND h.paymentDate <= :endDate");
			}
			if (criteria.getBranchId() != null) {
				query.append(" AND h.branchId = :branchId");
			}
			if (!criteria.getProductIdList().isEmpty()) {
				query.append(" AND h.productId IN :productIdList");
			}

			query.append(" ORDER BY h.paymentChannel ASC");

			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				q.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getBranchId() != null) {
				q.setParameter("branchId", criteria.getBranchId());
			}
			if (!criteria.getProductIdList().isEmpty()) {
				q.setParameter("productIdList", criteria.getProductIdList());
			}

			resultList = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find healthDailyReportDTO", pe);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyPremiumReportDTO> findHealthDailyPremiumReportDTO(HealthDailyIncomeReportCriteria criteria) throws DAOException {
		List<HealthDailyPremiumReportDTO> resultList = new ArrayList<HealthDailyPremiumReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT new org.ace.insurance.report.medical.HealthDailyPremiumReportDTO (h.policyNo, h.receiptNo, h.insuredPersonName, h.organizationName,"
					+ "h.paymentDate,h.branchId, h.branchName,"
					+ "h.netPremium, h.toTerm, h.paymentType, h.personsCount) FROM HealthDailyPremiumReportView h WHERE h.policyNo IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND h.paymentDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND h.paymentDate <= :endDate");
			}
			if (criteria.getBranchId() != null) {
				query.append(" AND h.branchId = :branchId");
			}
			if (!criteria.getProductIdList().isEmpty()) {
				query.append(" AND h.productId IN :productIdList");
			}

			query.append(" ORDER BY h.paymentType ASC");

			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				q.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getBranchId() != null) {
				q.setParameter("branchId", criteria.getBranchId());
			}
			if (!criteria.getProductIdList().isEmpty()) {
				q.setParameter("productIdList", criteria.getProductIdList());
			}

			resultList = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find healthDailyPremiumReportDTO", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyIncomeReportDTO> findMedicalClaimRequest(HealthProposalReportCriteria criteria) throws DAOException {
		List<HealthDailyIncomeReportDTO> resultList = new ArrayList<HealthDailyIncomeReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT new org.ace.insurance.report.medical.HealthDailyIncomeReportDTO(m) from MedicalClaimInitialReportView m WHERE m.claimRequestId IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND m.submittedDate >= :startDate");
			}

			if (criteria.getEndDate() != null) {
				query.append(" AND m.submittedDate <= :endDate");
			}

			if (criteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}

			if (criteria.getCustomer() != null) {
				query.append(" AND m.customerId = :customerId");
			}

			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}

			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}

			if (criteria.getCustomer() != null) {
				q.setParameter("customerId", criteria.getCustomer().getId());
			}

			resultList = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find healthDailyIncomeReportDTO", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyIncomeReportDTO> findMedicalClaimPaymentReport(HealthProposalReportCriteria criteria) throws DAOException {
		List<HealthDailyIncomeReportDTO> resultList = new ArrayList<HealthDailyIncomeReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT new org.ace.insurance.report.medical.HealthDailyIncomeReportDTO(m) from MedicalClaimPaymentReportView m WHERE m.claimRequestId IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND m.paymentDate >= :startDate");
			}

			if (criteria.getEndDate() != null) {
				query.append(" AND m.paymentDate <= :endDate");
			}

			if (criteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}

			if (criteria.getCustomer() != null) {
				query.append(" AND m.customerId = :customerId");
			}

			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getStartDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}

			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}

			if (criteria.getCustomer() != null) {
				q.setParameter("customerId", criteria.getCustomer().getId());
			}

			resultList = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find healthDailyIncomeReportDTO", pe);
		}
		return resultList;
	}

}

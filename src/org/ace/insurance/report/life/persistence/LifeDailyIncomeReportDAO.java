package org.ace.insurance.report.life.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.life.LifeDailyIncomeReportCriteria;
import org.ace.insurance.report.life.LifeDailyIncomeReportDTO;
import org.ace.insurance.report.life.LifeDailyPremiumIncomeReportDTO;
import org.ace.insurance.report.life.persistence.interfaces.ILifeDailyIncomeReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("LifeDailyIncomeReportDAO")
public class LifeDailyIncomeReportDAO extends BasicDAO implements ILifeDailyIncomeReportDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<LifeDailyIncomeReportDTO> find(LifeDailyIncomeReportCriteria lifeDailyCriteria) throws DAOException {
		List<LifeDailyIncomeReportDTO> result = new ArrayList<LifeDailyIncomeReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(
					"SELECT NEW org.ace.insurance.report.life.LifeDailyIncomeReportDTO (l.id, l.policyNo, l.proposalNo, l.productId, l.receiptNo, l.customerName, l.organizationName,"
							+ "l.agentName, l.paymentDate, l.sumInsured, l.totalAmount,l.bankName,l.branchId, l.branchName,"
							+ "l.stampFees, l.netPremium, l.paymentChannel) FROM LifeDailyIncomeReportView l WHERE l.policyNo IS NOT NULL ");

			if (!lifeDailyCriteria.getProductIdList().isEmpty())
				query.append(" AND l.productId IN :productIdList");

			if (lifeDailyCriteria.getStartDate() != null) {
				query.append(" AND l.paymentDate >= :startDate");
			}
			if (lifeDailyCriteria.getEndDate() != null) {
				query.append(" AND l.paymentDate <= :endDate");
			}
			if (lifeDailyCriteria.getBranchId() != null) {
				query.append(" AND l.branchId = :branchId");
			}
			query.append(" ORDER BY  l.paymentChannel");

			Query q = em.createQuery(query.toString());

			if (!lifeDailyCriteria.getProductIdList().isEmpty())
				q.setParameter("productIdList", lifeDailyCriteria.getProductIdList());

			if (lifeDailyCriteria.getStartDate() != null) {
				lifeDailyCriteria.setStartDate(Utils.resetStartDate(lifeDailyCriteria.getStartDate()));
				q.setParameter("startDate", lifeDailyCriteria.getStartDate());
			}
			if (lifeDailyCriteria.getEndDate() != null) {
				lifeDailyCriteria.setEndDate(Utils.resetEndDate(lifeDailyCriteria.getEndDate()));
				q.setParameter("endDate", lifeDailyCriteria.getEndDate());
			}
			if (lifeDailyCriteria.getBranchId() != null) {
				q.setParameter("branchId", lifeDailyCriteria.getBranchId());
			}

			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifeDailyIncomeReport by criteria.", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LifeDailyPremiumIncomeReportDTO> findPremium(LifeDailyIncomeReportCriteria lifeDailyCriteria) throws DAOException {
		List<LifeDailyPremiumIncomeReportDTO> result = new ArrayList<LifeDailyPremiumIncomeReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW org.ace.insurance.report.life.LifeDailyPremiumIncomeReportDTO (l.policyNo, l.receiptNo, l.insuredPersonName, l.organizationName,"
					+ "l.paymentDate,l.branchId, l.branchName,"
					+ "l.netPremium, l.toTerm, l.paymentType, l.personsCount) FROM LifeDailyPremiumIncomeReportView l WHERE l.policyNo IS NOT NULL ");

			if (!lifeDailyCriteria.getProductIdList().isEmpty())
				query.append(" AND l.productId IN :productIdList");

			if (lifeDailyCriteria.getStartDate() != null) {
				query.append(" AND l.paymentDate >= :startDate");
			}
			if (lifeDailyCriteria.getEndDate() != null) {
				query.append(" AND l.paymentDate <= :endDate");
			}
			if (lifeDailyCriteria.getBranchId() != null) {
				query.append(" AND l.branchId = :branchId");
			}
			query.append(" ORDER BY  l.paymentType");

			Query q = em.createQuery(query.toString());

			if (!lifeDailyCriteria.getProductIdList().isEmpty())
				q.setParameter("productIdList", lifeDailyCriteria.getProductIdList());

			if (lifeDailyCriteria.getStartDate() != null) {
				lifeDailyCriteria.setStartDate(Utils.resetStartDate(lifeDailyCriteria.getStartDate()));
				q.setParameter("startDate", lifeDailyCriteria.getStartDate());
			}
			if (lifeDailyCriteria.getEndDate() != null) {
				lifeDailyCriteria.setEndDate(Utils.resetEndDate(lifeDailyCriteria.getEndDate()));
				q.setParameter("endDate", lifeDailyCriteria.getEndDate());
			}
			if (lifeDailyCriteria.getBranchId() != null) {
				q.setParameter("branchId", lifeDailyCriteria.getBranchId());
			}

			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifeDailyPremiumIncomeReport by criteria.", pe);
		}
		return result;
	}
}

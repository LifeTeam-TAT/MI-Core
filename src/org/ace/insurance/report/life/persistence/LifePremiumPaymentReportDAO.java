package org.ace.insurance.report.life.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.life.LifePremiumPaymentCriteria;
import org.ace.insurance.report.life.LifePremiumPaymentReport;
import org.ace.insurance.report.life.persistence.interfaces.ILifePremiumPaymentReportDAO;
import org.ace.insurance.report.life.view.LifePolicyView;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("LifePremiumPaymentReportDAO")
public class LifePremiumPaymentReportDAO extends BasicDAO implements ILifePremiumPaymentReportDAO {

	public List<LifePremiumPaymentReport> find(LifePremiumPaymentCriteria premiumPaymentCriteria, List<String> productIdList) throws DAOException {
		List<LifePremiumPaymentReport> result = new ArrayList<LifePremiumPaymentReport>();
		List<LifePolicyView> viewList = new ArrayList<LifePolicyView>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT l FROM LifePolicyView l WHERE l.policyNo IS NOT NULL ");
			query.append(" AND l.productId IN :productIdList");
			if (premiumPaymentCriteria.getStartDate() != null) {
				premiumPaymentCriteria.setStartDate(Utils.resetStartDate(premiumPaymentCriteria.getStartDate()));
				query.append(" AND l.commenmanceDate >= :startDate");
			}

			if (premiumPaymentCriteria.getEndDate() != null) {
				premiumPaymentCriteria.setEndDate(Utils.resetEndDate(premiumPaymentCriteria.getEndDate()));
				query.append(" AND l.commenmanceDate <= :endDate");
			}

			if (premiumPaymentCriteria.getBranch() != null) {
				query.append(" AND l.branchId = :branchId");
			}
			if (premiumPaymentCriteria.getProposalType() != null) {
				query.append(" AND l.status = :status");
			}

			query.append(" ORDER BY l.branchName, l.policyNo ");
			Query q = em.createQuery(query.toString());
			q.setParameter("productIdList", productIdList);
			if (premiumPaymentCriteria.getStartDate() != null) {
				premiumPaymentCriteria.setStartDate(Utils.resetStartDate(premiumPaymentCriteria.getStartDate()));
				q.setParameter("startDate", premiumPaymentCriteria.getStartDate());
			}
			if (premiumPaymentCriteria.getEndDate() != null) {
				premiumPaymentCriteria.setEndDate(Utils.resetEndDate(premiumPaymentCriteria.getEndDate()));
				q.setParameter("endDate", premiumPaymentCriteria.getEndDate());
			}
			if (premiumPaymentCriteria.getBranch() != null) {
				q.setParameter("branchId", premiumPaymentCriteria.getBranch().getId());
			}
			if (premiumPaymentCriteria.getProposalType() != null) {
				q.setParameter("status", premiumPaymentCriteria.getProposalType().getLabel());
			}

			viewList = q.getResultList();
			if (viewList != null) {
				for (LifePolicyView view : viewList) {
					result.add(new LifePremiumPaymentReport(view));
				}
			}
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Life-Premium-Payment by criteria.", pe);
		}

		return result;
	}
}

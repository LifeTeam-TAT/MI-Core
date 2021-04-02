package org.ace.insurance.report.life.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.life.LifeClaimRegisterReport;
import org.ace.insurance.report.life.persistence.interfaces.ILifeClaimRegisterReportDAO;
import org.ace.insurance.report.life.view.LifeClaimRequestView;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("LifeClaimRegisterReportDAO")
public class LifeClaimRegisterReportDAO extends BasicDAO implements ILifeClaimRegisterReportDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<LifeClaimRegisterReport> find(EnquiryCriteria criteria, List<String> productIdList) throws DAOException {
		List<LifeClaimRegisterReport> result = new ArrayList<LifeClaimRegisterReport>();
		List<LifeClaimRequestView> rawList = null;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT lc FROM LifeClaimRequestView lc WHERE lc.claimRequestNo IS NOT NULL ");
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				queryString.append(" AND lc.submittedDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				queryString.append(" AND lc.submittedDate <= :endDate");
			}
			if (criteria.getCustomer() != null) {
				queryString.append(" AND lc.customerId = :customerId");
			}
			if (criteria.getOrganization() != null) {
				queryString.append(" AND lc.organizationId = :organizationId");
			}
			if (criteria.getBranch() != null) {
				queryString.append(" AND lc.branchId = :branchId");
			}
			if (criteria.getProduct() != null) {
				queryString.append(" AND lc.productId = :productId");
			} else if (productIdList != null) {
				queryString.append(" AND lc.productId IN :productIdList");
			}
			if (criteria.getPolicyNo() != null && !criteria.getPolicyNo().isEmpty()) {
				queryString.append(" AND lc.policyNo = :policyNo");
			}
			Query query = em.createQuery(queryString.toString());
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getCustomer() != null) {
				query.setParameter("customerId", criteria.getCustomer().getId());
			}
			if (criteria.getOrganization() != null) {
				query.setParameter("organizationId", criteria.getOrganization().getId());
			}
			if (criteria.getBranch() != null) {
				query.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getProduct() != null) {
				query.setParameter("productId", criteria.getProduct().getId());
			} else if (productIdList != null) {
				query.setParameter("productIdList", productIdList);
			}
			if (criteria.getPolicyNo() != null && !criteria.getPolicyNo().isEmpty()) {
				query.setParameter("policyNo", criteria.getPolicyNo());
			}

			rawList = query.getResultList();
			for (LifeClaimRequestView view : rawList) {
				result.add(new LifeClaimRegisterReport(view.getSubmittedDate(), view.getClaimRequestNo(), view.getInsuredPersonName(), view.getPolicyNo(), view.getClaimType(),
						view.getInsuredPersonAddress(), view.getSumInsured(), view.getClaimAmount(), view.getPaymentDate(), "", "", view.getBranchId()));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifeClaimRegisterReport by criteria.", pe);
		}
		return result;
	}

}

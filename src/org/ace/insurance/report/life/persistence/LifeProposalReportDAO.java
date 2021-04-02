package org.ace.insurance.report.life.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.life.LifeProposalCriteria;
import org.ace.insurance.report.life.LifeProposalReport;
import org.ace.insurance.report.life.persistence.interfaces.ILifeProposalReportDAO;
import org.ace.insurance.report.life.view.LifeProposalView;
import org.ace.insurance.report.personalAccident.PersonalAccidentProposalReport;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifeProposalReportDAO")
public class LifeProposalReportDAO extends BasicDAO implements ILifeProposalReportDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<LifeProposalReport> find(LifeProposalCriteria lifeProposalCriteria, List<String> productIdList) throws DAOException {
		List<LifeProposalReport> result = new ArrayList<LifeProposalReport>();
		List<LifeProposalView> viewList = new ArrayList<LifeProposalView>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT lv FROM LifeProposalView lv WHERE lv.id IS NOT NULL");

			if (lifeProposalCriteria.getStartDate() != null) {
				lifeProposalCriteria.setStartDate(Utils.resetStartDate(lifeProposalCriteria.getStartDate()));
				query.append(" AND lv.dateOfProposed >= :startDate");
			}
			if (lifeProposalCriteria.getEndDate() != null) {
				lifeProposalCriteria.setEndDate(Utils.resetEndDate(lifeProposalCriteria.getEndDate()));
				query.append(" AND lv.dateOfProposed <= :endDate");
			}

			if (lifeProposalCriteria.getAgent() != null) {
				query.append(" AND lv.agentId = :agentId");
			}
			if (lifeProposalCriteria.getCustomer() != null) {
				query.append(" AND lv.customerId = :customerId");
			}
			if (lifeProposalCriteria.getOrganization() != null) {
				query.append(" AND lv.organizationId = :orgId");
			}
			if (lifeProposalCriteria.getBranch() != null) {
				query.append(" AND lv.branchId = :branchId");
			}
			if (lifeProposalCriteria.getProduct() != null) {
				query.append(" AND lv.productId = :productId");
			} else if (productIdList != null && !productIdList.isEmpty()) {
				query.append(" AND lv.productId IN :productIdList");
			}
			query.append(" order by lv.branchName, lv.proposalNo");

			Query q = em.createQuery(query.toString());

			if (lifeProposalCriteria.getStartDate() != null) {
				q.setParameter("startDate", lifeProposalCriteria.getStartDate());
			}
			if (lifeProposalCriteria.getEndDate() != null) {
				q.setParameter("endDate", lifeProposalCriteria.getEndDate());
			}
			if (lifeProposalCriteria.getAgent() != null) {
				q.setParameter("agentId", lifeProposalCriteria.getAgent().getId());
			}
			if (lifeProposalCriteria.getCustomer() != null) {
				q.setParameter("customerId", lifeProposalCriteria.getCustomer().getId());
			}
			if (lifeProposalCriteria.getOrganization() != null) {
				q.setParameter("orgId", lifeProposalCriteria.getOrganization().getId());
			}
			if (lifeProposalCriteria.getBranch() != null) {
				q.setParameter("branchId", lifeProposalCriteria.getBranch().getId());
			}
			if (lifeProposalCriteria.getProduct() != null) {
				q.setParameter("productId", lifeProposalCriteria.getProduct().getId());
			} else if (productIdList != null && !productIdList.isEmpty()) {
				q.setParameter("productIdList", productIdList);
			}
			viewList = q.getResultList();
			em.flush();
			if (viewList != null) {
				for (LifeProposalView view : viewList) {
					result.add(new LifeProposalReport(view));
				}
			}
		} catch (IllegalArgumentException ie) {
			ie.printStackTrace();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifeProposal by criteria.", pe);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PersonalAccidentProposalReport> findPersonalAccidentProposal(LifeProposalCriteria lifeProposalCriteria) throws DAOException {
		List<PersonalAccidentProposalReport> resultList = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW org.ace.insurance.report.personalAccident.PersonalAccidentProposalReport(p.id, p.proposalNo, p.insuredPersonName, p.addressAndPhoneNo, "
					+ "p.ageAndDateOfBirth, p.maritalStatus, p.occupation, p.agentNameAndAgentCode, p.activedProposalStartDate, p.activedProposalEndDate, "
					+ "p.sumInsured, p.premium, p.cashReceiptNoAndPaymentDate, p.remark)" + " FROM PersonalAccidentProposalView p WHERE p.proposalNo IS NOT NULL");

			if (lifeProposalCriteria.getStartDate() != null) {
				lifeProposalCriteria.setStartDate(Utils.resetStartDate(lifeProposalCriteria.getStartDate()));
				buffer.append(" AND p.activedProposalStartDate >= :startDate");
			}
			if (lifeProposalCriteria.getEndDate() != null) {
				lifeProposalCriteria.setEndDate(Utils.resetEndDate(lifeProposalCriteria.getEndDate()));
				buffer.append(" AND p.activedProposalStartDate <= :endDate");
			}

			if (lifeProposalCriteria.getAgent() != null) {
				buffer.append(" AND p.agentId = :agentId");
			}
			if (lifeProposalCriteria.getCustomer() != null) {
				buffer.append(" AND p.customerId = :customerId");
			}
			if (lifeProposalCriteria.getOrganization() != null) {
				buffer.append(" AND p.organizationId = :orgId");
			}
			if (lifeProposalCriteria.getBranch() != null) {
				buffer.append(" AND p.branchId = :branchId");
			}
			if (lifeProposalCriteria.getProduct() != null) {
				buffer.append(" AND p.productId = :productId");
			}

			Query query = em.createQuery(buffer.toString());

			if (lifeProposalCriteria.getStartDate() != null) {
				query.setParameter("startDate", lifeProposalCriteria.getStartDate());
			}
			if (lifeProposalCriteria.getEndDate() != null) {
				query.setParameter("endDate", lifeProposalCriteria.getEndDate());
			}

			if (lifeProposalCriteria.getAgent() != null) {
				query.setParameter("agentId", lifeProposalCriteria.getAgent().getId());
			}
			if (lifeProposalCriteria.getCustomer() != null) {
				query.setParameter("customerId", lifeProposalCriteria.getCustomer().getId());
			}
			if (lifeProposalCriteria.getOrganization() != null) {
				query.setParameter("orgId", lifeProposalCriteria.getOrganization().getId());
			}
			if (lifeProposalCriteria.getBranch() != null) {
				query.setParameter("branchId", lifeProposalCriteria.getBranch().getId());
			}
			if (lifeProposalCriteria.getProduct() != null) {
				query.setParameter("productId", lifeProposalCriteria.getProduct().getId());
			}
			resultList = query.getResultList();
			RegNoSorter<PersonalAccidentProposalReport> sortedResultList = new RegNoSorter<>(resultList);
			resultList = sortedResultList.getSortedList();
		} catch (PersistenceException e) {
			throw translate("Find to find personal accident proposal report", e);
		}
		return resultList;
	}

}

package org.ace.insurance.report.life.persistence;

/**
 * @author NNH
 */
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.report.life.GroupLifeProposalCriteria;
import org.ace.insurance.report.life.GroupLifeProposalReport;
import org.ace.insurance.report.life.persistence.interfaces.IGroupLifeProposalReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("GroupLifeProposalReportDAO")
public class GroupLifeProposalReportDAO extends BasicDAO implements IGroupLifeProposalReportDAO {

	@Override
	public List<GroupLifeProposalReport> find(GroupLifeProposalCriteria grouplifeProposalCriteria) throws DAOException {
		List<GroupLifeProposalReport> result = new ArrayList<GroupLifeProposalReport>();

		try {
			StringBuffer query = new StringBuffer();

			/**
			 * Some keywords in this query only work well with eclipselink 2.4
			 * or higher
			 */
			query.append("SELECT DISTINCT f From LifeProposal f LEFT JOIN f.proposalInsuredPersonList pi " + " WHERE f.id IS NOT NULL AND pi.product.id = :productId");

			if (grouplifeProposalCriteria.getStartDate() != null) {
				grouplifeProposalCriteria.setStartDate(Utils.resetStartDate(grouplifeProposalCriteria.getStartDate()));
				query.append(" AND f.submittedDate >= :startDate");
			}
			if (grouplifeProposalCriteria.getEndDate() != null) {
				grouplifeProposalCriteria.setEndDate(Utils.resetStartDate(grouplifeProposalCriteria.getEndDate()));
				query.append(" AND f.submittedDate <= :endDate");
			}

			if (grouplifeProposalCriteria.getAgent() != null) {
				query.append(" AND f.agent.id = :agentId");
			}
			if (grouplifeProposalCriteria.getCustomer() != null) {
				query.append(" AND f.customer.id = :customerId");
			}
			if (grouplifeProposalCriteria.getOrganization() != null) {
				query.append(" AND f.organization.id = :orgId");
			}
			if (grouplifeProposalCriteria.getBranch() != null) {
				query.append(" AND f.branch.id = :branchId");
			}

			Query q = em.createQuery(query.toString());

			q.setParameter("productId", KeyFactorIDConfig.getGroupLifeId());

			if (grouplifeProposalCriteria.getStartDate() != null) {
				q.setParameter("startDate", grouplifeProposalCriteria.getStartDate());
			}
			if (grouplifeProposalCriteria.getEndDate() != null) {
				q.setParameter("endDate", grouplifeProposalCriteria.getEndDate());
			}
			if (grouplifeProposalCriteria.getAgent() != null) {
				q.setParameter("agentId", grouplifeProposalCriteria.getAgent().getId());
			}
			if (grouplifeProposalCriteria.getCustomer() != null) {
				q.setParameter("customerId", grouplifeProposalCriteria.getCustomer().getId());
			}
			if (grouplifeProposalCriteria.getOrganization() != null) {
				q.setParameter("orgId", grouplifeProposalCriteria.getOrganization().getId());
			}
			if (grouplifeProposalCriteria.getBranch() != null) {
				q.setParameter("branchId", grouplifeProposalCriteria.getBranch().getId());
			}

			List<LifeProposal> list = q.getResultList();
			em.flush();

			for (LifeProposal lifeProposal : list) {
				for (ProposalInsuredPerson person : lifeProposal.getProposalInsuredPersonList()) {
					String agentNameAndCode = "";
					if (lifeProposal.getAgent() != null) {
						agentNameAndCode = lifeProposal.getAgent().getName() + lifeProposal.getAgent().getCodeNo();
					}
					result.add(new GroupLifeProposalReport(lifeProposal.getProposalNo(), person.getInPersonGroupCodeNo(), agentNameAndCode, person.getFullName(),
							person.getFullAddress(), person.getProposedPremium(), person.getProposedPremium(), lifeProposal.getBranch().getName(),
							person.getInsuredPersonBeneficiariesList()));
				}
			}

		} catch (IllegalArgumentException ie) {
			ie.printStackTrace();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of GroupLifeProposal by criteria.", pe);
		}
		RegNoSorter<GroupLifeProposalReport> regNoSorter = new RegNoSorter<GroupLifeProposalReport>(result);
		return regNoSorter.getSortedList();
	}
}

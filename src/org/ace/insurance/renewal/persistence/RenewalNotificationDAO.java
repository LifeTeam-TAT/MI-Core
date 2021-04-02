package org.ace.insurance.renewal.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.renewal.RenewalNotification;
import org.ace.insurance.renewal.RenewalNotificationCriteria;
import org.ace.insurance.renewal.persistence.interfaces.IRenewalNotificationDAO;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.idgen.service.interfaces.IDConfigLoader;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("RenewalNotificationDAO")
public class RenewalNotificationDAO extends BasicDAO implements IRenewalNotificationDAO {

	@Resource(name = "UserProcessService")
	private IUserProcessService userProcessService;

	@Resource(name = "IDConfigLoader")
	private IDConfigLoader idConfigLoader;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<RenewalNotification> findPoliciesByCriteria(RenewalNotificationCriteria criteria) throws DAOException {
		List<RenewalNotification> results = new ArrayList<>();
		try {
			User loginUser = userProcessService.getLoginUser();
			List<String> productIdList = new ArrayList<>();
			String policy = null;
			switch (criteria.getPolicyReferenceType()) {

				case PA_POLICY:
					policy = "LifePolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getPersonalAccidentKytId());
					break;
				case FARMER_POLICY:
					policy = "LifePolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getFarmerId());
					break;
				case SNAKE_BITE_POLICY:
					policy = "LifePolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getSnakeBiteId());
					break;
				case GROUP_LIFE_POLICY:
					policy = "LifePolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getGroupLifeID());
					break;
				case SPORT_MAN_POLICY:
					policy = "LifePolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getSportManId());
					break;
				case SHORT_ENDOWMENT_LIFE_POLICY:
					policy = "LifePolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getShortTermEndowmentId());
					break;
				case ENDOWNMENT_LIFE_POLICY:
					policy = "LifePolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getPublicLifeId());
					break;

				case HEALTH_POLICY:
					policy = "MedicalPolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getIndividualHealththId());
					productIdList.add(KeyFactorChecker.getGroupHealththId());
					break;
				case MICRO_HEALTH_POLICY:
					policy = "MedicalPolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getMicroHealthId());
					break;
				case CRITICAL_ILLNESS_POLICY:
					policy = "MedicalPolicy p JOIN p.policyInsuredPersonList person";
					productIdList.add(KeyFactorChecker.getIndividualCriticalIllnessId());
					productIdList.add(KeyFactorChecker.getGroupCriticalIllnessId());
					break;

				default:
					break;
			}

			StringBuffer buffer = new StringBuffer("SELECT p.id, p.policyNo, c.initialId, c.name, o.name, p.activedPolicyEndDate ");
			buffer.append(" FROM " + policy + " LEFT OUTER JOIN p.customer c LEFT OUTER JOIN p.organization o ");
			buffer.append(" WHERE p.policyNo IS NOT NULL ");
			if (!productIdList.isEmpty())
				buffer.append(" AND person.product.id IN :productIdList");

			if (criteria.getStartDate() != null)
				buffer.append(" AND p.activedPolicyEndDate >= :startDate");

			if (criteria.getEndDate() != null)
				buffer.append(" AND p.activedPolicyEndDate <= :endDate");

			if (criteria.getSalesPoints() != null)
				buffer.append(" AND p.salesPoints.id = :salePointId");

			buffer.append(" AND p.branch.id = :branchId");

			Query query = em.createQuery(buffer.toString());
			if (!productIdList.isEmpty())
				query.setParameter("productIdList", productIdList);

			if (criteria.getStartDate() != null)
				query.setParameter("startDate", Utils.resetStartDate(criteria.getStartDate()));

			if (criteria.getEndDate() != null)
				query.setParameter("endDate", Utils.resetEndDate(criteria.getEndDate()));

			if (criteria.getSalesPoints() != null)
				query.setParameter("salePointId", criteria.getSalesPoints().getId());

			query.setParameter("branchId", criteria.getBranchId());

			List<Object[]> raws = query.getResultList();
			String id;
			String policyNo;
			String salutation = null;
			Name name = null;
			String organization = null;
			Date endDate;
			int days = 0;
			Date currentDate = new Date();
			String customerName = null;
			for (Object[] arr : raws) {
				id = (String) arr[0];
				policyNo = (String) arr[1];
				salutation = (String) arr[2];
				name = (Name) arr[3];
				organization = (String) arr[4];
				endDate = (Date) arr[5];
				customerName = name != null ? salutation + " " + name.getFullName() : organization;
				days = Utils.daysBetween(endDate, currentDate, false, true);
				results.add(new RenewalNotification(id, policyNo, customerName, endDate, days));
			}

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to findPolicies By Criteria.", pe);
		}

		RegNoSorter<RenewalNotification> regNoSorter = new RegNoSorter<RenewalNotification>(results);
		return regNoSorter.getSortedList();
	}

}

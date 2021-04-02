package org.ace.insurance.report.life.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.life.LifeClaimStatusReport;
import org.ace.insurance.report.life.LifeClaimStatusReportCriteria;
import org.ace.insurance.report.life.persistence.interfaces.ILifeClaimStatusReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifeClaimStatusReportDAO")
public class LifeClaimStatusReportDAO extends BasicDAO implements ILifeClaimStatusReportDAO {
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimStatusReport> findLifeClaimStatusReport(LifeClaimStatusReportCriteria criteria) throws DAOException {
		List<LifeClaimStatusReport> resultList = new ArrayList<LifeClaimStatusReport>();
		try {

			StringBuffer query = new StringBuffer();
			query.append(" SELECT DISTINCT(lp.policyNo), ");
			query.append(" CONCAT(TRIM(c.initialId),' ',TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ',TRIM(c.name.lastName)) ,");
			query.append(" o.name ,inp.age,CONCAT(TRIM(c.residentAddress.residentAddress),', ',TRIM(c.residentAddress.township.name), ', ',  ");
			query.append("  TRIM(c.residentAddress.township.province.name), ' ,',TRIM(c.residentAddress.township.province.country.name)) ,");
			query.append(" CONCAT(TRIM(a.initialId),' ',TRIM(a.name.firstName), ' ', TRIM(a.name.middleName), ' ',TRIM(a.name.lastName)) , ");
			query.append(" CONCAT(TRIM(s.initialId),' ',TRIM(s.name.firstName), ' ', TRIM(s.name.middleName), ' ',TRIM(s.name.lastName)) , ");
			query.append(" CONCAT(TRIM(r.initialId),' ',TRIM(r.name.firstName), ' ', TRIM(r.name.middleName), ' ',TRIM(r.name.lastName)) , ");
			query.append(" lp.branch.name, lp.lifePolicy.paymentType.name,inp.periodMonth, inp.sumInsured, lp.paidUpAmount,");
			query.append(" lp.submittedDate,lp.lifePolicy.policyStatus,inp.product FROM LifePaidUpProposal lp INNER JOIN LifePolicy l");
			query.append(" INNER JOIN lp.lifePolicy.policyInsuredPersonList inp");
			query.append(" LEFT OUTER JOIN lp.lifePolicy.customer c ");
			query.append(" LEFT OUTER JOIN lp.lifePolicy.organization o ");
			query.append(" LEFT OUTER JOIN lp.lifePolicy.agent a ");
			query.append(" LEFT OUTER JOIN lp.lifePolicy.saleMan s ");
			query.append(" LEFT OUTER JOIN lp.lifePolicy.referral r WHERE lp.lifePolicy IS NOT NULL AND lp.lifePolicy.policyStatus IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND lp.submittedDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND lp.submittedDate <= :endDate");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND lp.branch.id = :branchId");
			}
			if (criteria.getPolicyStatus() != null) {
				query.append(" AND lp.lifePolicy.policyStatus = :policyStatus ");
			}
			query.append(" UNION ALL ");
			query.append(" SELECT DISTINCT(ls.policyNo), ");
			query.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)) ,");
			query.append(" o.name AS orgName,inp.age,CONCAT(TRIM(c.residentAddress.residentAddress),', ',TRIM(c.residentAddress.township.name), ', ', ");
			query.append("  TRIM(c.residentAddress.township.province.name), ', ',TRIM(c.residentAddress.township.province.country.name)) ,");
			query.append(" CONCAT(TRIM(a.initialId),' ', TRIM(a.name.firstName), ' ', TRIM(a.name.middleName), ' ', TRIM(a.name.lastName)), ");
			query.append(" CONCAT(TRIM(s.initialId),' ', TRIM(s.name.firstName), ' ', TRIM(s.name.middleName), ' ', TRIM(s.name.lastName)) , ");
			query.append(" CONCAT(TRIM(r.initialId),' ', TRIM(r.name.firstName), ' ', TRIM(r.name.middleName), ' ', TRIM(r.name.lastName)) , ");
			query.append(" ls.branch.name, ls.lifePolicy.paymentType.name , inp.periodMonth, inp.sumInsured, ls.surrenderAmount ,");
			query.append(" ls.submittedDate,ls.lifePolicy.policyStatus,inp.product FROM LifeSurrenderProposal ls INNER JOIN LifePolicy l");
			query.append(" INNER JOIN ls.lifePolicy.policyInsuredPersonList inp");
			query.append(" LEFT OUTER JOIN ls.lifePolicy.customer c ");
			query.append(" LEFT OUTER JOIN ls.lifePolicy.organization o ");
			query.append(" LEFT OUTER JOIN ls.lifePolicy.agent a ");
			query.append(" LEFT OUTER JOIN ls.lifePolicy.saleMan s ");
			query.append(" LEFT OUTER JOIN ls.lifePolicy.referral r WHERE ls.lifePolicy IS NOT NULL AND ls.lifePolicy.policyStatus IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND ls.submittedDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND ls.submittedDate <= :endDate");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND ls.branch.id = :branchId");
			}
			if (criteria.getPolicyStatus() != null) {
				query.append(" AND ls.lifePolicy.policyStatus = :policyStatus ");
			}
			Query q = em.createQuery(query.toString());
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetStartDate(criteria.getEndDate()));
				q.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getPolicyStatus() != null) {
				q.setParameter("policyStatus", criteria.getPolicyStatus());
			}
			List<Object[]> objectList = q.getResultList();
			for (Object[] b : objectList) {
				String policyNo = (String) b[0];
				String customerName;
				if (b[1] != null) {
					customerName = (String) b[1];
				} else {
					customerName = (String) b[2];
				}
				int age = (Integer) b[3];
				String address = (String) b[4];
				String agentName;
				if (b[5] != null) {
					agentName = (String) b[5];
				} else if (b[6] != null) {
					agentName = (String) b[6];
				} else {
					agentName = (String) b[7];
				}
				String branchName = (String) b[8];
				String paymentType = (String) b[9];
				int policyPeriod = (Integer) b[10];
				Double sumInsured = (Double) b[11];
				Double amount = (Double) b[12];
				Date submittedDate = (Date) b[13];
				PolicyStatus policyStatus = (PolicyStatus) b[14];
				LifeClaimStatusReport report = new LifeClaimStatusReport(policyNo, customerName, age, address, agentName, branchName, paymentType, policyPeriod, sumInsured, amount,
						submittedDate, policyStatus);
				resultList.add(report);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaimStatusReport", pe);
		}
		return resultList;
	}
}

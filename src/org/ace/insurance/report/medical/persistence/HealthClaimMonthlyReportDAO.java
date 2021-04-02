package org.ace.insurance.report.medical.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthClaimMonthlyReport;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthClaimMonthlyReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("HealthClaimMonthlyReportDAO")
public class HealthClaimMonthlyReportDAO extends BasicDAO implements IHealthClaimMonthlyReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthClaimMonthlyReport> findHealthClaimMonthlyReport(MonthlyReportCriteria criteria) throws DAOException {
		List<HealthClaimMonthlyReport> result = new ArrayList<HealthClaimMonthlyReport>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW " + HealthClaimMonthlyReport.class.getName()
					+ "(m.id,m.activedPolicyStartDate,m.policyNo,m.branchId,m.insuredPersonName,m.gender,m.dateOfBirth,m.age,"
					+ "m.injuredDate,m.totalClaimAmount,m.curedHospital1,m.curedHospital2,m.diseaseName,m.noOfHospitalizationDay,m.beneficiaryName,m.relationship,m.operationName1,"
					+ "m.operationName2,m.deathDate,m.basicUnit,m.basicClaimAmount,m.basicPlusUnit,m.basicPlusClaimAmount,m.addOn1Unit,m.addOn1ClaimAmount,m.addOn2Unit,"
					+ "m.addOn2ClaimAmount,m.salePersonName,m.customerType,m.salePersonType,m.commission)"
					+ " FROM HealthClaimMonthlyView m WHERE m.policyNo IS NOT NULL AND m.activedPolicyStartDate >= :startDate " + "AND m.activedPolicyStartDate <= :endDate");

			if (criteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}

			Query q = em.createQuery(query.toString());
			q.setParameter("startDate", Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("endDate", Utils.getEndDate(criteria.getYear(), criteria.getMonth()));

			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			result = q.getResultList();

			RegNoSorter<HealthClaimMonthlyReport> sortedResultList = new RegNoSorter<>(result);
			result = sortedResultList.getSortedList();

		} catch (PersistenceException pe) {
			throw translate("Failed to find healthClaimMonthlyReport", pe);
		}
		return result;
	}
}

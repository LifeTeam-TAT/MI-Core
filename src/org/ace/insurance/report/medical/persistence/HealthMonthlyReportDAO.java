package org.ace.insurance.report.medical.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthMonthlyReport;
import org.ace.insurance.report.medical.HealthMonthlyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthMonthlyReportDAO;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("HealthMonthlyReportDAO")
public class HealthMonthlyReportDAO extends BasicDAO implements IHealthMonthlyReportDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthMonthlyReport> findHealthMonthlyReport(MonthlyReportCriteria criteria) throws DAOException {
		List<HealthMonthlyReport> resultList = new ArrayList<HealthMonthlyReport>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW org.ace.insurance.report.medical.HealthMonthlyReport(m.id,m.activedPolicyStartDate,m.policyNo,m.branchId,m.insuredPersonName,m.gender,m.dateOfBirth,m.age,m.fullIdNo," + 
							"m.occupation,m.address,m.paymentType,m.premium,m.receiptNo,m.paymentDate,m.beneficiaryName,m.relationship,m.unit,m.basicPlusUnit,m.addOn1,m.addOn2,m.salePersonName," + 
							"m.customerType,m.salePersonType,m.commission) FROM MedicalMonthlyView m WHERE m.policyNo IS NOT NULL AND m.activedPolicyStartDate >= :startDate " + 
							"AND m.activedPolicyStartDate <= :endDate");
			
			if (criteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}
			
			Query q = em.createQuery(query.toString());
			q.setParameter("startDate", Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("endDate", Utils.getEndDate(criteria.getYear(), criteria.getMonth()));
			
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			resultList = q.getResultList();
			
			RegNoSorter<HealthMonthlyReport> sortedResultList = new RegNoSorter<>(resultList);
			resultList = sortedResultList.getSortedList();
			
		} catch (PersistenceException pe) {
			throw translate("Failed to find healthMonthlyReportDTO", pe);
		}
		return resultList;
	}
}

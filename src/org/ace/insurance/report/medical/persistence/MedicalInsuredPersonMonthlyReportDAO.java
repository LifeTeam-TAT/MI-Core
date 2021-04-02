package org.ace.insurance.report.medical.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalInusuredPersonMonthlyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IMedicalInsuredPersonMonthlyReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalInsuredPersonMonthlyReportDAO")
public class MedicalInsuredPersonMonthlyReportDAO extends BasicDAO implements IMedicalInsuredPersonMonthlyReportDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalInusuredPersonMonthlyReportDTO> find(MonthlyReportCriteria criteria) throws DAOException {
		List<MedicalInusuredPersonMonthlyReportDTO> result = new ArrayList<MedicalInusuredPersonMonthlyReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(
					"SELECT new org.ace.insurance.report.medical.MedicalInusuredPersonMonthlyReportDTO(m) FROM MedicalInsuredPersonMonthlyReportView m WHERE m.policyNo IS NOT NULL ");
			query.append(" AND m.policyStartDate >= :startDate");
			query.append(" AND m.policyStartDate <= :endDate");
			if (criteria.getBranch() != null) {
				query.append(" AND m.branchid = :branchId ");
				query.append(" order by m.policyNo");
			}
			Query q = em.createQuery(query.toString());
			q.setParameter("startDate", Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("endDate", Utils.getEndDate(criteria.getYear(), criteria.getMonth()));
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Medical Inusured Person Monthly Report by criteria.", pe);
		}
		return result;
	}
}

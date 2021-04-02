package org.ace.insurance.report.medical.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalPolicyMonthlyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IMedicalPolicyMonthlyReportDAO;
import org.ace.insurance.report.medical.view.MedicalPolicyMonthlyReportView;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2015-11-17
 * @Version 1.0
 * @Purpose This class serves as the Data Manipulation to retrieve the
 *          <code>MedicalPolicyMonthlyReportDTO</code> Policy Monthly Report
 *          data.
 * 
 ***************************************************************************************/

@Repository("MedicalPolicyMonthlyReportDAO")
public class MedicalPolicyMonthlyReportDAO extends BasicDAO implements IMedicalPolicyMonthlyReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPolicyMonthlyReportDTO> find(MonthlyReportCriteria criteria) throws DAOException {
		List<MedicalPolicyMonthlyReportDTO> result = new ArrayList<MedicalPolicyMonthlyReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT m FROM MedicalPolicyMonthlyReportView m WHERE m.policyNo IS NOT NULL ");
			query.append("AND  m.paymentDate >= :startDate");
			query.append(" AND m.paymentDate <= :endDate");
			if (criteria.getBranch() != null) {
				query.append(" AND m.branchId = :branchId");
			}
			// query.append(" order by m.branchId, m.policyNo");
			Query q = em.createQuery(query.toString());

			q.setParameter("startDate", Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("endDate", Utils.getEndDate(criteria.getYear(), criteria.getMonth()));
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			List<MedicalPolicyMonthlyReportView> viewList = q.getResultList();
			for (MedicalPolicyMonthlyReportView view : viewList) {
				result.add(new MedicalPolicyMonthlyReportDTO(view));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Medical Policy Report by criteria.", pe);
		}
		return result;
	}
}

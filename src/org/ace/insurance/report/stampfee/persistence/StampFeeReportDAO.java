package org.ace.insurance.report.stampfee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.stampfee.StampFeeCriteria;
import org.ace.insurance.report.stampfee.StampFeeReport;
import org.ace.insurance.report.stampfee.persistence.interfaces.IStampFeeReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class serves as the DAO to manipulate the <code>Stamp Fee Report</code>
 * object.
 * 
 * @author TDP
 * @since 1.0.0
 * @date 2013/11/28
 * 
 * @Updated By PPA
 * @date 2.0.0
 * @date 2016/06/08
 * 
 * @CopyRight AcePlus Solution Co.Ltd.
 */
@Repository("StampFeeReportDAO")
public class StampFeeReportDAO extends BasicDAO implements IStampFeeReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<StampFeeReport> find(StampFeeCriteria criteria) throws DAOException {
		List<StampFeeReport> result = new ArrayList<StampFeeReport>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT s FROM StampFeeReport s WHERE s.id IS NOT NULL ");
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.append(" AND s.commenmanceDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.append(" AND s.commenmanceDate <= :endDate");
			}
			if (criteria.getPolicyReferenceType() != null) {
				query.append(" AND s.referenceType = :referenceType");
			}
			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", Utils.resetStartDate(criteria.getStartDate()));
			}

			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", Utils.resetEndDate(criteria.getEndDate()));
			}

			if (criteria.getPolicyReferenceType() != null) {
				q.setParameter("referenceType", criteria.getPolicyReferenceType().toString());
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find StampFee Report by criteria.", pe);
		}
		return result;
	}
}

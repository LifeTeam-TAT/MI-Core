package org.ace.insurance.report.life.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.life.persistence.interfaces.ILifePolicyMonthlyReportDAO;
import org.ace.insurance.report.life.report.LifeMonthlyReport;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePolicyMonthlyReportDAO")
public class LifePolicyMonthlyReportDAO extends BasicDAO implements ILifePolicyMonthlyReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeMonthlyReport> findLifePolicyMonthlyReport(SummaryReportCriteria criteria) throws DAOException {
		List<LifeMonthlyReport> result = new ArrayList<LifeMonthlyReport>();
		StringBuffer query = new StringBuffer();

		try {

			query.append("SELECT new org.ace.insurance.report.life.report.LifeMonthlyReport(l) FROM LifeMonthlyReportView l WHERE l.policyNo IS NOT NULL ");

			if (criteria.getYear() != 0) {
				query.append(" AND l.paymentDate >= :startDate");
			}
			if (criteria.getYear() != 0) {
				query.append(" AND l.paymentDate <= :endDate");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND l.branchId = :branchId");
			}

			if (criteria.getLifeProductType() != null) {
				query.append(" AND l.productId = :productId");
			}

			Query q = em.createQuery(query.toString());
			q.setParameter("startDate", getStartDate(criteria));
			q.setParameter("endDate", getEndDate(criteria));

			if (criteria.getLifeProductType().equals(LifeProductType.SNAKE_BITE)) {
				q.setParameter("productId", KeyFactorIDConfig.getSnakeBikeId());
			} else if (criteria.getLifeProductType().equals(LifeProductType.GROUP_LIFE)) {
				q.setParameter("productId", KeyFactorIDConfig.getGroupLifeId());
			} else if (criteria.getLifeProductType().equals(LifeProductType.PUBLIC_LIFE)) {
				q.setParameter("productId", KeyFactorIDConfig.getPublicLifeId());
			} else {
				q.setParameter("productId", KeyFactorIDConfig.getSportManId());
			}

			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			result = q.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicy by criteria.", pe);
		}
		RegNoSorter<LifeMonthlyReport> regNoSorter = new RegNoSorter<LifeMonthlyReport>(result);
		return regNoSorter.getSortedList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeMonthlyReport> findLifePolicyRenewalMonthlyReport(SummaryReportCriteria criteria) throws DAOException {
		List<LifeMonthlyReport> result = new ArrayList<LifeMonthlyReport>();
		StringBuffer query = new StringBuffer();
		try {

			query.append("SELECT new org.ace.insurance.report.life.report.LifeMonthlyReport(l) FROM LifeRenewalMonthlyReportView l WHERE l.policyNo IS NOT NULL ");

			if (criteria.getYear() != 0) {
				query.append(" AND l.paymentDate >= :startDate");
			}
			if (criteria.getYear() != 0) {
				query.append(" AND l.paymentDate <= :endDate");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND l.branchId = :branchId");
			}

			if (criteria.getLifeProductType() != null) {
				query.append(" AND l.productId = :productId");
			}

			Query q = em.createQuery(query.toString());
			q.setParameter("startDate", getStartDate(criteria));
			q.setParameter("endDate", getEndDate(criteria));

			if (criteria.getLifeProductType().equals(LifeProductType.GROUP_LIFE)) {
				q.setParameter("productId", KeyFactorIDConfig.getGroupLifeId());
			} else {
				q.setParameter("productId", KeyFactorIDConfig.getPublicLifeId());
			}

			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			result = q.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyMonthlyReport  by criteria.", pe);
		}
		RegNoSorter<LifeMonthlyReport> regNoSorter = new RegNoSorter<LifeMonthlyReport>(result);
		return regNoSorter.getSortedList();
	}

	public Date getStartDate(SummaryReportCriteria criteria) {
		Calendar cal = Calendar.getInstance();
		int year = criteria.getYear();
		int month = criteria.getMonth();
		cal.set(year, month, 1);
		Date startDate = cal.getTime();
		return Utils.resetStartDate(startDate);
	}

	public Date getEndDate(SummaryReportCriteria criteria) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, criteria.getMonth());
		cal.set(Calendar.YEAR, criteria.getYear());
		DateTime dateTime = new DateTime(cal.getTime());
		DateTime lastTime = dateTime.dayOfMonth().withMaximumValue();
		return Utils.resetEndDate(new Date(lastTime.getMillis()));
	}

}

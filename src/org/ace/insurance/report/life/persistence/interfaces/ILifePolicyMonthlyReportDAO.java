package org.ace.insurance.report.life.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.life.LifePolicyMonthlyReport;
import org.ace.insurance.report.life.report.LifeMonthlyReport;
import org.ace.java.component.persistence.exception.DAOException;

/**
 * This interface serves as the DAO to manipulate the
 * <code>Public LifePolicy Monthly Report</code> object.
 * 
 * @author Ace Plus
 * @since 1.0.0
 * @date 2013/06/27
 */
public interface ILifePolicyMonthlyReportDAO {
	/**
	 * This method is used to retrieve all existing
	 * <code>PublicLifePolicyMonthlyReport</code> objects from the database.
	 * 
	 * @param criteria
	 *            An instance of SummaryReportCriteria.
	 * @return A {@link List} of {@link LifePolicyMonthlyReport} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public List<LifeMonthlyReport> findLifePolicyMonthlyReport(SummaryReportCriteria criteria) throws DAOException;

	/**
	 * This method is used to retrieve all existing
	 * <code>PublicLifePolicyMonthlyReport</code> objects from the database.
	 * 
	 * @param criteria
	 *            An instance of SummaryReportCriteria.
	 * @return A {@link List} of {@link LifePolicyMonthlyReport} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public List<LifeMonthlyReport> findLifePolicyRenewalMonthlyReport(SummaryReportCriteria criteria) throws DAOException;

}

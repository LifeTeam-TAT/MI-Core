package org.ace.insurance.report.life.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.life.LifePolicyMonthlyReport;
import org.ace.insurance.report.life.report.LifeMonthlyReport;
import org.ace.java.component.SystemException;

/**
 * This interface serves as the service layer to manipulate the
 * <code>LifePolicy Monthly Report</code> object.
 * 
 * @author Ace Plus
 * @since 1.0.0
 * @date 2013/06/27
 */
public interface ILifePolicyMonthlyReportService {
	/**
	 * This method is used to retrieve all existing
	 * <code>LifePolicy Monthly Report</code> objects from the repository.
	 * 
	 * @param criteria
	 *            An instance of SummaryReportCriteria.
	 * @return A {@link List} of {@link LifePolicyMonthlyReport} instances
	 * @throws SystemException
	 *             An Exception which occurs during the operation
	 */
	public List<LifeMonthlyReport> findLifePolicyMonthlyReportByCriteria(SummaryReportCriteria criteria);

	/**
	 * This method is used to retrieve all existing
	 * <code>LifePolicy Monthly Report</code> objects from the repository.
	 * 
	 * @param criteria
	 *            An instance of SummaryReportCriteria.
	 * @return A {@link List} of {@link LifePolicyMonthlyReport} instances
	 * @throws SystemException
	 *             An Exception which occurs during the operation
	 */
	public List<LifeMonthlyReport> findLifePolicyRenewalMonthlyReportByCriteria(SummaryReportCriteria criteria);

	/**
	 * This method generate Jasper report in PDF format.
	 * 
	 * @param reports
	 *            - the report records to be generated and printed in PDF file
	 * @param fullFilePath
	 *            - the file path where the generated PDF will be saved
	 * @param criteria
	 *            - the user filtered criteria values that need to be passed to
	 *            report generation process
	 */
	public void generateLifePolicyMonthlyReport(List<LifePolicyMonthlyReport> reports, String fullFilePath, SummaryReportCriteria criteria);

}

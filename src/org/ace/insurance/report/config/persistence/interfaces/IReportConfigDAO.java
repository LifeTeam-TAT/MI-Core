package org.ace.insurance.report.config.persistence.interfaces;

import java.util.List;

import org.ace.java.component.persistence.exception.DAOException;

/**
 * This interface serves as the DAO to configure report by access SQL Agent Job.
 * 
 * @author HS
 * @since 1.0.0
 * @date 2015/03/18
 */
public interface IReportConfigDAO {
	public void configReport(List<String> reportType) throws DAOException, InterruptedException;

	public void jobFinish(String job) throws DAOException, InterruptedException;
}

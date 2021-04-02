package org.ace.insurance.report.common.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.SalesReport;
import org.ace.insurance.report.common.SalesReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface ISalesReportDAO {

	public List<SalesReport> find(SalesReportCriteria criteria)throws DAOException;
	
}

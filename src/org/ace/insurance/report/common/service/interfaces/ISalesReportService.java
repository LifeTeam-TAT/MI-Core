package org.ace.insurance.report.common.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.SalesReport;
import org.ace.insurance.report.common.SalesReportCriteria;

public interface ISalesReportService {
	public List<SalesReport> findSalesReport(SalesReportCriteria criteria);
	public void generateSaleReport(List<SalesReport> salesReportList, String fullReportFilePath);
}

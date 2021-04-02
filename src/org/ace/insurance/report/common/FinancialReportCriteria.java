package org.ace.insurance.report.common;

public class FinancialReportCriteria {
	private SummaryReportType summaryReportType;
	private int financialYear;
	
	public FinancialReportCriteria(SummaryReportType summaryReportType, int financialYear) {
		this.summaryReportType = summaryReportType;
		this.financialYear = financialYear;
	}

	public SummaryReportType getSummaryReportType() {
		return summaryReportType;
	}
	
	public int getFinancialYear() {
		return financialYear;
	}
}

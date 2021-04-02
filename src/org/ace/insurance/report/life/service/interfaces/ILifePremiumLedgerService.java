package org.ace.insurance.report.life.service.interfaces;

import org.ace.insurance.report.life.LifePremiumLedgerReport;

public interface ILifePremiumLedgerService {
	public LifePremiumLedgerReport findLifePremiumLedgerReport(String lifePolicyId);
	public void generateLifePremiumLedgerReportt(LifePremiumLedgerReport lifePolicyReports, String fullReportFilePath);
}

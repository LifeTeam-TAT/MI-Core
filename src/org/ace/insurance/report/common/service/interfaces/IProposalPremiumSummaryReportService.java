package org.ace.insurance.report.common.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.ProposalPremiumSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;

public interface IProposalPremiumSummaryReportService{
	public List<ProposalPremiumSummaryReport> findProposalPremiumSummaryReport(SummaryReportCriteria criteria);
	public void generateProposalPremiumSummaryReport(List<ProposalPremiumSummaryReport> proposalSummaryReports, String fullReportFilePath, SummaryReportCriteria criteria); 
}

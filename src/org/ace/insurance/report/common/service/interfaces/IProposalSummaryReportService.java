package org.ace.insurance.report.common.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.ProposalSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;

public interface IProposalSummaryReportService {
	public List<ProposalSummaryReport> findMotorProposal(SummaryReportCriteria criteria);
	public void generateProposalSummaryReport(List<ProposalSummaryReport> proposalSummaryReports, String fullReportFilePath, SummaryReportCriteria criteria);
}

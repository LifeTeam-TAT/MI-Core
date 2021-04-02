package org.ace.insurance.report.common.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.ProposalPremiumSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProposalPremiumSummaryReportDAO {
	public List<ProposalPremiumSummaryReport> find(SummaryReportCriteria criteria )throws DAOException;
}

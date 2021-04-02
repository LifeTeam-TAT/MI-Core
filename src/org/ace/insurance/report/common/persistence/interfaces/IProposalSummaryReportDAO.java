package org.ace.insurance.report.common.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.ProposalSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProposalSummaryReportDAO {
	public List<ProposalSummaryReport> find(SummaryReportCriteria criteria )throws DAOException;
}

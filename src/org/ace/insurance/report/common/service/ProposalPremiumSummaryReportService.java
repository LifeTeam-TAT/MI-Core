package org.ace.insurance.report.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.ace.insurance.report.JRGenerateUtility;
import org.ace.insurance.report.common.ProposalPremiumSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.common.persistence.interfaces.IProposalPremiumSummaryReportDAO;
import org.ace.insurance.report.common.service.interfaces.IProposalPremiumSummaryReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProposalPremiumSummaryReportService")
public class ProposalPremiumSummaryReportService implements IProposalPremiumSummaryReportService {
	@Resource(name = "ProposalPremiumSummaryReportDAO")
	private IProposalPremiumSummaryReportDAO proposalPremiumSummaryReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProposalPremiumSummaryReport> findProposalPremiumSummaryReport(SummaryReportCriteria criteria) {
		List<ProposalPremiumSummaryReport> result = null;
		try {
			result = proposalPremiumSummaryReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find ProposalPremiumSummaryReport by criteria.", e);
		}
		return result;
	}

	@Override
	public void generateProposalPremiumSummaryReport(List<ProposalPremiumSummaryReport> proposalSummaryReports, String fullReportFilePath, SummaryReportCriteria criteria) {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("ReportTitle", "Proposal Summary Report");
		// params.put("ReportDate", new Date());
		params.put("Criteria", criteria);
		params.put("ProposalPremiumSummaryReports", new JRBeanCollectionDataSource(proposalSummaryReports));
		String fullTemplateFilePath = "report-template/summary/proposalPremiumSummaryReport.jrxml";
		new JRGenerateUtility().generateReportWithDS(fullTemplateFilePath, fullReportFilePath, params);
	}
}

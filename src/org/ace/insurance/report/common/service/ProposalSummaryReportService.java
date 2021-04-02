package org.ace.insurance.report.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.ace.insurance.report.JRGenerateUtility;
import org.ace.insurance.report.common.ProposalSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.common.persistence.interfaces.IProposalSummaryReportDAO;
import org.ace.insurance.report.common.service.interfaces.IProposalSummaryReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProposalSummaryReportService")
public class ProposalSummaryReportService implements IProposalSummaryReportService {

	@Resource(name = "ProposalSummaryReportDAO")
	private IProposalSummaryReportDAO proposalSummaryReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProposalSummaryReport> findMotorProposal(SummaryReportCriteria criteria) {
		List<ProposalSummaryReport> result = null;
		try {
			result = proposalSummaryReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find MotorProposalReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateProposalSummaryReport(List<ProposalSummaryReport> proposalSummaryReports, String fullReportFilePath, SummaryReportCriteria criteria) {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("ReportTitle", "Proposal Summary Report");
		// params.put("ReportDate", new Date());
		params.put("Criteria", criteria);
		params.put("ProposalSummaryReports", new JRBeanCollectionDataSource(proposalSummaryReports));
		String fullTemplateFilePath = "report-template/summary/proposalSummaryReport.jrxml";
		System.out.println("size.." + proposalSummaryReports.size());
		new JRGenerateUtility().generateReportWithDS(fullTemplateFilePath, fullReportFilePath, params);
	}
}

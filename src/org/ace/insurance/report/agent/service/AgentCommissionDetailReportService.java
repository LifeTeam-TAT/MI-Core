package org.ace.insurance.report.agent.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.ace.insurance.report.JRGenerateUtility;
import org.ace.insurance.report.agent.AgentCommissionDetailCriteria;
import org.ace.insurance.report.agent.AgentCommissionDetailReport;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentCommissionDetailDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentCommissionDetailService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentCommissionDetailReportService")
public class AgentCommissionDetailReportService implements IAgentCommissionDetailService {

	@Resource(name = "AgentCommissionDetailReportDAO")
	private IAgentCommissionDetailDAO agentCommissionDetailDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentCommissionDetailReport> findAgentCommissionDetail(AgentCommissionDetailCriteria criteria) {
		List<AgentCommissionDetailReport> result = null;
		try {
			result = agentCommissionDetailDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentCommissionDetailReport by criteria.", e);
		}
		return result;
	}

	@Override
	public void generateReport(List<AgentCommissionDetailReport> reportList, String filePath, String fileName) {
		final String templatePath = "/report-template/agent/";
		final String templateName = "agentComReport.jrxml";
		String templateFullPath = templatePath + templateName;
		String outputFilePdf = filePath + fileName;

		// Create the DataSource instance with the given list which
		// in turn to be filled up in the report
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportList);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("totalCommission", getTotalCommission(reportList));

		paramMap.put("TableDataSource", ds);
		new JRGenerateUtility().generateReport(templateFullPath, outputFilePdf, paramMap);
	}

	private double getTotalCommission(List<AgentCommissionDetailReport> agentCommissionList) {
		double totalComm = 0.0;
		for (AgentCommissionDetailReport a : agentCommissionList) {
			totalComm += a.getCommission();
		}
		return totalComm;
	}
}
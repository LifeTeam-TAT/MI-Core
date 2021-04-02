package org.ace.insurance.report.agent.service;

/**
 * @author NNH
 * @since 1.0.0
 * @date 2014/Feb/18
 */
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.JRGenerateUtility;
import org.ace.insurance.report.agent.AgentInvoiceCriteria;
import org.ace.insurance.report.agent.AgentInvoiceDTO;
import org.ace.insurance.report.agent.AgentInvoiceReport;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentInvoiceReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentInvoiceReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service(value = "AgentInvoiceReportService")
public class AgentInvoiceReportService implements IAgentInvoiceReportService {

	@Resource(name = "AgentInvoiceReportDAO")
	private IAgentInvoiceReportDAO agentInvoiceReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentInvoiceDTO> findAgentInvoiceReports(AgentInvoiceCriteria criteria) {
		List<AgentInvoiceDTO> result = null;
		try {
			result = agentInvoiceReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentInvoiceReport by criteria.", e);
		}
		return result;
	}

	@Override
	public void generateReport(List<AgentInvoiceReport> reportList, String filePath, String fileName) {
		final String templatePath = "/report-template/agent/";

		final String templateName = "AgentCommission.jrxml";
		String templateFullPath = templatePath + templateName;

		String outputFilePdf = filePath + fileName;

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportList);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("TableDataSource", ds);

		new JRGenerateUtility().generateReport(templateFullPath, outputFilePdf, paramMap);

	}

	@Override
	public void generateAgentDetails(AgentInvoiceReport agent, String filePath, String fileName, InputStream is) {
		final String templatePath = "/report-template/agent/";

		final String templateName = "AgentCommission.jrxml";
		String templateFullPath = templatePath + templateName;

		String outputFilePdf = filePath + fileName;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("image", is);
		paramMap.put("invoiceNo", agent.getInvoiceNo());
		paramMap.put("invoiceDate", agent.getInvoiceDate());
		paramMap.put("agentName", agent.getAgentName());
		paramMap.put("licenseNo", agent.getLiscenseNo());
		paramMap.put("referenceType", agent.getReferenceType());
		paramMap.put("commissionAmount", agent.getCommissionAmount());

		new JRGenerateUtility().generateReport(templateFullPath, outputFilePdf, paramMap);

	}
}

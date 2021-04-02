package org.ace.insurance.report.agent.service.interfaces;

/**
 * @author NNH
 * @since 1.0.0
 * @date 2014/Feb/18
 */
import java.io.InputStream;
import java.util.List;

import org.ace.insurance.report.agent.AgentInvoiceCriteria;
import org.ace.insurance.report.agent.AgentInvoiceDTO;
import org.ace.insurance.report.agent.AgentInvoiceReport;

public interface IAgentInvoiceReportService {

	public List<AgentInvoiceDTO> findAgentInvoiceReports(AgentInvoiceCriteria criteria);

	public void generateReport(List<AgentInvoiceReport> reportList, String filePath, String fileName);

	public void generateAgentDetails(AgentInvoiceReport agent, String filePath, String fileName, InputStream is);
}

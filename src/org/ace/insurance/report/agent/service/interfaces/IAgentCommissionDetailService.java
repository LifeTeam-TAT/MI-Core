package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentCommissionDetailCriteria;
import org.ace.insurance.report.agent.AgentCommissionDetailReport;

public interface IAgentCommissionDetailService {
	public List<AgentCommissionDetailReport> findAgentCommissionDetail(AgentCommissionDetailCriteria criteria);

	/**
	 * Generates the {@link AgentCommissionDetailReport} in JasperReport-PDF format and 
	 * returns the full path of the output file.
	 * 
	 * @param reportList the list of the {@link AgentCommissionDetailReport} which are to be included in the output file
	 * @param filePath the fully qualified file path where the output report file should be produced
	 * @param fileName the name of the output file which contains the report
	 */
	public void generateReport(List<AgentCommissionDetailReport> reportList, String filePath, String fileName);
}

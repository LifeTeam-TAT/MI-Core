package org.ace.insurance.report.agent.service.interfaces;

import java.io.InputStream;
import java.util.List;

import org.ace.insurance.report.agent.AgentInformationCriteria;
import org.ace.insurance.report.agent.AgentInformationReport;

public interface IAgentInformationReportService {
	public List<AgentInformationReport> findAgentInformation(AgentInformationCriteria lifeProposalCriteria);

	/**
	 * Generates the {@link AgentInformationReport} in JasperReport-PDF format and 
	 * returns the full path of the output file.
	 * 
	 * @param reportList the list of the {@link AgentInformationReport} which are to be included in the output file
	 * @param filePath the fully qualified file path where the output report file should be produced
	 * @param fileName the name of the output file which contains the report
	 */
	public void generateReport(List<AgentInformationReport> reportList, String filePath, String fileName);
	
	public void generateAgentDetails(AgentInformationReport agent, String filePath, String fileName, InputStream is);
}

package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentInformationCriteria;
import org.ace.insurance.report.agent.AgentInformationReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentInformationReportDAO {
	public List<AgentInformationReport> find(AgentInformationCriteria agentInformationCriteria)throws DAOException;

}

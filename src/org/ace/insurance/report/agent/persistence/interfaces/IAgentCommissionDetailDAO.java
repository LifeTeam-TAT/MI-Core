package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentCommissionDetailCriteria;
import org.ace.insurance.report.agent.AgentCommissionDetailReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentCommissionDetailDAO {

	public List<AgentCommissionDetailReport> find(AgentCommissionDetailCriteria criteria)throws DAOException;
	
}

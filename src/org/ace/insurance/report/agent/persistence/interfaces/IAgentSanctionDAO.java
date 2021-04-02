package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionDTO;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentSanctionDAO {
	public List<AgentSanctionInfo> findAgents(AgentSanctionCriteria criteria) throws DAOException;

	public List<AgentSanctionDTO> findAgentSanctionDTO(AgentSanctionCriteria criteria) throws DAOException;

	public void updateAgentSanctionStaus(List<AgentSanctionInfo> sanctionInfoList) throws DAOException;

	public List<AgentSanctionInfo> findAgentCommissionBySanctionNo(String sanctionNo) throws DAOException;

}

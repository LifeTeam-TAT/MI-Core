/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.AgentCriteria;
import org.ace.insurance.system.common.agent.AGP001;
import org.ace.insurance.system.common.agent.AGP002;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.AgentPortfolio;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentDAO {
	public void insert(Agent Agent) throws DAOException;

	public Agent update(Agent Agent) throws DAOException;

	public AgentPortfolio updateAgentPortfolio(AgentPortfolio portfolio) throws DAOException;

	public void delete(Agent Agent) throws DAOException;

	public void deleteAgentPortfolio(AgentPortfolio agentPortfolio) throws DAOException;

	public Agent findById(String id) throws DAOException;

	public AgentPortfolio findAgentPortfolioById(String id) throws DAOException;

	public List<Agent> findAll() throws DAOException;

	public List<Agent> findByCriteria(AgentCriteria criteria) throws DAOException;

	public List<AGP002> findByCriteria(AgentCriteria criteria, int max) throws DAOException;

	public void insertAgentPortfolio(AgentPortfolio agentPortfolio);

	public List<AGP001> findAgentPortfolioByPolicyNo(String policyNo);

	public boolean checkExistingAgent(String fullIdNo) throws DAOException;
}

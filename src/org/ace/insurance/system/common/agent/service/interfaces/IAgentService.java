/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.agent.service.interfaces;

import java.util.List;

import org.ace.insurance.common.AgentCriteria;
import org.ace.insurance.system.common.agent.AGP001;
import org.ace.insurance.system.common.agent.AGP002;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.AgentPortfolio;

public interface IAgentService {
	public void addNewAgent(Agent Agent);

	public Agent updateAgent(Agent Agent);

	public void deleteAgent(Agent Agent);

	public Agent findAgentById(String id);

	public List<Agent> findAllAgent();

	public List<Agent> findAgentByCriteria(AgentCriteria criteria);

	
	public List<AGP002> findAgentByCriteria(AgentCriteria criteria, int max);

	public void addNewAgentPortfolio(AgentPortfolio agentPortfolio);

	public void deleteAgentPortfolio(AgentPortfolio portfolio);

	public AgentPortfolio updateAgentPortfolio(AgentPortfolio portfolio);

	public AgentPortfolio findAgentPortfolioById(String id);

	public List<AGP001> findAgentPortfolioByPolicyNo(String policyNo);

	public boolean checkExistingAgent(String fullIdNo);

}

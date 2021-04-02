package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.filter.policy.POLICY001;
import org.ace.insurance.system.common.agent.AGP001;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.AgentPortfolio;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageAgentPortfolioActionBean")
public class ManageAgentPortfolioActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	private List<AGP001> portfolioList;
	private AgentPortfolio agentPortfolio;
	private POLICY001 policy;

	@PostConstruct
	public void init() {
		createNewPortfolio();
		portfolioList = new ArrayList<>();
	}

	public void addAgentPortfolio() {
		try {
			agentService.addNewAgentPortfolio(agentPortfolio);
			portfolioList.add(new AGP001(agentPortfolio));
			createNewPortfolio();
			addInfoMessage(null, MessageId.INSERT_SUCCESS, agentPortfolio.getPolicyNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteAgentPortfolio(AGP001 agp001) {
		try {
			agentPortfolio = agentService.findAgentPortfolioById(agp001.getId());
			agentService.deleteAgentPortfolio(agentPortfolio);
			portfolioList.remove(agp001);
			createNewPortfolio();
			addInfoMessage(null, MessageId.DELETE_SUCCESS, agentPortfolio.getPolicyNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void createNewPortfolio() {
		agentPortfolio = new AgentPortfolio();
		if (policy != null) {
			agentPortfolio.setPolicyNo(policy.getPolicyNo());
			agentPortfolio.setEndDate(policy.getEndDate());
		}
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		agentPortfolio.setAgent(agent);
	}

	public void clearPolicy() {
		policy = null;
		createNewPortfolio();
		portfolioList = new ArrayList<>();
	}

	public void returnPolicy(SelectEvent event) {
		policy = (POLICY001) event.getObject();
		portfolioList = agentService.findAgentPortfolioByPolicyNo(policy.getPolicyNo());
		agentPortfolio.setPolicyNo(policy.getPolicyNo());
		agentPortfolio.setEndDate(policy.getEndDate());
	}

	public AgentPortfolio getAgentPortfolio() {
		return agentPortfolio;
	}

	public POLICY001 getPolicy() {
		return policy;
	}

	public List<AGP001> getPortfolioList() {
		return portfolioList;
	}

}

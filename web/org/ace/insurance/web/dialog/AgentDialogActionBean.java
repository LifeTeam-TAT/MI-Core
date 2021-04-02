package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.filter.agent.AGNT001;
import org.ace.insurance.filter.agent.interfaces.IAGENT_Filter;
import org.ace.insurance.filter.cirteria.CRIA001;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "AgentDialogActionBean")
@ViewScoped
public class AgentDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	@ManagedProperty(value = "#{AGENT_Filter}")
	protected IAGENT_Filter filter;

	private CRIA001 agentCriteria;
	private String criteriaValue;
	private List<AGNT001> agentList;

	@PostConstruct
	public void init() {
		criteriaValue = "";
		agentList = filter.find(30);
	}

	public void search() {
		agentList = filter.find(agentCriteria, criteriaValue);
	}

	public void selectAgent(AGNT001 agent001) {
		Agent agent = agentService.findAgentById(agent001.getId());
		PrimeFaces.current().dialog().closeDynamic(agent);
	}

	public List<AGNT001> getAgentList() {
		return agentList;
	}

	public CRIA001[] getCriteriaItems() {
		return CRIA001.values();
	}

	public CRIA001 getAgentCriteria() {
		return agentCriteria;
	}

	public void setAgentCriteria(CRIA001 agentCriteria) {
		this.agentCriteria = agentCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public void setFilter(IAGENT_Filter filter) {
		this.filter = filter;
	}

}

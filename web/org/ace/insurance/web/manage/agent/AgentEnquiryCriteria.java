package org.ace.insurance.web.manage.agent;

import java.util.Date;

import org.ace.insurance.system.common.agent.Agent;

public class AgentEnquiryCriteria {

	private Date startDate;
	private Date endDate;
	private Agent agent;
	private Agent selectedAgent;
	
	public AgentEnquiryCriteria(){
		
	}
	
	public AgentEnquiryCriteria(Date startDate,Date endDate, Agent agent){
		this.startDate = startDate;
		this.endDate = endDate;
		this.agent = agent;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Agent getSelectedAgent() {
		return selectedAgent;
	}

	public void setSelectedAgent(Agent selectedAgent) {
		this.selectedAgent = selectedAgent;
	}

}

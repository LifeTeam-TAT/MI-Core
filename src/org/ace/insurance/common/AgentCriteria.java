package org.ace.insurance.common;

import java.util.Arrays;
import java.util.List;

public class AgentCriteria {
	public String criteriaValue;
	public AgentCriteriaItems agentCriteriaItems;

	public AgentCriteria() {
	}

	public AgentCriteria(String criteriaValue, AgentCriteriaItems agentCriteriaItems) {
		this.criteriaValue = criteriaValue;
		this.agentCriteriaItems = agentCriteriaItems;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public AgentCriteriaItems getAgentCriteriaItems() {
		return agentCriteriaItems;
	}

	public void setAgentCriteriaItems(AgentCriteriaItems agentCriteriaItems) {
		this.agentCriteriaItems = agentCriteriaItems;
	}

	public List<AgentCriteriaItems> getAgentCriteriaItemsList() {
		return Arrays.asList(AgentCriteriaItems.values());
	}

}

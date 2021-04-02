package org.ace.insurance.system.common.agent;

import java.io.Serializable;
import java.util.Date;

public class AGP001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String agentName;
	private String policyNo;
	private Date startDate;
	private Date endDate;

	public AGP001(String id, String agentName, String policyNo, Date startDate, Date endDate) {
		this.id = id;
		this.agentName = agentName;
		this.policyNo = policyNo;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public AGP001(AgentPortfolio portfolio) {
		this.id = portfolio.getId();
		this.agentName = portfolio.getAgent().getFullName();
		this.policyNo = portfolio.getPolicyNo();
		this.startDate = portfolio.getStartDate();
		this.endDate = portfolio.getEndDate();
	}

	public String getId() {
		return id;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

}

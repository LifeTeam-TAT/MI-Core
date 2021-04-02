package org.ace.insurance.report.agent;

import java.util.Date;

import org.ace.insurance.common.AgentCriteriaItems;
import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.system.common.organization.Organization;

public class AgentInformationCriteria {
	private Date startDate;
	private Date endDate;
	private String agent;
	private ProductGroupType groupType;
	private Organization organization;
	private AgentCriteriaItems searchType;
	
	public AgentInformationCriteria(){
		
	}

	public AgentInformationCriteria(Date startDate, Date endDate, String agent, Organization organization, AgentCriteriaItems searchType) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.agent = agent;
		this.organization = organization;
		this.searchType = searchType;
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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public ProductGroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(ProductGroupType groupType) {
		this.groupType = groupType;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public AgentCriteriaItems getSearchType() {
		return searchType;
	}

	public void setSearchType(AgentCriteriaItems searchType) {
		this.searchType = searchType;
	}

}

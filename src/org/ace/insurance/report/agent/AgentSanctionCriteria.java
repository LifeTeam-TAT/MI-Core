package org.ace.insurance.report.agent;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.PolicyReferenceType;

public class AgentSanctionCriteria {
	private List<PolicyReferenceType> referenceTypeList;
	private Date startDate;
	private Date endDate;
	private String branchId;
	private String agentId;
	private String agentName;
	private String sanctionNo;
	private String currencyCode;
	private boolean isEnquiry;

	public AgentSanctionCriteria() {
	}

	public List<PolicyReferenceType> getReferenceTypeList() {
		return referenceTypeList;
	}

	public void setReferenceTypeList(List<PolicyReferenceType> referenceTypeList) {
		this.referenceTypeList = referenceTypeList;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
		this.agentName = null;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public boolean isEnquiry() {
		return isEnquiry;
	}

	public void setEnquiry(boolean isEnquiry) {
		this.isEnquiry = isEnquiry;
	}

}

package org.ace.insurance.report.agent.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2015-12-10
 * @Version 1.0
 * @Purpose This class serves as the Data Entity Object to show Agent Sale No of
 *          Policy Monthly Report from View.
 * 
 ***************************************************************************************/

@Entity
@Table(name = TableName.AGENTSALE)
@ReadOnly
public class AgentSaleComparisonReportView {

	@Id
	private String agentId;
	private String codeNo;
	private String agentName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyStartDate;
	private String proposalType;
	private int newPolicy;
	private int renewalPolicy;
	private int endowmentLife;
	private int groupLife;
	private double totalPremium;
	private double homeTotalPremium;
	private String referenceType;
	private String currencyId;
	private String branchId;
	private String branchName;

	public AgentSaleComparisonReportView() {
	}

	public AgentSaleComparisonReportView(String agentId, String codeNo, String agentName, Date activedPolicyStartDate, String proposalType, int newPolicy, int renewalPolicy,
			int endowmentLife, int groupLife, double totalPremium, double homeTotalPremium, String referenceType, String currency, String branchId, String branchName) {
		this.agentId = agentId;
		this.codeNo = codeNo;
		this.agentName = agentName;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.proposalType = proposalType;
		this.newPolicy = newPolicy;
		this.renewalPolicy = renewalPolicy;
		this.endowmentLife = endowmentLife;
		this.groupLife = groupLife;
		this.totalPremium = totalPremium;
		this.homeTotalPremium = homeTotalPremium;
		this.referenceType = referenceType;
		this.currencyId = currency;
		this.branchId = branchId;
		this.branchName = branchName;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}

	public int getNewPolicy() {
		return newPolicy;
	}

	public void setNewPolicy(int newPolicy) {
		this.newPolicy = newPolicy;
	}

	public int getRenewalPolicy() {
		return renewalPolicy;
	}

	public void setRenewalPolicy(int renewalPolicy) {
		this.renewalPolicy = renewalPolicy;
	}

	public int getEndowmentLife() {
		return endowmentLife;
	}

	public void setEndowmentLife(int endowmentLife) {
		this.endowmentLife = endowmentLife;
	}

	public int getGroupLife() {
		return groupLife;
	}

	public void setGroupLife(int groupLife) {
		this.groupLife = groupLife;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public double getHomeTotalPremium() {
		return homeTotalPremium;
	}

	public void setHomeTotalPremium(double homeTotalPremium) {
		this.homeTotalPremium = homeTotalPremium;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}

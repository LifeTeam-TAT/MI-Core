package org.ace.insurance.report.agent;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_AGENTSALEREPORT)
@ReadOnly
public class AgentSaleReportView {
	@Id
	private String id;
	private String agentName;
	private String productType;
	private String policyNo;
	private String policyHolder;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfInsured;
	private String period;
	private double insuredAmount;
	private double premium;
	private String proposalNo;
	private double commission;
	private String insuranceType;
	private String productId;
	private String agentId;
	private String unit;
	private String remark;

	public AgentSaleReportView() {
	}

	public AgentSaleReportView(String id, String agentName, String productType, String policyNo, String policyHolder, Date dateOfInsured, String period, double insuredAmount,
			double premium, String proposalNo, double commission, String insuranceType, String productId, String agentId, String unit, String remark) {
		super();
		this.id = id;
		this.agentName = agentName;
		this.productType = productType;
		this.policyNo = policyNo;
		this.policyHolder = policyHolder;
		this.dateOfInsured = dateOfInsured;
		this.period = period;
		this.insuredAmount = insuredAmount;
		this.premium = premium;
		this.proposalNo = proposalNo;
		this.commission = commission;
		this.insuranceType = insuranceType;
		this.productId = productId;
		this.agentId = agentId;
		this.unit = unit;
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(String policyHolder) {
		this.policyHolder = policyHolder;
	}

	public Date getDateOfInsured() {
		return dateOfInsured;
	}

	public void setDateOfInsured(Date dateOfInsured) {
		this.dateOfInsured = dateOfInsured;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

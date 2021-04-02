package org.ace.insurance.web.manage.agent;

import java.util.Date;

import org.ace.insurance.common.IdType;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PeriodType;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.medical.policy.MedicalPolicy;

public class PolicyDTO {
	private String id;
	private String policyNo;
	private PolicyReferenceType policyReferenceType;
	private String customerName;
	private IdType idType;
	private String idNo;
	private Double premium;
	private Double sumInsured;
	private Date commissionStartDate;
	private String paymentType;
	private int period;
	private PeriodType periodType;
	private Double commission;
	private InsuranceType insuranceType;
	private String agentName;
	private ProposalType proposalType;
	private int tranNo;

	public PolicyDTO(MedicalPolicy medicalPolicy, Date commissionStartDate, Double commission) {
		this.policyNo = medicalPolicy.getPolicyNo();
		this.policyReferenceType = PolicyReferenceType.HEALTH_POLICY;
		this.customerName = medicalPolicy.getCustomerName();
		this.idNo = medicalPolicy.getCustomer() == null ? "" : medicalPolicy.getCustomer().getFullIdNo();
		this.idType = medicalPolicy.getCustomer() == null ? null : medicalPolicy.getCustomer().getIdType();
		this.premium = medicalPolicy.getTotalPremium();
		this.sumInsured = medicalPolicy.getTotalSumInsured();
		this.commissionStartDate = commissionStartDate;
		this.paymentType = medicalPolicy.getPaymentType().getName();
		this.period = medicalPolicy.getPeriodYears();
		this.periodType = PeriodType.YEAR;
		this.commission = commission;
	}

	public PolicyDTO(LifePolicy lPolicy, Date commissionStartDate, Double commission, PolicyReferenceType referenceType) {
		this.policyNo = lPolicy.getPolicyNo();
		this.policyReferenceType = referenceType;
		this.customerName = lPolicy.getCustomerName();
		this.idNo = lPolicy.getCustomer() == null ? "" : lPolicy.getCustomer().getIdNo();
		this.idType = lPolicy.getCustomer() == null ? null : lPolicy.getCustomer().getIdType();
		this.premium = lPolicy.getTotalBasicTermPremium() + lPolicy.getTotalAddOnTermPremium();
		this.sumInsured = lPolicy.getTotalSumInsured();
		this.commissionStartDate = commissionStartDate;
		this.paymentType = lPolicy.getPaymentType().getName();
		int lPeriod = lPolicy.getPeriodMonth();
		if (lPeriod >= 12) {
			this.period = lPeriod / 12;
		} else {
			this.period = lPeriod;
		}
		if (lPeriod >= 12) {
			this.periodType = PeriodType.YEAR;
		} else {
			this.periodType = PeriodType.MONTH;
		}
		this.commission = commission;
	}

	public PolicyDTO(String id, String policyNo, InsuranceType insuranceType, String customerName, String agentName, Double sumInsured, Double premium, ProposalType proposalType,
			int tranNo) {
		super();
		this.id = id;
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.premium = premium;
		this.sumInsured = sumInsured;
		this.insuranceType = insuranceType;
		this.agentName = agentName;
		this.proposalType = proposalType;
		this.tranNo = tranNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public PolicyReferenceType getPolicyReferenceType() {
		return policyReferenceType;
	}

	public void setPolicyReferenceType(PolicyReferenceType policyReferenceType) {
		this.policyReferenceType = policyReferenceType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public Double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(Double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public Date getCommissionStartDate() {
		return commissionStartDate;
	}

	public void setCommissionStartDate(Date commissionStartDate) {
		this.commissionStartDate = commissionStartDate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

}

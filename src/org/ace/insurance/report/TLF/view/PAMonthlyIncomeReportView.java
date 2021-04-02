package org.ace.insurance.report.TLF.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.ace.insurance.web.common.SaleChannelType;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.PAMONTHLYINCOMEREPORT_VIEW)
@ReadOnly
public class PAMonthlyIncomeReportView {
	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyStartDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyEndDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String customerNam;
	private String address;
	private double sumInsured;
	private double premium;
	private String agentName;
	private String receiptNo;
	private String liscenseNo;
	private int periodMonth;
	private double commission;
	private int numberOfInsuredPerson;
	private String salePointId;
	private String branchName;
	private String branchId;
	private String policyNo;
	private double sumInsuredUsd;
	private double premiumUsd;
	private double commissionUsd;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	public String getId() {
		return id;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getCustomerName() {
		return customerNam;
	}

	public String getSalePointId() {
		return salePointId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setSalePointId(String salePointId) {
		this.salePointId = salePointId;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public double getCommission() {
		return commission;
	}

	public int getNumberOfInsuredPerson() {
		return numberOfInsuredPerson;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setCustomerName(String customerName) {
		this.customerNam = customerName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public void setNumberOfInsuredPerson(int numberOfInsuredPerson) {
		this.numberOfInsuredPerson = numberOfInsuredPerson;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCustomerNam() {
		return customerNam;
	}

	public void setCustomerNam(String customerNam) {
		this.customerNam = customerNam;
	}

	public double getSumInsuredUsd() {
		return sumInsuredUsd;
	}

	public void setSumInsuredUsd(double sumInsuredUsd) {
		this.sumInsuredUsd = sumInsuredUsd;
	}

	public double getPremiumUsd() {
		return premiumUsd;
	}

	public void setPremiumUsd(double premiumUsd) {
		this.premiumUsd = premiumUsd;
	}

	public double getCommissionUsd() {
		return commissionUsd;
	}

	public void setCommissionUsd(double commissionUsd) {
		this.commissionUsd = commissionUsd;
	}

	public String getSalePointsName() {
		return salePointsName;
	}

	public String getFromTermToTerm() {
		return fromTermToTerm;
	}

	public String getFromDateToDate() {
		return fromDateToDate;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

}

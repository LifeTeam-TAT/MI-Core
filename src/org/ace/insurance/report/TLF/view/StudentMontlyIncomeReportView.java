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
@Table(name = TableName.STUDENTMONTHLYINCOMEREPORT_VIEW)
@ReadOnly
public class StudentMontlyIncomeReportView {

	@Id
	private String id;
	private int age;
	private String receiptNo;
	private double amount;
	private double commission;
	private double sumInsured;
	private String policyNo;
	private String customerName;
	private String insuredPersonName;
	private String policyTerm;
	private String paymentType;
	private String agentName;
	private String liscenseNo;
	private String salesPointsId;
	private String branchId;
	private String fullAddress;
	private String salePointsName;
	private String fromTermToTerm;
	private Date activePolicystartDate;
	private Date activePolicyendDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;

	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;
	
	private int premiumPeriod;
	
	public StudentMontlyIncomeReportView() {
	}

	public String getId() {
		return id;
	}

	public int getAge() {
		return age;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public double getAmount() {
		return amount;
	}

	public double getCommission() {
		return commission;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public String getPolicyTerm() {
		return policyTerm;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public String getSalesPointsId() {
		return salesPointsId;
	}

	public String getBranchId() {
		return branchId;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public void setPolicyTerm(String policyTerm) {
		this.policyTerm = policyTerm;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public void setSalesPointsId(String salesPointsId) {
		this.salesPointsId = salesPointsId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getSalePointsName() {
		return salePointsName;
	}

	public String getFromTermToTerm() {
		return fromTermToTerm;
	}



	public Date getActivePolicystartDate() {
		return activePolicystartDate;
	}

	public void setActivePolicystartDate(Date activePolicystartDate) {
		this.activePolicystartDate = activePolicystartDate;
	}

	public Date getActivePolicyendDate() {
		return activePolicyendDate;
	}

	public void setActivePolicyendDate(Date activePolicyendDate) {
		this.activePolicyendDate = activePolicyendDate;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public int getPremiumPeriod() {
		return premiumPeriod;
	}

	public void setPremiumPeriod(int premiumPeriod) {
		this.premiumPeriod = premiumPeriod;
	}

}

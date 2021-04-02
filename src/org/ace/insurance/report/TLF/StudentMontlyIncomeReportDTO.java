package org.ace.insurance.report.TLF;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ace.insurance.web.common.SaleChannelType;

public class StudentMontlyIncomeReportDTO {

	private String customerName;
	private String insuredPerson;
	private int age;
	private String policyTerm;
	private String policyNo;
	private String address;
	private double suminsured;
	private double premium;
	private String paymentType;
	private double commission;
	private String receiptNo;
	private Date paymentDate;
	private String agentName;
	private String salePointsName;
	private String fromTermToTerm;
	private Date activePolicystartDate;
	private Date activePolicyendDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;
	
	private int premiumPeriod;
	

	public StudentMontlyIncomeReportDTO() {

	}

	public StudentMontlyIncomeReportDTO(String customerName, String insuredPerson, int age, String policyTerm, String policyNo, String fullAddress, double suminsured,
			double premium, String paymentType, double commission, String receiptNo, Date paymentDate, String agentName, String salePointsName, Date activePolicystartDate,
		 Date activePolicyendDate,String fromTermToTerm,SaleChannelType saleChannelType,int premiumPeriod) {
		this.customerName = customerName;
		this.insuredPerson = insuredPerson;
		this.age = age;
		this.policyTerm = policyTerm;
		this.policyNo = policyNo;
		this.suminsured = suminsured;
		this.premium = premium;
		this.paymentType = paymentType;
		this.commission = commission;
		this.receiptNo = receiptNo;
		this.paymentDate = paymentDate;
		this.agentName = agentName;
		this.address = fullAddress;
		this.salePointsName = salePointsName;
		this.activePolicystartDate=activePolicystartDate;
		this.activePolicyendDate=activePolicyendDate;
		this.fromTermToTerm = fromTermToTerm;
		this.saleChannelType=saleChannelType;
		this.premiumPeriod=premiumPeriod;
	}
	
	

	public String getInsuredPerson() {
		return insuredPerson;
	}

	public int getAge() {
		return age;
	}

	public String getPolicyTerm() {
		return policyTerm;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getAddress() {
		return address;
	}

	public double getSuminsured() {
		return suminsured;
	}

	public double getPremium() {
		return premium;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public double getCommission() {
		return commission;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getCustomerName() {
		return customerName;
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

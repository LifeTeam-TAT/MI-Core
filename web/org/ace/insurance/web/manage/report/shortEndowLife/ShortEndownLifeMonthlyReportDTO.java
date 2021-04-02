package org.ace.insurance.web.manage.report.shortEndowLife;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ace.insurance.web.common.SaleChannelType;

public class ShortEndownLifeMonthlyReportDTO {

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
	private String liscenseNo;
	private Date activedPolicyStartDate;
	private Date activedPolicyEndDate;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	public ShortEndownLifeMonthlyReportDTO(String insuredPerson, int age, String policyTerm, String policyNo, String residentAddress, String district, String province,
			double suminsured, double premium, String paymentType, double commission, String receiptNo, Date paymentDate, String agentName, String liscenseNo,
			Date activedPolicyStartDate, Date activedPolicyEndDate) {
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
		this.liscenseNo = liscenseNo;
		this.address = residentAddress + "," + district + "," + province;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.activedPolicyEndDate = activedPolicyEndDate;
	}
	
	

	public ShortEndownLifeMonthlyReportDTO(String insuredPerson, int age, String policyTerm, String policyNo, 
			String residentAddress, String district, String province, double suminsured, double premium, String paymentType, double commission, String receiptNo, Date paymentDate,
			String agentName, String liscenseNo, String salePointName, Date activedPolicyStartDate, Date activedPolicyEndDate, String fromTermToTerm,SaleChannelType saleChannelType) {
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
		this.liscenseNo = liscenseNo;
		this.address = residentAddress + "," + district + "," + province;
		this.salePointsName = salePointName;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.activedPolicyEndDate = activedPolicyEndDate;
		this.fromTermToTerm = fromTermToTerm;
		this.saleChannelType=saleChannelType;
	}

	public ShortEndownLifeMonthlyReportDTO(String insuredPerson, int age, String policyTerm, String policyNo,Date activedPolicyStartDate, Date activedPolicyEndDate, 
			String residentAddress, String district, String province, double suminsured, double premium, String paymentType, double commission, String receiptNo, Date paymentDate,
			String agentName, String liscenseNo, String salePointName,String fromDateToDate,String fromTermToTerm,SaleChannelType saleChannelType) {
		this.insuredPerson = insuredPerson;
		this.age = age;
		this.policyTerm = policyTerm;
		this.policyNo = policyNo;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.activedPolicyEndDate = activedPolicyEndDate;
		this.suminsured = suminsured;
		this.premium = premium;
		this.paymentType = paymentType;
		this.commission = commission;
		this.receiptNo = receiptNo;
		this.paymentDate = paymentDate;
		this.agentName = agentName;
		this.liscenseNo = liscenseNo;
		this.address = residentAddress + "," + district + "," + province;
		this.salePointsName = salePointName;
		this.fromDateToDate=fromDateToDate;
		this.fromTermToTerm = fromTermToTerm;
		this.saleChannelType=saleChannelType;
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

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
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

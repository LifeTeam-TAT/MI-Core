package org.ace.insurance.report.sportMan;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ace.insurance.web.common.SaleChannelType;

public class SportManMonthlyReportDTO {

	private double amount;
	private double commission;
	private double sumInsured;
	private String receiptNo;
	private String policyNo;
	private String insuredPersonName;
	private String agentName;
	private String liscenseNo;
	private String salesPointsId;
	private String branchId;
	private String residentAddress;
	private String typeOfSport;
	private Date paymentDate;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	public SportManMonthlyReportDTO(String insuredPerson, String address, String district, String province, String policyNo, String typeOfSport, double suminsured, double premium,
			String receiptNo, Date paymentDate, double commission, String agentName, String liscenseNo, String salePointsName, String fromDateToDate, String fromTermToTerm,SaleChannelType saleChannelType) {
		this.insuredPersonName = insuredPerson;
		this.residentAddress = address + "," + district + "," + province;
		this.policyNo = policyNo;
		this.typeOfSport = typeOfSport;
		this.sumInsured = suminsured;
		this.amount = premium;
		this.receiptNo = receiptNo;
		this.paymentDate = paymentDate;
		this.commission = commission;
		this.agentName = agentName;
		this.liscenseNo = liscenseNo;
		this.salePointsName = salePointsName;
		this.fromDateToDate = fromDateToDate;
		this.fromTermToTerm = fromTermToTerm;
		this.saleChannelType=saleChannelType;
				
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

	public String getReceiptNo() {
		return receiptNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
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

	public String getResidentAddress() {
		return residentAddress;
	}

	public String getTypeOfSport() {
		return typeOfSport;
	}

	public Date getPaymentDate() {
		return paymentDate;
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

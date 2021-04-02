package org.ace.insurance.report.TLF;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ace.insurance.web.common.SaleChannelType;

public class SnakeBiteMonthlyIncomeReportDTO {

	private String policyNo;
	private String customerName;
	private String agentName;
	private Date paymentDate;
	private double premium;
	private double commission;
	private double sumInsured;
	private int unit;
	private String address;
	private String idNo;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	public SnakeBiteMonthlyIncomeReportDTO(String policyNo, String customerName, String agentName, Date paymentDate, double sumInsured, double premium, double commission, int unit,
			String address, String idNo, String salePointsName, String fromDateToDate, String fromTermToTerm,SaleChannelType saleChannelType) {
		this.policyNo = policyNo;
		this.idNo = idNo;
		this.customerName = customerName;
		this.agentName = agentName;
		this.paymentDate = paymentDate;
		this.sumInsured = sumInsured;
		this.premium = premium;
		this.commission = commission;
		this.unit = unit;
		this.address = address;
		this.salePointsName = salePointsName;
		this.fromDateToDate = fromDateToDate;
		this.fromTermToTerm = fromTermToTerm;
		this.saleChannelType=saleChannelType;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAgentName() {
		return agentName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public double getPremium() {
		return premium;
	}

	public double getCommission() {
		return commission;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public int getUnit() {
		return unit;
	}

	public String getAddress() {
		return address;
	}

	public String getIdNo() {
		return idNo;
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

}

package org.ace.insurance.report.TLF;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ace.insurance.web.common.SaleChannelType;

public class FarmerMonthlyIncomeReportDTO {
	private Date paymentDate;
	private String customerName;
	private String address;
	private String nrcNo;
	private double sumInsured;
	private double premium;
	private String agentName;
	private String liscenseNo;
	private double commission;
	private String branchName;
	private String branchId;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	public FarmerMonthlyIncomeReportDTO(Date paymentDate, String customerName, String address, String nrcNo, double sumInsured, double premium, String agentName, String liscenseNo,
			double commission, String branchName, String branchId, String salesPointsName, String fromDateToDate, String fromTermToTerm,SaleChannelType saleChannelType) {
		super();
		this.paymentDate = paymentDate;
		this.customerName = customerName;
		this.address = address;
		this.nrcNo = nrcNo;
		this.sumInsured = sumInsured;
		this.premium = premium;
		this.agentName = agentName;
		this.liscenseNo = liscenseNo;
		this.commission = commission;
		this.branchName = branchName;
		this.branchId = branchId;
		this.salePointsName = salesPointsName;
		this.fromDateToDate = fromDateToDate;
		this.fromTermToTerm = fromTermToTerm;
		this.saleChannelType=saleChannelType;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAddress() {
		return address;
	}

	public String getNrcNo() {
		return nrcNo;
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

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public double getCommission() {
		return commission;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBranchId() {
		return branchId;
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

package org.ace.insurance.report.TLF;

import java.util.Date;

import org.ace.insurance.common.Utils;
import org.ace.insurance.web.common.SaleChannelType;

public class GroupLifeMonthlyIncomeReportDTO {

	private String policyNo;
	private String receiptNo;
	private String customerName;
	private String agentName;
	private Date paymentDate;
	private double premium;
	private double commission;
	private double sumInsured;
	private int insuPersonCount;
	private String address;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	private SaleChannelType saleChannelType;

	public GroupLifeMonthlyIncomeReportDTO(String policyNo, String receiptNo, String customerName, String agentName, Date paymentDate, double sumInsured, double premium,
			double commission, int insuPersonCount, String address) {
		this.policyNo = policyNo;
		this.receiptNo = receiptNo;
		this.customerName = customerName;
		this.agentName = agentName;
		this.paymentDate = paymentDate;
		this.sumInsured = sumInsured;
		this.premium = premium;
		this.commission = commission;
		this.insuPersonCount = insuPersonCount;
		this.address = address;
		
	}

	public GroupLifeMonthlyIncomeReportDTO(String policyNo, String receiptNo, String customerName, String agentName, Date paymentDate, double sumInsured, double premium,
			double commission, int insuPersonCount, String address, String salePointsName, String fromDateToDate, String fromTermToTerm,SaleChannelType saleChannelType) {
		this.policyNo = policyNo;
		this.receiptNo = receiptNo;
		this.customerName = customerName;
		this.agentName = agentName;
		this.paymentDate = paymentDate;
		this.sumInsured = sumInsured;
		this.premium = premium;
		this.commission = commission;
		this.insuPersonCount = insuPersonCount;
		this.address = address;
		this.salePointsName = salePointsName;
		this.fromDateToDate = fromDateToDate;
		this.fromTermToTerm = fromTermToTerm;
		this.saleChannelType = saleChannelType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getReceiptNo() {
		return receiptNo;
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

	public int getInsuPersonCount() {
		return insuPersonCount;
	}

	public String getAddress() {
		return address;
	}

	public String getReceiptNoAndDate() {
		return getReceiptNo() + " (" + Utils.getDateFormatString(getPaymentDate()) + ")";
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

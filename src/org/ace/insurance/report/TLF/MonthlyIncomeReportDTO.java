package org.ace.insurance.report.TLF;

import java.util.Date;

public class MonthlyIncomeReportDTO {

	private String customerName;
	private String organizationName;
	private double sumInsured;
	private double premium;
	private Date activedPolicyStartDate;
	private Date activedPolicyEndDate;
	private String productName;
	private Date paymentDate;
	private String saleChannelType;
	private String agentName;
	private String branchName;
	private long quantity;
	private String productId;

	public MonthlyIncomeReportDTO() {
	}

	public MonthlyIncomeReportDTO(String customerName, String organizationName, double sumInsured, double premium, Date activePolicyStartDate, Date activePolicyEndDate,
			String productName, Date paymentDate, String saleChannelType, String agentName, String branchName) {
		this.customerName = customerName;
		this.organizationName = organizationName;
		this.sumInsured = sumInsured;
		this.premium = premium;
		this.activedPolicyEndDate = activePolicyEndDate;
		this.activedPolicyStartDate = activePolicyStartDate;
		this.productName = productName;
		this.paymentDate = paymentDate;
		this.saleChannelType = saleChannelType;
		this.agentName = agentName;
		this.branchName = branchName;

	}

	public MonthlyIncomeReportDTO(String productId, long quantity, double suminsured) {
		this.productId = productId;
		this.quantity = quantity;
		this.sumInsured = suminsured;
	}

	public String getCustomerName() {
		if (getOrganizationName() != null && !getOrganizationName().isEmpty()) {
			return this.customerName = this.organizationName;
		}
		return customerName;
	}

	public String getOrganizationName() {
		return organizationName;
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

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public String getProductName() {
		return productName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getSaleChannelType() {
		return saleChannelType;
	}

	public String getBranchName() {
		return branchName;
	}

	public long getQuantity() {
		return quantity;
	}

	public String getProductId() {
		return productId;
	}

}

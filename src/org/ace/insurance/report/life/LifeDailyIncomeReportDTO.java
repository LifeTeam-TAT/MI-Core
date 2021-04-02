package org.ace.insurance.report.life;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.system.common.PaymentChannel;

public class LifeDailyIncomeReportDTO {
	private String policyId;
	private String policyNo;
	private String proposalNo;
	private String productId;
	private String receiptNo;
	private String customerName;
	private String organizationName;
	private String agentName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private double sumInsured;
	private double totalAmount;
	private String receivedBankBranchName;
	private String branchId;
	private String branchName;
	private double stampFees;
	private String netPremium;
	private String paymentChannel;

	public LifeDailyIncomeReportDTO() {
	}

	public LifeDailyIncomeReportDTO(String policyId, String policyNo, String proposalNo, String productId, String cashReceiptNo, String customerName, String organizationName,
			String agentName, Date paymentDate, double sumInsured, double totalAmount, String receivedBankBranchName, String branchId, String branchName, double stampFees,
			String netPremium, PaymentChannel paymentChannel) {
		if (customerName == null) {
			this.customerName = organizationName;
		} else {
			this.customerName = customerName;
		}
		if (agentName != null) {
			this.agentName = agentName;
		}
		this.policyNo = policyNo;
		this.proposalNo = proposalNo;
		this.policyId = policyId;
		this.paymentChannel = paymentChannel.toString();
		this.productId = productId;
		this.receiptNo = cashReceiptNo;
		this.stampFees = stampFees;
		this.branchName = branchName;
		this.sumInsured = sumInsured;
		this.paymentDate = paymentDate;
		this.branchId = branchId;
		this.branchName = branchName;
		this.receivedBankBranchName = receivedBankBranchName;
		this.totalAmount = totalAmount;
		this.netPremium = netPremium;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReceivedBankBranchName() {
		return receivedBankBranchName;
	}

	public void setReceivedBankBranchName(String receivedBankBranchName) {
		this.receivedBankBranchName = receivedBankBranchName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public double getStampFees() {
		return stampFees;
	}

	public void setStampFees(double stampFees) {
		this.stampFees = stampFees;
	}

	public String getNetPremium() {
		return netPremium;
	}

	public void setNetPremium(String netPremium) {
		this.netPremium = netPremium;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

}

package org.ace.insurance.report.medical;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.report.medical.view.HealthDailyReportView;

public class HealthDailyReportDTO {

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String paymentChannel;
	private String receiptType;
	private String proposerName;
	private String agentName;
	private String proposalNo;
	private String receiptNo;
	private String policyNo;
	private double suminsured;
	private double netPremium;
	private double stampfees;
	private double totalAmount;
	private String bankBranchName;
	private Date fromDate;
	private Date toDate;

	public HealthDailyReportDTO() {

	}

	public HealthDailyReportDTO(HealthDailyReportView view) {
		this.paymentDate = view.getPaymentDate();
		this.paymentChannel = view.getPaymentChannel();
		if (view.getCustomerName() != null)
			this.proposerName = view.getCustomerName();
		else
			this.proposerName = view.getOrganizationName();
		this.agentName = view.getAgentName() == null ? " " : view.getAgentName();
		this.proposalNo = view.getProposalNo();
		this.receiptNo = view.getReceiptNo();
		this.policyNo = view.getPolicyNo();
		this.suminsured = view.getSuminsured();
		this.netPremium = view.getNetPremium();
		this.stampfees = view.getStampFees();
		this.totalAmount = view.getTotalAmount();
		this.bankBranchName = view.getBankName();
		this.fromDate = view.getPaymentDate();
		this.toDate = view.getPaymentDate();
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public String getProposerName() {
		return proposerName;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public double getSuminsured() {
		return suminsured;
	}

	public double getNetPremium() {
		return netPremium;
	}

	public double getStampfees() {
		return stampfees;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

}

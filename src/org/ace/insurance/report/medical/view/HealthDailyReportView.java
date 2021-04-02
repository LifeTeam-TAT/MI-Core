package org.ace.insurance.report.medical.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.HEALTHDAILYREPORT_VIEW)
@ReadOnly
public class HealthDailyReportView {
	@Id
	private String id;

	private String policyNo;
	private String proposalNo;
	private String customerName;
	private String organizationName;
	private String agentName;
	private String productId;
	private String paymentChannel;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String receiptNo;
	private double stampFees;
	private double suminsured;
	private double netPremium;
	private double totalAmount;
	private String branchId;
	private String branchName;
	private String bankName;

	public HealthDailyReportView() {

	}

	public String getId() {
		return id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getProductId() {
		return productId;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public double getStampFees() {
		return stampFees;
	}

	public double getSuminsured() {
		return suminsured;
	}

	public double getNetPremium() {
		return netPremium;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public String getBranchId() {
		return branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBankName() {
		return bankName;
	}

}

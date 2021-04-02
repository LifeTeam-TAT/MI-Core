package org.ace.insurance.report.life.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_LIFEPOLICY)
@ReadOnly
public class LifePolicyView {
	@Column(name = "ID")
	@Id
	private String id;

	@Column(name = "POLICYID")
	private String policyId;

	@Column(name = "POLICYNO")
	private String policyNo;

	@Column(name = "PROPOSALNO")
	private String proposalNo;

	@Column(name = "RECEIPTNO")
	private String receiptNo;

	@Column(name = "CASHRECEIPTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cashReceiptDate;

	@Column(name = "PAYMENTTYPENAME")
	private String paymentTypeName;

	@Column(name = "BRANCHID")
	private String branchId;

	@Column(name = "BRANCHNAME")
	private String branchName;

	@Column(name = "AGENTID")
	private String agentId;

	@Column(name = "AGENTNAME")
	private String agentName;

	@Column(name = "REFERRALID")
	private String referralId;

	@Column(name = "SALEMANID")
	private String salemanId;

	@Column(name = "CUSTOMERID")
	private String customerId;

	@Column(name = "CUSTOMERNAME")
	private String customerName;

	@Column(name = "CUSTOMERADDRESS")
	private String customerAddress;

	@Column(name = "ORGANIZATIONID")
	private String organizationId;

	@Column(name = "ORGANIZATIONNAME")
	private String organizationName;

	@Column(name = "ORGANIZATIONADDRESS")
	private String organizationAddress;

	@Column(name = "SUMINSURED")
	private double sumInsured;

	@Column(name = "ONEYEARPREMIUM")
	private double oneYearPremium;

	@Column(name = "PREMIUM")
	private double premium;

	@Column(name = "PRODUCTID")
	private String productId;

	@Column(name = "POLICYYEAR")
	private double policyYear;

	@Column(name = "POLICYMONTH")
	private double policyMonth;

	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENTDATE")
	private Date paymentDate;

	@Column(name = "COMMENMANCEDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;

	@Column(name = "ACTIVEDPOLICYSTARTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyStartDate;

	@Column(name = "ACTIVEDPOLICYENDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyEndDate;

	@Column(name = "STATUS")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getCashReceiptDate() {
		return cashReceiptDate;
	}

	public void setCashReceiptDate(Date cashReceiptDate) {
		this.cashReceiptDate = cashReceiptDate;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
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

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public String getSalemanId() {
		return salemanId;
	}

	public void setSalemanId(String salemanId) {
		this.salemanId = salemanId;
	}

	public String getCustomerId() {
		if (this.customerId == null) {
			return this.organizationId;
		} else {
			return this.customerId;
		}
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getName() {
		if (this.customerName == null) {
			return this.organizationName;
		} else {
			return this.customerName;
		}
	}

	public String getAddress() {
		if (this.customerAddress == null) {
			return this.organizationAddress;
		} else {
			return this.customerAddress;
		}
	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getOneYearPremium() {
		return oneYearPremium;
	}

	public void setOneYearPremium(double oneYearPremium) {
		this.oneYearPremium = oneYearPremium;
	}

	public double getPremium() {
		return premium;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getPolicyYear() {
		return policyYear;
	}

	public void setPolicyYear(double policyYear) {
		this.policyYear = policyYear;
	}

	public double getPolicyMonth() {
		return policyMonth;
	}

	public void setPolicyMonth(double policyMonth) {
		this.policyMonth = policyMonth;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

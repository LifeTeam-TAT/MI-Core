package org.ace.insurance.report.shortEndowLife;

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
@Table(name = TableName.VWT_SHORTENDOWLIFEPOLICYVIEW)
@ReadOnly
public class ShortEndowLifePolicyView {

	@Column(name = "POLICYID")
	private String policyId;

	@Column(name = "POLICYNO")
	private String policyNo;

	@Id
	@Column(name = "INSUREDPERSONID")
	private String insuredPersonId;

	@Column(name = "INSUREDPERSON_NAME")
	private String insuredPersonName;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "DATEOFBIRTH")
	private String dateOfBirth;

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

	@Column(name = "ORGANIZATIONID")
	private String organizationId;

	@Column(name = "ORGANIZATIONNAME")
	private String organizationName;

	@Column(name = "BRANCHID")
	private String branchId;

	@Column(name = "BRANCHNAME")
	private String branchName;

	@Column(name = "POLICYSTARTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date policyStartDate;

	@Column(name = "POLICYENDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date policyEndDate;

	@Column(name = "SUMINSURED")
	private double sumInsured;

	@Column(name = "PREMIUM")
	private double premium;

	@Column(name = "TERMPREMIUM")
	private double termPremium;

	@Column(name = "PERIODOFMONTH")
	private int periodOfMonth;

	@Column(name = "PRODUCTID")
	private String productId;

	@Column(name = "RECEIPTNO")
	private String receiptNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENTDATE")
	private Date paymentDate;

	@Column(name = "COMMENMANCEDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;

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

	public String getInsuredPersonId() {
		return insuredPersonId;
	}

	public void setInsuredPersonId(String insuredPersonId) {
		this.insuredPersonId = insuredPersonId;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getName() {
		if (this.customerName == null) {
			return this.organizationName;
		} else {
			return this.customerName;
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public double getTermPremium() {
		return termPremium;
	}

	public void setTermPremium(double termPremium) {
		this.termPremium = termPremium;
	}

	public int getPeriodOfMonth() {
		return periodOfMonth;
	}

	public void setPeriodOfMonth(int periodOfMonth) {
		this.periodOfMonth = periodOfMonth;
	}

}
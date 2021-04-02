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
@Table(name = TableName.VWT_LIFE_CLAIMREQUEST_REPORT)
@ReadOnly
public class LifeClaimRequestView {

	@Id
	private String id;

	@Column(name = "POLICYNO")
	private String policyNo;

	@Column(name = "CUSTOMERNAME")
	private String insuredPersonName;

	@Column(name = "CUSTOMERADDRESS")
	private String insuredPersonAddress;

	@Column(name = "PAYMENTDATE")
	private String paymentDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "SUBMITTEDDATE")
	private Date submittedDate;

	@Column(name = "CLAIMAMOUNT")
	private double claimAmount;

	@Column(name = "SUMINSURED")
	private double sumInsured;

	@Column(name = "CLAIMROLE")
	private String claimType;

	@Column(name = "CLAIMREQUESTID")
	private String claimRequestNo;

	@Column(name = "BRANCHID")
	private String branchId;

	@Column(name = "CUSTOMERID")
	private String customerId;

	@Column(name = "ORGANIZATIONID")
	private String organizationId;

	@Column(name = "SALEMANID")
	private String saleManId;

	@Column(name = "PRODUCTID")
	private String productId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public String getInsuredPersonAddress() {
		return insuredPersonAddress;
	}

	public void setInsuredPersonAddress(String insuredPersonAddress) {
		this.insuredPersonAddress = insuredPersonAddress;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getClaimRequestNo() {
		return claimRequestNo;
	}

	public void setClaimRequestNo(String claimRequestNo) {
		this.claimRequestNo = claimRequestNo;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getSaleManId() {
		return saleManId;
	}

	public void setSaleManId(String saleManId) {
		this.saleManId = saleManId;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}

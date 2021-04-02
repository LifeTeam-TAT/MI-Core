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
@Table(name = TableName.VWT_LIFE_MONTHLY_REPORT)
@ReadOnly
public class LifeMonthlyReportView {
	@Id
	private String id;
	@Column(name = "POLICYNO")
	private String policyNo;
	@Column(name = "RECEIPTNO")
	private String receiptNo;
	@Column(name = "CUSTOMERNAME")
	private String customerName;
	@Column(name = "CUSTOMERADDRESS")
	private String customerAddress;
	@Column(name = "ORGANIZATIONNAME")
	private String organizationName;
	@Column(name = "ORG_ADDRESS")
	private String organizationAddress;
	@Column(name = "AGENTNAMEANDCODENO")
	private String agentNameAndCodeNo;
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENTDATE")
	private Date paymentDate;
	@Column(name = "SI")
	private double sumInsured;
	@Column(name = "PREMIUM")
	private double premium;
	@Column(name = "AGE")
	private int age;
	@Column(name = "PERIOD")
	private String periodOfMonth;
	@Column(name = "PERCENTAGE")
	private int percentage;
	@Column(name = "PRODUCTID")
	private String productId;
	@Column(name = "BRANCHID")
	private String branchId;
	@Column(name = "NAME")
	private String paymentTypeName;
	@Column(name = "NOOFINSU")
	private int noOfInsu;

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

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
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

	public String getAgentNameAndCodeNo() {
		return agentNameAndCodeNo;
	}

	public void setAgentNameAndCodeNo(String agentNameAndCodeNo) {
		this.agentNameAndCodeNo = agentNameAndCodeNo;
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

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPeriodOfMonth() {
		return periodOfMonth;
	}

	public void setPeriodOfMonth(String periodOfMonth) {
		this.periodOfMonth = periodOfMonth;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public int getNoOfInsu() {
		return noOfInsu;
	}

	public void setNoOfInsu(int noOfInsu) {
		this.noOfInsu = noOfInsu;
	}

}

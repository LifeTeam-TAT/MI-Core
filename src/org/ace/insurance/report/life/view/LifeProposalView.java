package org.ace.insurance.report.life.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_LIFEPROPOSAL)
@ReadOnly
public class LifeProposalView implements ISorter {
	@Id
	private String id;

	@Column(name = "PROPOSALNO")
	private String proposalNo;

	@Column(name = "DATEOFPROPOSED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfProposed;

	@Column(name = "CUSTOMERNAME")
	private String customerName;

	@Column(name = "ORGANIZATIONNAME")
	private String organizationName;

	@Column(name = "FATHERNAME")
	private String fatherName;

	@Column(name = "AGENTID")
	private String agentId;

	@Column(name = "CUSTOMERID")
	private String customerId;

	@Column(name = "ORGANIZATIONID")
	private String organizationId;

	@Column(name = "AGENTNAMEANDCODENO")
	private String agentNameAndCodeNo;

	@Column(name = "ADDRESSANDPHONENO")
	private String customerAddressAndPhoneNumber;

	@Column(name = "ORGANIZATIONADDRESSANDPHONE")
	private String organizationAddressAndPhoneNumber;

	@Column(name = "SUMINSURED")
	private double sumInsured;

	@Column(name = "ONEYEARPREMIUM")
	private double oneYearPremium;

	@Column(name = "PREMIUM")
	private double premium;

	@Column(name = "BRANCHNAME")
	private String branchName;

	@Column(name = "BRANCHID")
	private String branchId;

	@Column(name = "WORKFLOWSTATUS")
	private String workflowStatus;

	@Column(name = "RESPONSIBLEPERSON")
	private String responsiblePerson;

	@Column(name = "PRODUCTID")
	private String productId;

	@Column(name = "PROPOSALTYPE")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public Date getDateOfProposed() {
		return dateOfProposed;
	}

	public void setDateOfProposed(Date dateOfProposed) {
		this.dateOfProposed = dateOfProposed;
	}

	public String getCustomerName() {
		if (customerName == null) {
			customerName = getOrganizationName();
		}
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getAgentNameAndCodeNo() {
		return agentNameAndCodeNo;
	}

	public void setAgentNameAndCodeNo(String agentNameAndCodeNo) {
		this.agentNameAndCodeNo = agentNameAndCodeNo;
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

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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

	public String getCustomerAddressAndPhoneNumber() {
		if (customerAddressAndPhoneNumber == null) {
			customerAddressAndPhoneNumber = getOrganizationAddressAndPhoneNumber();
		}
		return customerAddressAndPhoneNumber;
	}

	public void setCustomerAddressAndPhoneNumber(String customerAddressAndPhoneNumber) {
		this.customerAddressAndPhoneNumber = customerAddressAndPhoneNumber;
	}

	public String getOrganizationAddressAndPhoneNumber() {
		return organizationAddressAndPhoneNumber;
	}

	public void setOrganizationAddressAndPhoneNumber(String organizationAddressAndPhoneNumber) {
		this.organizationAddressAndPhoneNumber = organizationAddressAndPhoneNumber;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}
}

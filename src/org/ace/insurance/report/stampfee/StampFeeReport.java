package org.ace.insurance.report.stampfee;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.STAMPFEEREPORT)
@ReadOnly
public class StampFeeReport implements ISorter {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String policyNo;
	private double sumInsured;
	private double stampFees;
	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;
	private String referenceType;
	private String customerId;
	private String customerName;
	private String customerAddress;
	private String organizationId;
	private String organizationName;
	private String organizationAddress;
	private String bankCustomerId;
	private String bankCustomerName;
	private String bankDescription;
	private String branchId;
	private String branchName;
	private String remark;

	public String getId() {
		return id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public double getStampFees() {
		return stampFees;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public String getBankCustomerId() {
		return bankCustomerId;
	}

	public String getBankCustomerName() {
		return bankCustomerName;
	}

	public String getBankDescription() {
		return bankDescription;
	}

	public String getBranchId() {
		return branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getRemark() {
		return remark;
	}

	@Override
	public String getRegistrationNo() {
		return id;
	}

}

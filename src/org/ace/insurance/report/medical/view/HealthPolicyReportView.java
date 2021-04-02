package org.ace.insurance.report.medical.view;

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
@Table(name = TableName.HEALTHPOLICYREPORT_VIEW)
@ReadOnly
public class HealthPolicyReportView {
	@Id
	private String id;
	private String policyNo;
	private String proposalNo;
	private String receiptNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENTDATE")
	private Date paymentDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date commencementDate;

	private String customerName;
	private String address;
	private String phoneNoAndEmailAddress;
	private int basicUnit;
	private int additionalUnit;
	private int option1Unit;
	private int option2Unit;
	private double premium;
	private String premiumMode;
	private String agentNameAndCodeNo;
	private String remarks;
	private String agentId;
	private String customerId;
	private String branchId;
	private String branch;

	public String getId() {
		return id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public Date getCommencementDate() {
		return commencementDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNoAndEmailAddress() {
		return phoneNoAndEmailAddress;
	}

	public int getBasicUnit() {
		return basicUnit;
	}

	public int getAdditionalUnit() {
		return additionalUnit;
	}

	public int getOption1Unit() {
		return option1Unit;
	}

	public int getOption2Unit() {
		return option2Unit;
	}

	public double getPremium() {
		return premium;
	}

	public String getPremiumMode() {
		return premiumMode;
	}

	public String getAgentNameAndCodeNo() {
		return agentNameAndCodeNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getBranchId() {
		return branchId;
	}

	public String getBranch() {
		return branch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + additionalUnit;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result + ((agentNameAndCodeNo == null) ? 0 : agentNameAndCodeNo.hashCode());
		result = prime * result + basicUnit;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((commencementDate == null) ? 0 : commencementDate.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + option1Unit;
		result = prime * result + option2Unit;
		result = prime * result + ((phoneNoAndEmailAddress == null) ? 0 : phoneNoAndEmailAddress.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((premiumMode == null) ? 0 : premiumMode.hashCode());
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HealthPolicyReportView other = (HealthPolicyReportView) obj;
		if (additionalUnit != other.additionalUnit)
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (agentId == null) {
			if (other.agentId != null)
				return false;
		} else if (!agentId.equals(other.agentId))
			return false;
		if (agentNameAndCodeNo == null) {
			if (other.agentNameAndCodeNo != null)
				return false;
		} else if (!agentNameAndCodeNo.equals(other.agentNameAndCodeNo))
			return false;
		if (basicUnit != other.basicUnit)
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (commencementDate == null) {
			if (other.commencementDate != null)
				return false;
		} else if (!commencementDate.equals(other.commencementDate))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (option1Unit != other.option1Unit)
			return false;
		if (option2Unit != other.option2Unit)
			return false;
		if (phoneNoAndEmailAddress == null) {
			if (other.phoneNoAndEmailAddress != null)
				return false;
		} else if (!phoneNoAndEmailAddress.equals(other.phoneNoAndEmailAddress))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (premiumMode == null) {
			if (other.premiumMode != null)
				return false;
		} else if (!premiumMode.equals(other.premiumMode))
			return false;
		if (proposalNo == null) {
			if (other.proposalNo != null)
				return false;
		} else if (!proposalNo.equals(other.proposalNo))
			return false;
		if (receiptNo == null) {
			if (other.receiptNo != null)
				return false;
		} else if (!receiptNo.equals(other.receiptNo))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		return true;
	}

}

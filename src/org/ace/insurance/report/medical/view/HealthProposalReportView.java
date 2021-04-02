package org.ace.insurance.report.medical.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.WorkflowTask;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.HEALTHPROPOSALREPORT_VIEW)
@ReadOnly
public class HealthProposalReportView {
	@Id
	private String id;
	private String proposalNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfProposed;
	
	private String customerName;
	private String nrcNo;
	private String fatherName;
	private String addressAndPhoneNo;
	private int basicUnit;
	private int additionalUnit;
	private int option1Unit;
	private int option2Unit;
	private double premium;
	private String agentNameAndCodeNo;
	
	@Enumerated(EnumType.STRING)
	private WorkflowTask workFlowTask;
	
	private String responsiblePerson;
	private String remark;
	private String customerId;
	private String agentId;
	private String branchId;
	private String branch;
	
	public String getId() {
		return id;
	}
	
	public String getProposalNo() {
		return proposalNo;
	}
	
	public Date getDateOfProposed() {
		return dateOfProposed;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public String getNrcNo() {
		return nrcNo;
	}
	
	public String getFatherName() {
		return fatherName;
	}
	
	public String getAddressAndPhoneNo() {
		return addressAndPhoneNo;
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
	
	public String getAgentNameAndCodeNo() {
		return agentNameAndCodeNo;
	}
	
	public WorkflowTask getWorkFlowTask() {
		return workFlowTask;
	}
	
	public String getResponsiblePerson() {
		return responsiblePerson;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public String getAgentId() {
		return agentId;
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
		result = prime * result + ((addressAndPhoneNo == null) ? 0 : addressAndPhoneNo.hashCode());
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result + ((agentNameAndCodeNo == null) ? 0 : agentNameAndCodeNo.hashCode());
		result = prime * result + basicUnit;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((dateOfProposed == null) ? 0 : dateOfProposed.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nrcNo == null) ? 0 : nrcNo.hashCode());
		result = prime * result + option1Unit;
		result = prime * result + option2Unit;
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((responsiblePerson == null) ? 0 : responsiblePerson.hashCode());
		result = prime * result + ((workFlowTask == null) ? 0 : workFlowTask.hashCode());
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
		HealthProposalReportView other = (HealthProposalReportView) obj;
		if (additionalUnit != other.additionalUnit)
			return false;
		if (addressAndPhoneNo == null) {
			if (other.addressAndPhoneNo != null)
				return false;
		} else if (!addressAndPhoneNo.equals(other.addressAndPhoneNo))
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
		if (dateOfProposed == null) {
			if (other.dateOfProposed != null)
				return false;
		} else if (!dateOfProposed.equals(other.dateOfProposed))
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nrcNo == null) {
			if (other.nrcNo != null)
				return false;
		} else if (!nrcNo.equals(other.nrcNo))
			return false;
		if (option1Unit != other.option1Unit)
			return false;
		if (option2Unit != other.option2Unit)
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (proposalNo == null) {
			if (other.proposalNo != null)
				return false;
		} else if (!proposalNo.equals(other.proposalNo))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (responsiblePerson == null) {
			if (other.responsiblePerson != null)
				return false;
		} else if (!responsiblePerson.equals(other.responsiblePerson))
			return false;
		if (workFlowTask != other.workFlowTask)
			return false;
		return true;
	}
	
}

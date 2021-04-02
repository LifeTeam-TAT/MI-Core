package org.ace.insurance.report.personalAccident;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.MaritalStatus;
import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.PERSONALACCIDENTPROPOSAL)
@ReadOnly
public class PersonalAccidentProposalView {

	@Id
	private String id;
	private String proposalNo;
	private String insuredPersonName;
	private String addressAndPhoneNo;
	private String ageAndDateOfBirth;
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;
	private String occupation;
	private String agentNameAndAgentCode;
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedProposalStartDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedProposalEndDate;
	private Double sumInsured;
	private Double premium;
	private String cashReceiptNoAndPaymentDate;
	private String remark;
	private String agentId;
	private String customerId;
	private String organizationId;
	private String branchId;
	private String productId;
	
	
	public PersonalAccidentProposalView() {
		
	}


	public String getId() {
		return id;
	}


	public String getProposalNo() {
		return proposalNo;
	}


	public String getInsuredPersonName() {
		return insuredPersonName;
	}


	public String getAddressAndPhoneNo() {
		return addressAndPhoneNo;
	}


	public String getAgeAndDateOfBirth() {
		return ageAndDateOfBirth;
	}


	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}


	public String getOccupation() {
		return occupation;
	}


	public String getAgentNameAndAgentCode() {
		return agentNameAndAgentCode;
	}


	public Date getActivedProposalStartDate() {
		return activedProposalStartDate;
	}


	public Date getActivedProposalEndDate() {
		return activedProposalEndDate;
	}


	public Double getSumInsured() {
		return sumInsured;
	}


	public Double getPremium() {
		return premium;
	}

	public String getCashReceiptNoAndPaymentDate() {
		return cashReceiptNoAndPaymentDate;
	}


	public String getRemark() {
		return remark;
	}


	public String getAgentId() {
		return agentId;
	}


	public String getCustomerId() {
		return customerId;
	}


	public String getOrganizationId() {
		return organizationId;
	}


	public String getBranchId() {
		return branchId;
	}


	public String getProductId() {
		return productId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activedProposalEndDate == null) ? 0 : activedProposalEndDate.hashCode());
		result = prime * result + ((activedProposalStartDate == null) ? 0 : activedProposalStartDate.hashCode());
		result = prime * result + ((addressAndPhoneNo == null) ? 0 : addressAndPhoneNo.hashCode());
		result = prime * result + ((ageAndDateOfBirth == null) ? 0 : ageAndDateOfBirth.hashCode());
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result + ((agentNameAndAgentCode == null) ? 0 : agentNameAndAgentCode.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((cashReceiptNoAndPaymentDate == null) ? 0 : cashReceiptNoAndPaymentDate.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuredPersonName == null) ? 0 : insuredPersonName.hashCode());
		result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((organizationId == null) ? 0 : organizationId.hashCode());
		result = prime * result + ((premium == null) ? 0 : premium.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((sumInsured == null) ? 0 : sumInsured.hashCode());
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
		PersonalAccidentProposalView other = (PersonalAccidentProposalView) obj;
		if (activedProposalEndDate == null) {
			if (other.activedProposalEndDate != null)
				return false;
		} else if (!activedProposalEndDate.equals(other.activedProposalEndDate))
			return false;
		if (activedProposalStartDate == null) {
			if (other.activedProposalStartDate != null)
				return false;
		} else if (!activedProposalStartDate.equals(other.activedProposalStartDate))
			return false;
		if (addressAndPhoneNo == null) {
			if (other.addressAndPhoneNo != null)
				return false;
		} else if (!addressAndPhoneNo.equals(other.addressAndPhoneNo))
			return false;
		if (ageAndDateOfBirth != other.ageAndDateOfBirth)
			return false;
		if (agentId == null) {
			if (other.agentId != null)
				return false;
		} else if (!agentId.equals(other.agentId))
			return false;
		if (agentNameAndAgentCode == null) {
			if (other.agentNameAndAgentCode != null)
				return false;
		} else if (!agentNameAndAgentCode.equals(other.agentNameAndAgentCode))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (cashReceiptNoAndPaymentDate == null) {
			if (other.cashReceiptNoAndPaymentDate != null)
				return false;
		} else if (!cashReceiptNoAndPaymentDate.equals(other.cashReceiptNoAndPaymentDate))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuredPersonName == null) {
			if (other.insuredPersonName != null)
				return false;
		} else if (!insuredPersonName.equals(other.insuredPersonName))
			return false;
		if (maritalStatus != other.maritalStatus)
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (organizationId == null) {
			if (other.organizationId != null)
				return false;
		} else if (!organizationId.equals(other.organizationId))
			return false;
		if (premium == null) {
			if (other.premium != null)
				return false;
		} else if (!premium.equals(other.premium))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
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
		if (sumInsured == null) {
			if (other.sumInsured != null)
				return false;
		} else if (!sumInsured.equals(other.sumInsured))
			return false;
		return true;
	}

	
}

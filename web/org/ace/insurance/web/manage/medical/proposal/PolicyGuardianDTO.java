package org.ace.insurance.web.manage.medical.proposal;

import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonGuardian;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.web.common.CommonDTO;
import org.ace.insurance.web.manage.medical.proposal.factory.CustomerFactory;

public class PolicyGuardianDTO extends CommonDTO {
	private String guardianNo;
	private CustomerDTO customerDTO;
	private RelationShip relationship;
	private boolean death;

	public PolicyGuardianDTO() {
	}

	public PolicyGuardianDTO(MedicalPolicyInsuredPersonGuardian guardian) {
		this.guardianNo = guardian.getGuardianNo();
		this.customerDTO = CustomerFactory.getCustomerDTO(guardian.getCustomer());
		this.relationship = guardian.getRelationship();
		this.death = guardian.isDeath();
	}

	public String getGuardianNo() {
		return guardianNo;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setGuardianNo(String guardianNo) {
		this.guardianNo = guardianNo;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((customerDTO == null) ? 0 : customerDTO.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		result = prime * result + ((guardianNo == null) ? 0 : guardianNo.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolicyGuardianDTO other = (PolicyGuardianDTO) obj;
		if (customerDTO == null) {
			if (other.customerDTO != null)
				return false;
		} else if (!customerDTO.equals(other.customerDTO))
			return false;
		if (death != other.death)
			return false;
		if (guardianNo == null) {
			if (other.guardianNo != null)
				return false;
		} else if (!guardianNo.equals(other.guardianNo))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		return true;
	}

}

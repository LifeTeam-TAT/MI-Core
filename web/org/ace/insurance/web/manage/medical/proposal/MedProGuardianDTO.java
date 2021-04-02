package org.ace.insurance.web.manage.medical.proposal;

import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.web.common.CommonDTO;

public class MedProGuardianDTO extends CommonDTO {
	private String guardianNo;
	private CustomerDTO customer;
	private RelationShip relationship;
	private String tempId;
	private boolean sameCustomer;

	public MedProGuardianDTO() {
		tempId = System.nanoTime() + "";
		customer = new CustomerDTO();
	}

	public MedProGuardianDTO(String guardianNo, CustomerDTO customer, RelationShip relationship) {
		this.guardianNo = guardianNo;
		this.customer = customer;
		this.relationship = relationship;
	}

	public String getGuardianNo() {
		return guardianNo;
	}

	public void setGuardianNo(String guardianNo) {
		this.guardianNo = guardianNo;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public boolean isSameCustomer() {
		return sameCustomer;
	}

	public void setSameCustomer(boolean sameCustomer) {
		this.sameCustomer = sameCustomer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((guardianNo == null) ? 0 : guardianNo.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + (sameCustomer ? 1231 : 1237);
		result = prime * result + ((tempId == null) ? 0 : tempId.hashCode());
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
		MedProGuardianDTO other = (MedProGuardianDTO) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
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
		if (sameCustomer != other.sameCustomer)
			return false;
		if (tempId == null) {
			if (other.tempId != null)
				return false;
		} else if (!tempId.equals(other.tempId))
			return false;
		return true;
	}

}

package org.ace.insurance.web.manage.life.claim.request;

import java.util.Date;
import java.util.List;

import org.ace.insurance.life.claim.LifeClaimInsuredPersonAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
public class DeathClaimInsuredPersonDTO {
	private String id;
	private List<LifeClaimInsuredPersonAttachment> claimInsuredPersonAttachment;
	private PolicyInsuredPerson policyInsuredPerson;
	private String deathReason;
	private Date deathDate;

	public DeathClaimInsuredPersonDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<LifeClaimInsuredPersonAttachment> getClaimInsuredPersonAttachment() {
		return claimInsuredPersonAttachment;
	}

	public void setClaimInsuredPersonAttachment(List<LifeClaimInsuredPersonAttachment> claimInsuredPersonAttachment) {
		this.claimInsuredPersonAttachment = claimInsuredPersonAttachment;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public String getDeathReason() {
		return deathReason;
	}

	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result
				+ ((deathReason == null) ? 0 : deathReason.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((policyInsuredPerson == null) ? 0 : policyInsuredPerson
						.hashCode());
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
		DeathClaimInsuredPersonDTO other = (DeathClaimInsuredPersonDTO) obj;
		if (deathDate == null) {
			if (other.deathDate != null)
				return false;
		} else if (!deathDate.equals(other.deathDate))
			return false;
		if (deathReason == null) {
			if (other.deathReason != null)
				return false;
		} else if (!deathReason.equals(other.deathReason))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		return true;
	}

}

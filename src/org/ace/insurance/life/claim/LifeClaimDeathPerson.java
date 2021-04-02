package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.interfaces.IEntity;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Entity
@DiscriminatorValue(value = LifeClaimBeneficiaryRole.DEATHPERSON)
public class LifeClaimDeathPerson extends LifeClaimInsuredPerson implements Serializable, IEntity {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deathDate;

	private String deathReason;

	public LifeClaimDeathPerson() {
		super();
	}

/*	public LifeClaimDeathPerson(InsuredPersonType name, Claim claim, Date deathDate, String deathReason) {
			super(name, claim);
			this.deathDate = deathDate;
			this.deathReason = deathReason;

	}
*/
	public Date getDeathDate() {
			return deathDate;
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	public String getDeathReason() {
		return deathReason;
	}

	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result + ((deathReason == null) ? 0 : deathReason.hashCode());
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
		LifeClaimDeathPerson other = (LifeClaimDeathPerson) obj;
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
		return true;
	}


}

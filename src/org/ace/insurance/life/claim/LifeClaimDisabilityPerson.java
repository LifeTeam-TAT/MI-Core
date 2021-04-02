package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IEntity;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Entity
@DiscriminatorValue(value = LifeClaimBeneficiaryRole.DISABILITYPERSON)
public class LifeClaimDisabilityPerson extends LifeClaimInsuredPerson implements Serializable, IEntity {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date disabilityDate;

	private String disabilityReason;

	@Embedded
	private UserRecorder recorder;

	public LifeClaimDisabilityPerson() {
		super();
	}

	/*
	 * public DisabilityPerson(InsuredPersonType name,Claim claim , Date
	 * disDate,String disReason) { super(name,claim); this.disabilityDate =
	 * disDate; this.disabilityReason = disReason; }
	 */

	public Date getDisabilityDate() {
		return disabilityDate;
	}

	public void setDisabilityDate(Date disabilityDate) {
		this.disabilityDate = disabilityDate;
	}

	public String getDisabilityReason() {
		return disabilityReason;
	}

	public void setDisabilityReason(String disabilityReason) {
		this.disabilityReason = disabilityReason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((disabilityDate == null) ? 0 : disabilityDate.hashCode());
		result = prime * result + ((disabilityReason == null) ? 0 : disabilityReason.hashCode());
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
		LifeClaimDisabilityPerson other = (LifeClaimDisabilityPerson) obj;
		if (disabilityDate == null) {
			if (other.disabilityDate != null)
				return false;
		} else if (!disabilityDate.equals(other.disabilityDate))
			return false;
		if (disabilityReason == null) {
			if (other.disabilityReason != null)
				return false;
		} else if (!disabilityReason.equals(other.disabilityReason))
			return false;
		return true;
	}

}

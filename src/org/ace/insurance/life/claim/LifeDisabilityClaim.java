package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@DiscriminatorValue(value = LifeClaimRole.DISABILITY)
@NamedQueries(value = { @NamedQuery(name = "DisabilityClaim.findByRequestId", query = "SELECT o FROM LifeDisabilityClaim o WHERE o.claimRequestId = :claimRequestId") })
public class LifeDisabilityClaim extends LifeClaim implements Serializable, IEntity {

	private static final long serialVersionUID = 535929952949737910L;

	private int anuityTerm;
	private int waitingPeriod;

	@Enumerated(value = EnumType.STRING)
	private LifeDisabilityClaimType disabilityClaimType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date waitingEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date waitingStartDate;

	@Embedded
	private UserRecorder recorder;

	public LifeDisabilityClaim() {}

	public LifeDisabilityClaimType getDisabilityClaimType() {
		return disabilityClaimType;
	}

	public void setDisabilityClaimType(LifeDisabilityClaimType disabilityClaimType) {
		this.disabilityClaimType = disabilityClaimType;
	}

	public int getAnuityTerm() {
		return anuityTerm;
	}

	public void setAnuityTerm(int anuityTerm) {
		this.anuityTerm = anuityTerm;
	}

	public int getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(int waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	public Date getWaitingEndDate() {
		return waitingEndDate;
	}

	public void setWaitingEndDate(Date waitingEndDate) {
		this.waitingEndDate = waitingEndDate;
	}

	public Date getWaitingStartDate() {
		return waitingStartDate;
	}

	public void setWaitingStartDate(Date waitingStartDate) {
		this.waitingStartDate = waitingStartDate;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + anuityTerm;
		result = prime * result + ((disabilityClaimType == null) ? 0 : disabilityClaimType.hashCode());
		result = prime * result + ((waitingEndDate == null) ? 0 : waitingEndDate.hashCode());
		result = prime * result + waitingPeriod;
		result = prime * result + ((waitingStartDate == null) ? 0 : waitingStartDate.hashCode());
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
		LifeDisabilityClaim other = (LifeDisabilityClaim) obj;
		if (anuityTerm != other.anuityTerm)
			return false;
		if (disabilityClaimType != other.disabilityClaimType)
			return false;
		if (waitingEndDate == null) {
			if (other.waitingEndDate != null)
				return false;
		} else if (!waitingEndDate.equals(other.waitingEndDate))
			return false;
		if (waitingPeriod != other.waitingPeriod)
			return false;
		if (waitingStartDate == null) {
			if (other.waitingStartDate != null)
				return false;
		} else if (!waitingStartDate.equals(other.waitingStartDate))
			return false;
		return true;
	}

}

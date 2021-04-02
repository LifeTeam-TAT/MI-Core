package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;

@Entity
@Table(name = TableName.LIFEDEATHCLAIM)
@DiscriminatorValue(value = LifeClaimRole.DEATH)
public class LifeDeathClaim extends LifePolicyClaim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "DEATH_PLACE")
	private String deathPlace;

	@Column(name = "DEATH_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deathDate;

	@Column(name = "DEATH_CLAIM_AMT")
	private double deathClaimAmount;

	private String causeofdeath;

	private String causeofPropose;
	
	


	@Embedded
	private UserRecorder recorder;
	
	

	public LifeDeathClaim() {
		super();
	}

	public String getDeathPlace() {
		return deathPlace;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public double getDeathClaimAmount() {
		return deathClaimAmount;
	}

	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public void setDeathClaimAmount(double deathClaimAmount) {
		this.deathClaimAmount = deathClaimAmount;
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
		long temp;
		temp = Double.doubleToLongBits(deathClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result + ((deathPlace == null) ? 0 : deathPlace.hashCode());
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
		LifeDeathClaim other = (LifeDeathClaim) obj;
		if (Double.doubleToLongBits(deathClaimAmount) != Double.doubleToLongBits(other.deathClaimAmount))
			return false;
		if (deathDate == null) {
			if (other.deathDate != null)
				return false;
		} else if (!deathDate.equals(other.deathDate))
			return false;
		if (deathPlace == null) {
			if (other.deathPlace != null)
				return false;
		} else if (!deathPlace.equals(other.deathPlace))
			return false;
		return true;
	}

	public String getCauseofPropose() {
		return causeofPropose;
	}

	public void setCauseofPropose(String causeofPropose) {
		this.causeofPropose = causeofPropose;
	}

	public String getCauseofdeath() {
		return causeofdeath;
	}

	public void setCauseofdeath(String causeofdeath) {
		this.causeofdeath = causeofdeath;
	}

}

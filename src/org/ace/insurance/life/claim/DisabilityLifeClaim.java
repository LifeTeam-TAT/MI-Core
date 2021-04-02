package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.paymenttype.PaymentType;

@Entity
@Table(name = TableName.DISABILITYLIFECLAIM)
@DiscriminatorValue(value = LifeClaimRole.DISIBILITY_CLAIM)
public class DisabilityLifeClaim extends LifePolicyClaim implements Serializable {
	private static final long serialVersionUID = 1L;

	private int waitingPeriod;
	private int paymentterm;
	private int paidterm;

	@Enumerated(value = EnumType.STRING)
	private ClaimStatus claimStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date waitingEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date waitingStartDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "DISABILITYLIFECLAIMID", referencedColumnName = "ID")
	private List<DisabilityLifeClaimPartLink> disabilityLifeClaimList;

	@Embedded
	private UserRecorder recorder;

	public DisabilityLifeClaim() {
		super();
	}

	public int getWaitingPeriod() {
		return waitingPeriod;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public int getPaymentterm() {
		return paymentterm;
	}

	public void setPaymentterm(int paymentterm) {
		this.paymentterm = paymentterm;
	}

	public int getPaidterm() {
		return paidterm;
	}

	public void setPaidterm(int paidterm) {
		this.paidterm = paidterm;
	}

	public Date getWaitingEndDate() {
		return waitingEndDate;
	}

	public Date getWaitingStartDate() {
		return waitingStartDate;
	}

	public void setWaitingPeriod(int waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setWaitingEndDate(Date waitingEndDate) {
		this.waitingEndDate = waitingEndDate;
	}

	public void setWaitingStartDate(Date waitingStartDate) {
		this.waitingStartDate = waitingStartDate;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public List<DisabilityLifeClaimPartLink> getDisabilityLifeClaimList() {
		return disabilityLifeClaimList;
	}

	public void setDisabilityLifeClaimList(List<DisabilityLifeClaimPartLink> disabilityLifeClaimList) {
		this.disabilityLifeClaimList = disabilityLifeClaimList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		result = prime * result + paidterm;
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + paymentterm;
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
		DisabilityLifeClaim other = (DisabilityLifeClaim) obj;
		if (paidterm != other.paidterm)
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (paymentterm != other.paymentterm)
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

	public double getDisabilityClaimAmount() {
		double claimAmount = 0;
		for (DisabilityLifeClaimPartLink claim : disabilityLifeClaimList) {
			claimAmount += claim.getDisabilityAmount();
		}
		return claimAmount;
	}

	public double getTermDisabilityClaimAmount() {
		double claimAmount = 0;
		for (DisabilityLifeClaimPartLink claim : disabilityLifeClaimList) {
			claimAmount += claim.getTermDisabilityAmount();
		}
		return claimAmount;
	}
}

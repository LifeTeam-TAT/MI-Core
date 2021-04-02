package org.ace.insurance.medical.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.common.TableName;

@Entity
@Table(name = TableName.DEATHCLAIM)
@DiscriminatorValue(value = MedicalClaimRole.DEATH_CLAIM)
@NamedQueries(value = { @NamedQuery(name = "DeathClaim.findAll", query = "SELECT d FROM DeathClaim d "),
		@NamedQuery(name = "DeathClaim.findById", query = "SELECT d FROM DeathClaim d WHERE d.id = :id") })
public class DeathClaim extends MedicalClaim implements Serializable {

	private static final long serialVersionUID = -21285200232975612L;

	@Column(name = "DEATH_PLACE")
	private String deathPlace;

	@Column(name = "OTHER_PLACE")
	private String otherPlace;

	@Column(name = "DEATH_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deathDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> attachmentList;

	@Column(name = "DEATH_CLAIM_AMT")
	private double deathClaimAmount;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deathClaim", orphanRemoval = true)
	private List<DeathClaimICD10> deathClaimICD10List;

	public DeathClaim() {

	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public double getDeathClaimAmount() {
		return deathClaimAmount;
	}

	public void setDeathClaimAmount(double deathClaimAmount) {
		this.deathClaimAmount = deathClaimAmount;
	}

	public String getDeathPlace() {
		return deathPlace;
	}

	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}

	public String getOtherPlace() {
		return otherPlace;
	}

	public void setOtherPlace(String otherPlace) {
		this.otherPlace = otherPlace;
	}

	public void addAttachment(Attachment attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		attachmentList.add(attachment);
	}

	public List<Attachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<DeathClaimICD10> getDeathClaimICD10List() {
		if (deathClaimICD10List == null) {
			deathClaimICD10List = new ArrayList<DeathClaimICD10>();
		}
		return deathClaimICD10List;
	}

	public void setDeathClaimICD10List(List<DeathClaimICD10> deathClaimICD10List) {
		this.deathClaimICD10List = deathClaimICD10List;
	}

	public void addDeathClaimICD10(DeathClaimICD10 deathClaimICD10) {
		if (deathClaimICD10List == null) {
			deathClaimICD10List = new ArrayList<DeathClaimICD10>();
		}
		deathClaimICD10.setDeathClaim(this);
		deathClaimICD10List.add(deathClaimICD10);
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
		result = prime * result + ((otherPlace == null) ? 0 : otherPlace.hashCode());
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
		DeathClaim other = (DeathClaim) obj;
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
		if (otherPlace == null) {
			if (other.otherPlace != null)
				return false;
		} else if (!otherPlace.equals(other.otherPlace))
			return false;
		return true;
	}

}

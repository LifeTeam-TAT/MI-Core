package org.ace.insurance.life.claim;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.ace.insurance.common.interfaces.IEntity;
import org.ace.insurance.life.policy.BeneficiaryStatus;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "ClaimBeneficiary.findBySuccessorId", query = "SELECT c FROM LifeClaimInsuredPersonBeneficiary c WHERE c.claimSuccessor.id = :successorId"),
		@NamedQuery(name = "ClaimBeneficiary.findByRelationshipId", query = "SELECT c FROM LifeClaimInsuredPersonBeneficiary c WHERE c.policyInsuredPersonBeneficiaries.relationship.id = :relationshipId") })
@DiscriminatorValue(value = LifeClaimBeneficiaryRole.INSUREDPERSONBENEFICIARY)
public class LifeClaimInsuredPersonBeneficiary extends LifeClaimBeneficiary implements IEntity {

	private static final long serialVersionUID = 951663058512725760L;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "BENEFICIARYSTATUS")
	BeneficiaryStatus beneficiaryStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMINSUREDPERSONID", referencedColumnName = "ID")
	private LifeClaimInsuredPerson claimInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYINSUREDPERSONBENEFICIARIESID", referencedColumnName = "ID")
	private PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "claimInsuredPersonBeneficiary", orphanRemoval = true)
	private List<LifeClaimInsuredPersonBeneficiaryAttachment> claimInsuredPersonBeneficiaryAttachmentList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMSUCCESSORID", referencedColumnName = "ID")
	private LifeClaimSuccessor claimSuccessor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMINSUREDPERSONBENEFICIARY_CLAIMID", referencedColumnName = "ID")
	private LifeClaim lifeClaim;

	public LifeClaimInsuredPersonBeneficiary() {
		super.claimStatus = ClaimStatus.WAITING;
	}

	public LifeClaimInsuredPerson getClaimInsuredPerson() {
		return claimInsuredPerson;
	}

	public void setClaimInsuredPerson(LifeClaimInsuredPerson claimInsuredPerson) {
		this.claimInsuredPerson = claimInsuredPerson;
	}

	public BeneficiaryStatus getBeneficiaryStatus() {
		return beneficiaryStatus;
	}

	public void setBeneficiaryStatus(BeneficiaryStatus beneficiaryStatus) {
		this.beneficiaryStatus = beneficiaryStatus;
	}

	public PolicyInsuredPersonBeneficiaries getPolicyInsuredPersonBeneficiaries() {
		return policyInsuredPersonBeneficiaries;
	}

	public void setPolicyInsuredPersonBeneficiaries(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
		this.policyInsuredPersonBeneficiaries = policyInsuredPersonBeneficiaries;
	}

	public List<LifeClaimInsuredPersonBeneficiaryAttachment> getClaimInsuredPersonBeneficiaryAttachmentList() {
		return claimInsuredPersonBeneficiaryAttachmentList;
	}

	public void setClaimInsuredPersonBeneficiaryAttachmentList(List<LifeClaimInsuredPersonBeneficiaryAttachment> claimInsuredPersonBeneficiaryAttachmentList) {
		this.claimInsuredPersonBeneficiaryAttachmentList = claimInsuredPersonBeneficiaryAttachmentList;
	}

	public LifeClaimSuccessor getClaimSuccessor() {
		return claimSuccessor;
	}

	public void setClaimSuccessor(LifeClaimSuccessor claimSuccessor) {
		this.claimSuccessor = claimSuccessor;
	}

	public void update(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		this.claimInsuredPerson = claimInsuredPersonBeneficiary.getClaimInsuredPerson();
		this.policyInsuredPersonBeneficiaries = claimInsuredPersonBeneficiary.getPolicyInsuredPersonBeneficiaries();
		this.claimInsuredPersonBeneficiaryAttachmentList = claimInsuredPersonBeneficiary.getClaimInsuredPersonBeneficiaryAttachmentList();
		super.update(claimInsuredPersonBeneficiary);
	}

	@Override
	public String getFullName() {
		return this.policyInsuredPersonBeneficiaries.getFullName();
	}

	@Override
	public String getFullResidentAddress() {
		return this.policyInsuredPersonBeneficiaries.getResidentAddress().getFullResidentAddress();
	}

	@Override
	public String getIdNo() {
		return this.policyInsuredPersonBeneficiaries.getIdNo();
	}

	@Override
	public String getFatherName() {
		return new String("");
	}

	public LifeClaim getLifeClaim() {
		return lifeClaim;
	}

	public void setLifeClaim(LifeClaim lifeClaim) {
		this.lifeClaim = lifeClaim;
	}

	public boolean isClaimInsuredPersonBeneficiary() {
		return LifeClaimBeneficiaryRole.INSUREDPERSONBENEFICIARY.equals(getBeneficiaryRole());
	}

	public boolean isClaimInsuredPerson() {
		return LifeClaimBeneficiaryRole.INSUREDPERSON.equals(getBeneficiaryRole());
	}

	public boolean isClaimSuccessor() {
		return LifeClaimBeneficiaryRole.SUCCESSOR.equals(getBeneficiaryRole());
	}

	public boolean isDeathPerson() {
		return LifeClaimBeneficiaryRole.DEATHPERSON.equals(getBeneficiaryRole());
	}

	public boolean isDisabilityPerson() {
		return LifeClaimBeneficiaryRole.DISABILITYPERSON.equals(getBeneficiaryRole());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((beneficiaryStatus == null) ? 0 : beneficiaryStatus.hashCode());
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
		LifeClaimInsuredPersonBeneficiary other = (LifeClaimInsuredPersonBeneficiary) obj;
		if (beneficiaryStatus != other.beneficiaryStatus)
			return false;
		return true;
	}

}

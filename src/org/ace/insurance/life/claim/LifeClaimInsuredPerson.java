package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IEntity;
import org.ace.insurance.life.policy.PolicyInsuredPerson;

@Entity
@NamedQueries(value = { @NamedQuery(name = "ClaimInsuredPerson.findAll", query = "SELECT c FROM LifeClaimInsuredPerson c "),
		@NamedQuery(name = "ClaimBeneficiary.updateApprovalStatus", query = "UPDATE LifeClaimInsuredPerson c SET c.approved = :approvalStatus, c.needSomeDocument = :needSomeDocument WHERE c.id = :id"),
		@NamedQuery(name = "ClaimBeneficiary.updateBeneficiaryStatus", query = "UPDATE LifeClaimInsuredPersonBeneficiary c SET c.beneficiaryStatus = :beneficiaryStatus WHERE c.id = :id"),
		@NamedQuery(name = "ClaimInsuredPerson.findByPolicyInsuredPersonId", query = "SELECT c FROM LifeClaimInsuredPerson c WHERE c.policyInsuredPerson.id = :id") })
@DiscriminatorValue(value = LifeClaimBeneficiaryRole.INSUREDPERSON)
public class LifeClaimInsuredPerson extends LifeClaimBeneficiary implements Serializable, IEntity {

	private static final long serialVersionUID = 9166872259841064295L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMINSUREDPERSON_CLAIMID", referencedColumnName = "ID")
	private LifeClaim claimInsuredPersonLinkClaim;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeClaimInsuredPerson", orphanRemoval = true)
	private List<LifeClaimInsuredPersonAttachment> claimInsuredPersonAttachmentList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "claimInsuredPerson", orphanRemoval = true)
	private List<LifeClaimInsuredPersonBeneficiary> claimInsuredPersonBeneficiaryList;

	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public LifeClaimInsuredPerson() {

	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson claimInsuredPerson) {
		this.policyInsuredPerson = claimInsuredPerson;
	}

	public LifeClaim getClaimInsuredPersonLinkClaim() {
		return claimInsuredPersonLinkClaim;
	}

	public void setClaimInsuredPersonLinkClaim(LifeClaim claimInsuredPersonLinkClaim) {
		this.claimInsuredPersonLinkClaim = claimInsuredPersonLinkClaim;
	}

	public List<LifeClaimInsuredPersonAttachment> getClaimInsuredPersonAttachmentList() {
		return claimInsuredPersonAttachmentList;
	}

	public void setClaimInsuredPersonAttachmentList(List<LifeClaimInsuredPersonAttachment> claimInsuredPersonAttachmentList) {
		this.claimInsuredPersonAttachmentList = claimInsuredPersonAttachmentList;
	}

	public List<LifeClaimInsuredPersonBeneficiary> getClaimInsuredPersonBeneficiaryList() {
		return claimInsuredPersonBeneficiaryList;
	}

	public void setClaimInsuredPersonBeneficiaryList(List<LifeClaimInsuredPersonBeneficiary> claimInsuredPersonBeneficiaryList) {
		this.claimInsuredPersonBeneficiaryList = claimInsuredPersonBeneficiaryList;
	}

	@Override
	public String getFullName() {
		// string test = policyInsuredPerson.setFatherName("MgMg");
		return this.policyInsuredPerson.getFullName();
	}

	@Override
	public String getFullResidentAddress() {
		return this.policyInsuredPerson.getResidentAddress().getFullResidentAddress();
	}

	@Override
	public String getIdNo() {
		return this.policyInsuredPerson.getIdNo();
	}

	@Override
	public String getFatherName() {
		return this.policyInsuredPerson.getFatherName();
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public void addClaimInsuredPersonAttachment(LifeClaimInsuredPersonAttachment claimInsuredPersonAttachment) {
		if (this.claimInsuredPersonAttachmentList == null) {
			this.claimInsuredPersonAttachmentList = new ArrayList<LifeClaimInsuredPersonAttachment>();
		}
		claimInsuredPersonAttachment.setLifeClaimInsuredPerson(this);
		claimInsuredPersonAttachmentList.add(claimInsuredPersonAttachment);
	}

	public void addClaimInsuredPersonBeneficiary(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		if (this.claimInsuredPersonBeneficiaryList == null) {
			this.claimInsuredPersonBeneficiaryList = new ArrayList<LifeClaimInsuredPersonBeneficiary>();
		}
		claimInsuredPersonBeneficiary.setClaimInsuredPerson(this);

		boolean newEntity = true;

		if (claimInsuredPersonBeneficiary.getId() != null) {
			for (LifeClaimInsuredPersonBeneficiary cipb : claimInsuredPersonBeneficiaryList) {
				if (cipb.getId() != null) {
					if (cipb.getId().equals(claimInsuredPersonBeneficiary.getId())) {
						cipb.update(claimInsuredPersonBeneficiary);
						newEntity = false;
					}
				}
			}
		}

		if (newEntity) {
			claimInsuredPersonBeneficiaryList.add(claimInsuredPersonBeneficiary);
		}
	}

	public void update(LifeClaimInsuredPerson claimInsuredPerson) {
		this.claimInsuredPersonBeneficiaryList = claimInsuredPerson.getClaimInsuredPersonBeneficiaryList();
		super.update(claimInsuredPerson);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + version;
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
		LifeClaimInsuredPerson other = (LifeClaimInsuredPerson) obj;
		if (version != other.version)
			return false;
		return true;
	}
}

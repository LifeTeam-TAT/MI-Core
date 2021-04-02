package org.ace.insurance.web.manage.life.claim.request;

import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiaryAttachment;
import org.ace.insurance.life.policy.BeneficiaryStatus;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
public class DeathClaimInsuredPersonBeneficiaryDTO {
	private String id;
	private int version;
	private PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries;
	private BeneficiaryStatus beneficiaryStatus;
	private ClaimStatus claimStatus;
	private boolean claimBeneficiary;
	private LifeClaimInsuredPersonBeneficiaryAttachment claimInsuredPersonBeficiaryAttachmentList;

	public DeathClaimInsuredPersonBeneficiaryDTO() {

	}

	public DeathClaimInsuredPersonBeneficiaryDTO(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		this.id = claimInsuredPersonBeneficiary.getId();
		this.version = claimInsuredPersonBeneficiary.getVersion();
		this.claimStatus = claimInsuredPersonBeneficiary.getClaimStatus();
		this.policyInsuredPersonBeneficiaries = claimInsuredPersonBeneficiary.getPolicyInsuredPersonBeneficiaries();
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

	public boolean isClaimBeneficiary() {
		return claimBeneficiary;
	}

	public void setClaimBeneficiary(boolean claimBeneficiary) {
		this.claimBeneficiary = claimBeneficiary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public LifeClaimInsuredPersonBeneficiaryAttachment getClaimInsuredPersonBeficiaryAttachmentList() {
		return claimInsuredPersonBeficiaryAttachmentList;
	}

	public void setClaimInsuredPersonBeficiaryAttachmentList(LifeClaimInsuredPersonBeneficiaryAttachment claimInsuredPersonBeficiaryAttachmentList) {
		this.claimInsuredPersonBeficiaryAttachmentList = claimInsuredPersonBeficiaryAttachmentList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((beneficiaryStatus == null) ? 0 : beneficiaryStatus
						.hashCode());
		result = prime * result + (claimBeneficiary ? 1231 : 1237);
		result = prime
				* result
				+ ((claimInsuredPersonBeficiaryAttachmentList == null) ? 0
						: claimInsuredPersonBeficiaryAttachmentList.hashCode());
		result = prime * result
				+ ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((policyInsuredPersonBeneficiaries == null) ? 0
						: policyInsuredPersonBeneficiaries.hashCode());
		result = prime * result + version;
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
		DeathClaimInsuredPersonBeneficiaryDTO other = (DeathClaimInsuredPersonBeneficiaryDTO) obj;
		if (beneficiaryStatus != other.beneficiaryStatus)
			return false;
		if (claimBeneficiary != other.claimBeneficiary)
			return false;
		if (claimInsuredPersonBeficiaryAttachmentList == null) {
			if (other.claimInsuredPersonBeficiaryAttachmentList != null)
				return false;
		} else if (!claimInsuredPersonBeficiaryAttachmentList
				.equals(other.claimInsuredPersonBeficiaryAttachmentList))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (policyInsuredPersonBeneficiaries == null) {
			if (other.policyInsuredPersonBeneficiaries != null)
				return false;
		} else if (!policyInsuredPersonBeneficiaries
				.equals(other.policyInsuredPersonBeneficiaries))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}

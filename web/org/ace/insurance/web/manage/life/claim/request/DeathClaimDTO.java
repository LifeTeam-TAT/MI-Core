package org.ace.insurance.web.manage.life.claim.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.relationship.RelationShip;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
public class DeathClaimDTO implements Serializable {
	private static final long serialVersionUID = 8803328692241604918L;

	private String policyNo;
	private LifePolicy lifePolicy;
	private DeathClaimInsuredPersonDTO claimInsuredPersonInfoDTO;
	private List<DeathClaimInsuredPersonBeneficiaryDTO> claimInsuredPersonBeneficiaryInfoList;
	private Branch branch;
	private Date submittedDate;
	/* successor */
	private String successorId;
	private String customerName;
	private String NRCNo;
	private RelationShip relationShip;

	public DeathClaimDTO() {
		this.submittedDate = new Date();
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNRCNo() {
		return NRCNo;
	}

	public void setNRCNo(String NRCNo) {
		this.NRCNo = NRCNo;
	}

	public RelationShip getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public DeathClaimInsuredPersonDTO getClaimInsuredPersonInfoDTO() {
		return claimInsuredPersonInfoDTO;
	}

	public void setClaimInsuredPersonInfoDTO(DeathClaimInsuredPersonDTO claimInsuredPersonInfoDTO) {
		this.claimInsuredPersonInfoDTO = claimInsuredPersonInfoDTO;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<DeathClaimInsuredPersonBeneficiaryDTO> getClaimInsuredPersonBeneficiaryInfoList() {
		if (claimInsuredPersonBeneficiaryInfoList == null) {
			claimInsuredPersonBeneficiaryInfoList = new ArrayList<DeathClaimInsuredPersonBeneficiaryDTO>();
		}
		return claimInsuredPersonBeneficiaryInfoList;
	}

	public void setClaimInsuredPersonBeneficiaryInfoList(List<DeathClaimInsuredPersonBeneficiaryDTO> claimInsuredPersonBeneficiaryInfoList) {
		this.claimInsuredPersonBeneficiaryInfoList = claimInsuredPersonBeneficiaryInfoList;
	}

	public String getSuccessorId() {
		return successorId;
	}

	public void setSuccessorId(String successorId) {
		this.successorId = successorId;
	}

	public boolean hasSuccessor() {
		return this.customerName != null && this.NRCNo != null && this.relationShip != null;
	}

	public boolean isNewSuccessor() {
		return successorId == null;
	}

	public String getClaimAttachmentRootPath() {
		return this.policyNo.replace("/", "");
	}

	public String getClaimInsuredPersonAttachmentRootPath() {
		return this.getClaimAttachmentRootPath() + "/" + this.claimInsuredPersonInfoDTO.getPolicyInsuredPerson().getId().replace("/", "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NRCNo == null) ? 0 : NRCNo.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((claimInsuredPersonInfoDTO == null) ? 0 : claimInsuredPersonInfoDTO.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((lifePolicy == null) ? 0 : lifePolicy.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((relationShip == null) ? 0 : relationShip.hashCode());
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		result = prime * result + ((successorId == null) ? 0 : successorId.hashCode());
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
		DeathClaimDTO other = (DeathClaimDTO) obj;
		if (NRCNo == null) {
			if (other.NRCNo != null)
				return false;
		} else if (!NRCNo.equals(other.NRCNo))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (claimInsuredPersonInfoDTO == null) {
			if (other.claimInsuredPersonInfoDTO != null)
				return false;
		} else if (!claimInsuredPersonInfoDTO.equals(other.claimInsuredPersonInfoDTO))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (lifePolicy == null) {
			if (other.lifePolicy != null)
				return false;
		} else if (!lifePolicy.equals(other.lifePolicy))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (relationShip == null) {
			if (other.relationShip != null)
				return false;
		} else if (!relationShip.equals(other.relationShip))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (successorId == null) {
			if (other.successorId != null)
				return false;
		} else if (!successorId.equals(other.successorId))
			return false;
		return true;
	}

}

package org.ace.insurance.web.manage.life.claim.request;

import java.util.Date;
import java.util.List;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.system.common.branch.Branch;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
public class DisabilityClaimDTO {
	private String policyNo;
	private Branch branch;
	private Date submittedDate;
	private LifePolicy lifePolicy;
	private DisabilityClaimInsuredPersonDTO disabilityClaimInsuredPersonDTO;
	private List<DisabilityClaimInsuredPersonDTO> disabilityClaimInsuredPersonDTOList;

	public DisabilityClaimDTO() {
		submittedDate = new Date();
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public DisabilityClaimInsuredPersonDTO getDisabilityClaimInsuredPersonDTO() {
		return disabilityClaimInsuredPersonDTO;
	}

	public void setDisabilityClaimInsuredPersonDTO(DisabilityClaimInsuredPersonDTO disabilityClaimInsuredPersonDTO) {
		this.disabilityClaimInsuredPersonDTO = disabilityClaimInsuredPersonDTO;
	}

	public List<DisabilityClaimInsuredPersonDTO> getDisabilityClaimInsuredPersonDTOList() {
		return disabilityClaimInsuredPersonDTOList;
	}

	public void setDisabilityClaimInsuredPersonDTOList(List<DisabilityClaimInsuredPersonDTO> disabilityClaimInsuredPersonDTOList) {
		this.disabilityClaimInsuredPersonDTOList = disabilityClaimInsuredPersonDTOList;
	}

	public String getClaimAttachmentRootPath() {
		return lifePolicy.getId();
	}

	public String getClaimInsuredPersonAttachmentRootPath() {
		return this.getClaimAttachmentRootPath() + "/" + getDisabilityClaimInsuredPersonDTO().getPolicyInsuredPerson().getId();
	}
}

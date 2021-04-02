package org.ace.insurance.web.manage.life.claim.request;

import java.util.Date;

import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.policy.PolicyInsuredPerson;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
public class DisabilityClaimInsuredPersonDTO {

	private String tempId;
	private PolicyInsuredPerson policyInsuredPerson;
	private DisabilityClaimDTO disabilityClaimDTO;
	private Date disabilityDate;
	private String disabilityReason;
	private ClaimStatus claimStatus;

	public DisabilityClaimInsuredPersonDTO() {
		tempId = System.nanoTime() + "";
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public DisabilityClaimDTO getDisabilityClaimDTO() {
		return disabilityClaimDTO;
	}

	public void setDisabilityClaimDTO(DisabilityClaimDTO disabilityClaimDTO) {
		this.disabilityClaimDTO = disabilityClaimDTO;
	}

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

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

}

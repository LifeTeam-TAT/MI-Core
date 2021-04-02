package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.List;

import org.ace.insurance.common.ClassificationOfHealth;

public class MedicalClaimInsuredPersonDTO {
	private String id;
	private boolean exitsEntity;
	private MedicalPolicyInsuredPersonDTO policyInsuredPersonDto;
	private List<MedClaimInsuAttDTO> claimInsuredPersonAttachmentList;
	private int version;
	private ClassificationOfHealth clsOfHealth;
	private boolean approved;
	private boolean needSomeDocument;
	private String rejectedReason;

	public MedicalClaimInsuredPersonDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isExitsEntity() {
		return exitsEntity;
	}

	public void setExitsEntity(boolean exitsEntity) {
		this.exitsEntity = exitsEntity;
	}

	public MedicalPolicyInsuredPersonDTO getPolicyInsuredPersonDto() {
		return policyInsuredPersonDto;
	}

	public void setPolicyInsuredPersonDto(MedicalPolicyInsuredPersonDTO policyInsuredPersonDto) {
		this.policyInsuredPersonDto = policyInsuredPersonDto;
	}

	public List<MedClaimInsuAttDTO> getClaimInsuredPersonAttachmentList() {
		if (claimInsuredPersonAttachmentList == null) {
			claimInsuredPersonAttachmentList = new ArrayList<MedClaimInsuAttDTO>();
		}
		return claimInsuredPersonAttachmentList;
	}

	public List<MedicalPolicyInsuredPersonDTO> getPolicyInsuredPersonList() {
		List<MedicalPolicyInsuredPersonDTO> mList = new ArrayList<MedicalPolicyInsuredPersonDTO>();
		mList.add(policyInsuredPersonDto);
		return mList;

	}

	public void setClaimInsuredPersonAttachmentList(List<MedClaimInsuAttDTO> claimInsuredPersonAttachmentList) {
		this.claimInsuredPersonAttachmentList = claimInsuredPersonAttachmentList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void addClaimInsuredPersonAttachment(MedClaimInsuAttDTO medClaimInsuAttDTO) {
		if (this.claimInsuredPersonAttachmentList == null) {
			this.claimInsuredPersonAttachmentList = new ArrayList<MedClaimInsuAttDTO>();
		}
		medClaimInsuAttDTO.setMedicalClaimInsuredPersonDTO(this);
		claimInsuredPersonAttachmentList.add(medClaimInsuAttDTO);
	}

	public ClassificationOfHealth getClsOfHealth() {
		return clsOfHealth;
	}

	public void setClsOfHealth(ClassificationOfHealth clsOfHealth) {
		this.clsOfHealth = clsOfHealth;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isNeedSomeDocument() {
		return needSomeDocument;
	}

	public void setNeedSomeDocument(boolean needSomeDocument) {
		this.needSomeDocument = needSomeDocument;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + ((clsOfHealth == null) ? 0 : clsOfHealth.hashCode());
		result = prime * result + (exitsEntity ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (needSomeDocument ? 1231 : 1237);
		result = prime * result + ((policyInsuredPersonDto == null) ? 0 : policyInsuredPersonDto.hashCode());
		result = prime * result + ((rejectedReason == null) ? 0 : rejectedReason.hashCode());
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
		MedicalClaimInsuredPersonDTO other = (MedicalClaimInsuredPersonDTO) obj;
		if (approved != other.approved)
			return false;
		if (clsOfHealth != other.clsOfHealth)
			return false;
		if (exitsEntity != other.exitsEntity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (needSomeDocument != other.needSomeDocument)
			return false;
		if (policyInsuredPersonDto == null) {
			if (other.policyInsuredPersonDto != null)
				return false;
		} else if (!policyInsuredPersonDto.equals(other.policyInsuredPersonDto))
			return false;
		if (rejectedReason == null) {
			if (other.rejectedReason != null)
				return false;
		} else if (!rejectedReason.equals(other.rejectedReason))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}

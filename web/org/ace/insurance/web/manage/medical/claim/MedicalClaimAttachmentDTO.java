package org.ace.insurance.web.manage.medical.claim;

import org.ace.insurance.web.common.CommonDTO;

public class MedicalClaimAttachmentDTO extends CommonDTO {
	private boolean exitsEntity;
	private String id;
	private String name;
	private String filePath;
	private int version;
	private MedicalClaimDTO medicalClaimDTO;

	public MedicalClaimAttachmentDTO() {
	}

	public MedicalClaimAttachmentDTO(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}

	public boolean isExitsEntity() {
		return exitsEntity;
	}

	public void setExitsEntity(boolean exitsEntity) {
		this.exitsEntity = exitsEntity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public MedicalClaimDTO getMedicalClaimDTO() {
		return medicalClaimDTO;
	}

	public void setMedicalClaimDTO(MedicalClaimDTO medicalClaimDTO) {
		this.medicalClaimDTO = medicalClaimDTO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (exitsEntity ? 1231 : 1237);
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicalClaimDTO == null) ? 0 : medicalClaimDTO.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MedicalClaimAttachmentDTO other = (MedicalClaimAttachmentDTO) obj;
		if (exitsEntity != other.exitsEntity)
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalClaimDTO == null) {
			if (other.medicalClaimDTO != null)
				return false;
		} else if (!medicalClaimDTO.equals(other.medicalClaimDTO))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}

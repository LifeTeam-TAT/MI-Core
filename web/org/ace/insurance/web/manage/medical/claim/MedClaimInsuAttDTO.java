package org.ace.insurance.web.manage.medical.claim;

public class MedClaimInsuAttDTO {
	private boolean existsEntity;
	private String id;
	private String name;
	private String filePath;
	private int version;
	private MedicalClaimInsuredPersonDTO medicalClaimInsuredPersonDTO;
	
	public MedClaimInsuAttDTO() {
	}
	
	public MedClaimInsuAttDTO(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}

	public boolean isExistsEntity() {
		return existsEntity;
	}

	public void setExistsEntity(boolean existsEntity) {
		this.existsEntity = existsEntity;
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

	public MedicalClaimInsuredPersonDTO getMedicalClaimInsuredPersonDTO() {
		return medicalClaimInsuredPersonDTO;
	}

	public void setMedicalClaimInsuredPersonDTO(
			MedicalClaimInsuredPersonDTO medicalClaimInsuredPersonDTO) {
		this.medicalClaimInsuredPersonDTO = medicalClaimInsuredPersonDTO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (existsEntity ? 1231 : 1237);
		result = prime * result
				+ ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((medicalClaimInsuredPersonDTO == null) ? 0
						: medicalClaimInsuredPersonDTO.hashCode());
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
		MedClaimInsuAttDTO other = (MedClaimInsuAttDTO) obj;
		if (existsEntity != other.existsEntity)
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
		if (medicalClaimInsuredPersonDTO == null) {
			if (other.medicalClaimInsuredPersonDTO != null)
				return false;
		} else if (!medicalClaimInsuredPersonDTO
				.equals(other.medicalClaimInsuredPersonDTO))
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
